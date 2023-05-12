package com.napas.achoffline.reportoffline.service;


import com.napas.achoffline.reportoffline.entity.DisputeCase;
import com.napas.achoffline.reportoffline.entity.HisBilling;
import com.napas.achoffline.reportoffline.entity.Payments;
import com.napas.achoffline.reportoffline.models.DisputeCaseDTO;
import com.napas.achoffline.reportoffline.models.DisputeExcelExporter;
import com.napas.achoffline.reportoffline.models.HisBillingDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.DisputeDAO;
import com.napas.achoffline.reportoffline.repository.PaymentsDAO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DisputeService {

    @Autowired
    private DisputeDAO disputeDAO;

    @Autowired
    private PaymentsDAO paymentsDAO;

    @Autowired
    private ModelMapper mapper;

    @Value("${napas.ach.offline.export.max-rows-for-xlsx}")
    private long maxRowsForXlsx;

    @Value("${napas.ach.offline.export.payment-export-dir}")
    private String paymentExportDir;

    @Value("${napas.ach.offline.export.dispute-export-dir}")
    private String disputeExportDir;

    private DisputeCaseDTO fromEntity(DisputeCase entity) {
        DisputeCaseDTO dto = mapper.map(entity, DisputeCaseDTO.class);
        return dto;
    }
    private DisputeCase fromDTO(DisputeCaseDTO dto) {
        DisputeCase entity = mapper.map(dto, DisputeCase.class);
        return entity;
    }

    public ResponseEntity<?> getDisputeList(String beginCreate, String endCreate, String beginModif, String endModif,
                                            String beginResponse, String endResponse, String reference, String claimant,
                                            String claimee, String respondent, String status, String dispCat,
                                            String dispBatchReference, String dispTxid, Integer page, Integer pagesize) throws ParseException {

        reference = like(reference);
        dispBatchReference = like(dispBatchReference);
        dispTxid = like(dispTxid);

        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date beginDateCreate = convertDate(beginCreate);
        Date endDateCreate = convertDate(endCreate);
        Date beginDateModif = convertDate(beginModif);
        Date endDateModif = convertDate(endModif);
        Date beginDateResponse = convertDate(beginResponse);
        Date endDateResponse = convertDate(endResponse);

        Pageable sortedById = PageRequest.of(page, pagesize, Sort.by("createDate").descending());
        Page<DisputeCase> p = disputeDAO.getDisputePage(beginDateCreate,endDateCreate,reference,claimant,claimee,
                respondent,status,dispCat,dispBatchReference,dispTxid, beginDateModif, endDateModif,
                beginDateResponse, endDateResponse,sortedById);
        List<DisputeCase> disputeCases = p.getContent();
        List<DisputeCaseDTO> disputeCaseDTOS = new ArrayList<>();
        disputeCases.stream().forEach(entity -> {
            entity.setParticipantAssigner(entity.getParticipantAssigner().substring(0,8));
            entity.setParticipantAssignee(entity.getParticipantAssignee().substring(0,8));
            entity.setParticipantRespondent(entity.getParticipantRespondent().substring(0,8));
            DisputeCaseDTO dto = fromEntity(entity);
            disputeCaseDTOS.add(dto);
        });
        Page<DisputeCaseDTO> pages = new PageImpl<>(disputeCaseDTOS, sortedById, p.getTotalElements());
        return ResponseEntity.ok(pages);
    }

    public void exportDispute(HttpServletResponse response,
                              String beginCreate, String endCreate, String beginModif, String endModif,
                              String beginResponse, String endResponse, String reference, String claimant,
                              String claimee, String respondent, String status, String dispCat,
                              String dispBatchReference, String dispTxid) throws IOException {
        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date beginDateCreate = convertDate(beginCreate);
        Date endDateCreate = convertDate(endCreate);
        Date beginDateModif = convertDate(beginModif);
        Date endDateModif = convertDate(endModif);
        Date beginDateResponse = convertDate(beginResponse);
        Date endDateResponse = convertDate(endResponse);

        reference = like(reference);
        dispBatchReference = like(dispBatchReference);
        dispTxid = like(dispTxid);

        List<DisputeCase> disputeCases = disputeDAO.getDisputeList(beginDateCreate,endDateCreate,reference,claimant,claimee,
                respondent,status,dispCat,dispBatchReference,dispTxid, beginDateModif, endDateModif,
                beginDateResponse, endDateResponse);
        disputeCases.stream().forEach(entity -> {
            entity.setParticipantAssigner(entity.getParticipantAssigner().substring(0,8));
            entity.setParticipantAssignee(entity.getParticipantAssignee().substring(0,8));
            entity.setParticipantRespondent(entity.getParticipantRespondent().substring(0,8));
        });
        disputeCases = disputeCases.stream().sorted(Comparator.comparing(DisputeCase::getCreateDate)).collect(Collectors.toList());
        log.info("Size: {}", disputeCases.size());

        int disputeCount = disputeCases.size();

        if (disputeCount <= maxRowsForXlsx) { // paymentExportDir
            DisputeExcelExporter excelExporter = new DisputeExcelExporter(disputeCases, disputeExportDir);
            excelExporter.export(response);
        } else {
            exportCsv(response, disputeCases);
        }
    }

    public void exportCsv(HttpServletResponse response, List<DisputeCase> disputeCases) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = String.format("Dispute_%s.csv", currentDateTime);
        String filePath = disputeExportDir + "/" + fileName;

        String headerLine = "Reference;Claimant;Claimee;Disp Cat;Status;Status Desc;Create Date;Modif Date;Respondent;Disp Batch Reference;Disp Txid;Disp Instrid;Disp Endtoendid\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(headerLine);

//            int pageIndex = 0;
            int dataIndex = 1;
//            while (true) {
//                if (disputeCases.size() == 0) {
//                    break;
//                }
            for (DisputeCase d : disputeCases) {
                String dataLine = createDataLine(dataIndex, d);
                writer.append(dataLine);
                dataIndex++;
            }
            writer.flush();
//                pageIndex++;
//            }
        }

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("X-Suggested-Filename", fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        File file = new File(filePath);

        // Content-Length
        response.setContentLength((int) file.length());

        try (BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
             BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream())) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
        }
    }

    private String createDataLine(int dataIndex, DisputeCase d) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuilder dataLines = new StringBuilder();
        dataLines.append(d.getReference());
        dataLines.append(";");
        dataLines.append(d.getParticipantAssigner());
        dataLines.append(";");
        dataLines.append(d.getParticipantAssignee());
        dataLines.append(";");
        dataLines.append(d.getCode());
        dataLines.append(";");
        dataLines.append(d.getStatusCode());
        dataLines.append(";");
        dataLines.append(d.getStatusDescription());
        dataLines.append(";");
        dataLines.append(dateFormat.format(d.getCreateDate()));
        dataLines.append(";");
        dataLines.append(dateFormat.format(d.getModifDate()));
        dataLines.append(";");
        dataLines.append(d.getParticipantRespondent());
        dataLines.append(";");
        dataLines.append(d.getRequestNum());
        dataLines.append(";");
        dataLines.append(d.getTxid());
        dataLines.append(";");
        dataLines.append(d.getInstrid());
        dataLines.append(";");
        dataLines.append(d.getEndtoendid());
        dataLines.append(";");
        dataLines.append("\n");

        return dataLines.toString();
    }

    private String like(String s) {
        if(s != null && s.length() > 0) {
            s = s.trim();
            s = "%" + s + "%";
            return s;
        }
        return null;
    }

    private Date convertDate(String s) {
        if (s.isEmpty() || s == null) return null;
        Date date = null;
        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            if(s.length() == 16) {
                s = s + ":00";
            }

            date = searchDateFmt.parse(s);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
