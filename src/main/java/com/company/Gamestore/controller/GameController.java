package com.company.Gamestore.controller;

import com.company.Gamestore.dto.Game;
import com.company.Gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

/** This controller handles all endpoints for the game entity.*/
@RestController
public class GameController {

    @Autowired
    private ServiceLayer serviceLayer;

    /** This endpoint allows users to create new entries for game in the database.
     * Only managers and above may create new products */
    @RequestMapping(value = "/game", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Game createGame(@RequestBody @Valid Game game){
            return serviceLayer.createGame(game);
    }

    /** Read endpoints allow users to view available games in the database.
     * No authentication is required for any searches.
     * The readAllGames method displays all entries in the game table.
     * @return Game objects as entered in the database.
     * @throws \File Not Found Exception */
    @RequestMapping(value = "/game", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Game> readAllGames() throws FileNotFoundException{
        if(serviceLayer.readAllGames().size() == 0) {
            throw new FileNotFoundException("No games were found in the database");
        } else {
            return serviceLayer.readAllGames();
        }
    }
    /** readGame method displays a single game which matches the provided ID.
     * @return A single game object with the matching ID.
     * @throws \Empty Result Data Access Exception*/
    @RequestMapping(value = "/game/{gameID}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public Game readGame(@PathVariable int gameID){
        if (serviceLayer.readGame(gameID) == null){
            throw new EmptyResultDataAccessException(1);
        } else {
            return serviceLayer.readGame(gameID);
        }
    }
    /** readGameByStudio method displays all games with the provided Studio.
     * @return A list of game object with a matching studio.
     * @throws \File Not Found Exception*/
    @RequestMapping(value = "/game/studio/{studio}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Game> readGameByStudio(@PathVariable String studio)throws FileNotFoundException{
        if(serviceLayer.readGameByStudio(studio).size() == 0) {
            throw new FileNotFoundException("No games were found in the database for this studio");
        } else {
            return serviceLayer.readGameByStudio(studio);
        }
    }
    /** readGameByESRB method displays all games with the provided rating.
     * @return A list of game objects with a matching ESRB rating.
     * @throws \File Not Found Exception*/
    @RequestMapping(value = "/game/esrb/{esrb}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Game> readGameByESRB(@PathVariable String esrb)throws FileNotFoundException{
        if(serviceLayer.readGameByESRB(esrb).size() == 0) {
            throw new FileNotFoundException("No games were found in the database for this ESRB rating");
        } else {
            return serviceLayer.readGameByESRB(esrb);
        }
    }
    /** readGameByTitle method displays all games with a matching title
     * @return A list of game objects with a matching title.
     * @throws \File Not Found Exception*/
    @RequestMapping(value = "/game/title/{title}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Game> readGameByTitle(@PathVariable String title)throws FileNotFoundException{
        if(serviceLayer.readGameByTitle(title).size() == 0) {
            throw new FileNotFoundException("No games were found in the database for this title");
        } else {
            return serviceLayer.readGameByTitle(title);
        }
    }

    /** The put endpoint allows the user to update an existing game in the database.
     * Any staff member can update a product.
     * updateGame method updates a single game which matches the provided ID with the provided values.*/
    @RequestMapping(value = "/game", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateGame(@RequestBody @Valid Game game){
        serviceLayer.updateGame(game);
    }

    /** The delete endpoint allows the user to delete an existing game in the database.
     * Only Admin level users may delete a product.
     * deleteGame method deletes a single game which matches the provided ID.*/
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteGame(@PathVariable int gameId){
       serviceLayer.deleteGame(gameId);
    }

}
