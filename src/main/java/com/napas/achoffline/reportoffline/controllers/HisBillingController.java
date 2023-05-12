package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.models.HisBillingDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.HisBillingService;
import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import static com.napas.achoffline.reportoffline.service.LogInterceptor.checkSignature;

@RestController
@RequestMapping("/api/HisBilling")
@Slf4j
public class HisBillingController {

    @Autowired
    private HisBillingService hisBillingService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public HisBillingDTO get(HttpServletRequest request,@PathVariable Long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisBillingService.get(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> put(HttpServletRequest request,@PathVariable Long id, @RequestBody HisBillingDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "",input.toString());
        if (checkSignature(request)){
            return hisBillingService.put(id, input);
        } else return ResponseEntity.ok("Sai chữ ký");
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody HisBillingDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "",input.toString());
        if (checkSignature(request)){
            return hisBillingService.post(input);
        } else return ResponseEntity.ok("Sai chữ ký");
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> delete(HttpServletRequest request,@PathVariable Long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return hisBillingService.delete(id);
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    @GetMapping()
    public Page<HisBillingDTO> find(HttpServletRequest request, @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                                     @RequestParam(name = "sessionTo", required = false) Long sessionTo,
                                     @RequestParam(name = "page", required = false) Integer page,
                                     @RequestParam(name = "pagesize", required = false) Integer pageSize) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        return hisBillingService.find(sessionFrom, sessionTo,page -1 , pageSize);
    }

    @GetMapping("/graph")
    public ResponseEntity<?> list(HttpServletRequest request,@RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                                    @RequestParam(name = "sessionTo", required = false) Long sessionTo) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        return hisBillingService.graph(sessionFrom,sessionTo);
    }

}
