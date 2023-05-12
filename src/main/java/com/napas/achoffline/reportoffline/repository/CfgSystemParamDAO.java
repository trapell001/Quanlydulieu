/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.CfgSystemParam;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HuyNX
 */
@Repository
public interface CfgSystemParamDAO extends JpaRepository<CfgSystemParam, Integer> {

    public Optional<CfgSystemParam> findByParamGroupAndName(String group, String name);

    public List<CfgSystemParam> findByParamGroup(Sort sort, String group);
}
