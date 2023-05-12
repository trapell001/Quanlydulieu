package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.models.HisReportOnlineDTO;
import com.napas.achoffline.reportoffline.models.HisReportOnlinePerformaneDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HisReportOnlineDAO {
    @Autowired
    protected DataSource localDataSource;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    public List<HisReportOnlinePerformaneDTO> graphParticipant(
            String fromSessionId,
            String toSessionId,
            String participant,
            String systemModule,
            String deliveryMethod,
            String rptType) {
        List<HisReportOnlinePerformaneDTO> hisReportOnlines = new ArrayList<>();

        String sql = "SELECT "
                + "(max(end_date) - min(create_date)) as processing_time , \n"
                + "sum(NBOFNTRIES) as slgd, \n"
                + "SESSION_ID \n"
                + "FROM HIS_REPORT_ONLINE \n"

                + "WHERE SESSION_ID >= ? \n"

                + "and SESSION_ID <= ? \n"

                + "and PARTICIPANT like ?\n"

                + "and SYSTEM_MODULE like ?\n"

                + "and DELIVERY_METHOD like ?\n"
                + "and RPT_TYPE like ?\n"
                + "group by session_id"
                ;
        try (Connection conn = localDataSource.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, fromSessionId);
            s.setString(2, toSessionId);
            s.setString(3, "%" + participant + "%");
            s.setString(4, "%" + systemModule + "%");
            s.setString(5, "%" + deliveryMethod + "%");
            s.setString(6, "%" + rptType + "%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                hisReportOnlines.add(new HisReportOnlinePerformaneDTO(
                                rs.getDouble(1),
                                rs.getLong(2),
                                rs.getLong(3)
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hisReportOnlines;

    }
    public List<HisReportOnlinePerformaneDTO> graph(
            String fromSessionId,
            String toSessionId,
            String systemModule,
            String deliveryMethod,
            String rptType
    ) {
        List<HisReportOnlinePerformaneDTO> hisReportOnlines = new ArrayList<>();

        String sql = "SELECT "
                + "(max(end_date) - min(create_date)) as processing_time , \n"
                + "sum(NBOFNTRIES) as slgd, \n"
                + "SESSION_ID \n"
                + "FROM HIS_REPORT_ONLINE \n"

                + "WHERE SESSION_ID >= ? \n"

                + "and SESSION_ID <= ? \n"


                + "and SYSTEM_MODULE like ?\n"

                + "and DELIVERY_METHOD like ?\n"

                + "and RPT_TYPE like ?\n"

                + "group by session_id"
                ;
        try (Connection conn = localDataSource.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, fromSessionId);
            s.setString(2, toSessionId);
            s.setString(3, "%" + systemModule + "%");
            s.setString(4, "%" + deliveryMethod + "%");
            s.setString(5, "%" + rptType + "%");
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                hisReportOnlines.add(new HisReportOnlinePerformaneDTO(
                                rs.getDouble(1),
                                rs.getLong(2),
                                rs.getLong(3)
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hisReportOnlines;

    }

    public Page<HisReportOnlineDTO> pageSearching(
            LocalDateTime beginDate,
            LocalDateTime endDate,
            String fromSessionId,
            String toSessionId,
            String rptType,
            String participant,
            String systemModule,
            Long pageNumber,
            String status,
            String deliveryMethod,
            String msgId,
            String groupMsgId,
            String fireType,
            Pageable pageable) {
        List<HisReportOnlineDTO> hisReportOnlines = new ArrayList<>();
        String pageNumberSql="";
        if(pageNumber==null){
            pageNumberSql=" ";
        }
        else {
            pageNumberSql="and PAGE_NUMBER = " +pageNumber+"\n";
        }
        String sql = "SELECT "
                + "ID, \n"
                + "CREATE_DATE, \n"
                + "START_DATE, \n"
                + "END_DATE, \n"
                + "RPT_TYPE, \n"
                + "PARTICIPANT, \n"
                + "ACCOUNT, \n"
                + "SYSTEM_MODULE, \n"
                + "PAGE_NUMBER, \n"
                + "STATUS, \n"
                + "SESSION_ID, \n"
                + "TOTAL_PAGE, \n"
                + "NBOFNTRIES, \n"
                + "DELIVERY_METHOD, \n"
                + "MSGID, \n"
                + "GROUP_MSGID, \n"
                + "TOTAL_ENTRIES, \n"
                + "FIRE_TYPE, \n"
                + "HMAC_DIGEST \n"

                + "FROM HIS_REPORT_ONLINE \n"

                + "WHERE CREATE_DATE BETWEEN ? AND ? \n"

                + "and SESSION_ID >= ? \n"

                + "and SESSION_ID <= ? \n"

                + "and RPT_TYPE like ?\n"

                + "and PARTICIPANT like ?\n"

                + "and SYSTEM_MODULE like ?\n"

                + pageNumberSql

                + "and STATUS like ?\n"

                + "and DELIVERY_METHOD like ?\n"

                + "and MSGID like ?\n"

                + "and GROUP_MSGID like ?\n"

                + "and FIRE_TYPE like ?\n";

        try (Connection conn = localDataSource.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setTimestamp(1, Timestamp.valueOf(beginDate));
            s.setTimestamp(2, Timestamp.valueOf(endDate));
            s.setString(3, fromSessionId);
            s.setString(4, toSessionId);
            System.out.println(fromSessionId);
            System.out.println(toSessionId);
            s.setString(5, "%" + rptType + "%");
            s.setString(6, "%" + participant + "%");
            s.setString(7, "%" + systemModule + "%");
            s.setString(8, "%" + status + "%");
            s.setString(9, "%" + deliveryMethod + "%");
            s.setString(10, "%" + msgId + "%");
            s.setString(11, "%" + groupMsgId + "%");
            s.setString(12, "%" + fireType + "%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                hisReportOnlines.add(new HisReportOnlineDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getLong(9),
                        rs.getString(10),
                        rs.getLong(11),
                        rs.getLong(12),
                        rs.getLong(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getLong(17),
                        rs.getString(18),
                        rs.getString(19)

                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toPage(hisReportOnlines, pageable);

    }

    public HisReportOnlineDTO findById(Long id) {
        String sql = "SELECT "
                + "ID, \n"
                + "CREATE_DATE, \n"
                + "START_DATE, \n"
                + "END_DATE, \n"
                + "RPT_TYPE, \n"
                + "PARTICIPANT, \n"
                + "ACCOUNT, \n"
                + "SYSTEM_MODULE, \n"
                + "PAGE_NUMBER, \n"
                + "STATUS, \n"
                + "SESSION_ID, \n"
                + "TOTAL_PAGE, \n"
                + "NBOFNTRIES, \n"
                + "DELIVERY_METHOD, \n"
                + "MSGID, \n"
                + "GROUP_MSGID, \n"
                + "TOTAL_ENTRIES, \n"
                + "FIRE_TYPE, \n"
                + "HMAC_DIGEST \n"

                + "FROM HIS_REPORT_ONLINE \n"

                + "WHERE ID = ?\n";
        HisReportOnlineDTO hisReportOnlineDTO = null;
        try (Connection conn = localDataSource.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setLong(1, id);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                hisReportOnlineDTO = new HisReportOnlineDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getLong(9),
                        rs.getString(10),
                        rs.getLong(11),
                        rs.getLong(12),
                        rs.getLong(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getLong(17),
                        rs.getString(18),
                        rs.getString(19)

                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hisReportOnlineDTO;
    }

    public Page<HisReportOnlineDTO> toPage(List<HisReportOnlineDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if(start > list.size())
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

}
