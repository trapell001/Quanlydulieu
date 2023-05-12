/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.models.PaymentFilter;
import com.napas.achoffline.reportoffline.models.PaymentsExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huynx
 */
@Repository
public class PaymentExportDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    protected DataSource localDataSource;
    
    @Value("${napas.ach.offline.export.max-payment-per-cicle}")
    private long maxRows;
    
    private static String buildSql(PaymentFilter f, boolean countOnly) {
        String transStatusCond = "";
        if (f.transStatus() != null && f.transStatus().length() > 0) {
            if (f.transStatus().contentEquals("Others")) {
                transStatusCond = "AND A_TRANS_STATUS not in ('Posted', 'Settled', 'Rejected')\n";
            } else {
                transStatusCond = "AND A_TRANS_STATUS = '" + f.transStatus() + "'\n";
            }
        }

        String bocCond = "";
        if (f.boc() != null && f.boc().length() > 0) {
            if (f.boc().contentEquals("Others")) {
                bocCond = "AND MX_BANK_OP_CODE not in ('CSDC', 'BPDC')\n";
            } else {
                bocCond = "AND MX_BANK_OP_CODE = '" + f.boc() + "'\n";
            }
        }

        String ttcCond = "";
        if (f.ttc() != null && f.ttc().length() > 0) {
            if (f.ttc().contentEquals("Others")) {
                ttcCond = "AND MX_TRANS_TYPE_CODE not in ('001', '008')\n";
            } else {
                ttcCond = "AND MX_TRANS_TYPE_CODE = '" + f.ttc() + "'\n";
            }
        }

        String covidCond = "";
        if (f.covidOnly()) {
            covidCond = "and exists (select 1 from tmp_covid_account a where hp.MX_INSTRUCTED_AGENT = a.participant_code and hp.mx_creditor_account = a.stk) \n";
        }
        
        String fetchData = "*";
        if(countOnly) {
            fetchData = "count(1)\n";
        } else {
            fetchData = "A_PROCESS_DATE,\n"
                    + "MX_TRANS_DATE,\n"
                    + "A_SESSION_ID,\n"
                    + "MX_TXID,\n"
                    + "MX_TYPE,\n"
                    + "MX_BANK_OP_CODE,\n"
                    + "I_CHANNEL_ID,\n"
                    + "MX_TRANS_TYPE_CODE,\n"
                    + "A_TRANS_STATUS,\n"
                    + "MX_AUTH_INFO,\n"
                    + "MX_SETTLEMENT_AMOUNT,\n"
                    + "MX_INSTRUCTING_AGENT,\n"
                    + "MX_INSTRUCTED_AGENT,\n"
                    + "MX_INSTRID,\n"
                    + "MX_ENDTOENDID,\n"
                    + "MX_ACH_RESULT_CODE,\n"
                    + "MX_PARTY_RESULT_CODE,\n"
                    + "HAVE_RETURN";
        }

        String sql = "SELECT "
                + fetchData
                + " FROM HIS_PAYMENTS hp WHERE A_PROCESS_DATE BETWEEN ? AND ? "
                + " and MX_TXID like ?\n"
                + "and (A_SESSION_ID >= NVL(?, A_SESSION_ID)) \n"
                + "and (A_SESSION_ID <= NVL(?, A_SESSION_ID)) \n"
                + "and (((A_RTP_PMTINFID is not null and ? = '1') or (A_RTP_PMTINFID is null and ? = '0')) or ? is null) \n"
                + transStatusCond
                + "and MX_AUTH_INFO = NVL(?, MX_AUTH_INFO)\n"
                + "and (MX_SETTLEMENT_AMOUNT >= NVL(?, MX_SETTLEMENT_AMOUNT))\n"
                + "and (MX_SETTLEMENT_AMOUNT <= NVL(?, MX_SETTLEMENT_AMOUNT))\n"
                + "and (MX_DEBTOR_AGENT like ? or ? is null)\n"
                + "and (MX_DEBTOR_AGENT like ? or ? is null)\n"
                + "and (MX_CREDITOR_AGENT like ? or ? is null)\n"
                + "and (MX_CREDITOR_AGENT like ? or ? is null)\n"
                + "and upper(MX_DEBTOR_NAME) like ?\n"
                + "and upper(MX_CREDITOR_NAME) like ?\n"
                + "and upper(MX_DEBTOR_ACCOUNT) like ?\n"
                + "and upper(MX_CREDITOR_ACCOUNT) like ?\n"
                + "and MX_DEBTOR_ACCOUNT_TYPE = NVL(?, MX_DEBTOR_ACCOUNT_TYPE)\n"
                + "and MX_CREDITOR_ACCOUNT_TYPE = NVL(?, MX_CREDITOR_ACCOUNT_TYPE)\n"
                + "and I_CHANNEL_ID = NVL(?, I_CHANNEL_ID)\n"
                + bocCond
                + ttcCond
                + "and (I_MSG_TO_CREDITOR is null or upper(I_MSG_TO_CREDITOR) like ?)\n"
                + "and MX_TYPE = NVL(?, MX_TYPE)\n"
                + covidCond;
        
        return sql;
    }
    
    public int setParamForStatement(PreparedStatement s, PaymentFilter f) throws SQLException {
        int i = 0;
        s.setTimestamp(++i, Timestamp.valueOf(f.beginDate()));
        s.setTimestamp(++i, Timestamp.valueOf(f.endDate()));
        s.setString(++i, "%" + f.msgId() + "%");
        s.setObject(++i, f.sessionId(), Types.INTEGER);
        s.setObject(++i, f.toSessionId(), Types.INTEGER);
        s.setString(++i, f.rtp());
        s.setString(++i, f.rtp());
        s.setString(++i, f.rtp());
        s.setString(++i, f.authInfo());
        s.setBigDecimal(++i, f.minAmount());
        s.setBigDecimal(++i, f.maxAmount());
        s.setString(++i,"%" + f.debitBank() + "%");
        s.setString(++i,f.debitBank());
        s.setString(++i,"%" + f.debtorSystem() + "%");
        s.setString(++i,f.debtorSystem());
        s.setString(++i,"%" + f.creditBank() + "%");
        s.setString(++i,f.creditBank());
        s.setString(++i,"%" + f.creditorSystem() + "%");
        s.setString(++i,f.creditorSystem());
        s.setString(++i, "%" + f.deptorName() + "%");
        s.setString(++i, "%" + f.creditorName() + "%");
        s.setString(++i, "%" + f.deptorAccount() + "%");
        s.setString(++i, "%" + f.creditorAccount() + "%");
        s.setString(++i, f.deptorAccountType());
        s.setString(++i, f.creditorAccountType());
        s.setString(++i, f.channelId());
        s.setString(++i, "%" + f.msgToCreditor() + "%");
        s.setString(++i, f.mxType());
        return i;
    }
    
    public long countPayment(PaymentFilter f) {
        long result = 0;
        String sql = buildSql(f, true);
        
        logger.info("Full SQL:'{}'", sql);

        try (Connection conn = localDataSource.getConnection();
                PreparedStatement s = conn.prepareStatement(sql)) {
            setParamForStatement(s, f);

            try (ResultSet rs = s.executeQuery()) {
                if (rs != null&&rs.next()) {
                    result = rs.getLong(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Loi chay lenh SQL:" + ex.getMessage());
        }
        
        return result;
    }

    public List<PaymentsExport> searchAllPayments(
            PaymentFilter f, boolean pagingEnable, long pageIndex
    ) {
        List<PaymentsExport> result = new ArrayList<>();
        
        String sql = buildSql(f, false);
        
        if(pagingEnable) {
            sql = sql + "\n"
                    + "ORDER BY A_DOC_ID\n"
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        }
        
        logger.info("Full SQL:'{}'", sql);

        try (Connection conn = localDataSource.getConnection();
                PreparedStatement s = conn.prepareStatement(sql)) {
            
            int currentParamIndex = setParamForStatement(s, f);
            if(pagingEnable) {
                s.setLong(++currentParamIndex, pageIndex * maxRows);
                s.setLong(++currentParamIndex, maxRows);
            }

            try (ResultSet rs = s.executeQuery()) {
                if (rs != null) {
                    while (rs.next()) {
                        PaymentsExport p = new PaymentsExport(
                                rs.getTimestamp("A_PROCESS_DATE").toLocalDateTime(),
                                rs.getTimestamp("MX_TRANS_DATE").toLocalDateTime(),
                                rs.getLong("A_SESSION_ID"),
                                rs.getString("MX_TXID"),
                                rs.getString("MX_INSTRID"),
                                rs.getString("MX_ENDTOENDID"),
                                rs.getString("MX_TYPE"),
                                rs.getString("I_CHANNEL_ID"),
                                rs.getString("A_TRANS_STATUS"),
                                rs.getString("MX_AUTH_INFO"),
                                rs.getBigDecimal("MX_SETTLEMENT_AMOUNT"),
                                rs.getString("MX_INSTRUCTING_AGENT"),
                                rs.getString("MX_INSTRUCTED_AGENT"),
                                rs.getString("MX_ACH_RESULT_CODE"),
                                rs.getString("MX_PARTY_RESULT_CODE"),
                                rs.getString("MX_TRANS_TYPE_CODE"));
                        result.add(p);
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Loi chay lenh SQL:" + ex.getMessage());
        }

        return result;
    }
}
