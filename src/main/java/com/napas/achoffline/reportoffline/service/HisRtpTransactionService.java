package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisRtpTransaction;
import com.napas.achoffline.reportoffline.models.HisRtpTransactionDTO;
import com.napas.achoffline.reportoffline.models.RptTransactionStatistic;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.HisRtpTransactionDAO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class HisRtpTransactionService {
    @Autowired
    private HisRtpTransactionDAO hisRtpTransactionDAO;

    @Value("${napas.ach.offline.export.payment-export-dir}")
    private String paymentExportDir;

    @Autowired
    private ModelMapper mapper;

    private HisRtpTransactionDTO fromEntity(HisRtpTransaction entity) {
        HisRtpTransactionDTO dto = mapper.map(entity, HisRtpTransactionDTO.class);
        return dto;
    }

    private HisRtpTransaction fromDTO(HisRtpTransactionDTO dto) {
        HisRtpTransaction entity = mapper.map(dto, HisRtpTransaction.class);
        return entity;
    }

    public ResponseEntity<?> searchpaging(String beginDate, String endDate,String txid, String msgIdRtp,
                                          String rtpType, Long sessionFrom, Long sessionTo,
                                          String transStatus, String boc, String initiatorAgent,
                                          String debtorAgent, String creditorAgent,
                                          String debtorName, String debtorAccount,
                                          String creditorName, String creditorAccount, String initiatingSystem,
                                          Integer pageIndex, Integer pagesize) {
        if(msgIdRtp != null && msgIdRtp.length() > 0) {
            msgIdRtp = msgIdRtp.trim();
            msgIdRtp = "%" + msgIdRtp + "%";
        }
        if(txid != null && txid.length() > 0) {
            txid = txid.trim();
            txid = "%" + txid + "%";
        }
        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date beginDateSearch;
        Date endDateSearch;

        try {
            if (beginDate.length() == 16) {
                beginDate = beginDate + ":00";
            }

            if (endDate.length() == 16) {
                endDate = endDate + ":00";
            }

            beginDateSearch = searchDateFmt.parse(beginDate);
            endDateSearch = searchDateFmt.parse(endDate);

            Page<HisRtpTransaction> page = hisRtpTransactionDAO.searchHisRpt(beginDateSearch, endDateSearch,
                    msgIdRtp, rtpType, sessionFrom,
                    sessionTo, transStatus, boc, initiatorAgent, debtorAgent, creditorAgent,
                    debtorName, debtorAccount, creditorName, creditorAccount, txid, convert(initiatingSystem),
                    PageRequest.of(pageIndex - 1, pagesize, Sort.by("processDate").descending()));

            Page<HisRtpTransactionDTO> listDTO = page.map(entity -> fromEntity(entity));
            return ResponseEntity.ok(listDTO);
        } catch (Exception ex) {
            log.error("Loi:" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Loi truy van"));
        }
    }

    public void export(HttpServletResponse response, String beginDate, String endDate,String txid, String msgIdRtp,
                       String rtpType, Long sessionFrom, Long sessionTo,
                       String transStatus, String boc, String initiatingParty,
                       String debtorAgent, String creditorAgent, String debtorName,
                       String debtorAccount, String creditorName, String creditorAccount, String initiatingSystem) {
        if(msgIdRtp != null && msgIdRtp.length() > 0) {
            msgIdRtp = msgIdRtp.trim();
            msgIdRtp = "%" + msgIdRtp + "%";
        }
        if(txid != null && txid.length() > 0) {
            txid = txid.trim();
            txid = "%" + txid + "%";
        }
        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date beginDateSearch;
        Date endDateSearch;

        try {
            if (beginDate.length() == 16) {
                beginDate = beginDate + ":00";
            }

            if (endDate.length() == 16) {
                endDate = endDate + ":00";
            }

            beginDateSearch = searchDateFmt.parse(beginDate);
            endDateSearch = searchDateFmt.parse(endDate);

            List<HisRtpTransaction> list = hisRtpTransactionDAO.export(beginDateSearch, endDateSearch, msgIdRtp,
                    rtpType, sessionFrom, sessionTo, transStatus, boc, initiatingParty, debtorAgent, creditorAgent,
                    debtorName, debtorAccount, creditorName, creditorAccount, txid, convert(initiatingSystem));
            List<HisRtpTransactionDTO> listDTO = list.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
            listDTO.forEach(entity -> {
                entity.setCreditorSystem(convertACHIBFT(entity.getCreditorAgent().substring(4,8)));
            });
            listDTO = listDTO.stream().sorted(Comparator.comparing(HisRtpTransactionDTO::getProcessDate)).collect(Collectors.toList());
            RptTransactionExporter exporter = new RptTransactionExporter(listDTO, paymentExportDir);
            exporter.export(response);
        } catch (Exception ex) {
            log.error("Loi:" + ex.getMessage());
        }
    }

    public void exportStatistic(HttpServletResponse response, String beginDate, String endDate, String reportType,
                                String boc, String ttc, String rtpType,String initiatorAgent,String debtorAgent,
                                String creditorAgent, String transStatus,String initiatingSystem) {
        List<String> listBoc = Arrays.asList(new String[]{"findAll"});
        List<String> listTtc = Arrays.asList(new String[]{"findAll"});
        List<String> listRtpType = Arrays.asList(new String[]{"findAll"});
        List<String> listInitiatorAgent = Arrays.asList(new String[]{"findAll"});
        List<String> listDebtorAgent = Arrays.asList(new String[]{"findAll"});
        List<String> listCreditorAgent = Arrays.asList(new String[]{"findAll"});
        List<String> listTransStatus = Arrays.asList(new String[]{"findAll"});

        if(boc != null && boc.length() > 0) {
            listBoc = Arrays.asList(boc.split(","));
        }

        if(ttc != null && ttc.length() > 0) {
            listTtc = Arrays.asList(ttc.split(","));
        }

        if(rtpType != null && rtpType.length() > 0) {
            listRtpType = Arrays.asList(rtpType.split(","));
        }

        if(initiatorAgent != null && initiatorAgent.length() > 0) {
            listInitiatorAgent = Arrays.asList(initiatorAgent.split(","));
        }

        if(debtorAgent != null && debtorAgent.length() > 0) {
            listDebtorAgent = Arrays.asList(debtorAgent.split(","));
        }

        if(creditorAgent != null && creditorAgent.length() > 0) {
            listCreditorAgent = Arrays.asList(creditorAgent.split(","));
        }


        if(transStatus != null && transStatus.length() > 0) {
            listTransStatus = Arrays.asList(transStatus.split(","));
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

            List<RptTransactionStatistic> list = new ArrayList<>();
            if(reportType.equals("0")) {
                String dateBegin = beginDate.split("T")[0];
                String dateEnd = endDate.split("T")[0];
                LocalDate localDateBegin = LocalDate.parse(dateBegin, formatter);
                LocalDate localDateEnd = LocalDate.parse(dateEnd, formatter);
                for (LocalDate begin = localDateBegin; begin.compareTo(localDateEnd) <= 0; begin = begin.plusDays(1)) {
                    List<RptTransactionStatistic> temp = hisRtpTransactionDAO.findStatisticRtpr(begin.getYear(),
                            begin.getMonthValue(), begin.getDayOfMonth(), listBoc, listTtc, listRtpType,
                            listInitiatorAgent, listDebtorAgent, listCreditorAgent,
                            listTransStatus, convert(initiatingSystem)
                            ,beginDateSearch, endDateSearch);
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
                    List<RptTransactionStatistic> temp = hisRtpTransactionDAO.findStatisticRtpr(begin.getYear(),
                            begin.getMonthValue(), null, listBoc, listTtc, listRtpType, listInitiatorAgent,
                            listDebtorAgent, listCreditorAgent, listTransStatus, convert(initiatingSystem),
                            beginDateSearch, endDateSearch);
                    int year = begin.getYear();
                    int month = begin.getMonthValue();
                    int day = begin.getDayOfMonth();
                    temp.stream().forEach(item -> {
                        item.setDate(String.format("%s-%s",
                                String.valueOf(month).length() == 2 ? month : "0" + String.valueOf(month)
                                ,year));
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
            list.stream().forEach(entity -> {
                entity.setCreditorSystem(convertACHIBFT(entity.getTcth().substring(4,8)));
            });
            list = list.stream().sorted(Comparator.comparing(RptTransactionStatistic::getConvertDate)).collect(Collectors.toList());
            RptTransactionStatisticExporter exporter = new RptTransactionStatisticExporter(list,reportType);
            exporter.export(response);
        } catch (Exception ex) {
            log.info("EX: {}", ex);
        }
    }

    private String convert(String s) {
        if(s.equals("ACH")) return "VNVN";
        if(s.equals("IBFT")) return "VNVP";
        return null;
    }

    private String convertACHIBFT(String s) {
        if(s.equals("VNVN")) return "ACH";
        return "IBFT";
    }
}
