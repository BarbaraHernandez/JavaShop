package com.company.Gamestore.dao;

import com.company.Gamestore.dto.ProcessingFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProcessingFeeImplementation implements ProcessingFeeDao{

    //Prepared Statement
    private static final  String SELECT_FEE_SQL = "SELECT * FROM processing_fee WHERE product_type = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProcessingFeeImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProcessingFee getProcessingFee(String product_type) {
        try{
            return jdbcTemplate.queryForObject(SELECT_FEE_SQL, this::mapRowToProcessingFee, product_type);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //row mapper
    public ProcessingFee mapRowToProcessingFee(ResultSet rs, int rowNum)throws SQLException {
        ProcessingFee fee = new ProcessingFee();
        fee.setProduct_type(rs.getString("product_type"));
        fee.setFee(rs.getBigDecimal("fee"));
        return fee;
    }
}
