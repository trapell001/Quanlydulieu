package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.models.PaymentStatistic;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.PaymentsDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentsStatisticService {

    @Autowired
    private PaymentsDAO paymentsDAO;

    public void getPaymentExcelStream(HttpServletResponse response, String beginDate, String endDate,
                                      String reportType, String boc, String ttc, String transType,
                                      String creditorType, String status, String auth,
                                      String channel, String instructingAgent, String instructedAgent,
                                      String debtorSystem, String creditorSystem) {
        List<String> listBoc = Arrays.asList(new String[]{"findAll"});
        List<String> listTtc = Arrays.asList(new String[]{"findAll"});
        List<String> listTransType = Arrays.asList(new String[]{"findAll"});
        List<String> listCreditorType = Arrays.asList(new String[]{"findAll"});
        List<String> listStatus = Arrays.asList(new String[]{"findAll"});
        List<String> listAuth = Arrays.asList(new String[]{"findAll"});
        List<String> listChannel = Arrays.asList(new String[]{"findAll"});
        List<String> listInstructing = Arrays.asList(new String[]{"findAll"});
        List<String> listInstructed = Arrays.asList(new String[]{"findAll"});

        if(boc != null && boc.length() > 0) {
            listBoc = Arrays.asList(boc.split(","));
        }

        if(ttc != null && ttc.length() > 0) {
            listTtc = Arrays.asList(ttc.split(","));
        }

        if(transType != null && transType.length() > 0) {
            listTransType = Arrays.asList(transType.split(","));
        }

        if(creditorType != null && creditorType.length() > 0) {
            listCreditorType = Arrays.asList(creditorType.split(","));
        }

        if(status != null && status.length() > 0) {
            listStatus = Arrays.asList(status.split(","));
        }

        if(auth != null && auth.length() > 0) {
            listAuth = Arrays.asList(auth.split(","));
        }

        if(channel != null && channel.length() > 0) {
            listChannel = Arrays.asList(channel.split(","));
        }

        if(instructingAgent != null && instructingAgent.length() > 0) {
            listInstructing = Arrays.asList(instructingAgent.split(","));
        }

        if(instructedAgent != null && instructedAgent.length() > 0) {
            listInstructed = Arrays.asList(instructedAgent.split(","));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date beginDateSearch = null;
        Date endDateSearch = null;



        try {
            if (beginDate.length() == 10) {
                beginDate = beginDate + "T00:00:00";
            }

            if (endDate.length() == 10) {
                endDate = endDate + "T23:59:59";
            }
            beginDateSearch = searchDateFmt.parse(beginDate);
            endDateSearch = searchDateFmt.parse(endDate);
            List<PaymentStatistic> list = new ArrayList<>();
            if(reportType.equals("0")) {
                String dateBegin = beginDate.split("T")[0];
                String dateEnd = endDate.split("T")[0];
                LocalDate localDateBegin = LocalDate.parse(dateBegin, formatter);
                LocalDate localDateEnd = LocalDate.parse(dateEnd, formatter);
                for (LocalDate begin = localDateBegin; begin.compareTo(localDateEnd) <= 0; begin = begin.plusDays(1)) {
                    List<PaymentStatistic> temp = paymentsDAO.findPayment(begin.getYear(), begin.getMonthValue(), begin.getDayOfMonth(),
                            convert(debtorSystem), convert(creditorSystem), listBoc, listTtc,
                            listTransType, listCreditorType, listStatus, listAuth, listChannel,
                            listInstructing, listInstructed, beginDateSearch, endDateSearch);
                    int year = begin.getYear();
                    int month = begin.getMonthValue();
                    int day = begin.getDayOfMonth();
                    temp.stream().forEach(item -> {
                        item.setDate(String.format("%s-%s-%s",
                                String.valueOf(day).length() == 2 ? day : "0" + String.valueOf(day),
                                String.valueOf(month).length() == 2 ? month : "0" + String.valueOf(month)
                                ,year));
                        item.setTransGroup(group(item.getInstructingAgent(), item.getInstructedAgent()));
                    });
                    list.addAll(temp);
                }
            } else {
                String dateBegin = beginDate.split("T")[0].substring(0,8) + "01";
                String dateEnd = endDate.split("T")[0].substring(0,8) + "01";
                LocalDate localDateBegin = LocalDate.parse(dateBegin, formatter);
                LocalDate localDateEnd = LocalDate.parse(dateEnd, formatter);
                for (LocalDate begin = localDateBegin; begin.compareTo(localDateEnd) <= 0; begin = begin.plusMonths(1)) {
                    List<PaymentStatistic> temp = paymentsDAO.findPayment(begin.getYear(), begin.getMonthValue(), null,
                            convert(debtorSystem), convert(creditorSystem), listBoc, listTtc, listTransType,
                            listCreditorType, listStatus, listAuth, listChannel, listInstructing,
                            listInstructed, beginDateSearch, endDateSearch);
                    int year = begin.getYear();
                    int month = begin.getMonthValue();
                    temp.stream().forEach(item -> {
                        item.setDate(String.format("%s-%s",
                                String.valueOf(month).length() == 2 ? month : "0" + String.valueOf(month),
                                year));
                        item.setTransGroup(group(item.getInstructingAgent(), item.getInstructedAgent()));
                    });
                    list.addAll(temp);
                }
            }
            if(reportType.equals("0")) {
                list.stream().forEach(entity -> {
                    try {
                        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(entity.getDate());
                        entity.setConvertDate(date);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else {
                list.stream().forEach(entity -> {
                    try {
                        Date date = new SimpleDateFormat("MM-yyyy").parse(entity.getDate());
                        entity.setConvertDate(date);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            list = list.stream().sorted(Comparator.comparing(PaymentStatistic::getConvertDate)).collect(Collectors.toList());
            PaymentsStatisticExporter exporter = new PaymentsStatisticExporter(list,reportType);
            exporter.export(response);
        } catch (Exception ex) {
            log.info("EX: {}", ex);
        }
    }

    public String group(String s1, String s2) {
        if(s1.substring(4,8).equals("VNVN") &&
                s2.substring(4,8).equals("VNVP")) {
            return "ACH-IBFT";
        }
        if(s1.substring(4,8).equals("VNVP") &&
                s2.substring(4,8).equals("VNVN")) {
            return "IBFT-ACH";
        }
        if(s1.substring(4,8).equals("VNVP") &&
                s2.substring(4,8).equals("VNVP")) {
            return "IBFT";
        }
        return "ACH";
    }

    public String convert(String s) {
        if(s.equals("ACH")) return "VNVN";
        if(s.equals("IBFT")) return "VNVP";
        return null;
    }
}
