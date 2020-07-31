package com.company.Gamestore.dao;

import com.company.Gamestore.dto.ProcessingFee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProcessingFeeDaoTest {
    @Autowired
    protected ProcessingFeeDao feeDao;

    @Test
    public void getProcessingFee() {

        ProcessingFee known = new ProcessingFee("Games", new BigDecimal("1.49"));

        ProcessingFee checkDB = feeDao.getProcessingFee("Games");

        assertEquals(known, checkDB);

    }
}