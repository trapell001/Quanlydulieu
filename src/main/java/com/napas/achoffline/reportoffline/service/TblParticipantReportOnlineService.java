package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblParticipantReportOnline;
import com.napas.achoffline.reportoffline.models.TblParticipantReportOnlineDTO;
import com.napas.achoffline.reportoffline.repository.TblParticipantReportOnlineDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TblParticipantReportOnlineService {

    @Autowired
    private TblParticipantReportOnlineDAO tblParticipantReportOnlineDAO;

    @Autowired
    private ModelMapper mapper;

    private TblParticipantReportOnlineDTO fromEntity(TblParticipantReportOnline entity) {
        TblParticipantReportOnlineDTO dto = mapper.map(entity, TblParticipantReportOnlineDTO.class);
        return dto;
    }

    public TblParticipantReportOnlineDTO get(Long id){
        return fromEntity(tblParticipantReportOnlineDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> post(TblParticipantReportOnlineDTO input){

        TblParticipantReportOnline participantReportOnline = new TblParticipantReportOnline();

        participantReportOnline.setParticipantBic(input.getParticipantBic());
        participantReportOnline.setEndPoint(input.getEndPoint());
        participantReportOnline.setCamt998Online(input.isCamt998Online());
        participantReportOnline.setCamt998Offline(input.isCamt998Offline());
        participantReportOnline.setCamt052Online(input.isCamt052Online());
        participantReportOnline.setCamt052Offline(input.isCamt052Offline());
        participantReportOnline.setCamt053Online(input.isCamt053Online());
        participantReportOnline.setCamt053Offline(input.isCamt053Offline());

        TblParticipantReportOnline savedData = tblParticipantReportOnlineDAO.save(participantReportOnline);
        return ResponseEntity.ok(fromEntity(savedData));
    }
    public ResponseEntity<?> put(Long id, TblParticipantReportOnlineDTO input){

        TblParticipantReportOnline participantReportOnline = tblParticipantReportOnlineDAO.findById(id).orElse(null);

        participantReportOnline.setEndPoint(input.getEndPoint());
        participantReportOnline.setCamt998Online(input.isCamt998Online());
        participantReportOnline.setCamt998Offline(input.isCamt998Offline());
        participantReportOnline.setCamt052Online(input.isCamt052Online());
        participantReportOnline.setCamt052Offline(input.isCamt052Offline());
        participantReportOnline.setCamt053Online(input.isCamt053Online());
        participantReportOnline.setCamt053Offline(input.isCamt053Offline());

        TblParticipantReportOnline savedData = tblParticipantReportOnlineDAO.save(participantReportOnline);
        return ResponseEntity.ok(fromEntity(savedData));
    }

    public ResponseEntity<?> delete(Long id){
        tblParticipantReportOnlineDAO.deleteById(id);
        return ResponseEntity.ok("Delete Suscess");
    }
    public ResponseEntity<?>find(Integer page, Integer pageSize, String participantBic){

        Pageable sortedById = null;

        Sort sort = Sort.by("participantBic").ascending();

        if (pageSize == -1) {
            sortedById = Pageable.unpaged();
        } else {
            sortedById = PageRequest.of(page, pageSize, sort);
        }
        Page<TblParticipantReportOnline> dbResult = tblParticipantReportOnlineDAO.findParticipant(participantBic, sortedById);
        return ResponseEntity.ok(dbResult.map(entity -> fromEntity(entity)));
    }
}
