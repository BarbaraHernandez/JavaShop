package com.company.Gamestore.dao;


import com.company.Gamestore.dto.Game;

import java.util.List;

public interface GameDao {
    //C
    Game addGame(Game game);

    //R
    //all
    List<Game> getAllGames();
    //one
    Game getGame(int game_id);

    //U
    void updateGame(Game game);

    //D
    void deleteGame(int game_id);

    //get by studio
    List<Game> getGamesByStudio(String studio);

    //get by ESRB
    List<Game> getGamesByESRB(String esrb);

    //get by Title
    List<Game> getGamesByTitle(String title);
}
