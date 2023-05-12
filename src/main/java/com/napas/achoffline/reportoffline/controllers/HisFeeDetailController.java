package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.entity.HisFeeDetail;
import com.napas.achoffline.reportoffline.models.HisFeeDetailDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.HisFeeDetailService;
import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/HisFeeDetail")
public class HisFeeDetailController {

    @Autowired
    private HisFeeDetailService hisFeeDetailService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/graph")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> list(HttpServletRequest request, @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                                  @RequestParam(name = "sessionTo", required = false) Long sessionTo) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        return hisFeeDetailService.searchHisFeeDetail(sessionFrom,sessionTo);
    }
}
