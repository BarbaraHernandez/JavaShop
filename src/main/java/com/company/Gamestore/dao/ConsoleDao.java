package com.company.Gamestore.dao;


import com.company.Gamestore.dto.Console;

import java.util.List;

public interface ConsoleDao {
    //C
    Console addConsole(Console console);

    //R
    //all
    List<Console> getAllConsoles();
    //one
    Console getConsole(int console_id);

    //U
    void updateConsole(Console console);

    //D
    void deleteConsole(int console_id);

    //get by manufacturer
    List<Console> getConsolesByManufacturer(String manufacturer);
}
