package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Game;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameImplementation implements GameDao{

    //prepared statements
    private static final String INSERT_GAME_SQL = "INSERT INTO game (title, esrb_rating, description, price, studio, quantity) VALUE (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_GAMES_SQL = "SELECT * FROM game";
    private static final String GET_GAME_SQL = "SELECT * FROM game WHERE game_id = ?";
    private static final String UPDATE_GAME_SQL = "UPDATE game SET title = ?, esrb_rating = ?, description = ?, price = ?, studio = ?, quantity = ? WHERE game_id = ?";
    private static final String DELETE_GAME_SQL = "DELETE FROM game WHERE game_id = ?";
    private static final String GET_GAME_BY_STUDIO_SQL = "SELECT * FROM game WHERE studio = ?";
    private static final String GET_GAME_BY_ESRB = "SELECT * FROM game WHERE esrb_rating = ?";
    private static final String GET_GAME_BY_TITLE = "SELECT * FROM game WHERE title = ?";

    private JdbcTemplate jdbcTemplate;

    public GameImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //method implementations
    @Override
    public Game addGame(Game game) {
        jdbcTemplate.update(INSERT_GAME_SQL,
                game.getTitle(),
                game.getEsrb_rating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",Integer.class);
        game.setGame_id(id);
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        return jdbcTemplate.query(GET_ALL_GAMES_SQL, this::mapRowToGame);
    }

    @Override
    public Game getGame(int game_id) {
        try{
            return jdbcTemplate.queryForObject(GET_GAME_SQL, this::mapRowToGame, game_id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void updateGame(Game game) {
        jdbcTemplate.update(UPDATE_GAME_SQL,
                game.getTitle(),
                game.getEsrb_rating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity(),
                game.getGame_id());
    }

    @Override
    public void deleteGame(int game_id) {
        jdbcTemplate.update(DELETE_GAME_SQL, game_id);
    }

    @Override
    public List<Game> getGamesByStudio(String studio) {
        return jdbcTemplate.query(GET_GAME_BY_STUDIO_SQL, this::mapRowToGame, studio);
    }

    @Override
    public List<Game> getGamesByESRB(String esrb) {
        return jdbcTemplate.query(GET_GAME_BY_ESRB, this::mapRowToGame, esrb);
    }

    @Override
    public List<Game> getGamesByTitle(String title) {
        return jdbcTemplate.query(GET_GAME_BY_TITLE, this::mapRowToGame, title);
    }

    //row mapper
    private Game mapRowToGame(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setGame_id(rs.getInt("game_id"));
        game.setTitle(rs.getString("title"));
        game.setEsrb_rating(rs.getString("esrb_rating"));
        game.setDescription(rs.getString("description"));
        game.setPrice(rs.getBigDecimal("price"));
        game.setStudio(rs.getString("studio"));
        game.setQuantity(rs.getInt("quantity"));
        return game;
    }
}
