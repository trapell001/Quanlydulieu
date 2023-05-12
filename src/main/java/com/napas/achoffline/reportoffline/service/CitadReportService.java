/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.napas.achoffline.reportoffline.service;

import com.google.common.collect.Lists;
import com.napas.achoffline.reportoffline.define.SpecialDayType;
import com.napas.achoffline.reportoffline.entity.AchSessions;
import com.napas.achoffline.reportoffline.entity.TblSpecialDay;
import com.napas.achoffline.reportoffline.models.TblSessions;
import com.napas.achoffline.reportoffline.models.WorkingDay;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.AchSessionsDAO;
import com.napas.achoffline.reportoffline.repository.CitadReportDAO;
import com.napas.achoffline.reportoffline.repository.DAOTblSpecialDay;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class CitadReportService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private CitadReportDAO citadReportDAO;
    
    @Autowired
    private DAOTblSpecialDay daoTblSpecialDay;
    
    @Autowired
    private AchSessionsDAO achSessionsDAO;

    public ResponseEntity<?> exportToCitad(String sessionId) {
        logger.info("Bat dau xuat citad cho session {}", sessionId);
        String result = citadReportDAO.exportToCitad(sessionId);
        logger.info("Ket qua xuat citad cho session {}, ket qua:{}", sessionId, result);

        String resultArray[] = result.split("\\|");

        String resultCode = resultArray[0];
        String resultDescription = resultArray[1];

        if (resultCode.contentEquals("0")) {
            return ResponseEntity.ok(new MessageResponse("Xuất báo cáo tới citad thành công"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Xuất báo cáo citad không thành công. Lỗi:" + resultDescription));
        }
    }
    
    public ResponseEntity<?> settleCurrentDay() {
        List<TblSessions> listSessions = getListSettleSession(3L);
        
        if(listSessions.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Hôm nay không phải là ngày quyết toán"));
        }
        
        Long fromSessionId = listSessions.get(0).getId();
        Long toSessionId = listSessions.get(listSessions.size() - 1).getId();
        logger.info("Bat dau xuat citad cho session {} den session {}", fromSessionId, toSessionId);
        String result = citadReportDAO.exportToCitadForSessionRange(String.valueOf(fromSessionId), String.valueOf(toSessionId));
        logger.info("Ket qua xuat citad cho session {}, ket qua:{}", fromSessionId, toSessionId, result);

        String resultArray[] = result.split("\\|");

        String resultCode = resultArray[0];
        String resultDescription = resultArray[1];

        if (resultCode.contentEquals("0")) {
            return ResponseEntity.ok(new MessageResponse("Xuất báo cáo tới citad thành công"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Xuất báo cáo citad không thành công. Lỗi:" + resultDescription));
        }
    }
    
    public List<TblSessions> getListSettleSession(Long sessionType) {
        List<TblSessions> listSettleSession = new ArrayList<>();
        final LocalDate checkEndDate = LocalDate.now();
        LocalDate checkBeginDate = checkEndDate.minusDays(30);
        
        Date dbCheckDate = Date.from(checkBeginDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        List<WorkingDay> listWorkingDays = new ArrayList<>();
        
        List<TblSpecialDay> listSpecialDay = daoTblSpecialDay.findAllByBeginDateAfter(dbCheckDate);
        
        for(LocalDate checkDate = checkBeginDate; checkDate.compareTo(checkEndDate) <= 0; checkDate = checkDate.plusDays(1)) {
            WorkingDay workingDay = new WorkingDay();
            workingDay.setDate(checkDate);
            
            Date dateCompare = Date.from(checkDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(checkDate.getDayOfWeek() == DayOfWeek.SATURDAY || checkDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                boolean swapDay = listSpecialDay.stream().anyMatch(d -> d.getDayType() == SpecialDayType.SWAP
                        && dateCompare.compareTo(d.getBeginDate()) >= 0
                        && dateCompare.compareTo(d.getEndDate()) <= 0);
                
                if(swapDay) {
                    workingDay.setWork(true);
                } else {
                    workingDay.setWork(false);
                }
            } else {
                boolean holiday = listSpecialDay.stream().anyMatch(d -> d.getDayType() == SpecialDayType.HOLIDAY
                        && dateCompare.compareTo(d.getBeginDate()) >= 0
                        && dateCompare.compareTo(d.getEndDate()) <= 0);

                if (holiday) {
                    workingDay.setWork(false);
                } else {
                    workingDay.setWork(true);
                }
            }
            
            listWorkingDays.add(workingDay);
        }
        
        WorkingDay todayWorkday = listWorkingDays.get(listWorkingDays.size() - 1);
        if(!todayWorkday.getWork()) {
            return listSettleSession;
        }
        
        List<WorkingDay> listWorkingDayWithoutToday = listWorkingDays.subList(0, listWorkingDays.size() - 1);
        final LocalDate lastWorkDay = Lists.reverse(listWorkingDayWithoutToday)
                .stream().filter(d -> d.getWork()).findFirst().orElse(null).getDate();
        
        
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<AchSessions> listSession = achSessionsDAO.findBySessionTypeIdAndStartTimeAfter(sort, sessionType, dbCheckDate);
        
        List<AchSessions> listSessionFiltered = listSession.stream()
                .filter(s -> s.getFinishTime() != null)
                .filter(s -> {
                    LocalDate sessionDate = s.getFinishTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return sessionDate.compareTo(lastWorkDay) >= 0 && sessionDate.isBefore(checkEndDate);
                }).toList();
        
        listSettleSession = listSessionFiltered.stream().map(s -> {
                    TblSessions tblSession = new TblSessions();
                    tblSession.setStartTime(s.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    tblSession.setFinishTime(s.getFinishTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    tblSession.setId(s.getId());
                    return tblSession;
                }).toList();
        
        return listSettleSession;
    }
    
    public ResponseEntity<?> checkSettleDay(Long sessionType) {
        List<TblSessions> listSettleSession = getListSettleSession(sessionType);
        if(listSettleSession.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Không phải ngày quyết toán"));
        } else {
            return ResponseEntity.ok(listSettleSession);
        }
    }
    
    
}
