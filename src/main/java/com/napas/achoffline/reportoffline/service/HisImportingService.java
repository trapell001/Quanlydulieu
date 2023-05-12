package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisImporting;
import com.napas.achoffline.reportoffline.models.HisImportingDTO;
import com.napas.achoffline.reportoffline.repository.HisImportingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class HisImportingService {
    
    @Autowired
    private HisImportingDAO hisImportingDAO;

    private List<HisImportingDTO> processing(List<HisImporting> list) {
        List<HisImportingDTO> newList = new ArrayList<>();
        for(HisImporting item : list) {
            Timestamp ts1= (Timestamp) item.getDateBegin();
            Timestamp ts2= (Timestamp) item.getDateEnd();
            Date date1=ts1;
            Date date2=ts2;
            Long temp = (Long)TimeUnit.MILLISECONDS.toMillis(date2.getTime() - date1.getTime()) * 1000L;
            HisImportingDTO h = new HisImportingDTO();
            h.setDateCreate(item.getDateCreate());
            h.setSlgd(item.getSlgd());
            h.setProcessingTime(temp);
            if(item.getSlgd().compareTo(0L) != 0) {
                Double t;
                t = (Double) (temp / item.getSlgd().doubleValue());
                h.setProcessingTimeRatio((double) Math.round(t * 1000) / 1000);
                newList.add(h);
            } else if(item.getSlgd().compareTo(0L) == 0) {
                h.setProcessingTimeRatio(0.0);
                newList.add(h);
            }
        }
        return newList.stream().sorted(Comparator.comparing(HisImportingDTO::getDateCreate).reversed()).collect(Collectors.toList());
    }

    public ResponseEntity<?> table(Date dateFrom, Date dateTo, String typeOfState, Integer page, Integer pagesize) {
        Pageable sortedById = PageRequest.of(page -1, pagesize, Sort.by("dateCreate").descending());
        Page<HisImporting> list = hisImportingDAO.page(dateFrom, dateTo,typeOfState,sortedById);
        long size = list.getTotalElements();
        Page<HisImportingDTO> newPage = new PageImpl<>(processing(list.getContent()), sortedById,size);
        return ResponseEntity.ok(newPage);
    }

    public ResponseEntity<?> graph(Date dateFrom, Date dateTo, String typeOfState) {
        List<HisImporting> listHis = hisImportingDAO.list(dateFrom,dateTo,typeOfState);
        return ResponseEntity.ok(processing(listHis));
    }
}
