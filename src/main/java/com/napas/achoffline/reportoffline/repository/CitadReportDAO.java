/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package com.napas.achoffline.reportoffline.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author huynx
 */
@Component
public class CitadReportDAO {

    @Autowired
    protected DataSource localDataSource;

    public String exportToCitad(String sessionId) {
        try (Connection connection = localDataSource.getConnection();
                CallableStatement stmt = connection.prepareCall("{? = call pkg_rpt_offline.INSERT_CITAD_NP_BATCH(?)}")) {
            stmt.registerOutParameter(1, Types.VARCHAR);
            stmt.setString(2, sessionId);

            stmt.execute();

            String result = stmt.getString(1);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ReportOfflineDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "99|Có lỗi trong quá trình xuất CITAD";
        }
    }
    
    public String exportToCitadForSessionRange(String fromSessionId, String toSessionId) {
        try (Connection connection = localDataSource.getConnection();
                CallableStatement stmt = connection.prepareCall("{? = call pkg_rpt_offline.INSERT_CITAD_SESSION_RANGE(?, ?)}")) {
            stmt.registerOutParameter(1, Types.VARCHAR);
            stmt.setString(2, fromSessionId);
            stmt.setString(3, toSessionId);

            stmt.execute();

            String result = stmt.getString(1);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ReportOfflineDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "99|Có lỗi trong quá trình xuất CITAD";
        }
    }
}
