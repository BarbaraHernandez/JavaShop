package com.company.Gamestore.controller;

import com.company.Gamestore.dao.ConsoleDao;
import com.company.Gamestore.dto.Console;
import com.company.Gamestore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
@WebMvcTest(ConsoleController.class)
@WithMockUser(username = "adminUser",authorities = "ADMIN")
public class ConsoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    @MockBean
    private ConsoleDao consoleDao;

    @MockBean
    DataSource dataSource;

    @InjectMocks
    private ConsoleControllerTest consoleControllerTest;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createConsole() throws Exception{
        Console sample1 = new Console(
                "lorem",
                "ipsum",
                "500",
                "dolor",
                new BigDecimal("199.99"),
                20);

        String inputJson = mapper.writeValueAsString(sample1);

        Console mockOutput = sample1;
        mockOutput.setConsole_id(1);

        String outputJson = mapper.writeValueAsString(mockOutput);

        //when(service.createConsole(sample1)).thenReturn(mockOutput);
        when(service.createConsole(any(Console.class))).thenReturn(mockOutput);
        //(mockMvc.perform(post(inputJson))).thenReturn(outputJson);

        this.mockMvc.perform(post("/console").with(csrf().asHeader())
            .content(inputJson)
            .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void readAllConsoles() throws Exception{
        Console sample1 = new Console(
                "lorem",
                "ipsum",
                "500",
                "dolor",
                new BigDecimal("199.99"),
                20);
        sample1.setConsole_id(1);
        Console sample2 = new Console(
                "lrm",
                "psm",
                "500",
                "dlr",
                new BigDecimal("199.99"),
                20);
        sample2.setConsole_id(2);

        List<Console> savedList = new ArrayList<>();
        savedList.add(sample1);
        savedList.add(sample2);

        when(service.readAllConsoles()).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/console"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void readConsole() throws Exception {
        Console sample1 = new Console(
                "lorem",
                "ipsum",
                "500",
                "dolor",
                new BigDecimal("199.99"),
                20);
        sample1.setConsole_id(1);

        String outputJson = mapper.writeValueAsString(sample1);

        when(service.readConsole(1)).thenReturn(sample1);

        this.mockMvc.perform(get("/console/" + sample1.getConsole_id()))
                .andDo(print())
//                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void updateConsole() throws Exception{
        Console sample1 = new Console(
                "lorem",
                "ipsum",
                "500",
                "dolor",
                new BigDecimal("199.99"),
                20);
        sample1.setConsole_id(1);

        String inputJson = mapper.writeValueAsString(sample1);

        this.mockMvc.perform(put("/console").with(csrf().asHeader())
            .content(inputJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    //!!!!!!!!!!!!!!! false positive
    @Test
    public void deleteConsole() throws Exception{

        //add a verify

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/console/1").with(csrf().asHeader()))
            .andDo(print())
            .andExpect(content().string(""));
    }

    @Test
    public void readConsolesByManufacturer() throws Exception {
        Console sample1 = new Console(
                "lorem",
                "ipsum",
                "500",
                "dolor",
                new BigDecimal("199.99"),
                20);
        sample1.setConsole_id(1);

        Console sample2 = new Console(
                "lrm",
                "ipsum",
                "500",
                "dlr",
                new BigDecimal("199.99"),
                20);
        sample2.setConsole_id(2);

        List<Console> savedList = new ArrayList<>();
        savedList.add(sample1);
        savedList.add(sample2);

        when(service.readConsolesByManufacturer(sample1.getManufacturer())).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/console/maker/ipsum"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }
}