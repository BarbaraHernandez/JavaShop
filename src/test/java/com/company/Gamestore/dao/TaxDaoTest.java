package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Tax;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaxDaoTest {

    @Autowired
    protected TaxDao taxDao;

    @Test
    public void getTax() {
        Tax known = new Tax("AK", 0.06f);

        Tax checkDB = taxDao.getTax("AK");

        assertEquals(known, checkDB);
    }
}