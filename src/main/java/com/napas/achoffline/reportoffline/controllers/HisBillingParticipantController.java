package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.models.HisBillingParticipantDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.HisBillingParticipantService;
import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import static com.napas.achoffline.reportoffline.service.LogInterceptor.checkSignature;


@RestController
@RequestMapping("/api/HisBillingParticipant")
public class HisBillingParticipantController {

    @Autowired
    private HisBillingParticipantService hisBillingParticipantService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public HisBillingParticipantDTO get(HttpServletRequest request,@PathVariable Long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisBillingParticipantService.get(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> put(HttpServletRequest request,@PathVariable Long id, @RequestBody HisBillingParticipantDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "",input.toString());
        if (checkSignature(request)){
            return hisBillingParticipantService.put(id, input);
        } else return ResponseEntity.ok("Sai chữ ký");

    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody HisBillingParticipantDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "",input.toString());
        if (checkSignature(request)){
            return hisBillingParticipantService.post(input);
        } else return ResponseEntity.ok("Sai chữ ký");
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> delete(HttpServletRequest request,@PathVariable Long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisBillingParticipantService.delete(id);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    @GetMapping()
    public Page<HisBillingParticipantDTO> find(HttpServletRequest request,@RequestParam(name = "participantBic", required = false) String participantBic,
                                               @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                                               @RequestParam(name = "sessionTo", required = false) Long sessionTo,
                                               @RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "pagesize", required = false) Integer pageSize) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        return hisBillingParticipantService.find(participantBic, sessionFrom, sessionTo, page - 1, pageSize);
    }

    @GetMapping("/graph")
    public ResponseEntity<?> list(HttpServletRequest request,@RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                                  @RequestParam(name = "sessionTo", required = false) Long sessionTo,
                                  @RequestParam(name = "participantBic", required = false) String participantBic) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        return hisBillingParticipantService.graph(sessionFrom,sessionTo, participantBic);
    }
}
