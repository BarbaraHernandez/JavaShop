package com.company.Gamestore.dao;


import com.company.Gamestore.dto.Tshirt;

import java.util.List;

public interface TshirtDao {
    //C
    Tshirt addTshirt(Tshirt tshirt);

    //R
    //all
    List<Tshirt> getAllTshirts();
    //one
    Tshirt getTshirt(int t_shirt_id);

    //U
    void updateTshirt(Tshirt tshirt);

    //D
    void deleteTshirt(int t_shirt_id);

    //get by color
    List<Tshirt> getTshirtByColor(String color);

    //get by size
    List<Tshirt> getTshirtBySize(String size);
}
