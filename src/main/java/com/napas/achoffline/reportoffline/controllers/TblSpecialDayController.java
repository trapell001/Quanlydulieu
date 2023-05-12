package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import com.napas.achoffline.reportoffline.define.SpecialDayType;
import com.napas.achoffline.reportoffline.models.TblSpecialDayDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblSpecialDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestController
@CrossOrigin
@RequestMapping("/api/tblSpecialDay")
public class TblSpecialDayController {

    @Autowired
    private TblSpecialDayService tblSpecialDayService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?>find(
            @RequestParam(name = "page", required = false) Integer pageIndex,
            @RequestParam(name = "pagesize", required = false) Integer pageSize,
            @RequestParam(name = "dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @RequestParam(name = "dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
            @RequestParam(name = "dayType", required = false) SpecialDayType dayType
    ){
        return tblSpecialDayService.find(pageIndex, pageSize, dateFrom, dateTo, dayType);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public TblSpecialDayDTO get(HttpServletRequest request, @PathVariable int id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "", "");
        return tblSpecialDayService.get(id);
    }
    
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> post(HttpServletRequest request, @Valid @RequestBody TblSpecialDayDTO input){
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "",input.toString());
        return tblSpecialDayService.post(input);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> put(HttpServletRequest request, @PathVariable Integer id, @Valid @RequestBody TblSpecialDayDTO input){
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "",input.toString());
        return tblSpecialDayService.put(id, input);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Integer id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblSpecialDayService.delete(id);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public MessageResponse handleError(Exception ex) {
        return new MessageResponse(ex.getMessage());
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringBuilder listErrors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            listErrors.append(errorMessage + "; ");
        });
        return new MessageResponse(listErrors.toString());
    }
}
