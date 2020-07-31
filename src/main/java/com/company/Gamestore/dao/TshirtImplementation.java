package com.company.Gamestore.dao;


import com.company.Gamestore.dto.Tshirt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TshirtImplementation implements TshirtDao{
    //prepared statements
    private static final String INSERT_TSHIRT_SQL = "INSERT INTO t_shirt (size, color, description, price, quantity) VALUE (?, ?, ?, ?, ?)";
    private static final String GET_ALL_TSHIRTS_SQL = "SELECT * FROM t_shirt";
    private static final String GET_TSHIRT_SQL = "SELECT * FROM t_shirt WHERE t_shirt_id = ?";
    private static final String UPDATE_TSHIRT_SQL = "UPDATE t_shirt SET size = ?, color = ?, description = ?, price =?, quantity =? WHERE t_shirt_id = ?";
    private static final String DELETE_TSHIRT_SQL = "DELETE FROM t_shirt WHERE t_shirt_id = ?";
    private static final String GET_TSHIRT_BY_COLOR_SQL = "SELECT * FROM t_shirt WHERE color = ?";
    private static final String GET_TSHIRT_BY_SIZE_SQL = "SELECT * FROM t_shirt WHERE size = ?";

    private JdbcTemplate jdbcTemplate;

    public TshirtImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //method implementations

    @Override
    public Tshirt addTshirt(Tshirt tshirt) {
        jdbcTemplate.update(INSERT_TSHIRT_SQL,
                tshirt.getSize(),
                tshirt.getColor(),
                tshirt.getDescription(),
                tshirt.getPrice(),
                tshirt.getQuantity());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",Integer.class);
        tshirt.setT_shirt_id(id);
        return tshirt;
    }

    @Override
    public List<Tshirt> getAllTshirts() {
        return jdbcTemplate.query(GET_ALL_TSHIRTS_SQL, this::mapRowToGame);
    }

    @Override
    public Tshirt getTshirt(int t_shirt_id) {
        try{
            return jdbcTemplate.queryForObject(GET_TSHIRT_SQL, this::mapRowToGame, t_shirt_id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void updateTshirt(Tshirt tshirt) {
        jdbcTemplate.update(UPDATE_TSHIRT_SQL,
                tshirt.getSize(),
                tshirt.getColor(),
                tshirt.getDescription(),
                tshirt.getPrice(),
                tshirt.getQuantity(),
                tshirt.getT_shirt_id());
    }

    @Override
    public void deleteTshirt(int t_shirt_id) {
        jdbcTemplate.update(DELETE_TSHIRT_SQL, t_shirt_id);
    }

    @Override
    public List<Tshirt> getTshirtByColor(String color) {
        return jdbcTemplate.query(GET_TSHIRT_BY_COLOR_SQL, this::mapRowToGame, color);
    }

    @Override
    public List<Tshirt> getTshirtBySize(String size) {
        return jdbcTemplate.query(GET_TSHIRT_BY_SIZE_SQL, this::mapRowToGame, size);
    }

    //row mapper
    private Tshirt mapRowToGame(ResultSet rs, int rowNum) throws SQLException {
        Tshirt shirt = new Tshirt();
        shirt.setT_shirt_id(rs.getInt("t_shirt_id"));
        shirt.setSize(rs.getString("size"));
        shirt.setColor(rs.getString("color"));
        shirt.setDescription(rs.getString("description"));
        shirt.setPrice(rs.getBigDecimal("price"));
        shirt.setQuantity(rs.getInt("quantity"));
        return shirt;
    }
}
