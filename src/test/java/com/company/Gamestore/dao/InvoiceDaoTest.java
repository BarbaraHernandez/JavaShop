package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {

    @Autowired
    protected InvoiceDao idao;

    @Before
    public void setUp() throws Exception {
        List<Invoice> invoices = idao.getAllInvoices();
        invoices.stream().forEach(invoice -> idao.deleteInvoice(invoice.getInvoice_id()));
    }

    @Test
    public void addInvoice() {
        Invoice sample1 = new Invoice("name","street","city","ST","12345","game",1,new BigDecimal("12.99"),2,new BigDecimal("25.98"),new BigDecimal("5.30"), new BigDecimal("4.50"),new BigDecimal("35.78"));
        sample1 = idao.addInvoice(sample1);

        Invoice checkInvoice = idao.getInvoice(sample1.getInvoice_id());

        assertEquals(sample1, checkInvoice);
    }

    @Test
    public void getAllInvoices() {
        Invoice sample1 = new Invoice("name","street","city","ST","12345","game",1,new BigDecimal("12.99"),2,new BigDecimal("25.98"),new BigDecimal("5.30"), new BigDecimal("4.50"),new BigDecimal("35.78"));
        sample1 = idao.addInvoice(sample1);
        Invoice sample2 = new Invoice("namename","streetstreet","citycity","TS","12345","gamegame",11,new BigDecimal("1.00"),2,new BigDecimal("2.00"),new BigDecimal("1.00"), new BigDecimal("3.00"),new BigDecimal("6.00"));
        sample2 = idao.addInvoice(sample2);

        List<Invoice> localList = new ArrayList<>();
        localList.add(sample1);
        localList.add(sample2);

        List<Invoice> checkList = idao.getAllInvoices();

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void getInvoice() {
        //same as add
        Invoice sample1 = new Invoice("name","street","city","ST","12345","game",1,new BigDecimal("12.99"),2,new BigDecimal("25.98"),new BigDecimal("5.30"), new BigDecimal("4.50"),new BigDecimal("35.78"));
        sample1 = idao.addInvoice(sample1);

        Invoice checkInvoice = idao.getInvoice(sample1.getInvoice_id());

        assertEquals(sample1, checkInvoice);
    }

    @Test
    public void deleteInvoice() {
        Invoice sample1 = new Invoice("name","street","city","ST","12345","game",1,new BigDecimal("12.99"),2,new BigDecimal("25.98"),new BigDecimal("5.30"), new BigDecimal("4.50"),new BigDecimal("35.78"));
        sample1 = idao.addInvoice(sample1);

        idao.deleteInvoice(sample1.getInvoice_id());

        Invoice checkInvoice = idao.getInvoice(sample1.getInvoice_id());

        assertNull(checkInvoice);
    }
}