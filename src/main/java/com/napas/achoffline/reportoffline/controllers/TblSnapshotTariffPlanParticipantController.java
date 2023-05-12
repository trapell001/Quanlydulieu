package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.models.TblSnapshotTariffPlanParticipantDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblSnapshotTariffPlanParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/TblSnapshotTariffPlanParticipant")
public class TblSnapshotTariffPlanParticipantController {

    @Autowired
    private TblSnapshotTariffPlanParticipantService tblSnapshotTariffPlanParticipantService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblSnapshotTariffPlanParticipantDTO> find(HttpServletRequest request, @RequestParam(name = "tariffPlanId") Long tariffPlanId) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblSnapshotTariffPlanParticipantService.find(tariffPlanId);
    }
}
