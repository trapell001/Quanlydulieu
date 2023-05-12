package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisBatchInstr;
import com.napas.achoffline.reportoffline.entity.HisBatchPayments;
import com.napas.achoffline.reportoffline.models.HisBatchInstrDTO;
import com.napas.achoffline.reportoffline.models.HisBatchPaymentsDTO;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HisBatchInstrService {
    @Autowired
    private HisBatchInstrDAO hisBatchInstrDAO;

    @Autowired
    private HisBatchPaymentsDAO hisBatchPaymentsDAO;
    @Autowired
    private ModelMapper mapper;

    @Value("${napas.ach.offline.export.dns-export-dir}")
    private String paymentExportDir;

    @Value("${napas.ach.offline.export.max-rows-for-xlsx}")
    private long maxRowsForXlsx;

    private HisBatchInstrDTO fromEntity(HisBatchInstr entity) {
        HisBatchInstrDTO dto = mapper.map(entity, HisBatchInstrDTO.class);
        return dto;
    }
    private HisBatchInstr fromDTO(HisBatchInstrDTO dto) {
        HisBatchInstr entity = mapper.map(dto, HisBatchInstr.class);
        return entity;
    }

    public ResponseEntity<?> search(String beginDate, String endDate, Long aDocId ,String txid, String status,
                                    String viewStatus, String debtorName, String debtorType, String debtorAccount,
                                    String creditorName, String creditorType, String creditorAccount, String rtp,
                                    Integer pageIndex, Integer pagesize) {
        if(txid != null && txid.length() > 0) {
            txid = txid.trim();
            txid = "%" + txid + "%";
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
        } catch (Exception ex) {
            log.error("Loi:" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Loi date time"));
        }
        Page<HisBatchInstr> list = hisBatchInstrDAO.search(beginDateSearch, endDateSearch, aDocId, txid, status,
                debtorName, debtorType, debtorAccount, creditorName, creditorType, creditorAccount, viewStatus,
                rtp, PageRequest.of(pageIndex - 1, pagesize, Sort.by("processDate").descending()));
        List<HisBatchInstrDTO> listDTO = new ArrayList<>();
        for(HisBatchInstr entity : list.getContent()) {
            HisBatchInstrDTO dto = fromEntity(entity);
            HisBatchPayments item = hisBatchPaymentsDAO.findByADocId(entity.getDocId());
            dto.setBoc(item.getMxBankOpCode());
            dto.setSession(item.getSessionId());
            dto.setOperdayId(item.getOperdayId());
            dto.setPriority(item.getMxPriority());
            dto.setMsgId(item.getMxMsgid());
            dto.setValueDate(item.getMxValueDate());
            listDTO.add(dto);
        }
        Page<HisBatchInstrDTO> page = new PageImpl<>(listDTO, PageRequest.of(pageIndex - 1, pagesize,
                Sort.by("processDate").descending()), list.getTotalElements());
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<?> findByADocId(Long aDocId, Integer pageIndex, Integer pagesize) {
        Page<HisBatchInstr> page = hisBatchInstrDAO.findByADocId(aDocId, PageRequest.of(pageIndex - 1, pagesize,
                Sort.by("processDate").descending()));
        return ResponseEntity.ok(page);
    }

    public void exportToExcel(HttpServletResponse response,String beginDate, String endDate, Long aDocId, String txid,
                              String status, String viewStatus, String debtorName,
                              String debtorType, String debtorAccount, String creditorName, String creditorType,
                              String creditorAccount, String rtp) throws IOException {
        if(txid != null && txid.length() > 0) {
            txid = txid.trim();
            txid = "%" + txid + "%";
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
        } catch (Exception ex) {
            log.error("Loi:" + ex.getMessage());
        }
        List<HisBatchInstr> list = hisBatchInstrDAO.searchExcel(beginDateSearch, endDateSearch, aDocId,txid, status,
                debtorName, debtorType, debtorAccount, creditorName, creditorType, creditorAccount, viewStatus, rtp);
        List<HisBatchInstrDTO> listDTO = new ArrayList<>();
        for(HisBatchInstr entity : list) {
            HisBatchInstrDTO dto = fromEntity(entity);
            HisBatchPayments item = hisBatchPaymentsDAO.findByADocId(entity.getDocId());
            dto.setBoc(item.getMxBankOpCode());
            dto.setSession(item.getSessionId());
            dto.setOperdayId(item.getOperdayId());
            dto.setPriority(item.getMxPriority());
            dto.setMsgId(item.getMxMsgid());
            dto.setValueDate(item.getMxValueDate());
            listDTO.add(dto);
        }
        listDTO = listDTO.stream().sorted(Comparator.comparing(HisBatchInstrDTO::getProcessDate)).collect(Collectors.toList());
        if(list.size() <= maxRowsForXlsx) {
            BatchInstrExcelExporter excelExporter = new BatchInstrExcelExporter(listDTO ,paymentExportDir);
            excelExporter.export(response);
        }
    }
}
