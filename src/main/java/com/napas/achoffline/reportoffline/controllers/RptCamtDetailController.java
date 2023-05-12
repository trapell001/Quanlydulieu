package com.napas.achoffline.reportoffline.controllers;


import com.napas.achoffline.reportoffline.entity.RptCamtDetail;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.RptCamtDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/campach")
@Slf4j
public class RptCamtDetailController {

    @Autowired
    private RptCamtDetailService rptCamtDetailService;
    @Autowired
    private HisApiAccessService hisAccessService;
    @GetMapping(path = "/searchpaging")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public Page<RptCamtDetail> getCamAchList(
            @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
            @RequestParam(name = "sessionTo", required = false) Long sessionTo,
            @RequestParam(name = "txidReference", required = false) String txidReference,
            @RequestParam(name = "participantAgent", required = false) String participantAgent,
            @RequestParam(name = "msgType", required = false) String msgType,
            @RequestParam(name = "pageNumber", required = false) Long pageNumber,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "pagesize", required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request
    ){
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return rptCamtDetailService.getAllElements(sessionFrom,sessionTo,txidReference,participantAgent,msgType,pageNumber,page-1,pageSize);
    }
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public RptCamtDetail findMxTxid(
            @RequestParam(name = "mxTxid", required = false) String mxTxid,
            HttpServletRequest request
    ){
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return rptCamtDetailService.getCamAchByTxid(mxTxid);
    }
}