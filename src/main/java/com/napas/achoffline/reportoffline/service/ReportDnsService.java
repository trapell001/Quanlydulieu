package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.config.ReportDnsConfig;
import com.napas.achoffline.reportoffline.define.ReportOfflineExportFileType;
import com.napas.achoffline.reportoffline.define.ReportOfflineParticipantType;
import com.napas.achoffline.reportoffline.define.ReportOfflineRangeType;
import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel1;
import com.napas.achoffline.reportoffline.models.*;
import com.napas.achoffline.reportoffline.repository.ReportDnsDAO;
import com.napas.achoffline.reportoffline.repository.ReportOfflineDAO;
import com.napas.achoffline.reportoffline.repository.TblRptoHeaderLevel1DAO;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
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

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportDnsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ReportDnsDAO reportDnsDAO;

    @Autowired
    private AchSessionService achSessionService;

    @Autowired
    private TblRptoHeaderLevel1DAO transTypeDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportDnsConfig reportDnsConfig;

    private Map<String, ReportDnsDef> mapReportDnsDef;

    @PostConstruct
    private void postConstruct() {
        mapReportDnsDef = reportDnsConfig.getListReportDns().stream()
                .collect(Collectors.toUnmodifiableMap(ReportDnsDef::getReportCode, Function.identity()));
    }

    public List<ReportDnsDefDTO> listAllReportDns() {
        return reportDnsConfig.getListReportDns().stream()
                .filter((var p) -> p.getEnabled() == true)
                .map(p -> new ReportDnsDefDTO(p.getReportCode(), p.getName(), p.getParameters()))
                .collect(Collectors.toList());
    }

    public List<TblRptoHeaderLevel1DTO> listAllReportOfflineTransType() {
        return transTypeDAO.findAll(Sort.by("headerOrder")).stream()
                .map(p -> new TblRptoHeaderLevel1DTO(p.getId(), p.getHeaderCode(), p.getHeaderName(), p.getHeaderOrder()))
                .collect(Collectors.toList());
    }

    public void generateReport(
            String reportCode,
            ReportDnsFilter filter,
            ReportOfflineExportFileType format,
            OutputStream outputStream) {

        ReportDnsDef roDef = mapReportDnsDef.get(reportCode);

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String fullName = userDetailsDTO.getFullName();

        Map<String, Object> parameters = buildParam(filter);
        parameters.put("pReportUser", fullName);

        if (format == ReportOfflineExportFileType.PDF) {
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
        } else {
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
        }

        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(roDef.getJasperFile()));
            JasperPrint jasperPrint = reportDnsDAO.getReport(
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

    private Map<String, Object> buildParam(ReportDnsFilter filter) {
        ReportOfflineRangeType rangeType = getRangeType(filter);
        if (rangeType == null) {
            return null;
        }

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
        LocalDateTime dateBegin, dateEnd;
        if (rangeType == ReportOfflineRangeType.SESSION) {
            dateBegin = achSessionBegin.getStartTime();
            dateEnd = achSessionEnd.getFinishTime();
        } else {
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

        String status = filter.getStatus();
        if(filter.getStatus() == null || filter.getStatus().length() == 0) {
            status = "Tất cả";
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

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", getClass().getResourceAsStream("/report/image/napas.png"));
        parameters.put("pFromDate", dateBegin.format(dateTimeFormatter));
        parameters.put("pToDate", dateEnd.format(dateTimeFormatter));
        parameters.put("boc", paramBoc);
        parameters.put("pParticCode", paramBic);
        parameters.put("pReportDate", currentDate.format(dateTimeFormatter));
        parameters.put("pService", "DNS");
        parameters.put("ttc", paramTtc);
        parameters.put("loaiGiaoDich", paramTransType);
        parameters.put("pSessionID", paramSession);
        parameters.put("vaitro", paramVaiTro);
        parameters.put("status", status);
        parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);

        return parameters;
    }

    private static ReportOfflineRangeType getRangeType(ReportDnsFilter filter) {
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
