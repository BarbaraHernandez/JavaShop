package com.company.Gamestore.dao;


import com.company.Gamestore.dto.ProcessingFee;

public interface ProcessingFeeDao {

    //We only need to read from this table by product type
    ProcessingFee getProcessingFee(String product_type);
    
}
