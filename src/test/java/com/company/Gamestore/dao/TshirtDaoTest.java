package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Tshirt;
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
public class TshirtDaoTest {

//    @Autowired
//    protected InvoiceDao idao;
    @Autowired
    protected TshirtDao tdao;

    @Before
    public void setUp() throws Exception {
//        List<Invoice> invoices = idao.getAllInvoices();
//        invoices.stream().forEach(i -> idao.deleteInvoice(i.getInvoice_id()));

        List<Tshirt> tshirts = tdao.getAllTshirts();
        tshirts.stream().forEach(t -> tdao.deleteTshirt(t.getT_shirt_id()));
    }

    @Test
    public void addTshirt() {
        Tshirt sample1 = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        sample1 = tdao.addTshirt(sample1);

        Tshirt checkShirt = tdao.getTshirt(sample1.getT_shirt_id());

        assertEquals(sample1, checkShirt);
    }

    @Test
    public void getAllTshirts() {
        Tshirt sample1 = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        sample1 = tdao.addTshirt(sample1);
        Tshirt sample2 = new Tshirt("L", "gray", "comfy t-shirt", new BigDecimal("15.99"), 15);
        sample2 = tdao.addTshirt(sample2);

        List<Tshirt> localList = new ArrayList<>();
        localList.add(sample1);
        localList.add(sample2);

        List<Tshirt> checkList = tdao.getAllTshirts();

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void getTshirt() {
        //same as add
        Tshirt sample1 = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        sample1 = tdao.addTshirt(sample1);

        Tshirt checkShirt = tdao.getTshirt(sample1.getT_shirt_id());

        assertEquals(sample1, checkShirt);
    }

    @Test
    public void updateTshirt() {
        Tshirt sample1 = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        sample1 = tdao.addTshirt(sample1);

        sample1.setPrice(new BigDecimal("9.99"));

        tdao.updateTshirt(sample1);

        Tshirt checkShirt = tdao.getTshirt(sample1.getT_shirt_id());

        assertEquals(sample1.getPrice(), checkShirt.getPrice());
        assertEquals(sample1, checkShirt);
    }

    @Test
    public void deleteTshirt() {
        Tshirt sample1 = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        sample1 = tdao.addTshirt(sample1);

        tdao.deleteTshirt(sample1.getT_shirt_id());

        Tshirt checkShirt = tdao.getTshirt(sample1.getT_shirt_id());

        assertNull(checkShirt);
    }

    @Test
    public void getTshirtByColor() {
        Tshirt sample1 = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        sample1 = tdao.addTshirt(sample1);
        Tshirt sample2 = new Tshirt("L", "gray", "comfy t-shirt", new BigDecimal("15.99"), 15);
        sample2 = tdao.addTshirt(sample2);

        List<Tshirt> localList = new ArrayList<>();
        localList.add(sample1);


        List<Tshirt> checkList = tdao.getTshirtByColor(sample1.getColor());

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void getTshirtBySize() {
        Tshirt sample1 = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        sample1 = tdao.addTshirt(sample1);
        Tshirt sample2 = new Tshirt("L", "gray", "comfy t-shirt", new BigDecimal("15.99"), 15);
        sample2 = tdao.addTshirt(sample2);

        List<Tshirt> localList = new ArrayList<>();
        localList.add(sample2);

        List<Tshirt> checkList = tdao.getTshirtBySize(sample2.getSize());

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }
}