package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.*;
import com.napas.achoffline.reportoffline.models.DisputeHistoryDTO;
import com.napas.achoffline.reportoffline.models.PaymentHistoryDTO;
import com.napas.achoffline.reportoffline.repository.DisputeDAO;
import com.napas.achoffline.reportoffline.repository.HisBatchInstrDAO;
import com.napas.achoffline.reportoffline.repository.DisputeHistoryDAO;
import com.napas.achoffline.reportoffline.repository.PaymentsDAO;
import com.napas.achoffline.reportoffline.repository.HisBatchPaymentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisputeHistoryService {

    @Autowired
    private DisputeDAO disputeDAO;

    @Autowired
    private PaymentsDAO paymentsDAO;
    @Autowired
    private DisputeHistoryDAO disputeHistoryDAO;

    @Autowired
    private HisBatchInstrDAO hisBatchInstrDAO;

    @Autowired
    private HisBatchPaymentsDAO hisBatchPaymentsDAO;

    public ResponseEntity<?> getDisputeHistory(String reference) {
        DisputeCase disputeCase = disputeDAO.findByReference(reference);

        List<DisputeHistory> disputeHistories = disputeHistoryDAO.findByDispId(disputeCase.getDispId());

        List<DisputeHistoryDTO> disputeHistoryDTOS = new ArrayList<>();
        for(DisputeHistory disputeHistory : disputeHistories) {
            DisputeHistoryDTO item = new DisputeHistoryDTO();
            item.setDispId(disputeCase.getDispId());
            item.setStatusCode(disputeHistory.getStatusCode());
            item.setStatusDescription(disputeHistory.getStatusDescription());
            item.setPreviousStatusCode(disputeHistory.getPreviousStatusCode());
            item.setPreviousStatusDescription(disputeHistory.getPreviousStatusDescription());
            item.setMsgText(disputeHistory.getMsgText());
            item.setModifDate(disputeHistory.getModifDate());
            item.setResponseTime(disputeCase.getResponseTime());
            item.setAtchNum(disputeHistory.getAtchNum());
            item.setMDate(disputeHistory.getMDate());
            disputeHistoryDTOS.add(item);
        }
        disputeHistoryDTOS = disputeHistoryDTOS.stream().sorted(Comparator.comparing(DisputeHistoryDTO::getModifDate).reversed()).collect(Collectors.toList());
        return ResponseEntity.ok(disputeHistoryDTOS);
    }

    public ResponseEntity<?> getPaymentsHistory(String reference) {
        DisputeCase disputeCase = disputeDAO.findByReference(reference);

        Payments payments = paymentsDAO.findByTxid(disputeCase.getTxid());
        List<PaymentHistoryDTO> paymentHistoryDTOS = new ArrayList<>();
        if(payments != null) {
            PaymentHistoryDTO p = new PaymentHistoryDTO();
            p.setMsgId(payments.getMsgID());
            p.setTxid(payments.getTxid());
            p.setMxType(payments.getType());
            p.setProcessDate(payments.getProcessDate());
            p.setModifDate(payments.getModifDate());
            p.setInstructingAgent(payments.getInstructingAgent());
            p.setInstructedAgent(payments.getInstructedAgent());
            p.setAmount(payments.getSettlementAmount());
            p.setChannel(payments.getChannelId());
            p.setTransStatus(payments.getTransStatus());
            p.setMxAuthInfo(payments.getAuthInfo());
            paymentHistoryDTOS.add(p);
            List<Payments> list = paymentsDAO.findByMxOrigTxid(payments.getTxid());
            if(list != null) {
                for(Payments payments1 : list) {
                    PaymentHistoryDTO p1 = new PaymentHistoryDTO();
                    p1.setMsgId(payments1.getMsgID());
                    p1.setTxid(payments1.getTxid());
                    p1.setMxType(payments1.getType());
                    p1.setProcessDate(payments1.getProcessDate());
                    p1.setModifDate(payments1.getModifDate());
                    p1.setInstructingAgent(payments1.getInstructingAgent());
                    p1.setInstructedAgent(payments1.getInstructedAgent());
                    p1.setAmount(payments1.getSettlementAmount());
                    p1.setChannel(payments1.getChannelId());
                    p1.setTransStatus(payments1.getTransStatus());
                    p1.setMxAuthInfo(payments1.getAuthInfo());
                    paymentHistoryDTOS.add(p1);
                }
            }
        }
        if(paymentHistoryDTOS.size() == 0) {
            HisBatchInstr h = hisBatchInstrDAO.findByTxid(disputeCase.getTxid());
            if(h != null) {
                PaymentHistoryDTO p = new PaymentHistoryDTO();
                HisBatchPayments h1 = hisBatchPaymentsDAO.findByADocId(h.getDocId());
                p.setMsgId(h1.getMxMsgid());
                p.setTxid(h.getMxTxid());
                p.setMxType(h1.getMxType());
                p.setProcessDate(h.getProcessDate());
                p.setModifDate(h.getModifDate());
                p.setInstructingAgent(h.getMxInstructingAgent());
                p.setInstructedAgent(h.getMxInstructedAgent());
                p.setAmount(h.getMxSettlementAmount());
                p.setChannel(h.getIChannelId());
                p.setTransStatus(h.getStatus());
                paymentHistoryDTOS.add(p);
                List<HisBatchInstr> list = hisBatchInstrDAO.getByTxid(h.getMxTxid());
                if(list != null) {
                    for(HisBatchInstr h2: list) {
                        PaymentHistoryDTO p1 = new PaymentHistoryDTO();
                        HisBatchPayments h3 = hisBatchPaymentsDAO.findByADocId(h2.getDocId());
                        p1.setMsgId(h3.getMxMsgid());
                        p1.setTxid(h2.getMxTxid());
                        p1.setMxType(h3.getMxType());
                        p1.setProcessDate(h2.getProcessDate());
                        p1.setModifDate(h2.getModifDate());
                        p1.setInstructingAgent(h2.getMxInstructingAgent());
                        p1.setInstructedAgent(h2.getMxInstructedAgent());
                        p1.setAmount(h2.getMxSettlementAmount());
                        p1.setChannel(h2.getIChannelId());
                        p1.setTransStatus(h2.getStatus());
                        paymentHistoryDTOS.add(p1);
                    }
                }
            }
        }
        paymentHistoryDTOS = paymentHistoryDTOS.stream().sorted(Comparator.comparing(PaymentHistoryDTO::getProcessDate).reversed()).collect(Collectors.toList());
        return ResponseEntity.ok(paymentHistoryDTOS);
    }
}
