package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.ChannelType;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantDNSMultiRequestDTO;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantDNSMultiDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblTariffPlanParticipantDNSMultiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/TblTariffPlanParticipantDnsMulti")
public class TblTariffPlanParticipantDNSMultiController {
    @Autowired
    private TblTariffPlanParticipantDNSMultiService tblTariffPlanParticipantDNSMultiServiceService;

    @Autowired
    private HisApiAccessService hisAccessService;
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblTariffPlanParticipantDNSMultiDTO> list(
            @RequestParam (name = "tariffPlanId", required = false) Integer tariffPlanId,
            @RequestParam (name = "channelId",required = false) String channelId,
            @RequestParam (name = "channelType",required = false) ChannelType channelType,
            @RequestParam (name = "participant",required = false) String participant,
            HttpServletRequest request) {
        return tblTariffPlanParticipantDNSMultiServiceService.list(tariffPlanId,channelId,channelType,participant);
    }
    @GetMapping("/{tariffPlanId}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public TblTariffPlanParticipantDNSMultiRequestDTO get(HttpServletRequest request, @PathVariable Integer tariffPlanId) {
        return tblTariffPlanParticipantDNSMultiServiceService.get(tariffPlanId);
    }
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody TblTariffPlanParticipantDNSMultiDTO tblTariffPlanParticipantDNSMultiDTO) throws ParseException {
        return tblTariffPlanParticipantDNSMultiServiceService.post(tblTariffPlanParticipantDNSMultiDTO);
    }
    @PutMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> put(HttpServletRequest request, @RequestBody TblTariffPlanParticipantDNSMultiRequestDTO tblTariffPlanParticipantDNSMultiRequestDTO) throws ParseException {
        return tblTariffPlanParticipantDNSMultiServiceService.put(tblTariffPlanParticipantDNSMultiRequestDTO);
    }
    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> delete(HttpServletRequest request,
                                    @RequestParam (name = "tariffPlanId", required = false) Integer tariffPlanId,
                                    @RequestParam (name = "channelId",required = false) String channelId,
                                    @RequestParam (name = "channelType",required = false) ChannelType channelType) {
        return tblTariffPlanParticipantDNSMultiServiceService.delete(tariffPlanId,channelId,channelType);
    }
}
