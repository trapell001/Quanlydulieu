package com.napas.achoffline.reportoffline.controllers;


import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.HisFeeDetailParticipantService;
import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/api/HisFeeDetailParticipant")
public class HisFeeDetailParticipantController {

    @Autowired
    private HisFeeDetailParticipantService hisFeeDetailParticipantService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/graph")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> list(HttpServletRequest request, @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                                  @RequestParam(name = "sessionTo", required = false) Long sessionTo,
                                  @RequestParam(name = "participantBic", required = false) String participantBic) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        return hisFeeDetailParticipantService.searchHisFeeDetailParticipant(sessionFrom,sessionTo, participantBic);
    }
}
