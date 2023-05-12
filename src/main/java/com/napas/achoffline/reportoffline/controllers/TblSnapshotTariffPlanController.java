package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffPlan;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffPlanDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblSnapshotTariffPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/TblSnapshotTariffPlan")
public class TblSnapshotTariffPlanController {

    @Autowired
    private TblSnapshotTariffPlanService tblSnapshotTariffPlanService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/{tariffPlanId}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public Optional<TblSnapshotTariffPlan> get(HttpServletRequest request, @PathVariable Long tariffPlanId) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblSnapshotTariffPlanService.find(tariffPlanId);
    }
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblSnapshotTariffPlanDTO> list(HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblSnapshotTariffPlanService.list();
    }
}
