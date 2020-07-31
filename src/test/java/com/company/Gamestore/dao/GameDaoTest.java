package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Game;
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
public class GameDaoTest {

    @Autowired
    protected GameDao gdao;
//    @Autowired
//    protected InvoiceDao idao;

    @Before
    public void setUp() throws Exception {
//        List<Invoice> invoices = idao.getAllInvoices();
//        invoices.stream().forEach(i ->idao.deleteInvoice(i.getInvoice_id()));

        List<Game> consoles = gdao.getAllGames();
        consoles.stream().forEach(g ->gdao.deleteGame(g.getGame_id()));
    }

    @Test
    public void addGame() {
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                 new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1 = gdao.addGame(sample1);

        Game checkGame = gdao.getGame(sample1.getGame_id());

        assertEquals(sample1, checkGame);
    }

    @Test
    public void getAllGames() {
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1 = gdao.addGame(sample1);
        Game sample2 = new Game(
                "Consectetur Adipiscing",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Elit",
                150);
        sample2 = gdao.addGame(sample2);
        Game sample3 = new Game(
                "Eiusmod Tempor",
                "M",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Incididunt",
                300);
        sample3 = gdao.addGame(sample3);

        List<Game> localList = new ArrayList<>();
        localList.add(sample1);
        localList.add(sample2);
        localList.add(sample3);

        List<Game> checkList = gdao.getAllGames();

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void getGame() {
        //same as add
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1 = gdao.addGame(sample1);

        Game checkGame = gdao.getGame(sample1.getGame_id());

        assertEquals(sample1, checkGame);
    }

    @Test
    public void updateGame() {
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1 = gdao.addGame(sample1);

        sample1.setPrice(new BigDecimal("29.99"));

        gdao.updateGame(sample1);

        Game checkGame = gdao.getGame(sample1.getGame_id());

        assertEquals(sample1.getPrice(), checkGame.getPrice());
        assertEquals(sample1, checkGame);
    }

    @Test
    public void deleteGame() {
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1 = gdao.addGame(sample1);

        gdao.deleteGame(sample1.getGame_id());

        Game checkGame = gdao.getGame(sample1.getGame_id());

        assertNull(checkGame);
    }

    @Test
    public void getGamesByStudio() {
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1 = gdao.addGame(sample1);
        Game sample2 = new Game(
                "Consectetur Adipiscing",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Elit",
                150);
        sample2 = gdao.addGame(sample2);
        Game sample3 = new Game(
                "Eiusmod Tempor",
                "M",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Incididunt",
                300);
        sample3 = gdao.addGame(sample3);

        List<Game> localList = new ArrayList<>();
        localList.add(sample1);


        List<Game> checkList = gdao.getGamesByStudio(sample1.getStudio());

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void getGamesByESRB() {
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1 = gdao.addGame(sample1);
        Game sample2 = new Game(
                "Consectetur Adipiscing",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Elit",
                150);
        sample2 = gdao.addGame(sample2);
        Game sample3 = new Game(
                "Eiusmod Tempor",
                "M",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Incididunt",
                300);
        sample3 = gdao.addGame(sample3);

        List<Game> localList = new ArrayList<>();
        localList.add(sample1);
        localList.add(sample2);

        List<Game> checkList = gdao.getGamesByESRB(sample1.getEsrb_rating());

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void getGamesByTitle() {
        Game sample1 = new Game(
                "Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        sample1 = gdao.addGame(sample1);
        Game sample2 = new Game(
                "Consectetur Adipiscing",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Elit",
                150);
        sample2 = gdao.addGame(sample2);
        Game sample3 = new Game(
                "Eiusmod Tempor",
                "M",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Incididunt",
                300);
        sample3 = gdao.addGame(sample3);

        List<Game> localList = new ArrayList<>();
        localList.add(sample3);

        List<Game> checkList = gdao.getGamesByTitle(sample3.getTitle());

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }
}