package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.HisImportingService;
import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/HisImporting")
@Slf4j
public class HisImportingController {

    @Autowired
    private HisImportingService hisImportingService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/table")
    public ResponseEntity<?> table(HttpServletRequest request, @RequestParam(name = "dateFrom", required = false) String dateFrom,
                                   @RequestParam(name = "dateTo", required = false) String dateTo,
                                   @RequestParam(name = "typeOfState", required = false) String typeOfState,
                                   @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(name = "pagesize", required = false, defaultValue = "10") Integer pagesize) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date1 = null;
            Date date2 = null;
            if(!dateFrom.equals("")) {
                dateFrom = dateFrom.replace("T", " ");
                date1 = simpleDateFormat.parse(dateFrom);
            }
            if(!dateTo.equals("")) {
                dateTo = dateTo.replace("T", " ");
                date2 = simpleDateFormat.parse(dateTo);
            }
            return hisImportingService.table(date1,date2,typeOfState,page,pagesize);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/graph")
    public ResponseEntity<?> graph(@RequestParam(name = "dateFrom", required = false) String dateFrom,
                                   @RequestParam(name = "dateTo", required = false) String dateTo,
                                   @RequestParam(name = "typeOfState", required = false) String typeOfState) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date1 = null;
            Date date2 = null;
            if(!dateFrom.equals("")) {
                dateFrom = dateFrom.replace("T", " ");
                date1 = simpleDateFormat.parse(dateFrom);
            }
            if(!dateTo.equals("")) {
                dateTo = dateTo.replace("T", " ");
                date2 = simpleDateFormat.parse(dateTo);
            }
            return hisImportingService.graph(date1,date2,typeOfState);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
