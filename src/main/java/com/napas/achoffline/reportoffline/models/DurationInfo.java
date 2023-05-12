/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.type.TrafficDurationType;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;

/**
 *
 * @author huynx
 */
public class DurationInfo {

    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private DateTimeFormatter tickOutputFormatter;
    private List<LocalDateTime> listTick = new ArrayList<>();
    private Date beginDateSearch;
    private Date endDateSearch;

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public DateTimeFormatter getTickOutputFormatter() {
        return tickOutputFormatter;
    }

    public void setTickOutputFormatter(DateTimeFormatter tickOutputFormatter) {
        this.tickOutputFormatter = tickOutputFormatter;
    }

    public List<LocalDateTime> getListTick() {
        return listTick;
    }

    public void setListTick(List<LocalDateTime> listTick) {
        this.listTick = listTick;
    }

    public Date getBeginDateSearch() {
        return beginDateSearch;
    }

    public void setBeginDateSearch(Date beginDateSearch) {
        this.beginDateSearch = beginDateSearch;
    }

    public Date getEndDateSearch() {
        return endDateSearch;
    }

    public void setEndDateSearch(Date endDateSearch) {
        this.endDateSearch = endDateSearch;
    }

    public static DurationInfo calculate(TrafficDurationType durationType, long duration, String endDate) {
        DurationInfo d = new DurationInfo();

        LocalDateTime today = null;

        if (endDate == null) {
            today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        } else {
            today = LocalDateTime.parse(endDate);
        }

        String tickOutputFormat = "";

        switch (durationType) {
            case HOURLY:
                tickOutputFormat = "HH:mm";
                d.endDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
                d.beginDate = d.endDate.minusHours(duration);

                for (LocalDateTime ldt = d.beginDate; ldt.isBefore(d.endDate) || ldt.isEqual(d.endDate); ldt = ldt.plusHours(1)) {
                    d.listTick.add(ldt);
                }
                break;
            case DAILY:
                tickOutputFormat = "dd/MMM";
                d.endDate = today;
                d.beginDate = d.endDate.minusDays(duration);
                for (LocalDateTime ldt = d.beginDate; ldt.isBefore(d.endDate) || ldt.isEqual(d.endDate); ldt = ldt.plusDays(1)) {
                    d.listTick.add(ldt);
                }
                break;
            case WEEKLY:
                tickOutputFormat = "dd/MMM";
                DayOfWeek dayOfWeek = today.getDayOfWeek();
                d.endDate = today.truncatedTo(ChronoUnit.DAYS).minusDays(dayOfWeek.getValue() - DayOfWeek.MONDAY.getValue());
                d.beginDate = d.endDate.minusWeeks(duration);
                for (LocalDateTime ldt = d.beginDate; ldt.isBefore(d.endDate) || ldt.isEqual(d.endDate); ldt = ldt.plusWeeks(1)) {
                    d.listTick.add(ldt);
                }
                break;
            case MONTHLY:
                tickOutputFormat = "MMM";
                int dayOfMon = today.getDayOfMonth();
                d.endDate = today.truncatedTo(ChronoUnit.DAYS).minusDays(dayOfMon - 1);
                d.beginDate = d.endDate.minusMonths(duration);
                for (LocalDateTime ldt = d.beginDate; ldt.isBefore(d.endDate) || ldt.isEqual(d.endDate); ldt = ldt.plusMonths(1)) {
                    d.listTick.add(ldt);
                }
                break;
        }

        d.tickOutputFormatter = DateTimeFormatter.ofPattern(tickOutputFormat);

        d.endDate.minusSeconds(1);

        d.beginDateSearch = Date.from(d.beginDate.atZone(ZoneId.systemDefault()).toInstant());
        d.endDateSearch = Date.from(d.endDate.minusSeconds(1).atZone(ZoneId.systemDefault()).toInstant());

        return d;
    }
}
