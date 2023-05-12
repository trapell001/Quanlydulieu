package com.napas.achoffline.reportoffline.controllers;


import com.napas.achoffline.reportoffline.entity.HisAlertEmailConfig;
import com.napas.achoffline.reportoffline.service.HisAlertConfigService;
import com.napas.achoffline.reportoffline.service.HisAlertEmailConfigService;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hisalertemailconfig")
public class HisAlertEmailConfigController {
    @Autowired
    private HisAlertEmailConfigService hisAlertEmailConfigService;


    @Autowired
    private HisApiAccessService hisAccessService;
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @GetMapping()
    public Page<HisAlertEmailConfig> searchPaging(
            @RequestParam(name = "page",defaultValue = "1",required = false) String pageIndex,
            @RequestParam(name = "pagesize",defaultValue = "10",required = false) String pageSize,
            @RequestParam(name = "alertCode", required = false) String alertName,
            @RequestParam(name = "emailReceive", required = false) String emailReceive,
            @RequestParam(name = "participant", required = false) String participant,
            HttpServletRequest request

    ) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisAlertEmailConfigService.getAllElements( Integer.parseInt(pageIndex) -1, Integer.parseInt(pageSize),alertName,emailReceive,participant);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @GetMapping("/{id}")
    public Optional<HisAlertEmailConfig> get(@PathVariable  Long id,HttpServletRequest request){
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisAlertEmailConfigService.get(id);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody HisAlertEmailConfig hisAlertEmailConfig,HttpServletRequest request) throws ParseException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        hisAlertEmailConfig.setCreatedDate(simpleDateFormat.parse(String.valueOf(LocalDateTime.now())));
        hisAlertEmailConfig.setModifiDate(simpleDateFormat.parse(String.valueOf(LocalDateTime.now())));
        return hisAlertEmailConfigService.insert(hisAlertEmailConfig);

    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody HisAlertEmailConfig hisAlertEmailConfig,HttpServletRequest request) throws ParseException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        hisAlertEmailConfig.setModifiDate(simpleDateFormat.parse(String.valueOf(LocalDateTime.now())));
        return hisAlertEmailConfigService.update(id, hisAlertEmailConfig);

    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisAlertEmailConfigService.delete(id);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @GetMapping("/emailconfig")
    public ResponseEntity<?>  emailconfig(@RequestParam String email,HttpServletRequest request){
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisAlertEmailConfigService.saveEmail(email);
    }
}
