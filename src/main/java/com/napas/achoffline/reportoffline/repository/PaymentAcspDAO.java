package com.napas.achoffline.reportoffline.repository;


import com.napas.achoffline.reportoffline.models.PaymentFilterAcspNaut;
import com.napas.achoffline.reportoffline.models.PaymentsExportAcspNaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
// sửa sql rs
public class PaymentAcspDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    protected DataSource localDataSource;

    @Value("${napas.ach.offline.export.max-payment-per-cicle}")
    private long maxRows;

    private static String buildSql(PaymentFilterAcspNaut f, boolean countOnly) {
        String sql = "SELECT "
                + "h1.A_PROCESS_DATE ,\n"
                + "h1.MX_TRANS_DATE,\n"
                + "h1.MX_TXID,\n"
                + "h1.MX_MSGID,\n"
                + "h1.A_TRANS_STATUS,\n"
                + "h1.MX_AUTH_INFO,\n"
                + "h1.MX_SETTLEMENT_AMOUNT,\n"
                + "h1.MX_DEBTOR_AGENT,\n"
                + "h1.MX_CREDITOR_AGENT,\n"
                + "h1.MX_TYPE,\n"
                + "h1.A_SESSION_ID,\n"
                + "h2.MX_TXID,\n"
                + "h2.A_TRANS_STATUS,\n"
                + "h2.MX_AUTH_INFO,\n"
                + "h2.MX_SETTLEMENT_AMOUNT,\n"
                + "h2.MX_TRANS_DATE,\n"
                + "h2.A_SESSION_ID\n"
                + "FROM HIS_PAYMENTS h1 LEFT JOIN HIS_PAYMENTS h2 ON h1.mx_txid = h2.mx_orig_txid\n"
                + "WHERE "
                + "h1.A_PROCESS_DATE BETWEEN ? AND ? \n"
                + "and h1.MX_TYPE like ?\n"
                + "and h1.A_TRANS_STATUS in(?,?)\n"
                + "and h1.MX_AUTH_INFO like ?\n"
                + "and h1.MX_DEBTOR_AGENT like ?\n"
                + "and h1.HAVE_RETURN like ?\n"
                + "or "
                + "h1.A_PROCESS_DATE BETWEEN ? AND ? \n"
                + "and h1.MX_TYPE like ?\n"
                + "and h1.A_TRANS_STATUS in(?,?)\n"
                + "and h1.MX_AUTH_INFO like ?\n"
                + "and h1.MX_CREDITOR_AGENT like ?\n"
                + "and h1.HAVE_RETURN like ?\n"

                ;
        return sql;
    }

    public int setParamForStatement(PreparedStatement s, PaymentFilterAcspNaut f) throws SQLException {
        int i = 0;
        s.setTimestamp(++i, Timestamp.valueOf(f.beginDate()));
        s.setTimestamp(++i, Timestamp.valueOf(f.endDate()));
        s.setString(++i, "%" + f.mxType() + "%");
        s.setString(++i, "Posted");
        s.setString(++i, "Settled");
        s.setString(++i, "NAUT");
        s.setString(++i, "%" + f.participant());
        s.setString(++i, "%" + f.haveReturn() + "%");
        s.setTimestamp(++i, Timestamp.valueOf(f.beginDate()));
        s.setTimestamp(++i, Timestamp.valueOf(f.endDate()));
        s.setString(++i, "%" + f.mxType() + "%");
        s.setString(++i, "Posted");
        s.setString(++i, "Settled");
        s.setString(++i, "NAUT");
        s.setString(++i, "%" + f.participant());
        s.setString(++i, "%" + f.haveReturn() + "%");
        return i;
    }

    public Page<PaymentsExportAcspNaut> searchAllPaymentsAcspNaut(PaymentFilterAcspNaut f, Pageable pageable

    ) {
        List<PaymentsExportAcspNaut> result = new ArrayList<>();

        String sql = buildSql(f, false);


        try (Connection conn = localDataSource.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            setParamForStatement(s, f);
            try (ResultSet rs = s.executeQuery()) {
                if (rs != null) {
                    while (rs.next()) {
                        String endDate=null;
                        String beginDate=null;
                        String endDate004=null;

                        if(rs.getString(16)==null){
                            endDate004 = rs.getString(16);
                        }
                        if(rs.getString(16)!=null){
                            endDate004 = rs.getString(16).substring(0,19);
                        }
                        if(rs.getString(1)==null){
                            beginDate =  rs.getString(1);
                        }
                        if(rs.getString(1)!=null){
                            beginDate = rs.getString(1).substring(0,19);
                        }
                        if(rs.getString(2)==null){
                            endDate =  rs.getString(2);
                        }
                        if(rs.getString(2)!=null){
                            endDate = rs.getString(2).substring(0,19);
                        }
                        PaymentsExportAcspNaut p = new PaymentsExportAcspNaut(
                                beginDate,
                                endDate,
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getString(10),
                                rs.getLong(11),
                                rs.getString(12),
                                rs.getString(13),
                                rs.getString(14),
                                rs.getString(15),
                                endDate004,
                                rs.getString(17)
                        );
                        result.add(p);
                    }
                }
            }

        } catch (SQLException ex) {
            logger.error("Loi chay lenh SQL:" + ex.getMessage());
        }
        Page<PaymentsExportAcspNaut> paymentsExportPage = toPage(result,pageable);
        return paymentsExportPage;
    }
    public Page<PaymentsExportAcspNaut> toPage(List<PaymentsExportAcspNaut> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if(start > list.size())
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}