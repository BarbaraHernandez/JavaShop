package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TaxImplementation implements TaxDao {

    //prepared statement
    private static final String SELECT_TAX_SQL = "SELECT * FROM sales_tax_rate WHERE state = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TaxImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tax getTax(String state) {
        try{
            return jdbcTemplate.queryForObject(SELECT_TAX_SQL, this::mapRowToTax, state);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //row mapper
    public Tax mapRowToTax(ResultSet rs, int rowNum) throws SQLException {
        Tax tax = new Tax();
        tax.setState(rs.getString("state"));
        tax.setRate(rs.getFloat("rate"));
        return tax;
    }
}
