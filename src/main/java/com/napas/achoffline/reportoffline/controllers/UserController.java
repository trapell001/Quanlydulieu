/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.payload.request.SaveMyPasswordRequest;
import com.napas.achoffline.reportoffline.payload.request.SavePasswordRequest;
import com.napas.achoffline.reportoffline.payload.request.SaveUserRolesRequest;
import com.napas.achoffline.reportoffline.payload.request.SignupRequest;
import com.napas.achoffline.reportoffline.models.UserDetail;
import com.napas.achoffline.reportoffline.payload.response.UserListPagingResponse;
import com.napas.achoffline.reportoffline.models.UserShortInfo;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author huynx
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserService userService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    @GetMapping()
    public List<UserShortInfo> list() {
        return userService.listUsers();
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    @GetMapping("/paging")
    public UserListPagingResponse listPaging(
            @RequestParam(name = "page", required = false) String pageIndex,
            @RequestParam(name = "pagesize", required = false) String pageSize) {
        return userService.listUsersPaging(Integer.parseInt(pageIndex) - 1, Integer.parseInt(pageSize));
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    @GetMapping("/{id}")
    public UserDetail get(@PathVariable String id) {
        return userService.get(Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest input) {
        return userService.createUser(input);
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    @PostMapping("/saveuserroles")
    public ResponseEntity<?> saveUserRoles(@Valid @RequestBody SaveUserRolesRequest input) {
        return userService.saveUserRoles(input);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        logger.info("Delete user:{}", id);
        return userService.delete(Long.parseLong(id));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

}
