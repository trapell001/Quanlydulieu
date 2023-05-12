package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.models.TblSnapshotTariffDTO;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffPlanDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblSnapshotTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/TblSnapshotTariff")
public class TblSnapshotTariffController {
    @Autowired
    private TblSnapshotTariffService tblSnapshotTariffService;

    @Autowired
    private HisApiAccessService hisAccessService;
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblSnapshotTariffDTO> find(HttpServletRequest request, @RequestParam(name = "tariffLadderId") Long tariffLadderId) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblSnapshotTariffService.find(tariffLadderId);
    }
}
