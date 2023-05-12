package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisBilling;
import com.napas.achoffline.reportoffline.models.HisBillingDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.HisBillingDAO;
import com.napas.achoffline.reportoffline.utils.CalculateS;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HisBillingService {

    @Autowired
    private HisBillingDAO hisBillingDAO;

    @Autowired
    private ModelMapper mapper;

    private HisBillingDTO fromEntity(HisBilling entity) {
        HisBillingDTO dto = mapper.map(entity, HisBillingDTO.class);
        return dto;
    }
    private HisBilling fromDTO(HisBillingDTO dto) {
        HisBilling entity = mapper.map(dto, HisBilling.class);
        return entity;
    }

    public Page<HisBillingDTO> find(Long sessionFrom, Long sessionTo, Integer page, Integer pageSize) {

        Pageable sortedById = PageRequest.of(page, pageSize, Sort.by("dateCreate").descending());
        Page<HisBilling> pages =
                hisBillingDAO.searchHisBilling(sessionFrom, sessionTo, sortedById);

        return pages.map(entity -> fromEntity(entity));
    }

    public ResponseEntity<?> delete(Long id){
        hisBillingDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Lịch sử phí thành công"));
    }
    public ResponseEntity<?>post(HisBillingDTO input){

       HisBilling entity = fromDTO(input);

       entity.setDateCreate(new Date());
       entity.setDateBegin(input.getDateBegin());
       entity.setDateEnd(input.getDateEnd());
       entity.setSessionId(input.getSessionId());
       entity.setSlgd(input.getSlgd());
       hisBillingDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?>put(Long id, HisBillingDTO input){

        HisBilling entity = fromDTO(input);
        entity.setId(id);
        entity.setDateCreate(new Date());
        entity.setDateBegin(input.getDateBegin());
        entity.setDateEnd(input.getDateEnd());
        entity.setSessionId(input.getSessionId());
        entity.setSlgd(input.getSlgd());
        hisBillingDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public HisBillingDTO get(Long id) {
        return fromEntity(hisBillingDAO.findById(id).orElse(null));
    }

    public List<HisBillingDTO> list() {
        List<HisBilling> dbResult = hisBillingDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public ResponseEntity<?> graph(Long sessionFrom, Long sessionTo) {
        List<HisBilling> list = hisBillingDAO.graph(sessionFrom,sessionTo);
        List<HisBillingDTO> newList = new ArrayList<>();
        for(HisBilling item : list) {
            Long temp = (Long) (CalculateS.calculateDay(item.getDateBegin(),
                    item.getDateEnd()) * 100000);
            if(item.getSlgd().compareTo(0L) != 0) {
                temp = (Long) (temp / item.getSlgd());
                HisBillingDTO h = new HisBillingDTO();
                h.setProcessingTime(temp);
                h.setSession(item.getSessionId());
                newList.add(h);
            }
        }
        newList = newList.stream().sorted(Comparator.comparingLong(HisBillingDTO::getSession)).collect(Collectors.toList());
        return ResponseEntity.ok(newList);
    }
}
