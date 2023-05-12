package com.napas.achoffline.reportoffline.controllers;


import com.napas.achoffline.reportoffline.models.TblRptoLadderValueLevelDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblRptoLadderValueLevelService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

import static com.napas.achoffline.reportoffline.service.LogInterceptor.checkSignature;

@RestController
@RequestMapping("/api/TblRptoLadderValueLevel")
public class TblRptoLadderValueLevelController {

    @Autowired
    private TblRptoLadderValueLevelService tblRptoLadderValueLevelService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public TblRptoLadderValueLevelDTO get(@PathVariable int id) {
        return tblRptoLadderValueLevelService.get(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> put(HttpServletRequest request,@PathVariable int id, @RequestBody TblRptoLadderValueLevelDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        if (checkSignature(request)){
            return tblRptoLadderValueLevelService.put(id, input);
        } else return ResponseEntity.ok("Sai chữ ký");
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> post(HttpServletRequest request,@RequestBody TblRptoLadderValueLevelDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        if (checkSignature(request)){
            return tblRptoLadderValueLevelService.post(input);
        } else return ResponseEntity.ok("Sai chữ ký");
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> delete(HttpServletRequest request,@PathVariable int id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblRptoLadderValueLevelService.delete(id);
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public List<TblRptoLadderValueLevelDTO> find(HttpServletRequest request,@RequestParam(name = "ladderId") Long ladderId) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblRptoLadderValueLevelService.find(ladderId);
    }
}
