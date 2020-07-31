package com.company.Gamestore.controller;

import com.company.Gamestore.dto.Game;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
@WebMvcTest(GameController.class)
@WithMockUser(username = "adminUser",authorities = "ADMIN")
public class GameControllerTest {

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
    public void createGame() throws Exception{
        Game sample1 = new Game(
            "Lorem Ipsum",
            "G",
            "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            new BigDecimal("59.99"),
            "Dolor",
            200);

        String inputJson = mapper.writeValueAsString(sample1);

        Game mockOutput = sample1;
        mockOutput.setGame_id(1);

        String outputJson = mapper.writeValueAsString(mockOutput);

        when(service.createGame(any(Game.class))).thenReturn(mockOutput);

        this.mockMvc.perform(post("/game").with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void readAllGames() throws Exception{
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1.setGame_id(1);

        Game sample2 = new Game(
                "Lrm psm",
                "G",
                "Lresm Ipsum olr sit amet conseecte adipriing elit, shedo eiumod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample2.setGame_id(2);

        List<Game> savedList = new ArrayList<>();
        savedList.add(sample1);
        savedList.add(sample2);

        when(service.readAllGames()).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/game"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void readGame() throws Exception{
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1.setGame_id(1);

        String outputJson = mapper.writeValueAsString(sample1);

        when(service.readGame(1)).thenReturn(sample1);

        this.mockMvc.perform(get("/game/" + sample1.getGame_id()))
                .andDo(print())
//                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void updateGame() throws Exception{
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1.setGame_id(1);

        String inputJson = mapper.writeValueAsString(sample1);

        this.mockMvc.perform(put("/game").with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void deleteGame() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/game/1"))
                .andDo(print())
                .andExpect(content().string(""));
    }

    @Test
    public void readGameByStudio() throws Exception{
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1.setGame_id(1);

        Game sample2 = new Game(
                "Lrm psm",
                "G",
                "Lresm Ipsum olr sit amet conseecte adipriing elit, shedo eiumod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample2.setGame_id(2);

        List<Game> savedList = new ArrayList<>();
        savedList.add(sample1);
        savedList.add(sample2);

        when(service.readGameByStudio("Dolor")).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/game/studio/" + sample1.getStudio()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void readGameByESRB() throws Exception {
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1.setGame_id(1);

        Game sample2 = new Game(
                "Lrm psm",
                "G",
                "Lresm Ipsum olr sit amet conseecte adipriing elit, shedo eiumod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample2.setGame_id(2);

        List<Game> savedList = new ArrayList<>();
        savedList.add(sample1);
        savedList.add(sample2);

        when(service.readGameByESRB("G")).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/game/esrb/" + sample1.getEsrb_rating()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void readGameByTitle() throws Exception{
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1.setGame_id(1);

        List<Game> savedList = new ArrayList<>();
        savedList.add(sample1);

        when(service.readGameByTitle(sample1.getTitle())).thenReturn(savedList);

        String outputJson = mapper.writeValueAsString(savedList);

        this.mockMvc.perform(get("/game/title/" + sample1.getTitle()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(outputJson));
    }
}