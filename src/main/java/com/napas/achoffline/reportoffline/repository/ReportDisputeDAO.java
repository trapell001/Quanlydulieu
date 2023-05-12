package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.define.ReportDisputeFunctionParameter;
import com.napas.achoffline.reportoffline.models.ReportDisputeDef;
import com.napas.achoffline.reportoffline.models.ReportDisputeFilter;
import org.springframework.stereotype.Component;
import com.napas.achoffline.reportoffline.define.DisputeType;
import com.napas.achoffline.reportoffline.define.ReportOfflineFunctionParameter;
import com.napas.achoffline.reportoffline.define.ReportOfflineParticipantType;
import com.napas.achoffline.reportoffline.models.ReportOfflineDef;
import com.napas.achoffline.reportoffline.models.ReportOfflineFilter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class ReportDisputeDAO {
    @Autowired
    protected DataSource localDataSource;

    public JasperPrint getReport(
            ReportDisputeDef reportDef,
            ReportDisputeFilter filter,
            JasperReport jasperReport,
            Map<String, Object> parameters
    ) throws IOException, JRException {

        String paramBic;
        if (filter.getParticipantType() == null) {
            paramBic = "";
        } else if (filter.getParticipantType() == ReportOfflineParticipantType.INDIVIDUAL) {
            paramBic = filter.getBic();
        } else if (filter.getParticipantType() == ReportOfflineParticipantType.ACH) {
            paramBic = "";
        } else if (filter.getParticipantType() == ReportOfflineParticipantType.IBFT) {
            paramBic = "";
        } else {
            paramBic = "";
        }

        Map<ReportDisputeFunctionParameter, Boolean> mapParamsDef = reportDef.getParameters();

        try (Connection connection = localDataSource.getConnection();
             CallableStatement stmt = connection.prepareCall("{? = call " + reportDef.getFunctionName() + "}")) {
            stmt.registerOutParameter(1, Types.REF_CURSOR);

            int paramIndex = 2;

            if (mapParamsDef.get(ReportDisputeFunctionParameter.DATE_BEGIN)) {
                stmt.setDate(paramIndex++, filter.getDateBegin() != null ? Date.valueOf(filter.getDateBegin()) : null);
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.DATE_END)) {
                stmt.setDate(paramIndex++, filter.getDateEnd() != null ? Date.valueOf(filter.getDateEnd()) : null);
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.SESSION_BEGIN)) {
                stmt.setString(paramIndex++, filter.getSessionBegin());
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.SESSION_END)) {
                stmt.setString(paramIndex++, filter.getSessionEnd());
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.BIC)) {
                stmt.setString(paramIndex++, paramBic);
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.BOC)) {
                stmt.setString(paramIndex++, filter.getBoc() != null ? filter.getBoc().toString() : "");
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.TTC)) {
                stmt.setString(paramIndex++, filter.getTtc());
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.PARTICIPANT_TYPE)) {
                stmt.setString(paramIndex++, filter.getParticipantType() != null ? filter.getParticipantType().toString() : "");
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.TRANS_TYPE)) {
                stmt.setString(paramIndex++, filter.getTransType());
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.PARTICIPAN_ROLE)) {
                stmt.setString(paramIndex++, filter.getParticipantRole() != null ? filter.getParticipantRole().toString() : "");
            }
            if (mapParamsDef.get(ReportDisputeFunctionParameter.DISPUTE_TYPE)) {
                stmt.setString(paramIndex++, filter.getDisputeType() != null ? filter.getDisputeType().toString() : "");
            }
            log.info("SERVICE: {}", filter.getService());
            if (mapParamsDef.get(ReportDisputeFunctionParameter.SERVICE)) {
                stmt.setString(paramIndex++, filter.getService());
            }

            if (mapParamsDef.get(ReportDisputeFunctionParameter.RESPONSE_DATE)) {
                stmt.setDate(paramIndex++, filter.getResponseDate() != null ? Date.valueOf(filter.getResponseDate()) : null);
            }

            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);
                return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportOfflineDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
