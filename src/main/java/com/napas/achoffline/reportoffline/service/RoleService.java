/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.Role;
import com.napas.achoffline.reportoffline.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> list() {
        return roleRepository.findAll();
    }
}
