package com.company.Gamestore.controller;

import com.company.Gamestore.dto.Tshirt;
import com.company.Gamestore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(TshirtController.class)
@WithMockUser(username = "adminUser",authorities = "ADMIN")
public class TshirtControllerTest {

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
    public void createTshirt() throws Exception{
        Tshirt sample1 = new Tshirt(
                "M",
                "black",
                "best t-shirt ever",
                new BigDecimal("19.99"),
                10);

        String inputJson = mapper.writeValueAsString(sample1);

        Tshirt mockOutput = sample1;
        mockOutput.setT_shirt_id(1);

        String outputJson = mapper.writeValueAsString(mockOutput);

        when(service.createTshirt(any(Tshirt.class))).thenReturn(mockOutput);

        this.mockMvc.perform(post("/t-shirt").with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void readAllTshirts() throws Exception{
        Tshirt sample1 = new Tshirt(
            "M",
            "black",
            "best t-shirt ever",
            new BigDecimal("19.99"),
            10);
        sample1.setT_shirt_id(1);
        Tshirt sample2 = new Tshirt(
                "M",
                "black",
                "second best t-shirt ever",
                new BigDecimal("16.99"),
                10);
        sample2.setT_shirt_id(2);

        List<Tshirt> savedList = new ArrayList<>();
        savedList.add(sample1);
        savedList.add(sample2);

        when(service.readAllTshirts()).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/t-shirt"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void readTshirt() throws Exception{
        Tshirt sample1 = new Tshirt(
                "M",
                "black",
                "best t-shirt ever",
                new BigDecimal("19.99"),
                10);
        sample1.setT_shirt_id(1);

        String outputJson = mapper.writeValueAsString(sample1);

        when(service.readTshirt(1)).thenReturn(sample1);

        this.mockMvc.perform(get("/t-shirt/" + sample1.getT_shirt_id()))
                .andDo(print())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void updateTshirt() throws Exception{
        Tshirt sample1 = new Tshirt(
            "M",
            "black",
            "best t-shirt ever",
            new BigDecimal("19.99"),
            10);
        sample1.setT_shirt_id(1);

        String inputJson = mapper.writeValueAsString(sample1);

        this.mockMvc.perform(put("/t-shirt").with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTshirt() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/t-shirt/1"))
                .andDo(print())
                .andExpect(content().string(""));
    }

    @Test
    public void readTshirtsByColor() throws Exception {
        Tshirt sample1 = new Tshirt(
                "M",
                "black",
                "best t-shirt ever",
                new BigDecimal("19.99"),
                10);
        sample1.setT_shirt_id(1);
        Tshirt sample2 = new Tshirt(
                "M",
                "black",
                "second best t-shirt ever",
                new BigDecimal("16.99"),
                10);
        sample2.setT_shirt_id(2);

        List<Tshirt> savedList = new ArrayList<>();
        savedList.add(sample1);
        savedList.add(sample2);

        when(service.readTshirtByColor(sample1.getColor())).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/t-shirt/color/" + sample1.getColor()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void readTshirtsBySize() throws Exception {
        Tshirt sample1 = new Tshirt(
                "M",
                "black",
                "best t-shirt ever",
                new BigDecimal("19.99"),
                10);
        sample1.setT_shirt_id(1);
        Tshirt sample2 = new Tshirt(
                "M",
                "black",
                "second best t-shirt ever",
                new BigDecimal("16.99"),
                10);
        sample2.setT_shirt_id(2);

        List<Tshirt> savedList = new ArrayList<>();
        savedList.add(sample1);
        savedList.add(sample2);

        when(service.readTshirtBySize(sample1.getSize())).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/t-shirt/size/" + sample1.getSize()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }
}