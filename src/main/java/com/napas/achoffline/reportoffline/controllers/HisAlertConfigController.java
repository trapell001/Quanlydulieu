package com.napas.achoffline.reportoffline.controllers;


import com.napas.achoffline.reportoffline.entity.HisAlertConfig;
import com.napas.achoffline.reportoffline.service.HisAlertConfigService;
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
@RequestMapping("/api/hisalertconfig")
public class HisAlertConfigController {
    @Autowired
    private HisAlertConfigService hisAlertConfigService;
    @Autowired
    private HisApiAccessService hisAccessService;
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @GetMapping()
    public Page<HisAlertConfig> searchPaging(
            @RequestParam(name = "page", required = false) String pageIndex,
            @RequestParam(name = "pagesize", required = false) String pageSize,
            @RequestParam(name = "alertCode", required = false) String alertCode,
            @RequestParam(name = "alertName", required = false) String alertName,
            HttpServletRequest request


    ) throws ParseException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisAlertConfigService.getAllElements( Integer.parseInt(pageIndex) -1, Integer.parseInt(pageSize),alertCode,alertName);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    public Optional<HisAlertConfig> get(@PathVariable Long id,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisAlertConfigService.get(id);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody HisAlertConfig hisAlertConfig,HttpServletRequest request) throws ParseException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        hisAlertConfig.setCreatedDate(simpleDateFormat.parse(String.valueOf(LocalDateTime.now())));
        hisAlertConfig.setModifiDate(simpleDateFormat.parse(String.valueOf(LocalDateTime.now())));
        System.out.println("haha");
        return hisAlertConfigService.insert(hisAlertConfig);

    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody HisAlertConfig hisAlertConfig,HttpServletRequest request) throws ParseException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        hisAlertConfig.setModifiDate(simpleDateFormat.parse(String.valueOf(LocalDateTime.now())));
        return hisAlertConfigService.update(id, hisAlertConfig);

    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisAlertConfigService.delete(id);
    }
}