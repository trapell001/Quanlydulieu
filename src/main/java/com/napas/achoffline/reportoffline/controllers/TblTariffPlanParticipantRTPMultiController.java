package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.ChannelType;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantRTPDTO;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantRTPRequestDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblTariffPlanParticipantRTPMultiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/TblTariffPlanParticipantRtpMulti")
public class TblTariffPlanParticipantRTPMultiController {
    @Autowired
    private TblTariffPlanParticipantRTPMultiService tblTariffPlanParticipantMultiService;

    @Autowired
    private HisApiAccessService hisAccessService;
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblTariffPlanParticipantRTPDTO> list(
            @RequestParam (name = "tariffPlanId", required = false) Integer tariffPlanId,
            @RequestParam (name = "channelId",required = false) String channelId,
            @RequestParam (name = "channelType",required = false) ChannelType channelType,
            @RequestParam (name = "participant",required = false) String participant,
            HttpServletRequest request) {
        return tblTariffPlanParticipantMultiService.list(tariffPlanId,channelId,channelType,participant);
    }
    @GetMapping("/{tariffPlanId}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public TblTariffPlanParticipantRTPRequestDTO get(HttpServletRequest request, @PathVariable Integer tariffPlanId) {
        return tblTariffPlanParticipantMultiService.get(tariffPlanId);
    }
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody TblTariffPlanParticipantRTPDTO tblTariffPlanParticipantRTPDTO) throws ParseException {
        return tblTariffPlanParticipantMultiService.post(tblTariffPlanParticipantRTPDTO);
    }
    @PutMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> put(HttpServletRequest request, @RequestBody TblTariffPlanParticipantRTPRequestDTO tblTariffPlanParticipantRTPRequestDTO) throws ParseException {
        return tblTariffPlanParticipantMultiService.put(tblTariffPlanParticipantRTPRequestDTO);
    }
    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> delete(HttpServletRequest request,
                                    @RequestParam (name = "tariffPlanId", required = false) Integer tariffPlanId,
                                    @RequestParam (name = "channelId",required = false) String channelId,
                                    @RequestParam (name = "channelType",required = false) ChannelType channelType) {
        return tblTariffPlanParticipantMultiService.delete(tariffPlanId,channelId,channelType);
    }
}
