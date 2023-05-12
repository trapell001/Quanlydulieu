package com.napas.achoffline.reportoffline.controllers;


import com.napas.achoffline.reportoffline.models.HisReportOnlineDTO;
import com.napas.achoffline.reportoffline.models.HisReportOnlineResendDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.HisReportOnlineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hisreportonline")
@Slf4j
public class HisReportOnlineController {

    @Autowired
    private HisReportOnlineService hisReportOnlineService;
    @Autowired
    private HisApiAccessService hisAccessService;
    @GetMapping(path = "/graphParticipant")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?>  graphParticipant(@RequestParam(name = "fromSessionId",defaultValue = "0",required = false) String fromSessionId,
                                           @RequestParam(name = "toSessionId", defaultValue = "9999999999",required = false) String toSessionId,
                                           @RequestParam(name = "systemModule", required = false) String systemModule,
                                           @RequestParam(name = "participant", required = false) String participant,
                                           @RequestParam(name = "deliveryMethod", required = false) String deliveryMethod,
                                           @RequestParam(name = "rptType", required = false) String rptType,
                                           HttpServletRequest request
    ) throws ParseException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisReportOnlineService.graphParticipant(fromSessionId,toSessionId,participant,systemModule,deliveryMethod,rptType);
    }

    @GetMapping(path = "/graph")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?>  graph(@RequestParam(name = "fromSessionId",defaultValue = "0",required = false) String fromSessionId,
                                    @RequestParam(name = "toSessionId", defaultValue = "9999999999",required = false) String toSessionId,
                                    @RequestParam(name = "systemModule", required = false) String systemModule,
                                    @RequestParam(name = "deliveryMethod", required = false) String deliveryMethod,
                                    @RequestParam(name = "rptType", required = false) String rptType,
                                    HttpServletRequest request
    ) throws ParseException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisReportOnlineService.graph(fromSessionId,toSessionId,systemModule,deliveryMethod,rptType);
    }
    @GetMapping(path = "/searchpaging")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?>  pageSearching(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate ,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name = "fromSessionId",defaultValue = "0",required = false) String fromSessionId,
            @RequestParam(name = "toSessionId", defaultValue = "9999999999",required = false) String toSessionId,
            @RequestParam(name = "rptType", required = false) String rptType,
            @RequestParam(name = "participant", required = false) String participant,
            @RequestParam(name = "systemModule", required = false) String systemModule,
            @RequestParam(name = "pageNumber", required = false) Long pageNumber,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "deliveryMethod", required = false) String deliveryMethod,
            @RequestParam(name = "msgId", required = false) String msgId,
            @RequestParam(name = "groupMsgId", required = false) String groupMsgId,
            @RequestParam(name = "fireType", required = false) String fireType,
            @RequestParam(name = "page",defaultValue = "1",required = false) String pageIndex,
            @RequestParam(name = "pagesize",defaultValue = "10",required = false) String pageSize,
            HttpServletRequest request
    ){

        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisReportOnlineService.pageSearching(
                startDate ,
                endDate,
                fromSessionId,
                toSessionId,
                rptType,
                participant,
                systemModule,
                pageNumber,
                status,
                deliveryMethod,
                msgId,
                groupMsgId,
                fireType,
                Integer.parseInt(pageIndex) - 1,
                Integer.parseInt(pageSize));
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public HisReportOnlineDTO get(@PathVariable Long id,HttpServletRequest request){
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisReportOnlineService.get(id);
    }
    @GetMapping(path = "/export/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public void export(HttpServletResponse response, @PathVariable Long id,HttpServletRequest request) throws IOException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        hisReportOnlineService.export(id,response);
    }
    @PostMapping(path = "/resend")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?> resendReport(@RequestBody HisReportOnlineResendDTO hisReportOnlineResendDTO, HttpServletRequest request) throws NoSuchAlgorithmException, IOException, InvalidKeyException, URISyntaxException, KeyStoreException, KeyManagementException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisReportOnlineService.sendRO(hisReportOnlineResendDTO);
    }
}
