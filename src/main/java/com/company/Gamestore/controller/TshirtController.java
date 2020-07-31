package com.company.Gamestore.controller;

import com.company.Gamestore.dto.Tshirt;
import com.company.Gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

/** This controller handles all endpoints for the t-shirt(t_shirt) entity.*/
@RestController
public class TshirtController {
    @Autowired
    private ServiceLayer serviceLayer;

    /** This endpoint allows users to create new entries for t-shirt in the database.
     * Only managers and above may create new products */
    @RequestMapping(value = "/t-shirt", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Tshirt createTshirt(@RequestBody @Valid Tshirt tshirt){
        return serviceLayer.createTshirt(tshirt);
    }

    /** Read endpoints allow users to view available t-shirts in the database.
     * No authentication is required for any searches.
     * The readAllTshirts method displays all entries in the t-shirt table.
     * @return T-shirt objects as entered in the database.
     * @throws \File Not Found Exception */
    @RequestMapping(value = "/t-shirt", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Tshirt> readAllTshirts() throws FileNotFoundException{
        if(serviceLayer.readAllTshirts().size() == 0) {
            throw new FileNotFoundException("No t-shirts were found in the database");
        } else {
            return serviceLayer.readAllTshirts();
        }
    }
    /** readTshirt method displays a single t-shirt which matches the provided ID.
     * @return A single t-shirt object with the matching ID.
     * @throws \Empty Result Data Access Exception*/
    @RequestMapping(value = "/t-shirt/{tShirtId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public Tshirt readTshirt(@PathVariable int tShirtId){
        if (serviceLayer.readTshirt(tShirtId) == null){
            throw new EmptyResultDataAccessException(1);
        } else {
            return serviceLayer.readTshirt(tShirtId);
        }
    }
    /** readTshirtsByColor method displays all t-shirts with the provided color.
     * @return A list of t-shirt objects with a matching color.
     * @throws \File Not Found Exception*/
    @RequestMapping(value = "/t-shirt/color/{color}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Tshirt> readTshirtsByColor(@PathVariable String color)throws FileNotFoundException{
        if(serviceLayer.readTshirtByColor(color).size() == 0) {
            throw new FileNotFoundException("No t-shirts were found in the database for this color");
        } else {
            return serviceLayer.readTshirtByColor(color);
        }
    }
    /** readTshirtsBySize method displays all t-shirts with the provided size.
     * @return A list of t-shirt objects with a matching size.
     * @throws \File Not Found Exception*/
    @RequestMapping(value = "/t-shirt/size/{size}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Tshirt> readTshirtsBySize(@PathVariable String size)throws FileNotFoundException{
        if(serviceLayer.readTshirtBySize(size).size() == 0) {
            throw new FileNotFoundException("No t-shirts were found in the database for this size");
        } else {
            return serviceLayer.readTshirtBySize(size);
        }
    }

    /** The put endpoint allows the user to update an existing t-shirt in the database.
     * Any staff member can update a product.
     * updateTshirt method updates a single t-shirt which matches the provided ID with the provided values.*/
    @RequestMapping(value = "/t-shirt", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateTshirt(@RequestBody @Valid Tshirt tshirt){
        serviceLayer.updateTshirt(tshirt);
    }

    /** The delete endpoint allows the user to delete an existing t-shirt in the database.
     * Only Admin level users may delete a product.
     * deleteTshirt method deletes a single t-shirt which matches the provided ID.*/
    @RequestMapping(value = "/t-shirt/{tShirtId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteTshirt(@PathVariable int tShirtId){
        serviceLayer.deleteTshirt(tShirtId);
    }


}
