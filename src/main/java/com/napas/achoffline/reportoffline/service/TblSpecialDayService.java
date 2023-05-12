package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.define.SpecialDayType;
import com.napas.achoffline.reportoffline.entity.TblSpecialDay;
import com.napas.achoffline.reportoffline.models.TblSpecialDayDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblSpecialDayRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class TblSpecialDayService {

    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private TblSpecialDayRepository tblSpecialDayRepository;
    
    private TblSpecialDayDTO fromEntity(TblSpecialDay entity) {
        TblSpecialDayDTO dto = mapper.map(entity, TblSpecialDayDTO.class);
        return dto;
    }
    
    public TblSpecialDayDTO get(int id) {
        return fromEntity(tblSpecialDayRepository.findById(id).orElse(null));
    }

    public ResponseEntity<?> post(TblSpecialDayDTO input){
        try {
            preConditional(input);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse(ex.getMessage()));
        }
        
        if (!isRangeOverlap(input, true)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Khoảng thời gian bị trùng với một trong các khoảng thời gian đã cài đặt trước đó"));
        }
        
        String userMaker = SecurityContextHolder.getContext().getAuthentication().getName();
        TblSpecialDay tblSpecialDay = new TblSpecialDay();
        tblSpecialDay.setDayType(input.getDayType());
        tblSpecialDay.setBeginDate(input.getBeginDate());
        tblSpecialDay.setEndDate(input.getEndDate());
        tblSpecialDay.setCreateDate(new Date());
        tblSpecialDay.setCreateBy(userMaker);
        tblSpecialDay.setDescription(input.getDescription());
        TblSpecialDay savedData = tblSpecialDayRepository.save(tblSpecialDay);
        return ResponseEntity.ok(fromEntity(savedData));
    }
    public ResponseEntity<?> put( Integer id,TblSpecialDayDTO input){
        try {
            preConditional(input);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse(ex.getMessage()));
        }
        
        if (!isRangeOverlap(input, false)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Khoảng thời gian bị trùng với một trong các khoảng thời gian đã cài đặt trước đó"));
        }
        
        TblSpecialDay currentEntity = tblSpecialDayRepository.findById(id).orElse(null);

        currentEntity.setDayType(input.getDayType());
        currentEntity.setBeginDate(input.getBeginDate());
        currentEntity.setEndDate(input.getEndDate());
        currentEntity.setDescription(input.getDescription());
        TblSpecialDay savedData = tblSpecialDayRepository.save(currentEntity);
        return ResponseEntity.ok(fromEntity(savedData));
    }
    public ResponseEntity<?> delete(Integer id){
        tblSpecialDayRepository.deleteById(id);
        return ResponseEntity.ok("Delete Suscess");
    }
    public ResponseEntity<?> find(int page, int  pageSize, Date dateFrom, Date dateTo, SpecialDayType dayType){
        Pageable sortedById = null;

        Sort sort = Sort.by("beginDate").descending();

        if (pageSize == -1) {
            sortedById = Pageable.unpaged();
        } else {
            sortedById = PageRequest.of(page, pageSize, sort);
        }
        
        Page<TblSpecialDay> dbResult = tblSpecialDayRepository.findDays(sortedById, dateFrom, dateTo, dayType);
        return ResponseEntity.ok(dbResult.map(entity -> fromEntity(entity)));
    }
    
    private void preConditional(TblSpecialDayDTO input) throws Exception {
        LocalDate beginDate = input.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = input.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        if(beginDate.isAfter(endDate)) {
            throw new Exception("Ngày kết thúc không được phép nhỏ hơn ngày bắt đầu");
        }
        
        for(LocalDate checkDate = beginDate; checkDate.compareTo(endDate) <=0; checkDate = checkDate.plusDays(1)) {
            DayOfWeek checkDayOfWeek = checkDate.getDayOfWeek();
            if(input.getDayType() == SpecialDayType.HOLIDAY && 
                    (checkDayOfWeek == DayOfWeek.SATURDAY || checkDayOfWeek == DayOfWeek.SUNDAY)) {
                throw new Exception("Không thể tạo ngày nghỉ lễ mà bao gồm cả ngày thứ 7, chủ nhật");
            }
            
            if(input.getDayType() == SpecialDayType.SWAP && checkDayOfWeek != DayOfWeek.SATURDAY && checkDayOfWeek != DayOfWeek.SUNDAY) {
                throw new Exception("Không thể tạo ngày làm bù mà bao gồm cả ngày làm việc thông thường");
            }
        }
    }
    
    private boolean isRangeOverlap(TblSpecialDayDTO entity, boolean check) {
        List<TblSpecialDay> list = tblSpecialDayRepository.findAll();

        if (list.size() == 0) {
            return true;
        }
        
        if (check) {
            List<TblSpecialDay> newList = list.stream().filter(item
                    -> item.getEndDate().before(entity.getBeginDate())
                    || item.getBeginDate().after(entity.getEndDate())).collect(Collectors.toList());
            if (newList.size() == list.size()) {
                return true;
            }
        }
        
        if (!check) {
            List<TblSpecialDay> newList = list.stream().filter(item -> (item.getId() != entity.getId())
                    && (item.getEndDate().before(entity.getBeginDate())
                    || item.getBeginDate().after(entity.getEndDate()))).collect(Collectors.toList());
            if (newList.size() == list.size() - 1) {
                return true;
            }
        }
        return false;
    }
}
