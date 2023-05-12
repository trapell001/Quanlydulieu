package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.config.ReportDisputeConfig;
import com.napas.achoffline.reportoffline.config.ReportOfflineConfig;
import com.napas.achoffline.reportoffline.define.DisputeType;
import com.napas.achoffline.reportoffline.define.ReportOfflineExportFileType;
import com.napas.achoffline.reportoffline.define.ReportOfflineParticipantType;
import com.napas.achoffline.reportoffline.define.ReportOfflineRangeType;
import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel1;
import com.napas.achoffline.reportoffline.models.*;
import com.napas.achoffline.reportoffline.repository.ReportDisputeDAO;
import com.napas.achoffline.reportoffline.repository.ReportOfflineDAO;
import com.napas.achoffline.reportoffline.repository.TblRptoHeaderLevel1DAO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReportDisputeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ReportDisputeDAO reportDisputeDAO;

    @Autowired
    private AchSessionService achSessionService;

    @Autowired
    private TblRptoHeaderLevel1DAO transTypeDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportDisputeConfig reportDisputeConfig;

    private Map<String, ReportDisputeDef> mapReportDisputeDef;

    @PostConstruct
    private void postConstruct() {
        mapReportDisputeDef = reportDisputeConfig.getListReportDispute().stream()
                .collect(Collectors.toUnmodifiableMap(ReportDisputeDef::getReportCode, Function.identity()));
    }

    public List<ReportDisputeDefDTO> listAllReportOffline() {
        return reportDisputeConfig.getListReportDispute().stream()
                .filter((var p) -> p.getEnabled() == true)
                .map(p -> new ReportDisputeDefDTO(p.getReportCode(), p.getName(), p.getParameters()))
                .collect(Collectors.toList());
    }


    public void generateReport(
            String reportCode,
            ReportDisputeFilter filter,
            ReportOfflineExportFileType format,
            OutputStream outputStream) {

        ReportDisputeDef roDef = mapReportDisputeDef.get(reportCode);

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String fullName = userDetailsDTO.getFullName();

        Map<String, Object> parameters = buildParam(filter,roDef);
        parameters.put("pReportUser", fullName);

        if (format == ReportOfflineExportFileType.PDF) {
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
        } else {
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
        }

        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(roDef.getJasperFile()));
            JasperPrint jasperPrint = reportDisputeDAO.getReport(
                    roDef,
                    filter,
                    jasperReport,
                    parameters
            );
            if (format == ReportOfflineExportFileType.PDF) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            } else {
                JRXlsxExporter exporter = new JRXlsxExporter();
                SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
                reportConfigXLS.setSheetNames(new String[]{"sheet1"});
                exporter.setConfiguration(reportConfigXLS);
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
        } catch (IOException ex) {
            logger.error("Loi:" + ex.getMessage());
        } catch (JRException ex) {
            logger.error("Loi:" + ex.getMessage());
        }
    }

    private Map<String, Object> buildParam(ReportDisputeFilter filter, ReportDisputeDef roDef) {
        ReportOfflineRangeType rangeType = getRangeType(filter, roDef);
        if (rangeType == null) {
            log.info("null");
            return null;
        }
        log.info("not null");
        LocalDateTime currentDate = LocalDateTime.now();
        TblSessions achSessionBegin = null, achSessionEnd = null;
        if (rangeType == ReportOfflineRangeType.SESSION || rangeType == ReportOfflineRangeType.BOTH) {
            achSessionBegin = achSessionService.getTblSessionById(Long.valueOf(filter.getSessionBegin()));
            achSessionEnd = achSessionService.getTblSessionById(Long.valueOf(filter.getSessionEnd()));

            if (achSessionBegin == null || achSessionEnd == null) {
                return null;
            }
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateBegin = null, dateEnd = null;
        if (rangeType == ReportOfflineRangeType.SESSION) {
            dateBegin = achSessionBegin.getStartTime();
            dateEnd = achSessionEnd.getFinishTime();
        } else if (rangeType == ReportOfflineRangeType.TIME || rangeType == ReportOfflineRangeType.BOTH){
            LocalDateTime filterDateEnd = filter.getDateEnd().atStartOfDay().plusDays(1).minusSeconds(1);
            LocalDateTime filterDateBegin = filter.getDateBegin().atStartOfDay();

            if (rangeType == ReportOfflineRangeType.TIME) {
                dateBegin = filterDateBegin;
                dateEnd = filterDateEnd;
            } else {
                if (filterDateBegin.isBefore(achSessionBegin.getStartTime())) {
                    dateBegin = achSessionBegin.getStartTime();
                } else {
                    dateBegin = filterDateBegin;
                }

                if (filterDateEnd.isBefore(achSessionEnd.getFinishTime())) {
                    dateEnd = filterDateEnd;
                } else {
                    dateEnd = achSessionEnd.getFinishTime();
                }
            }
        }

        String paramBoc = filter.getBoc() != null ? filter.getBoc().toString() : "Tất cả";
        String paramBic;
        if (filter.getParticipantType() == null) {
            paramBic = "Tất cả";
        } else if (filter.getParticipantType() == ReportOfflineParticipantType.INDIVIDUAL) {
            paramBic = filter.getBic();
        } else if (filter.getParticipantType() == ReportOfflineParticipantType.ACH) {
            paramBic = "ACH";
        } else if (filter.getParticipantType() == ReportOfflineParticipantType.IBFT) {
            paramBic = "IBFT";
        } else {
            paramBic = "Tất cả";
        }

        String paramDisputeType;
        if(filter.getDisputeType() == null) {
            paramDisputeType = "Tất cả";
        } else if(filter.getDisputeType() == DisputeType.RQAD) {
            paramDisputeType = "RQAD";
        } else if(filter.getDisputeType() == DisputeType.RQRN) {
            paramDisputeType = "RQRN";
        } else if(filter.getDisputeType() == DisputeType.RQNF) {
            paramDisputeType = "RQNF";
        } else if(filter.getDisputeType() == DisputeType.RQSP) {
            paramDisputeType = "RQSP";
        } else if(filter.getDisputeType() == DisputeType.GDFT) {
            paramDisputeType = "GDFT";
        } else {
            paramDisputeType = "Tất cả";
        }

        String paramTtc = filter.getTtc() != null && filter.getTtc().length() > 0 ? filter.getTtc() : "Tất cả";
        String paramTransType;
        if (filter.getTransType() != null && filter.getTransType().length() > 0) {
            TblRptoHeaderLevel1 transType = transTypeDAO.findByHeaderCode(filter.getTransType()).orElse(null);

            if (transType == null) {
                paramTransType = "N/A";
            } else {
                paramTransType = transType.getHeaderName();
            }
        } else {
            paramTransType = "Tất cả";
        }

        String paramSession;
        if (filter.getSessionBegin() != null && !filter.getSessionBegin().isBlank()) {
            if (filter.getSessionBegin().contentEquals(filter.getSessionEnd())) {
                paramSession = filter.getSessionBegin();
            } else {
                paramSession = String.format("Từ %s đến %s", filter.getSessionBegin(), filter.getSessionEnd());
            }
        } else {
            paramSession = "";
        }

        String paramVaiTro = filter.getParticipantRole() != null ? filter.getParticipantRole().toString() : "Tất cả";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", getClass().getResourceAsStream("/report/image/napas.png"));
        parameters.put("pFromDate", dateBegin != null ? dateBegin.format(dateTimeFormatter) : "null");
        parameters.put("pToDate",   dateEnd != null ? dateEnd.format(dateTimeFormatter) : "null");
        parameters.put("pResponseDate", filter.getResponseDate() != null ? filter.getResponseDate().format(dateFormatter) : "null");
        parameters.put("boc", paramBoc);
        parameters.put("pParticCode", paramBic);
        parameters.put("pReportDate", currentDate.format(dateTimeFormatter));
        parameters.put("pService", filter.getService());
        parameters.put("pDisputeType", paramDisputeType);
        parameters.put("ttc", paramTtc);
        parameters.put("loaiGiaoDich", paramTransType);
        parameters.put("pSessionID", paramSession);
        parameters.put("vaitro", paramVaiTro);
        parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
        return parameters;
    }

    private static ReportOfflineRangeType getRangeType(ReportDisputeFilter filter,ReportDisputeDef roDef) {
        if (filter.getDateBegin() == null && filter.getDateEnd() == null
                && (filter.getSessionBegin() == null || filter.getSessionBegin().isBlank())
                && (filter.getSessionEnd() == null || filter.getSessionEnd().isBlank())
                && roDef.getName().equals("BC.03 - BÁO CÁO CHI TIẾT CÁC GIAO DỊCH ĐẾN HẠN TRẢ LỜI TRA SOÁT")) {
            return ReportOfflineRangeType.NOT_BOTH;
        }

        if (filter.getSessionBegin() != null && !filter.getSessionBegin().isBlank()
                && (filter.getSessionEnd() == null || filter.getSessionEnd().isBlank())) {
            return null;
        }

        if (filter.getSessionEnd() != null && !filter.getSessionEnd().isBlank()
                && (filter.getSessionBegin() == null || filter.getSessionBegin().isBlank())) {
            return null;
        }

        if (filter.getDateBegin() != null && filter.getDateEnd() == null) {
            return null;
        }


        if (filter.getDateEnd() != null && filter.getDateBegin() == null) {
            return null;
        }

        if (filter.getDateBegin() == null && filter.getDateEnd() == null
                && (filter.getSessionBegin() == null || filter.getSessionBegin().isBlank())
                && (filter.getSessionEnd() == null || filter.getSessionEnd().isBlank())) {
            return null;
        }

        if (filter.getDateBegin() != null
                && (filter.getSessionBegin() == null || filter.getSessionBegin().isBlank())) {
            return ReportOfflineRangeType.TIME;
        }

        if (filter.getDateBegin() == null
                && filter.getSessionBegin() != null && !filter.getSessionBegin().isBlank()) {
            return ReportOfflineRangeType.SESSION;
        }

        if (filter.getDateBegin() != null
                && filter.getSessionBegin() != null
                && !filter.getSessionBegin().isBlank()) {
            return ReportOfflineRangeType.BOTH;
        }

        return null;
    }
}
