package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Console;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsoleImplementation implements ConsoleDao{

    //prepared statements
    private static final String INSERT_CONSOLE_SQL = "INSERT INTO console (model, manufacturer, memory_amount, processor, price, quantity) VALUE (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_CONSOLES_SQL = "SELECT * FROM console";
    private static final String GET_CONSOLE_SQL = "SELECT * FROM console WHERE console_id = ?";
    private static final String UPDATE_CONSOLE_SQL = "UPDATE console SET model = ?, manufacturer = ?, memory_amount = ?, processor = ?, price = ?, quantity = ? WHERE console_id = ?";
    private static final String DELETE_CONSOLE_SQL = "DELETE FROM console WHERE console_id = ?";
    private static final String GET_CONSOLE_BY_MAN_SQL = "SELECT * FROM console WHERE manufacturer = ?";

    private JdbcTemplate jdbcTemplate;

    public ConsoleImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //method implementations
    @Override
    public Console addConsole(Console console) {
        jdbcTemplate.update(INSERT_CONSOLE_SQL,
                console.getModel(),
                console.getManufacturer(),
                console.getMemory_amount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",Integer.class);
        console.setConsole_id(id);
        return console;
    }

    @Override
    public List<Console> getAllConsoles() {
        return jdbcTemplate.query(GET_ALL_CONSOLES_SQL, this::mapRowToConsole);
    }

    @Override
    public Console getConsole(int console_id) {
        try{
            return jdbcTemplate.queryForObject(GET_CONSOLE_SQL, this::mapRowToConsole, console_id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void updateConsole(Console console) {
        jdbcTemplate.update(UPDATE_CONSOLE_SQL,
                console.getModel(),
                console.getManufacturer(),
                console.getMemory_amount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity(),
                console.getConsole_id());
    }

    @Override
    public void deleteConsole(int console_id) {
        jdbcTemplate.update(DELETE_CONSOLE_SQL, console_id);

    }

    @Override
    public List<Console> getConsolesByManufacturer(String manufacturer) {
        return jdbcTemplate.query(GET_CONSOLE_BY_MAN_SQL, this::mapRowToConsole, manufacturer);
    }

    //row mapper
    private Console mapRowToConsole(ResultSet rs, int rowNum) throws SQLException {
        Console console = new Console();
        console.setConsole_id(rs.getInt("console_id"));
        console.setModel(rs.getString("model"));
        console.setManufacturer(rs.getString("manufacturer"));
        console.setMemory_amount(rs.getString("memory_amount"));
        console.setProcessor(rs.getString("processor"));
        console.setPrice(rs.getBigDecimal("price"));
        console.setQuantity(rs.getInt("quantity"));
        return console;
    }
}
