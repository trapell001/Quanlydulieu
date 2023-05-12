package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.RptCamtDetail;
import com.napas.achoffline.reportoffline.repository.RptCamtDetailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RptCamtDetailService {

    @Autowired
    private RptCamtDetailDAO rptCamtDetailDAO;

    public Page<RptCamtDetail> getAllElements(Long sessionFrom, Long sessionTo, String reference, String participantAgent, String msgType, Long numberPage, Integer page, Integer pageSize){
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        Pageable sortedById = PageRequest.of(page, pageSize, sort);
        Page<RptCamtDetail> pages = rptCamtDetailDAO.getReportOnlineCampAchList(sessionFrom, sessionTo,reference,participantAgent,msgType,numberPage, sortedById);

        return pages;
    }
    public RptCamtDetail getCamAchByTxid(String txid){
        return rptCamtDetailDAO.findByMxTxid(txid);
    }

}