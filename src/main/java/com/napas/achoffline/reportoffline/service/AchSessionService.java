/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.AchSessions;
import com.napas.achoffline.reportoffline.models.SessionRange;
import com.napas.achoffline.reportoffline.models.TblSessions;
import com.napas.achoffline.reportoffline.repository.AchSessionsDAO;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class AchSessionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private AchSessionsDAO achSessionsDAO;

    public List<AchSessions> listSessions(Long sessionType) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<AchSessions> listSession = achSessionsDAO.findBySessionTypeId(sort, sessionType);
        return listSession;
    }

    public AchSessions findCurrentSession(Long sessionType) {
        List<AchSessions> listSessions = listSessions(sessionType);
        AchSessions result = listSessions.stream().filter(s -> s.getFinishTime() == null).findFirst().orElse(null);
        return result;
    }

    public TblSessions getTblSessionById(long sessionId) {
        AchSessions entity = achSessionsDAO.findById(sessionId).orElse(null);

        if (entity != null) {
            return transformAchSessionToTblSession(entity);
        } else {
            return null;
        }

    }

    public SessionRange getMonthSessionRangeBySession(TblSessions session) {
        //Thời gian bắt đầu của session đang tính toán
        LocalDateTime curSessionBeginTime = session.getStartTime();

        //Ngày đầu tiên của tháng của begin time của session
        LocalDateTime dayStartOfMonthOfCurSessionBeginTime = curSessionBeginTime.with(TemporalAdjusters.firstDayOfMonth())
                .truncatedTo(ChronoUnit.DAYS);

        //Xem ngày bắt đầu của session đang tính toán của phải là ngày cuối tháng không
        LocalDateTime compareDate = dayStartOfMonthOfCurSessionBeginTime.plusMonths(1).minusDays(1);

        //Thời gian bắt đầu của tháng đang tính toán
        LocalDateTime dayStartOfCalMonthTmp = null;

        //Nếu ngày bắt đầu của session là ngày cuối tháng thì tháng cần tính là tháng sau
        if (curSessionBeginTime.truncatedTo(ChronoUnit.DAYS).isEqual(compareDate)) {
            dayStartOfCalMonthTmp = dayStartOfMonthOfCurSessionBeginTime.plusMonths(1);
        } else {
            //Nếu ngày bắt đầu của session không phải là ngày cuối tháng thì tháng cần tính là tháng hiện tại của session
            dayStartOfCalMonthTmp = dayStartOfMonthOfCurSessionBeginTime;
        }

        return getMonthSessionRange(dayStartOfCalMonthTmp);
    }

    public SessionRange getMonthsessionRangeByMonth(YearMonth month) {
        return getMonthSessionRange(month.atEndOfMonth().plusDays(1).minusMonths(1).atStartOfDay());
    }

    private static TblSessions transformAchSessionToTblSession(AchSessions entity) {
        TblSessions s = new TblSessions();
        s.setId(entity.getId());
        s.setStartTime(entity.getStartTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        if (entity.getFinishTime() != null) {
            s.setFinishTime(entity.getFinishTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }

        return s;
    }

    private SessionRange getMonthSessionRange(final LocalDateTime dayStartOfCalMonth) {
        List<TblSessions> listSessions = null;
        listSessions = achSessionsDAO.findAll().stream()
                .filter(p -> p.getSessionTypeId() == 3 && p.getFinishTime() != null)
                .sorted(Comparator.comparingLong(AchSessions::getId))
                .map(p -> transformAchSessionToTblSession(p)).collect(Collectors.toList());

        //Ngày cuối cùng của tháng đang tính toán
        LocalDateTime dayEndOfCalMonth = dayStartOfCalMonth.plusMonths(1).minusDays(1);

        //Ngày cuối cùng của tháng trước tháng tính toán
        LocalDateTime dayEndOfPreviousMonth = dayStartOfCalMonth.minusDays(1);

        TblSessions lastSessionOfPreviousMonth = null;
        TblSessions firstSessionOfCalMonth = null;

        //Nếu tháng tính toán là tháng 1
        if (dayStartOfCalMonth.getMonthValue() == 1) {
            //Session đầu tiên của tháng tính toán
            firstSessionOfCalMonth = listSessions.stream().filter(
                    s -> dayStartOfCalMonth.compareTo(s.getStartTime()) >= 0
                    && dayStartOfCalMonth.compareTo(s.getFinishTime()) <= 0
            ).findAny().orElse(null);

            firstSessionOfCalMonth.setStartTime(dayStartOfCalMonth);
        } else {
            //Sesion kết thúc của tháng trước
            lastSessionOfPreviousMonth = listSessions.stream().filter(s -> dayEndOfPreviousMonth.compareTo(s.getStartTime()) >= 0
                    && dayEndOfPreviousMonth.compareTo(s.getFinishTime()) <= 0
            ).findAny().orElse(null);

            long sessionId = lastSessionOfPreviousMonth.getId();

            //Session đầu tiên của tháng tính toán
            firstSessionOfCalMonth = listSessions.stream().filter(
                    s -> s.getId() > sessionId
            ).findFirst().orElse(null);
        }

        //Sesion kết thúc của tháng đang tính toán
        Optional<TblSessions> lastSessionOfCalMonthFound = listSessions.stream().filter(
                s -> dayEndOfCalMonth.compareTo(s.getStartTime()) >= 0
                && dayEndOfCalMonth.compareTo(s.getFinishTime()) <= 0
        ).findAny();

        //Nếu không tìm thấy session kết thúc tháng thì có nghĩa là tháng chưa kết thúc
        TblSessions lastSessionOfCalMonth = lastSessionOfCalMonthFound.orElse(listSessions.get(listSessions.size() - 1));

        //Nếu tháng tính toán là tháng 12
        if (lastSessionOfCalMonth != null && dayStartOfCalMonth.getMonthValue() == 12) {
            lastSessionOfCalMonth.setFinishTime(dayEndOfCalMonth.plusDays(1).minusSeconds(1));
        }

        SessionRange sessionRange = new SessionRange();
        sessionRange.setBeginSession(firstSessionOfCalMonth);
        sessionRange.setEndSession(lastSessionOfCalMonth);
        sessionRange.setSessionMonth(dayStartOfCalMonth.toLocalDate());

        long beginSessionId = firstSessionOfCalMonth.getId();

        //Nếu tháng tính toán chưa kết thúc thì cho end là Long MAX luôn
        long endSessionId = lastSessionOfCalMonth != null ? lastSessionOfCalMonth.getId() : Long.MAX_VALUE;

        List<TblSessions> listSessionsInRange = listSessions.stream()
                .filter(s -> s.getId() >= beginSessionId && s.getId() <= endSessionId)
                .collect(Collectors.toList());

        sessionRange.setListSessions(listSessionsInRange);

        return sessionRange;
    }
}
