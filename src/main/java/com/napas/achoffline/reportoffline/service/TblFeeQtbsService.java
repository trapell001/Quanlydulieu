/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import com.napas.achoffline.reportoffline.entity.AchSessions;
import com.napas.achoffline.reportoffline.entity.TblFeeQtbs;
import com.napas.achoffline.reportoffline.models.TblFeeQtbsDTO;
import com.napas.achoffline.reportoffline.payload.request.ReviewRequest;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblFeeQtbsDAO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class TblFeeQtbsService {

    @Autowired
    private TblFeeQtbsDAO feeQtbsDAO;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AchSessionService achSessionService;

    private TblFeeQtbsDTO fromEntity(TblFeeQtbs entity) {
        TblFeeQtbsDTO dto = mapper.map(entity, TblFeeQtbsDTO.class);
        return dto;
    }

    public TblFeeQtbsDTO rptFeeQtbsBySessionRange(String bic, long sessionFrom, long sessionTo) {
        return feeQtbsDAO.rptFeeQtbsBySessionRange(bic, sessionFrom, sessionTo);
    }

    public List<TblFeeQtbsDTO> list() {
        List<TblFeeQtbs> dbResult = feeQtbsDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public Page<TblFeeQtbsDTO> find(
            Integer pageIndex,
            Integer pageSize,
            Date dateFrom,
            Date dateTo,
            Long sessionFrom,
            Long sessionTo,
            String bank,
            QtbsPaymentStatus status
    ) {
        Pageable sortedById = null;

        Sort sort = Sort.by("dateCreated").descending();

        if (pageSize == -1) {
            sortedById = Pageable.unpaged();
        } else {
            sortedById = PageRequest.of(pageIndex, pageSize, sort);
        }

        Page<TblFeeQtbs> dbResult = feeQtbsDAO.searchPayments(
                sortedById,
                dateFrom,
                dateTo,
                sessionFrom,
                sessionTo,
                bank,
                status);
        return dbResult.map(entity -> fromEntity(entity));
    }

    public TblFeeQtbsDTO get(long id) {
        return fromEntity(feeQtbsDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(long id, TblFeeQtbsDTO input) {
        String userMaker = SecurityContextHolder.getContext().getAuthentication().getName();
        TblFeeQtbs currentEntity = feeQtbsDAO.findById(id).orElse(null);

        if (currentEntity.getStatus() == QtbsPaymentStatus.CREATED) {
            currentEntity.setDescription(input.getDescription());
            currentEntity.setUserMaker(userMaker);
            currentEntity.setBic(input.getBic());
            currentEntity.setCreditAmount(input.getCreditAmount());
            currentEntity.setDebitAmount(input.getDebitAmount());
            currentEntity.setDateCreated(new Date());

            currentEntity = feeQtbsDAO.save(currentEntity);

            return ResponseEntity.ok(fromEntity(currentEntity));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse("Không cho phép sửa giao dịch đã review"));
        }
    }

    public ResponseEntity<?> post(TblFeeQtbsDTO input) {
        String userMaker = SecurityContextHolder.getContext().getAuthentication().getName();

        TblFeeQtbs entity = new TblFeeQtbs();

        entity.setBic(input.getBic());
        entity.setCreditAmount(input.getCreditAmount());
        entity.setDebitAmount(input.getDebitAmount());
        entity.setDescription(input.getDescription());
        entity.setStatus(QtbsPaymentStatus.CREATED);
        entity.setUserMaker(userMaker);
        entity.setDateCreated(new Date());
        entity.setSessionId(null);

        entity = feeQtbsDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(long id) {
      feeQtbsDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa giao dịch thành công"));
    }



    public ResponseEntity<?> applyReview(Integer id, ReviewRequest input) {
        String userChecker = SecurityContextHolder.getContext().getAuthentication().getName();

        if (input.getStatus() == QtbsPaymentStatus.CREATED) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new MessageResponse("Không chấp nhận trạng thái này"));
        }

        TblFeeQtbs currentEntity = feeQtbsDAO.findById(Long.valueOf(id)).orElse(null);

        if (currentEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Không tìm thấy giao dịch cần review"));
        }

        if (currentEntity.getStatus() == QtbsPaymentStatus.CREATED) {
            currentEntity.setStatus(input.getStatus());
            currentEntity.setUserChecker(userChecker);
            currentEntity.setDateApproved(new Date());

            AchSessions currentSession = achSessionService.findCurrentSession(3L);
            if (currentSession == null) {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                        .body(new MessageResponse("Không lấy được phiên giao dịch hiện tại"));
            }
            currentEntity.setSessionId(currentSession.getId());

            currentEntity = feeQtbsDAO.save(currentEntity);
            return ResponseEntity.ok(fromEntity(currentEntity));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse("Không cho phép review lại giao dịch đã review"));
        }
    }
}
