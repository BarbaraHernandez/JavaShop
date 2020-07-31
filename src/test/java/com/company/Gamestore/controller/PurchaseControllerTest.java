package com.company.Gamestore.controller;

import com.company.Gamestore.dto.Invoice;
import com.company.Gamestore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    @MockBean
    DataSource dataSource;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void makePurchase() throws Exception{
        Invoice exampleInvoiceInput = new Invoice();
        exampleInvoiceInput.setName("name");
        exampleInvoiceInput.setStreet("street");
        exampleInvoiceInput.setCity("city");
        exampleInvoiceInput.setState("AK");
        exampleInvoiceInput.setZipcode("12345");
        exampleInvoiceInput.setItem_type("game");
        exampleInvoiceInput.setItem_id(1);
        exampleInvoiceInput.setQuantity(2);

        Invoice mockOutput = new Invoice(
                "name",
                "street",
                "city",
                "AK",
                "12345",
                "game",
                1,
                new BigDecimal("59.99"),
                2,
                new BigDecimal("119.98"),
                new BigDecimal("7.20"),
                new BigDecimal("1.49"),
                new BigDecimal("128.67"));
        mockOutput.setInvoice_id(1);

        String inputJson = mapper.writeValueAsString(exampleInvoiceInput);

        when(service.makePurchase(any(Invoice.class))).thenReturn(mockOutput);

        String outputJson = mapper.writeValueAsString(mockOutput);

        this.mockMvc.perform(post("/purchase").with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void readInvoice() throws Exception{
        Invoice mockOutput = new Invoice(
                "name",
                "street",
                "city",
                "AK",
                "12345",
                "game",
                1,
                new BigDecimal("59.99"),
                2,
                new BigDecimal("119.98"),
                new BigDecimal("7.20"),
                new BigDecimal("1.49"),
                new BigDecimal("128.67"));
        mockOutput.setInvoice_id(1);

        String outputJson = mapper.writeValueAsString(mockOutput);

        when(service.readInvoice(1)).thenReturn(mockOutput);

        this.mockMvc.perform(get("/purchase/" + mockOutput.getInvoice_id()))
                .andDo(print())
//                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }
}