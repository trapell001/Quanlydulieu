package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.ChannelType;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantNRTDTO;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantNRTRequestDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblTariffPlanParticipantMultiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/TblTariffPlanParticipantNrtMulti")
public class TblTariffPlanParticipantNRTMultiController {
    @Autowired
    private TblTariffPlanParticipantMultiService tblTariffPlanParticipantMultiService;

    @Autowired
    private HisApiAccessService hisAccessService;
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblTariffPlanParticipantNRTDTO> list(
            @RequestParam (name = "tariffPlanId", required = false) Integer tariffPlanId,
            @RequestParam (name = "channelId",required = false) String channelId,
            @RequestParam (name = "channelType",required = false) ChannelType channelType,
            @RequestParam (name = "participant",required = false) String participant,
            HttpServletRequest request) {
        return tblTariffPlanParticipantMultiService.list(tariffPlanId,channelId,channelType,participant);
    }
    @GetMapping("/{tariffPlanId}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public TblTariffPlanParticipantNRTRequestDTO get(HttpServletRequest request, @PathVariable Integer tariffPlanId) {
        return tblTariffPlanParticipantMultiService.get(tariffPlanId);
    }
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody TblTariffPlanParticipantNRTDTO tblTariffPlanParticipantNRTDTO) throws ParseException {
        return tblTariffPlanParticipantMultiService.post(tblTariffPlanParticipantNRTDTO);
    }
    @PutMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> put(HttpServletRequest request, @RequestBody TblTariffPlanParticipantNRTRequestDTO tblTariffPlanParticipantNRTRequestDTO) throws ParseException {
        return tblTariffPlanParticipantMultiService.put(tblTariffPlanParticipantNRTRequestDTO);
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
