package com.example.repositories;

import com.example.model.TrackingData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Manages interaction with database
 */
@Repository
public class TrackingRepository {
    private static final Logger logger = LoggerFactory.getLogger(TrackingRepository.class);

    private final JdbcTemplate jdbc;

    public TrackingRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Saves the measurement results to database
     * Measurement results should be passed as object of TrackingData class
      * @param data to be saved
     */
    public void save(TrackingData data) {
        String sql = """
            insert into tracking_data(executed_at, package_name, method_name, execution_time)
            values(?, ?, ?, ?)
            """;
        jdbc.update(sql, data.getExecutedAt(), data.getPackageName(), data.getMethodName(), data.getExecutionTime());
    }

    /**
     * Finds all saved benchmark data
     * @return list of TrackingData objects
     */
    public List<TrackingData> findAll() {
        String sql = """
            select id, executed_at, package_name, method_name, execution_time
            from tracking_data
            """;
        return jdbc.query(sql, new TrackingDataMapper());
    }

    /**
     * Finds average execution time of specified method
     * @param methodName - method name without specifiers
     * @return long value for average execution time
     */
    public Long findMethodAverageTime(String methodName) {
        String sql = """
                select avg(execution_time) from tracking_data
                where method_name like ?
                """;
        return jdbc.queryForObject(sql, Long.class, "%" + methodName + "%");
    }

    /**
     * Finds total execution time of specified method
     * @param methodName - method name without specifiers
     * @return long value for total execution time
     */
    public Long findMethodTotalTime(String methodName) {
        String sql = """
                select sum(execution_time) from tracking_data
                where method_name like ?
                """;
        return jdbc.queryForObject(sql, Long.class, "%" + methodName + "%");
    }

    /**
     * Finds average execution time of methods from specified group of methods (package or class)
     * @param packageName - part of package name
     * @return long value for average execution time
     */
    public Long findGroupAverageTime(String packageName) {
        String sql = """
                select avg(execution_time) from tracking_data 
                group by package_name 
                having package_name like ?
                """;
        return jdbc.queryForObject(sql, Long.class, "%" + packageName + "%");
    }

    /**
     * Finds total execution time of method rom specified group of methods (package or class)
     * @param packageName - part of package name
     * @return long value for total execution time
     */
    public Long findGroupTotalTime(String packageName) {
        String sql = """
                select sum(execution_time) from tracking_data 
                group by package_name 
                having package_name like ?
                """;
        return jdbc.queryForObject(sql, Long.class, "%" + packageName + "%");
    }

    /**
     * Data mapper to convert data from ResultSet row into TrackingData object
     */
    private static class TrackingDataMapper implements RowMapper<TrackingData> {

        @Override
        public TrackingData mapRow(ResultSet rs, int rowNum) throws SQLException {
            TrackingData data = new TrackingData();

            data.setId(rs.getLong("id"));
            data.setExecutedAt(rs.getTimestamp("executed_at").toLocalDateTime());
            data.setPackageName(rs.getString("package_name"));
            data.setMethodName(rs.getString("method_name"));
            data.setExecutionTime(rs.getLong("execution_time"));

            return data;
        }
    }
}
