package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Invoice;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceImplementation implements InvoiceDao {
    //prepared statements
    private static final String INSERT_INVOICE_SQL = "INSERT INTO invoice (name, street, city, state, zipcode, item_type, item_id, unit_price, quantity, subtotal, tax, processing_fee, total) VALUE ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_INVOICES_SQL = "SELECT * FROM invoice";
    private static final String GET_INVOICE_SQL = "SELECT * FROM invoice WHERE invoice_id = ?";
    private static final String DELETE_INVOICE_SQL = "DELETE FROM invoice WHERE invoice_id = ?";

    private JdbcTemplate jdbcTemplate;

    public InvoiceImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //method implementations

    @Override
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(INSERT_INVOICE_SQL,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipcode(),
                invoice.getItem_type(),
                invoice.getItem_id(),
                invoice.getUnit_price(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessing_fee(),
                invoice.getTotal());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",Integer.class);
        invoice.setInvoice_id(id);
        return invoice;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(GET_ALL_INVOICES_SQL, this::mapRowToInvoice);
    }

    @Override
    public Invoice getInvoice(int invoice_id) {
        try{
            return jdbcTemplate.queryForObject(GET_INVOICE_SQL, this::mapRowToInvoice, invoice_id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void deleteInvoice(int invoice_id) {
        jdbcTemplate.update(DELETE_INVOICE_SQL, invoice_id);
    }

    //row mapper
    private Invoice mapRowToInvoice(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoice_id(rs.getInt("invoice_id"));
        invoice.setName(rs.getString("name"));
        invoice.setStreet(rs.getString("street"));
        invoice.setCity(rs.getString("city"));
        invoice.setState(rs.getString("state"));
        invoice.setZipcode(rs.getString("zipcode"));
        invoice.setItem_type(rs.getString("item_type"));
        invoice.setItem_id(rs.getInt("item_id"));
        invoice.setUnit_price(rs.getBigDecimal("unit_price"));
        invoice.setQuantity(rs.getInt("quantity"));
        invoice.setSubtotal(rs.getBigDecimal("subtotal"));
        invoice.setTax(rs.getBigDecimal("tax"));
        invoice.setProcessing_fee(rs.getBigDecimal("processing_fee"));
        invoice.setTotal(rs.getBigDecimal("total"));
        return invoice;
    }
}
