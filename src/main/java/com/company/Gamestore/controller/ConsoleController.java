package com.company.Gamestore.controller;

import com.company.Gamestore.dto.Console;
import com.company.Gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

/** This controller handles all endpoints for the console entity.*/

@RestController
public class ConsoleController {

    @Autowired
    private ServiceLayer serviceLayer;

    /** This endpoint allows users to create new entries for console in the database.
     * Only managers and above may create new products */
    @RequestMapping(value = "/console", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Console createConsole(@RequestBody @Valid Console console){
        return serviceLayer.createConsole(console);
    }

    /** Read endpoints allow users to view available consoles in the database.
     * No authentication is required for any searches.
     * The readAllConsoles method displays all entries in the console table.
     * @return Console objects as entered in the database.
     * @throws \File Not Found Exception */
    @RequestMapping(value = "/console", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Console> readAllConsoles()throws FileNotFoundException {
        if(serviceLayer.readAllConsoles().size() == 0) {
            throw new FileNotFoundException("No consoles were found in the database");
        } else {
            return serviceLayer.readAllConsoles();
        }
    }
    /** readConsole method displays a single console which matches the provided ID.
     * @return A single console object with a matching ID.
     * @throws \Empty Result Data Access Exception*/
    @RequestMapping(value = "/console/{consoleId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public Console readConsole(@PathVariable int consoleId){
        if (serviceLayer.readConsole(consoleId) == null){
            throw new EmptyResultDataAccessException(1);
        } else {
            return serviceLayer.readConsole(consoleId);
        }
    }
    /** readConsoleByManufacturer method displays all consoles with the provided manufacturer.
     * @return A list of console object with a matching manufacturer.
     * @throws \File Not Found Exception*/
    @RequestMapping(value = "/console/maker/{manufacturer}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Console> readConsolesByManufacturer(@PathVariable String manufacturer)throws FileNotFoundException{
        if(serviceLayer.readConsolesByManufacturer(manufacturer).size() == 0) {
            throw new FileNotFoundException("No consoles were found in the database for this manufacturer");
        } else {
            return serviceLayer.readConsolesByManufacturer(manufacturer);
        }
    }

    /** The put endpoint allows the user to update an existing console in the database.
     * Any staff member can update a product.
     * updateConsole method updates a single console which matches the provided ID with the provided values.*/
    @RequestMapping(value = "/console", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateConsole(@RequestBody @Valid Console console){
        serviceLayer.updateConsole(console);
    }

    /** The delete endpoint allows the user to delete an existing console in the database.
     * Only Admin level users may delete a product.
     * deleteConsole method deletes a single console which matches the provided ID.*/
    @RequestMapping(value = "/console/{consoleId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteConsole(@PathVariable int consoleId){
        serviceLayer.deleteConsole(consoleId);
    }
}
