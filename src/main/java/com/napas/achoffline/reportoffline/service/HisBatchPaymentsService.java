package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisBatchInstr;
import com.napas.achoffline.reportoffline.entity.HisBatchPayments;
import com.napas.achoffline.reportoffline.entity.HisBilling;
import com.napas.achoffline.reportoffline.models.HisBatchPaymentsDTO;
import com.napas.achoffline.reportoffline.models.HisBillingDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.HisBatchInstrDAO;
import com.napas.achoffline.reportoffline.repository.HisBatchPaymentsDAO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HisBatchPaymentsService {

    @Autowired
    private HisBatchPaymentsDAO hisBatchPaymentsDAO;

    @Autowired
    private HisBatchInstrDAO hisBatchInstrDAO;

    @Autowired
    private ModelMapper mapper;

    @Value("${napas.ach.offline.export.dns-export-dir}")
    private String paymentExportDir;

    @Value("${napas.ach.offline.export.max-rows-for-xlsx}")
    private long maxRowsForXlsx;

    private HisBatchPaymentsDTO fromEntity(HisBatchPayments entity) {
        HisBatchPaymentsDTO dto = mapper.map(entity, HisBatchPaymentsDTO.class);
        return dto;
    }
    private HisBatchPayments fromDTO(HisBatchPaymentsDTO dto) {
        HisBatchPayments entity = mapper.map(dto, HisBatchPayments.class);
        return entity;
    }

    public ResponseEntity<?> searchBatchPayments(String beginDate, String endDate, String msgId, Long sessionFrom,
                                                 Long sessionTo, String status, String viewStatus, String msgType,
                                                 String boc, String ttc,
                                                 String tcpl, String tcnl, String creditorType, Integer page,
                                                 Integer pagesize) {
        if(msgId != null && msgId.length() > 0) {
            msgId = msgId.trim();
            msgId = "%" + msgId + "%";
        }

        if(tcpl != null && tcpl.length() > 0) {
            tcpl = tcpl.trim();
            tcpl = "%" + tcpl + "%";
        }

        if(tcnl != null && tcnl.length() > 0) {
            tcnl = tcnl.trim();
            tcnl = "%" + tcnl + "%";
        }
        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date beginDateSearch = null;
        Date endDateSearch = null;
        try {
            if (beginDate.length() == 16) {
                beginDate = beginDate + ":00";
            }

            if (endDate.length() == 16) {
                endDate = endDate + ":00";
            }
            if (beginDate != null && !beginDate.equals(""))
                beginDateSearch = searchDateFmt.parse(beginDate);
            if(endDate != null && !endDate.equals(""))
                endDateSearch = searchDateFmt.parse(endDate);
            Page<HisBatchPayments> list = hisBatchPaymentsDAO.searchBatchPayments(beginDateSearch, endDateSearch, msgId,
                    sessionFrom, sessionTo, status, msgType, boc, ttc,viewStatus, tcpl, tcnl, creditorType,
                    PageRequest.of(page-1, pagesize, Sort.by("processDate").descending()));
            List<HisBatchPaymentsDTO> listDTO = new ArrayList<>();
            if(list.getContent().size() != 0 || list.getContent() != null) {
                for (HisBatchPayments entity : list.getContent()) {
                    HisBatchPaymentsDTO h = fromEntity(entity);
                    List<HisBatchInstr> listInstr = hisBatchInstrDAO.searchBatchInstr(entity.getDocId(),
                            tcpl, tcnl, creditorType);
                    if (listInstr.size() != 0 || listInstr != null) {
                        h.setChannelId(listInstr.get(0).getIChannelId());
                        h.setMxInstructingAgent(listInstr.get(0).getMxInstructingAgent());
                        h.setMxDebtorAgent(listInstr.get(0).getMxDebtorAgent());
                        h.setMxInstructedAgent(listInstr.get(0).getMxInstructedAgent());
                        h.setMxCreditorAgent(listInstr.get(0).getMxCreditorAgent());
                        h.setCurrency(listInstr.get(0).getITransactionCurrency());
                    }
                    listDTO.add(h);
                }
            }
            Page<HisBatchPaymentsDTO> pageBatchPayments = new PageImpl<>(listDTO, PageRequest.of(page-1,
                    pagesize, Sort.by("processDate").descending()), list.getTotalElements());
            return ResponseEntity.ok(pageBatchPayments);
        } catch (Exception ex) {
            log.error("Loi:" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Loi truy van"));
        }
    }

    public void exportExcel(HttpServletResponse response, String beginDate, String endDate, String msgId,
                            Long sessionFrom, Long sessionTo, String status, String viewStatus, String msgType, String boc,
                            String ttc, String tcpl, String tcnl, String creditorType) {
        if(msgId != null && msgId.length() > 0) {
            msgId = msgId.trim();
            msgId = "%" + msgId + "%";
        }
        if(tcpl != null && tcpl.length() > 0) {
            tcpl = tcpl.trim();
            tcpl = "%" + tcpl + "%";
        }

        if(tcnl != null && tcnl.length() > 0) {
            tcnl = tcnl.trim();
            tcnl = "%" + tcnl + "%";
        }
        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date beginDateSearch = null;
        Date endDateSearch = null;
        try {
            if (beginDate.length() == 16) {
                beginDate = beginDate + ":00";
            }

            if (endDate.length() == 16) {
                endDate = endDate + ":00";
            }
            if (beginDate != null && !beginDate.equals(""))
                beginDateSearch = searchDateFmt.parse(beginDate);
            if (endDate != null && !endDate.equals(""))
                endDateSearch = searchDateFmt.parse(endDate);
            List<HisBatchPayments> list = hisBatchPaymentsDAO.exportExcel(beginDateSearch, endDateSearch, msgId,
                    sessionFrom, sessionTo, status, msgType, boc, ttc, viewStatus, tcpl, tcnl, creditorType);
            List<HisBatchPaymentsDTO> listDTO = new ArrayList<>();
            if(list.size() != 0 || list != null) {
                for (HisBatchPayments entity : list) {
                    HisBatchPaymentsDTO h = fromEntity(entity);
                    List<HisBatchInstr> listInstr = hisBatchInstrDAO.searchBatchInstr(entity.getDocId(),
                            tcpl, tcnl, creditorType);
                    if (listInstr.size() != 0 || listInstr != null) {
                        h.setChannelId(listInstr.get(0).getIChannelId());
                        h.setMxInstructingAgent(listInstr.get(0).getMxInstructingAgent());
                        h.setMxDebtorAgent(listInstr.get(0).getMxDebtorAgent());
                        h.setMxInstructedAgent(listInstr.get(0).getMxInstructedAgent());
                        h.setMxCreditorAgent(listInstr.get(0).getMxCreditorAgent());
                        h.setCurrency(listInstr.get(0).getITransactionCurrency());
                    }
                    listDTO.add(h);
                }
            }
            listDTO = listDTO.stream().sorted(Comparator.comparing(HisBatchPaymentsDTO::getProcessDate)).collect(Collectors.toList());
            if(list.size() <= maxRowsForXlsx) {
                BatchPaymentsExcelExporter excelExporter = new BatchPaymentsExcelExporter(listDTO ,paymentExportDir);
                excelExporter.export(response);
            }
        } catch (Exception ex) {
            log.error("Loi:" + ex.getMessage());
        }
    }
}
