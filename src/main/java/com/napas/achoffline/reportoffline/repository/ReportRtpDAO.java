package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.define.ReportOfflineFunctionParameter;
import com.napas.achoffline.reportoffline.define.ReportOfflineParticipantType;
import com.napas.achoffline.reportoffline.define.ReportRtpFunctionParameter;
import com.napas.achoffline.reportoffline.models.ReportOfflineDef;
import com.napas.achoffline.reportoffline.models.ReportOfflineFilter;
import com.napas.achoffline.reportoffline.models.ReportRtpDef;
import com.napas.achoffline.reportoffline.models.ReportRtpFilter;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ReportRtpDAO {
    @Autowired
    protected DataSource localDataSource;

    public JasperPrint getReport(
            ReportRtpDef reportDef,
            ReportRtpFilter filter,
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

        if(paramBic.isEmpty()) paramBic = null;

        Map<ReportRtpFunctionParameter, Boolean> mapParamsDef = reportDef.getParameters();

        try (Connection connection = localDataSource.getConnection();
             CallableStatement stmt = connection.prepareCall("{? = call " + reportDef.getFunctionName() + "}")) {
            stmt.registerOutParameter(1, Types.REF_CURSOR);

            int paramIndex = 2;

            if (mapParamsDef.get(ReportRtpFunctionParameter.DATE_BEGIN)) {
                stmt.setDate(paramIndex++, filter.getDateBegin() != null ? Date.valueOf(filter.getDateBegin()) : null);
            }

            if (mapParamsDef.get(ReportRtpFunctionParameter.DATE_END)) {
                stmt.setDate(paramIndex++, filter.getDateEnd() != null ? Date.valueOf(filter.getDateEnd()) : null);
            }

            if (mapParamsDef.get(ReportRtpFunctionParameter.SESSION_BEGIN)) {
                stmt.setString(paramIndex++, filter.getSessionBegin());
            }

            if (mapParamsDef.get(ReportRtpFunctionParameter.SESSION_END)) {
                stmt.setString(paramIndex++, filter.getSessionEnd());
            }

            if (mapParamsDef.get(ReportRtpFunctionParameter.BIC)) {
                stmt.setString(paramIndex++, paramBic);
            }

//            if (mapParamsDef.get(ReportRtpFunctionParameter.PARTICIPANT_TYPE)) {
//                stmt.setString(paramIndex++, filter.getParticipantType() != null ? filter.getParticipantType().toString() : "");
//            }

            if (mapParamsDef.get(ReportRtpFunctionParameter.PARTICIPAN_ROLE)) {
                stmt.setString(paramIndex++, filter.getParticipantRole() != null ? filter.getParticipantRole().toString() : "");
            }

            if (mapParamsDef.get(ReportRtpFunctionParameter.STATUS)) {
                stmt.setString(paramIndex++, filter.getStatus() != null ? filter.getStatus() : "");
            }

            if (mapParamsDef.get(ReportRtpFunctionParameter.SERVICE)) {
                stmt.setString(paramIndex++, filter.getService() != null ? filter.getService() : "");
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
