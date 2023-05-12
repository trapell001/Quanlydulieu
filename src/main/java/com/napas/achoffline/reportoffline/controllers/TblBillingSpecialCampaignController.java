package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.models.TblBillingSpecialCampaignDTO;
import com.napas.achoffline.reportoffline.models.TblPaymentsQtbsDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblBillingSpecialCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SignatureException;
import java.util.List;

import static com.napas.achoffline.reportoffline.service.LogInterceptor.checkSignature;

@RestController
@RequestMapping("/api/TblBillingSpecialCampaign")
public class TblBillingSpecialCampaignController {

    @Autowired
    private TblBillingSpecialCampaignService tblBillingSpecialCampaignService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblBillingSpecialCampaignDTO> list(HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblBillingSpecialCampaignService.list();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public TblBillingSpecialCampaignDTO get(HttpServletRequest request,@PathVariable Long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblBillingSpecialCampaignService.get(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> put(HttpServletRequest request1, @PathVariable Long id, @RequestBody TblBillingSpecialCampaignDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request1.getMethod(), request1.getRemoteAddr(),
                principal.getName(), request1.getRequestURI(), "",input.toString());
        if (checkSignature(request1)){
            return tblBillingSpecialCampaignService.put(id, input);
        } else return ResponseEntity.ok("Sai chữ ký");

    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> post(HttpServletRequest request1,@RequestBody TblBillingSpecialCampaignDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request1.getMethod(), request1.getRemoteAddr(),
                principal.getName(), request1.getRequestURI(), "",input.toString());
        if (checkSignature(request1)){
            return tblBillingSpecialCampaignService.post(input);
        } else return ResponseEntity.ok("Sai chữ ký");
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return tblBillingSpecialCampaignService.delete(id);
    }
}
