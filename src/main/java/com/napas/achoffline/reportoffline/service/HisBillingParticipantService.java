package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisBillingParticipant;
import com.napas.achoffline.reportoffline.models.HisBillingParticipantDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.HisBillingParticipantDAO;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HisBillingParticipantService {

    @Autowired
    private HisBillingParticipantDAO hisBillingParticipantDAO;

    @Autowired
    private ModelMapper mapper;

    private HisBillingParticipantDTO fromEntity(HisBillingParticipant entity) {
        HisBillingParticipantDTO dto = mapper.map(entity, HisBillingParticipantDTO.class);
        return dto;
    }
    private HisBillingParticipant fromDTO(HisBillingParticipantDTO dto) {
        HisBillingParticipant entity = mapper.map(dto, HisBillingParticipant.class);
        return entity;
    }
    public Page<HisBillingParticipantDTO> find(String participantBic,Long sessionFrom, Long sessionTo, Integer page, Integer pageSize) {

        Pageable sortedById = PageRequest.of(page, pageSize, Sort.by("dateCreated").descending());
        Page<HisBillingParticipant> pages =
                hisBillingParticipantDAO.searchHisBillingPanticipant(participantBic,sessionFrom, sessionTo, sortedById);

        return pages.map(entity -> fromEntity(entity));
    }
    public ResponseEntity<?> delete(Long id){
        hisBillingParticipantDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Lịch sử phí thành công"));
    }
    public ResponseEntity<?>post(HisBillingParticipantDTO input){

        HisBillingParticipant entity = fromDTO(input);

        entity.setSessionId(input.getSessionId());
        entity.setParticipantBic(input.getParticipantBic());
        entity.setChannelType(input.getChannelType());
        entity.setChannelId(input.getChannelId());
        entity.setTariffPlanId(input.getTariffPlanId());
        entity.setBillingDateBegin(input.getBillingDateBegin());
        entity.setBillingDateEnd(input.getBillingDateEnd());
        entity.setSlgd(input.getSlgd());
        hisBillingParticipantDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?>put(Long id, HisBillingParticipantDTO input){
        HisBillingParticipant entity = fromDTO(input);
        entity.setSessionId(input.getSessionId());
        entity.setParticipantBic(input.getParticipantBic());
        entity.setChannelType(input.getChannelType());
        entity.setChannelId(input.getChannelId());
        entity.setTariffPlanId(input.getTariffPlanId());
        entity.setBillingDateBegin(input.getBillingDateBegin());
        entity.setBillingDateEnd(input.getBillingDateEnd());
        entity.setSlgd(input.getSlgd());
        hisBillingParticipantDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public HisBillingParticipantDTO get(Long id) {
        return fromEntity(hisBillingParticipantDAO.findById(id).orElse(null));
    }

    public List<HisBillingParticipantDTO> list() {
        List<HisBillingParticipant> dbResult = hisBillingParticipantDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public ResponseEntity<?> graph(Long sessionFrom, Long sessionTo, String participantBic) {
        List<HisBillingParticipant> list = hisBillingParticipantDAO.graph(sessionFrom,sessionTo, participantBic);
        List<HisBillingParticipantDTO> newList = new ArrayList<>();
        for(HisBillingParticipant item : list) {
            Long temp = (Long) (CalculateS.calculateDay(item.getBillingDateBegin(),
                    item.getBillingDateEnd()) * 100000);
            if(item.getSlgd().compareTo(0L) != 0) {
                temp = (Long) (temp / item.getSlgd());
                HisBillingParticipantDTO h = new HisBillingParticipantDTO();
                h.setSession(item.getSessionId());
                h.setProcessingTime(temp);
                newList.add(h);
            }
        }
        newList = newList.stream().sorted(Comparator.comparingLong(HisBillingParticipantDTO::getSession)).collect(Collectors.toList());
        return ResponseEntity.ok(newList);
    }
}
