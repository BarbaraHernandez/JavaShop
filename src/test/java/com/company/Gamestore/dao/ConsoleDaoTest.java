package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Console;
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
public class ConsoleDaoTest {
    @Autowired
    protected ConsoleDao cdao;
//    @Autowired
//    protected InvoiceDao idao;

    @Before
    public void setUp() throws Exception {
//        List<Invoice> invoices = idao.getAllInvoices();
//        invoices.stream().forEach(i ->idao.deleteInvoice(i.getInvoice_id()));

        List<Console> consoles = cdao.getAllConsoles();
        consoles.stream().forEach(c ->cdao.deleteConsole(c.getConsole_id()));
    }

    @Test
    public void addConsole() {
        Console sample1 = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        sample1 = cdao.addConsole(sample1);

        Console checkConsole = cdao.getConsole(sample1.getConsole_id());

        assertEquals(sample1, checkConsole);
    }

    @Test
    public void getAllConsoles() {
        Console sample1 = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        sample1 = cdao.addConsole(sample1);

        Console sample2 = new Console("amet","consectetur","1000","adipiscing",new BigDecimal("299.99"),40);
        sample2 = cdao.addConsole(sample2);

        List<Console> localList = new ArrayList<>();
        localList.add(sample1);
        localList.add(sample2);

        List<Console> checkList = cdao.getAllConsoles();

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void getConsole() {
        //same as add
        Console sample1 = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        sample1 = cdao.addConsole(sample1);

        Console checkConsole = cdao.getConsole(sample1.getConsole_id());

        assertEquals(sample1, checkConsole);
    }

    @Test
    public void updateConsole() {
        Console sample1 = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        sample1 = cdao.addConsole(sample1);

        sample1.setQuantity(300);

        cdao.updateConsole(sample1);

        Console checkConsole = cdao.getConsole(sample1.getConsole_id());

        assertEquals(sample1.getQuantity(), checkConsole.getQuantity());
    }

    @Test
    public void deleteConsole() {
        Console sample1 = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        sample1 = cdao.addConsole(sample1);

        cdao.deleteConsole(sample1.getConsole_id());

        Console checkConsole = cdao.getConsole(sample1.getConsole_id());

        assertNull(checkConsole);
    }

    @Test
    public void getConsolesByManufacturer() {
        Console sample1 = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        sample1 = cdao.addConsole(sample1);

        Console sample2 = new Console("amet","consectetur","1000","adipiscing",new BigDecimal("299.99"),40);
        sample2 = cdao.addConsole(sample2);

        List<Console> localList = new ArrayList<>();
        localList.add(sample1);

        List<Console> checkList = cdao.getConsolesByManufacturer(sample1.getManufacturer());

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }
}