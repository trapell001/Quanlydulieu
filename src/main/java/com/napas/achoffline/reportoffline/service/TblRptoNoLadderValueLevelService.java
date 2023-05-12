package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRptoNoLadderValueLevel;
import com.napas.achoffline.reportoffline.models.TblRptoNoLadderValueLevelDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblRptoNoLadderValueLevelDAO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TblRptoNoLadderValueLevelService {

    @Autowired
    private TblRptoNoLadderValueLevelDAO tblRptoNoLadderValueLevelDAO;

    @Autowired
    private ModelMapper mapper;

    private boolean isNumTransRangeOverlap(TblRptoNoLadderValueLevel entity, boolean check) {
        List<TblRptoNoLadderValueLevel> list = tblRptoNoLadderValueLevelDAO.findAll();
        if(list.size() == 0)
            return true;
        if(check) {
            List<TblRptoNoLadderValueLevel> newList = list.stream().filter(item ->
                    item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax()).collect(Collectors.toList());
            if(newList.size() == list().size())
                return true;
        }
        if(!check) {
            List<TblRptoNoLadderValueLevel> newList = list.stream().filter(item -> (item.getId() != entity.getId()) &&
                    (item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax())).collect(Collectors.toList());
            if(newList.size() == list().size() - 1)
                return true;
        }
        return false;
    }
    private TblRptoNoLadderValueLevelDTO fromEntity(TblRptoNoLadderValueLevel entity) {
        TblRptoNoLadderValueLevelDTO dto = mapper.map(entity, TblRptoNoLadderValueLevelDTO.class);
        return dto;
    }
    private TblRptoNoLadderValueLevel fromDTO(TblRptoNoLadderValueLevelDTO dto) {
        TblRptoNoLadderValueLevel entity = mapper.map(dto, TblRptoNoLadderValueLevel.class);
        return entity;
    }

    public ResponseEntity<?> delete(Integer id){
        tblRptoNoLadderValueLevelDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa cấu hình phí thành công"));
    }
    public ResponseEntity<?> post(TblRptoNoLadderValueLevelDTO input) {
        TblRptoNoLadderValueLevel entity = fromDTO(input);
        if (input.getValueRangeMin() > input.getValueRangeMax()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max nhỏ hơn min, không thỏa mãn"));
        }
        log.info("A,B: {}, {}",input.getValueRangeMax(),input.getValueRangeMin());
        if (input.getValueRangeMax().toString().equals(input.getValueRangeMin().toString())) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max bằng giá trị min, không thỏa mãn"));
        }
        entity.setValueRangeMax(input.getValueRangeMax());
        entity.setValueRangeMin(input.getValueRangeMin());
        if (isNumTransRangeOverlap(entity, true)) {
            tblRptoNoLadderValueLevelDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        }
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(new MessageResponse("Khoảng tiền không bậc thang bị trùng với khoảng tiền đã cài đặt trước đó"));
    }
    public ResponseEntity<?> put(int id, TblRptoNoLadderValueLevelDTO input) {

        TblRptoNoLadderValueLevel entity = fromDTO(input);//
        entity.setId(id)
        ;
        if (input.getValueRangeMin() > input.getValueRangeMax()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max nhỏ hơn min, không thỏa mãn"));
        }
        if (input.getValueRangeMax().toString().equals(input.getValueRangeMin().toString())) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max bằng giá trị min, không thỏa mãn"));
        }
        if (isNumTransRangeOverlap(entity, false)) {
            entity.setValueRangeMax(input.getValueRangeMax());
            entity.setValueRangeMin(input.getValueRangeMin());
            tblRptoNoLadderValueLevelDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        }
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(new MessageResponse("Khoảng tiền không bậc thang bị trùng với khoảng tiền đã cài đặt trước đó"));
    }
    public TblRptoNoLadderValueLevelDTO get(int id) {
        return fromEntity(tblRptoNoLadderValueLevelDAO.findById(id).orElse(null));
    }
    public List<TblRptoNoLadderValueLevelDTO> list() {
        List<TblRptoNoLadderValueLevel> dbResult = tblRptoNoLadderValueLevelDAO.findAll();
        dbResult = dbResult.stream().sorted(Comparator.comparingLong(TblRptoNoLadderValueLevel::getValueRangeMin))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
