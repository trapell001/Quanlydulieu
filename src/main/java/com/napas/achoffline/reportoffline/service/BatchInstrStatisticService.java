package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.models.BatchInstrPaymentsStatistic;
import com.napas.achoffline.reportoffline.models.BatchInstrStatistic;
import com.napas.achoffline.reportoffline.models.BatchPaymentsStatistic;
import com.napas.achoffline.reportoffline.repository.HisBatchInstrDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BatchInstrStatisticService {

    @Autowired
    private HisBatchInstrDAO hisBatchInstrDAO;

    public void getBatchInstrExcelStream(HttpServletResponse response, String beginDate, String endDate,
                                         String reportType, String boc, String ttc, String transType,
                                         String creditorType, String transStatus,
                                         String debtorAgent,String msgId) {

        List<String> listBoc = Arrays.asList(new String[]{"findAll"});
        List<String> listTtc = Arrays.asList(new String[]{"findAll"});
        List<String> listTransType = Arrays.asList(new String[]{"findAll"});
        List<String> listCreditorType = Arrays.asList(new String[]{"findAll"});
        List<String> listTransStatus = Arrays.asList(new String[]{"findAll"});
        List<String> listDebtorAgent = Arrays.asList(new String[]{"findAll"});

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

        if(transStatus != null && transStatus.length() > 0) {
            listTransStatus = Arrays.asList(transStatus.split(","));
        }

        if(debtorAgent != null && debtorAgent.length() > 0) {
            listDebtorAgent = Arrays.asList(debtorAgent.split(","));
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
            List<BatchInstrStatistic> list = new ArrayList<>();
            if(reportType.equals("0")) {
                String dateBegin = beginDate.split("T")[0];
                String dateEnd = endDate.split("T")[0];
                LocalDate localDateBegin = LocalDate.parse(dateBegin, formatter);
                LocalDate localDateEnd = LocalDate.parse(dateEnd, formatter);
                for (LocalDate begin = localDateBegin; begin.compareTo(localDateEnd) <= 0; begin = begin.plusDays(1)) {
                    List<BatchInstrStatistic> temp = hisBatchInstrDAO.findStatisticBatchInstr(begin.getYear(),
                            begin.getMonthValue(), begin.getDayOfMonth(), listBoc, listTtc, listTransType, listCreditorType,
                            listTransStatus, listDebtorAgent, msgId, beginDateSearch, endDateSearch);
                    int year = begin.getYear();
                    int month = begin.getMonthValue();
                    int day = begin.getDayOfMonth();
                    temp.stream().forEach(item -> {
                        item.setDate(String.format("%s-%s-%s",
                                String.valueOf(day).length() == 2 ? day : "0" + String.valueOf(day),
                                String.valueOf(month).length() == 2 ? month : "0" + String.valueOf(month)
                                ,year));
                    });
                    list.addAll(temp);
                }
            } else {
                String dateBegin = beginDate.split("T")[0].substring(0,8) + "01";
                String dateEnd = endDate.split("T")[0].substring(0,8) + "01";
                LocalDate localDateBegin = LocalDate.parse(dateBegin, formatter);
                LocalDate localDateEnd = LocalDate.parse(dateEnd, formatter);
                for (LocalDate begin = localDateBegin; begin.compareTo(localDateEnd) <= 0; begin = begin.plusMonths(1)) {
                    List<BatchInstrStatistic> temp = hisBatchInstrDAO.findStatisticBatchInstr(begin.getYear(),
                            begin.getMonthValue(), null, listBoc, listTtc, listTransType, listCreditorType,
                            listTransStatus, listDebtorAgent, msgId, beginDateSearch, endDateSearch);
                    int year = begin.getYear();
                    int month = begin.getMonthValue();
                    temp.stream().forEach(item -> {
                        item.setDate(String.format("%s-%s",
                                String.valueOf(month).length() == 2 ? month : "0" + String.valueOf(month),
                                year));
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
            list = list.stream().sorted(Comparator.comparing(BatchInstrStatistic::getConvertDate)).collect(Collectors.toList());
            BatchInstrStatisticExporter exporter = new BatchInstrStatisticExporter(list,reportType);
            exporter.export(response);
        } catch (Exception ex) {
            log.info("EX: {}", ex);
        }
    }

    public void getBatchInstrPaymentsExcelStream(HttpServletResponse response, String beginDate, String endDate,
                                                 String reportType, String boc, String ttc, String transType,
                                                 String creditorType, String batchStatus, String transStatus,
                                                 String debtorAgent, String creditorAgent) {
        List<String> listBoc = Arrays.asList(new String[]{"findAll"});
        List<String> listTtc = Arrays.asList(new String[]{"findAll"});
        List<String> listTransType = Arrays.asList(new String[]{"findAll"});
        List<String> listCreditorType = Arrays.asList(new String[]{"findAll"});
        List<String> listBatchStatus = Arrays.asList(new String[]{"findAll"});
        List<String> listTransStatus = Arrays.asList(new String[]{"findAll"});
        List<String> listDebtorAgent = Arrays.asList(new String[]{"findAll"});
        List<String> listCreditorAgent = Arrays.asList(new String[]{"findAll"});

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

        if(batchStatus != null && batchStatus.length() > 0) {
            listBatchStatus = Arrays.asList(batchStatus.split(","));
        }

        if(transStatus != null && transStatus.length() > 0) {
            listTransStatus = Arrays.asList(transStatus.split(","));
        }

        if(debtorAgent != null && debtorAgent.length() > 0) {
            listDebtorAgent = Arrays.asList(debtorAgent.split(","));
        }

        if(creditorAgent != null && creditorAgent.length() > 0) {
            listCreditorAgent = Arrays.asList(creditorAgent.split(","));
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
            List<BatchInstrPaymentsStatistic> list = new ArrayList<>();
            if (reportType.equals("0")) {
                String dateBegin = beginDate.split("T")[0];
                String dateEnd = endDate.split("T")[0];
                LocalDate localDateBegin = LocalDate.parse(dateBegin, formatter);
                LocalDate localDateEnd = LocalDate.parse(dateEnd, formatter);
                for (LocalDate begin = localDateBegin; begin.compareTo(localDateEnd) <= 0; begin = begin.plusDays(1)) {
                    List<BatchInstrPaymentsStatistic> temp = hisBatchInstrDAO.findStatisticBatchInstrPayments(begin.getYear(),
                            begin.getMonthValue(), begin.getDayOfMonth(), listBoc, listTtc, listTransType, listCreditorType,
                            listBatchStatus, listTransStatus, listDebtorAgent, listCreditorAgent, beginDateSearch, endDateSearch);
                    int year = begin.getYear();
                    int month = begin.getMonthValue();
                    int day = begin.getDayOfMonth();
                    temp.stream().forEach(item -> {
                        item.setDate(String.format("%s-%s-%s",
                                String.valueOf(day).length() == 2 ? day : "0" + String.valueOf(day),
                                String.valueOf(month).length() == 2 ? month : "0" + String.valueOf(month)
                                ,year));
                    });
                    list.addAll(temp);
                }
            } else {
                String dateBegin = beginDate.split("T")[0].substring(0, 8) + "01";
                String dateEnd = endDate.split("T")[0].substring(0, 8) + "01";
                LocalDate localDateBegin = LocalDate.parse(dateBegin, formatter);
                LocalDate localDateEnd = LocalDate.parse(dateEnd, formatter);
                for (LocalDate begin = localDateBegin; begin.compareTo(localDateEnd) <= 0; begin = begin.plusMonths(1)) {
                    List<BatchInstrPaymentsStatistic> temp = hisBatchInstrDAO.findStatisticBatchInstrPayments(begin.getYear(),
                            begin.getMonthValue(), null, listBoc, listTtc, listTransType, listCreditorType,
                            listBatchStatus, listTransStatus, listDebtorAgent, listCreditorAgent, beginDateSearch, endDateSearch);
                    int year = begin.getYear();
                    int month = begin.getMonthValue();
                    temp.stream().forEach(item -> {
                        item.setDate(String.format("%s-%s",
                                String.valueOf(month).length() == 2 ? month : "0" + String.valueOf(month),
                                year));
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
            list = list.stream().sorted(Comparator.comparing(BatchInstrPaymentsStatistic::getConvertDate)).collect(Collectors.toList());
            BatchInstrPaymentsStatisticExporter exporter = new BatchInstrPaymentsStatisticExporter(list, reportType);
            exporter.export(response);
        } catch (Exception ex) {
            log.info("EX: {}", ex);
        }
    }
}
