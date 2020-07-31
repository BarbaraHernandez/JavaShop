package com.company.Gamestore.dao;


import com.company.Gamestore.dto.Tax;

public interface TaxDao {

    //we only need to read from this table by state
    Tax getTax(String state);
}
