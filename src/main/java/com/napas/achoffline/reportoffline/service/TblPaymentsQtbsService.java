/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.define.FundTransferSystem;
import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import com.napas.achoffline.reportoffline.define.QtbsPaymentType;
import com.napas.achoffline.reportoffline.entity.AchSessions;
import com.napas.achoffline.reportoffline.entity.Payments;
import com.napas.achoffline.reportoffline.entity.TblPaymentsQtbs;
import com.napas.achoffline.reportoffline.models.TblPaymentsQtbsDTO;
import com.napas.achoffline.reportoffline.payload.request.ReviewRequest;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.PaymentsDAO;
import com.napas.achoffline.reportoffline.repository.TblPaymentsQtbsDAO;
import com.napas.achoffline.reportoffline.utils.ParticipantCoupleUtil;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class TblPaymentsQtbsService {

    @Autowired
    private TblPaymentsQtbsDAO paymentQtbsDAO;

    @Autowired
    private AchSessionService achSessionService;

    @Autowired
    private PaymentsDAO paymentDAO;

    @Autowired
    private ModelMapper mapper;

    private TblPaymentsQtbsDTO fromEntity(TblPaymentsQtbs entity) {
        TblPaymentsQtbsDTO dto = mapper.map(entity, TblPaymentsQtbsDTO.class);
        Payments p = paymentDAO.findByTxid(entity.getTxid());
        if (p != null) {
            dto.setOrigAuthInfo(p.getAuthInfo());
            dto.setOrigTransStatus(p.getTransStatus());
            dto.setOrigInstructingAgent(p.getInstructingAgent());
            dto.setOrigInstructedAgent(p.getInstructedAgent());
            dto.setOrigSettlementAmount(p.getSettlementAmount());
            dto.setOrigSessionId(p.getSessionId());
            dto.setOrigProcessDate(p.getProcessDate());
            dto.setOrigTransDate(new Date(p.getTransDate().toInstant().toEpochMilli()));
            dto.setOrigMsgType(p.getType());
        }
        return dto;
    }

    private ResponseEntity isValidForQtbs(TblPaymentsQtbsDTO input) {
        Payments p = paymentDAO.findByTxid(input.getTxid());
        if (p == null) {
            //Không tìm thấy giao dịch gốc
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Không tìm thấy giao dịch gốc"));
        }

        QtbsPaymentType qtbsType = input.getMsgType();
        if (qtbsType == QtbsPaymentType.PAYMENT) {
            if (!p.getTransStatus().contentEquals("Rejected")) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new MessageResponse("Không cho phép tạo QTBS payment với giao dịch đã quyết toán"));
            }
        }

        if (qtbsType == QtbsPaymentType.RETURN) {
            if (p.getTransStatus().contentEquals("Rejected")) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new MessageResponse("Không cho phép tạo QTBS return với giao dịch chưa quyết toán"));
            }

            //So sánh giá trị giao dịch QTBS và giao dịch gốc
            if (input.getReturnAmount().compareTo(p.getSettlementAmount()) > 0) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new MessageResponse("Giá trị giao dịch hoàn trả không được lớn hơn giá trị giao dịch gốc"));
            }
        }

        if (qtbsType == QtbsPaymentType.VOID) {
            if (p.getTransStatus().contentEquals("Rejected")) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new MessageResponse("Không cho phép tạo QTBS hủy với giao dịch chưa quyết toán"));
            }
        }

        return ResponseEntity.ok(new MessageResponse("Đầu vào ok"));
    }

    public Page<TblPaymentsQtbsDTO> find(
            Integer pageIndex,
            Integer pageSize,
            Date dateFrom,
            Date dateTo,
            String txid,
            Long sessionFrom,
            Long sessionTo,
            String debitBank,
            String creditBank,
            QtbsPaymentType paymentType,
            QtbsPaymentStatus status
    ) {
        Pageable pageable = null;

        Sort sort = Sort.by("dateCreated").descending();

        if (pageSize == -1) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageIndex, pageSize, sort);
        }
        
        //Fund System
        ParticipantCoupleUtil.ParticipantCouple participantSearch = ParticipantCoupleUtil.calculateParticipantCoupleRegex(debitBank, FundTransferSystem.BOTH, creditBank, FundTransferSystem.BOTH);

        List<TblPaymentsQtbsDTO> dbResult = null;
        List<TblPaymentsQtbsDTO> filteredResult = null;

        try {
            dbResult = paymentQtbsDAO.searchPayments(
                    dateFrom,
                    dateTo,
                    txid,
                    sessionFrom,
                    sessionTo,
                    paymentType,
                    status);
            
            for(TblPaymentsQtbsDTO pq : dbResult) {
                Payments p = paymentDAO.findByTxid(pq.getTxid());
                if(p != null) {
                    pq.setOrigAuthInfo(p.getAuthInfo());
                    pq.setOrigTransStatus(p.getTransStatus());
                    pq.setOrigTransDate(Date.from(p.getTransDate().toInstant()));
                    pq.setOrigProcessDate(p.getProcessDate());
                    pq.setOrigMsgType(p.getType());
                    pq.setOrigSessionId(p.getSessionId());
                    pq.setOrigSettlementAmount(p.getSettlementAmount());
                    pq.setOrigInstructingAgent(p.getDebtorAgent());
                    pq.setOrigInstructedAgent(p.getCreditorAgent());
                }
            }
            
            filteredResult = dbResult.stream()
                    .filter(p -> p.getOrigInstructingAgent() != null && p.getOrigInstructedAgent() != null && p.getOrigInstructingAgent().matches(participantSearch.debtorAgent) 
                            && p.getOrigInstructedAgent().matches(participantSearch.creditorAgent))
                    .collect(Collectors.toList());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        int start = (int) pageable.getOffset();
        int end = (int) ((start + pageable.getPageSize()) > filteredResult.size() ? filteredResult.size() : (start + pageable.getPageSize()));

        Page<TblPaymentsQtbsDTO> pagedResult
                = new PageImpl<TblPaymentsQtbsDTO>(filteredResult.subList(start, end), pageable, filteredResult.size());
        
        return pagedResult;

//        if(txid != null && txid.length() > 0) {
//            dbResult = dbResult.stream().filter(p -> p.getTxid().contains(txid)).collect(Collectors.toList());
//        }
//        
//        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblPaymentsQtbsDTO get(Integer id) {
        return fromEntity(paymentQtbsDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> applyReview(Integer id, ReviewRequest input) {
        String userChecker = SecurityContextHolder.getContext().getAuthentication().getName();

        if (input.getStatus() == QtbsPaymentStatus.CREATED) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new MessageResponse("Không chấp nhận trạng thái này"));
        }

        TblPaymentsQtbs currentEntity = paymentQtbsDAO.findById(id).orElse(null);

        if (currentEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Không tìm thấy giao dịch cần review"));
        }

        if (currentEntity.getStatus() == QtbsPaymentStatus.CREATED) {
            currentEntity.setStatus(input.getStatus());
            currentEntity.setUserChecker(userChecker);
            currentEntity.setDateApproved(new Date());

            if (input.getStatus() == QtbsPaymentStatus.APPROVED) {
                AchSessions currentSession = achSessionService.findCurrentSession(3L);
                if (currentSession == null) {
                    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                            .body(new MessageResponse("Không lấy được phiên giao dịch hiện tại"));
                }

                if (currentEntity.getSessionId() == null) {
                    currentEntity.setSessionId(currentSession.getId());
                }
            }

            currentEntity = paymentQtbsDAO.save(currentEntity);
            return ResponseEntity.ok(fromEntity(currentEntity));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse("Không cho phép review lại giao dịch đã review"));
        }
    }

    public ResponseEntity<?> put(Integer id, TblPaymentsQtbsDTO input) {
        String userMaker = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity checkStatus = isValidForQtbs(input);
        if (checkStatus.getStatusCode() != HttpStatus.OK) {
            return checkStatus;
        }

        TblPaymentsQtbs currentEntity = paymentQtbsDAO.findById(id).orElse(null);

        //Chỉ giao dịch nào chưa duyệt mới cho phép sửa
        if (currentEntity.getStatus() == QtbsPaymentStatus.CREATED) {
            currentEntity.setTxid(input.getTxid());
            currentEntity.setDescription(input.getDescription());
            currentEntity.setMsgType(input.getMsgType());
            currentEntity.setUserMaker(userMaker);

            //Chỉ giao dịch Return mới cho phép đặt giá trị giao dịch
            if (input.getMsgType() == QtbsPaymentType.RETURN) {
                currentEntity.setReturnAmount(input.getReturnAmount());
            }

            currentEntity = paymentQtbsDAO.save(currentEntity);
            return ResponseEntity.ok(fromEntity(currentEntity));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse("Không cho phép sửa giao dịch đã review"));
        }

    }

    public ResponseEntity<?> post(TblPaymentsQtbsDTO input) {
        String userMaker = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity checkStatus = isValidForQtbs(input);
        if (checkStatus.getStatusCode() != HttpStatus.OK) {
            return checkStatus;
        }

        TblPaymentsQtbs entity = new TblPaymentsQtbs();
        entity.setDescription(input.getDescription());
        entity.setMsgType(input.getMsgType());
        entity.setTxid(input.getTxid());

        //Chỉ giao dịch Return mới cho phép đặt giá trị giao dịch
        if (input.getMsgType() == QtbsPaymentType.RETURN) {
            entity.setReturnAmount(input.getReturnAmount());
        }

        entity.setStatus(QtbsPaymentStatus.CREATED);
        entity.setUserMaker(userMaker);
        entity.setDateCreated(new Date());

        //Khi tạo thì không cho phép đặt session
        entity.setSessionId(null);

        entity = paymentQtbsDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(Integer id) {
        paymentQtbsDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa giao dịch thành công"));
    }
}
