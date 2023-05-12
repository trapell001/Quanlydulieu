/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.common.AppConstants;
import com.napas.achoffline.reportoffline.entity.Role;
import com.napas.achoffline.reportoffline.entity.User;
import com.napas.achoffline.reportoffline.payload.request.SaveUserRolesRequest;
import com.napas.achoffline.reportoffline.payload.request.SignupRequest;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.models.UserDetail;
import com.napas.achoffline.reportoffline.payload.response.UserListPagingResponse;
import com.napas.achoffline.reportoffline.models.UserShortInfo;
import com.napas.achoffline.reportoffline.repository.RoleRepository;
import com.napas.achoffline.reportoffline.repository.TblUserTokenRepository;
import com.napas.achoffline.reportoffline.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private TblUserTokenRepository tblUserTokenRepository;

    public ResponseEntity<?> createUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();

        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setFullName(signUpRequest.getFullName());
        user.setCreationDate(Timestamp.from(Instant.now()));
        user.setModifDate(Timestamp.from(Instant.now()));

        Set<Role> roles = new HashSet<>();

        signUpRequest.getRole().forEach((roleID) -> {
            try {
                Role role = roleRepository.getOne(roleID);
                roles.add(role);
            } catch (Exception ex) {
                logger.error("Loi role:{} ex:{}", roleID, ex.getMessage());
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> saveUserRoles(SaveUserRolesRequest req) {
        logger.info("Save user roles {}", req);
        User user = userRepository.findById(req.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User not found"));
        }

        if (user.getId() == AppConstants.ROOT_USER_ID) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: cannot modify root user roles"));
        }

        Set<Role> roles = new HashSet<>();

        req.getRoles().forEach((roleID) -> {
            try {
                Role role = roleRepository.getOne(roleID);
                roles.add(role);
            } catch (Exception ex) {
                logger.error("Loi role:{} ex:{}", roleID, ex.getMessage());
            }
        });

        user.setRoles(roles);
        user.setModifDate(Timestamp.from(Instant.now()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Update roles successfully"));
    }
    public List<UserShortInfo> listUsers() {
        List<UserShortInfo> listUserInfo = new ArrayList<>();
        List<User> listUser = userRepository.findAll();

        listUser.forEach((user) -> {
            listUserInfo.add(new UserShortInfo(user.getId(), user.getUsername(), user.getEmail(),
                    user.getCreationDate().toString(), user.getModifDate().toString(), user.getFullName()));
        });

        return listUserInfo;
    }

    public UserListPagingResponse listUsersPaging(int page, int pageSize) {
        UserListPagingResponse resp = new UserListPagingResponse();
        Pageable sortedById = PageRequest.of(page, pageSize, Sort.by("username").ascending());

        List<UserShortInfo> listUserInfo = new ArrayList<>();
        Page<User> listUser = userRepository.findAll(sortedById);

        listUser.forEach((user) -> {
            listUserInfo.add(new UserShortInfo(user.getId(), user.getUsername(), user.getEmail(),
                    user.getCreationDate().toString(), user.getModifDate().toString(), user.getFullName()));
        });

        resp.setListUsers(listUserInfo);
        resp.setTotalPages(listUser.getTotalPages());
        resp.setTotalRows(listUser.getTotalElements());
        return resp;
    }

    public UserDetail get(Long id) {
        User user = userRepository.getOne(id);
        return new UserDetail(user.getId(), user.getUsername(), user.getEmail(),
                user.getCreationDate().toString(), user.getModifDate().toString(), user.getRoles(), user.getFullName());
    }

    public UserDetail getByUsername(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow();
        return new UserDetail(user.getId(), user.getUsername(), user.getEmail(),
                user.getCreationDate().toString(), user.getModifDate().toString(), user.getRoles(), user.getFullName());
    }

    public ResponseEntity<?> delete(Long id) {
        logger.info("Delete user {}", id);
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User ot found"));
        }

        if (user.getId() == AppConstants.ROOT_USER_ID) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: cannot delete root user"));
        }

        userRepository.delete(user);
        return ResponseEntity.ok(new MessageResponse("Delete user " + user.getUsername() + " successfully"));
    }
}
