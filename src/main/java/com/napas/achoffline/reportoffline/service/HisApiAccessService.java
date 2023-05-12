/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisApiAccess;
import com.napas.achoffline.reportoffline.models.HisApiAccessDTO;
import com.napas.achoffline.reportoffline.repository.HisApiAccessDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author huynx
 */
@Service
public class HisApiAccessService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private HisApiAccessDAO accessDAO;

    @Autowired
    private ModelMapper mapper;

    private HisApiAccessDTO fromEntity(HisApiAccess entity) {
        HisApiAccessDTO dto = mapper.map(entity, HisApiAccessDTO.class);

        String apiParam = entity.getApiParam();

        if (apiParam != null) {
            dto.setApiParam(apiParam.replace("&", "\n"));
        }
        return dto;
    }

    @Async("accessLogAsyncExecutor")
    public void logAccess(String method, String remoteAddr, String username, String requestUri, String requestParam,
                          String addInfo) {
        logger.info("{} request from:{} user:{} to:{} param:{} addInfo:{}",
                method, remoteAddr, username, requestUri, requestParam, addInfo);

        HisApiAccess access = new HisApiAccess();
        access.setApiMethod(method);
        access.setRemoteAddr(remoteAddr);
        access.setUsername(username);
        access.setApiUri(requestUri.substring(0, Math.min(requestUri.length(), 254)));
        access.setAddInfo(addInfo);
        if (requestParam != null) {
            access.setApiParam(requestParam.substring(0, Math.min(requestParam.length(), 999)));
        }
        access.setDateAccess(new Date());
        accessDAO.save(access);
    }

    public Page<HisApiAccessDTO> searchPaging(String username, String method, String beginDate, String endDate, int page, int pageSize) throws ParseException {

        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date beginDateSearch = searchDateFmt.parse(beginDate);
        Date endDateSearch = searchDateFmt.parse(endDate);

        Pageable sortedById = PageRequest.of(page, pageSize, Sort.by("DATE_ACCESS").descending());
        Page<HisApiAccess> listUser = accessDAO.find(sortedById,
                beginDateSearch, endDateSearch, username, method);

        Page<HisApiAccessDTO> output = listUser.map(entity -> fromEntity(entity));

        return output;
    }
}
