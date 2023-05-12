package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.models.TblSnapshotTariffLadderDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblSnapshotTariffLadderService;
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
@RequestMapping("/api/TblSnapshotTariffLadder")
public class TblSnapshotTariffLadderController {

    @Autowired
    private TblSnapshotTariffLadderService tblSnapshotTariffLadderService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblSnapshotTariffLadderDTO> find(HttpServletRequest request, @RequestParam(name = "tariffPlanId") Long tariffPlanId) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblSnapshotTariffLadderService.find(tariffPlanId);
    }
}
