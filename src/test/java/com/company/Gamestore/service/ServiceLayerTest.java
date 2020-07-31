package com.company.Gamestore.service;

import com.company.Gamestore.dao.*;
import com.company.Gamestore.dto.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class ServiceLayerTest {

    @MockBean
    private GameDao gameDao;
    @MockBean
    private ConsoleDao consoleDao;
    @MockBean
    private TshirtDao tshirtDao;
    @MockBean
    private InvoiceDao invoiceDao;
    @MockBean
    private TaxDao taxDao;
    @MockBean
    private ProcessingFeeDao feeDao;

    @InjectMocks
    private ServiceLayer service;

    //Argument Captors
    private ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
    private ArgumentCaptor<Console> consoleArgumentCaptor = ArgumentCaptor.forClass(Console.class);
    private ArgumentCaptor<Tshirt> tshirtArgumentCaptor = ArgumentCaptor.forClass(Tshirt.class);
    private ArgumentCaptor<Invoice> invoiceArgumentCaptor = ArgumentCaptor.forClass(Invoice.class);
    private ArgumentCaptor<ProcessingFee> processingFeeArgumentCaptor = ArgumentCaptor.forClass(ProcessingFee.class);
    private ArgumentCaptor<Tax> taxArgumentCaptor = ArgumentCaptor.forClass(Tax.class);
    private ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
    private ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);


    @Before
    public void setUp() throws Exception {
        //setup mocks of DAOs
        setUpGameDaoMock();
        setUpConsoleDaoMock();
        setUpTshirtDaoMock();
        setUpInvoiceDaoMock();
        setUpTaxDaoMock();
        setUpProcessingFeeDaoMock();
        service = new ServiceLayer(gameDao, consoleDao, tshirtDao, invoiceDao, taxDao, feeDao);
    }

    @Test
    public void makePurchase() {
        //create and attempt to send/save purchase information as invoice
        Invoice exampleInvoiceInput = new Invoice();
        exampleInvoiceInput.setName("name");
        exampleInvoiceInput.setStreet("street");
        exampleInvoiceInput.setCity("city");
        exampleInvoiceInput.setState("AK");
        exampleInvoiceInput.setZipcode("12345");
        exampleInvoiceInput.setItem_type("game");
        exampleInvoiceInput.setItem_id(1);
        exampleInvoiceInput.setQuantity(2);

        exampleInvoiceInput = service.makePurchase(exampleInvoiceInput);//example is null

        //verify with read
        Invoice checkInvoice = service.readInvoice(exampleInvoiceInput.getInvoice_id());

        //assert equal
        assertEquals(exampleInvoiceInput.getInvoice_id(), checkInvoice.getInvoice_id());
        assertEquals(exampleInvoiceInput.getName(), checkInvoice.getName());
        assertNotNull(checkInvoice.getTotal());
    }

    @Test
    public void readInvoice() {
        //simplified - just checking that an invoice is returned
        Invoice checkInvoice = service.readInvoice(1);

        assertNotNull(checkInvoice);
    }

    @Test
    public void createGame() {
        //create and attempt to send/save game
        Game exampleGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        exampleGame = service.createGame(exampleGame);

        //verify with read
        Game checkGame = service.readGame(exampleGame.getGame_id());

        //assert equal
        assertEquals(exampleGame, checkGame);
    }

    @Test
    public void readAllGames() {
        //create and attempt to send/save game
        Game exampleGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        exampleGame = service.createGame(exampleGame);

        List<Game> localList = new ArrayList<>();
        localList.add(exampleGame);

        //verify with read all
        List<Game> checkList = service.readAllGames();

        //assert equal
        assertEquals(localList, checkList);

    }

    @Test
    public void readGame() {
        //simplified, checking to see than some game is returned

        Game checkGame = service.readGame(1);

        assertNotNull(checkGame);
    }

    @Test
    public void updateGame() {
        //create and attempt to send/save game
        Game exampleGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        exampleGame = service.createGame(exampleGame);

        //update, value auto captured in class level variable by method on set up
        exampleGame.setPrice(new BigDecimal("49.99"));

        service.updateGame(exampleGame);

        //verify the number of time run
        verify(gameDao, times(1)).updateGame(exampleGame);
        //assert local object and captured object.value are the same
        assertEquals(exampleGame,gameArgumentCaptor.getValue());
    }

    @Test
    public void deleteGame() {
        //create and attempt to send/save game
        Game exampleGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        exampleGame = service.createGame(exampleGame);

        //delete by id, id auto captured in class level variable by method on set up
        service.deleteGame(exampleGame.getGame_id());

        int argCap = integerArgumentCaptor.getValue();
        //verify the number of time run
        verify(gameDao, times(1)).deleteGame(argCap);
        //assert local object and captured object.value are the same
        assertEquals(exampleGame.getGame_id(), argCap);
    }

    @Test
    public void readGameByStudio() {
        //create and attempt to send/save game
        Game exampleGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        exampleGame = service.createGame(exampleGame);

        //create local list
        List<Game> localList = new ArrayList<>();
        localList.add(exampleGame);

        //verify with read
        List<Game> checkList = service.readAllGames();

        //assert equal
        assertEquals(localList, checkList);
        assertEquals(localList.size(), checkList.size());
    }

    @Test
    public void readGameByESRB() {
        //create and attempt to send/save game
        Game exampleGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        exampleGame = service.createGame(exampleGame);

        //create local list
        List<Game> localList = new ArrayList<>();
        localList.add(exampleGame);

        //verify with read
        List<Game> checkList = service.readGameByESRB("G");

        //assert equal
        assertEquals(localList, checkList);
        assertEquals(localList.size(), checkList.size());
    }

    @Test
    public void readGameByTitle() {
        //create and attempt to send/save game
        Game exampleGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        exampleGame = service.createGame(exampleGame);

        //create local list
        List<Game> localList = new ArrayList<>();
        localList.add(exampleGame);

        //verify with read
        List<Game> checkList = service.readGameByTitle("Lorem Ipsum");

        //assert equal
        assertEquals(localList, checkList);
        assertEquals(localList.size(), checkList.size());
    }

    @Test
    public void createConsole() {
        Console exampleConsole = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        exampleConsole = service.createConsole(exampleConsole);

        Console checkConsole = service.readConsole(exampleConsole.getConsole_id());

        assertEquals(exampleConsole, checkConsole);
    }

    @Test
    public void readAllConsoles() {
        Console exampleConsole = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        exampleConsole = service.createConsole(exampleConsole);

        List<Console> localList = new ArrayList<>();
        localList.add(exampleConsole);

        List<Console> checkList = service.readAllConsoles();

        assertEquals(localList, checkList);
        assertEquals(localList.size(), checkList.size());
    }

    @Test
    public void readConsole() {
        Console checkConsole = service.readConsole(1);

        assertNotNull(checkConsole);
    }

    @Test
    public void updateConsole() {
        Console exampleConsole = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        exampleConsole = service.createConsole(exampleConsole);

        exampleConsole.setPrice(new BigDecimal("79.99"));

        service.updateConsole(exampleConsole);

        verify(consoleDao, times(1)).updateConsole(consoleArgumentCaptor.getValue());
        assertEquals(exampleConsole, consoleArgumentCaptor.getValue());
    }

    @Test
    public void deleteConsole() {
        Console exampleConsole = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        exampleConsole = service.createConsole(exampleConsole);

        service.deleteConsole(exampleConsole.getConsole_id());

        int capVal = integerArgumentCaptor.getValue();

        verify(consoleDao, times(1)).deleteConsole(capVal);
        assertEquals(exampleConsole.getConsole_id(), capVal);
    }

    @Test
    public void readConsolesByManufacturer() {
        Console exampleConsole = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        exampleConsole = service.createConsole(exampleConsole);

        List<Console> localList = new ArrayList<>();
        localList.add(exampleConsole);

        List<Console> checkList = service.readConsolesByManufacturer("ipsum");

        assertEquals(localList, checkList);
        assertEquals(localList.size(), checkList.size());
    }

    @Test
    public void createTshirt() {
        Tshirt exampleTshirt = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        exampleTshirt = service.createTshirt(exampleTshirt);

        Tshirt checkShirt = service.readTshirt(exampleTshirt.getT_shirt_id());

        assertEquals(exampleTshirt, checkShirt);
    }

    @Test
    public void readAllTshirts() {
        Tshirt exampleTshirt = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        exampleTshirt = service.createTshirt(exampleTshirt);

        List<Tshirt> localList = new ArrayList<>();
        localList.add(exampleTshirt);

        List<Tshirt> checkList = service.readAllTshirts();

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void readTshirt() {
        Tshirt checkShirt = service.readTshirt(1);

        assertNotNull(checkShirt);
    }

    @Test
    public void updateTshirt() {
        Tshirt exampleTshirt = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        exampleTshirt = service.createTshirt(exampleTshirt);

        exampleTshirt.setQuantity(50);

        service.updateTshirt(exampleTshirt);

        verify(tshirtDao, times(1)).updateTshirt(tshirtArgumentCaptor.getValue());
        assertEquals(exampleTshirt, tshirtArgumentCaptor.getValue());
    }

    @Test
    public void deleteTshirt() {
        Tshirt exampleTshirt = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        exampleTshirt = service.createTshirt(exampleTshirt);

        service.deleteTshirt(exampleTshirt.getT_shirt_id());

        verify(tshirtDao, times(1)).deleteTshirt(integerArgumentCaptor.getValue());
    }

    @Test
    public void readTshirtByColor() {
        Tshirt exampleTshirt = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        exampleTshirt = service.createTshirt(exampleTshirt);

        List<Tshirt> localList = new ArrayList<>();
        localList.add(exampleTshirt);

        List<Tshirt> checkList = service.readTshirtByColor("black");

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    @Test
    public void readTshirtBySize() {
        Tshirt exampleTshirt = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        exampleTshirt = service.createTshirt(exampleTshirt);

        List<Tshirt> localList = new ArrayList<>();
        localList.add(exampleTshirt);

        List<Tshirt> checkList = service.readTshirtBySize("M");

        assertEquals(localList.size(), checkList.size());
        assertEquals(localList, checkList);
    }

    //mocks

    //game
    private void setUpGameDaoMock(){
        gameDao = mock(GameImplementation.class);
        Game savedGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);
        savedGame.setGame_id(1);

        Game exampleGame = new Game("Lorem Ipsum",
                "G",
                "Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                new BigDecimal("59.99"),
                "Dolor",
                200);

        List<Game> gList = new ArrayList<>();
        gList.add(savedGame);

        doReturn(savedGame).when(gameDao).addGame(exampleGame);
        doReturn(savedGame).when(gameDao).getGame(1);
        doReturn(gList).when(gameDao).getAllGames();
        doReturn(gList).when(gameDao).getGamesByTitle("Lorem Ipsum");
        doReturn(gList).when(gameDao).getGamesByESRB("G");
        doReturn(gList).when(gameDao).getGamesByStudio("Dolor");
        doNothing().when(gameDao).updateGame(gameArgumentCaptor.capture());
        doNothing().when(gameDao).deleteGame(integerArgumentCaptor.capture());
    }

    //console
    private void setUpConsoleDaoMock(){
        consoleDao = mock(ConsoleImplementation.class);
        Console savedConsole = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);
        savedConsole.setConsole_id(1);

        Console exampleConsole = new Console("lorem","ipsum","500","dolor",new BigDecimal("199.99"),20);

        List<Console> cList = new ArrayList<>();
        cList.add(savedConsole);

        doReturn(savedConsole).when(consoleDao).addConsole(exampleConsole);
        doReturn(savedConsole).when(consoleDao).getConsole(1);
        doReturn(cList).when(consoleDao).getAllConsoles();
        doReturn(cList).when(consoleDao).getConsolesByManufacturer("ipsum");
        doNothing().when(consoleDao).updateConsole(consoleArgumentCaptor.capture());
        doNothing().when(consoleDao).deleteConsole(integerArgumentCaptor.capture());
    }

    //t-shirt
    private void setUpTshirtDaoMock(){
        tshirtDao = mock(TshirtImplementation.class);
        Tshirt savedTshirt = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);
        savedTshirt.setT_shirt_id(1);

        Tshirt exampleTshirt = new Tshirt("M", "black", "best t-shirt ever", new BigDecimal("19.99"), 10);

        List<Tshirt> tList = new ArrayList<>();
        tList.add(savedTshirt);

        doReturn(savedTshirt).when(tshirtDao).addTshirt(exampleTshirt);
        doReturn(savedTshirt).when(tshirtDao).getTshirt(1);
        doReturn(tList).when(tshirtDao).getAllTshirts();
        doReturn(tList).when(tshirtDao).getTshirtBySize("M");
        doReturn(tList).when(tshirtDao).getTshirtByColor("black");
        doNothing().when(tshirtDao).updateTshirt(tshirtArgumentCaptor.capture());
        doNothing().when(tshirtDao).deleteTshirt(integerArgumentCaptor.capture());
    }

    //invoice

    private void setUpInvoiceDaoMock(){
        invoiceDao = mock(InvoiceImplementation.class);

        Invoice savedInvoice = new Invoice(
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
        savedInvoice.setInvoice_id(1);

        //expecting partial data
        Invoice exampleInvoiceInput = new Invoice();
        exampleInvoiceInput.setName("name");
        exampleInvoiceInput.setStreet("street");
        exampleInvoiceInput.setCity("city");
        exampleInvoiceInput.setState("AK");
        exampleInvoiceInput.setZipcode("12345");
        exampleInvoiceInput.setItem_type("game");
        exampleInvoiceInput.setItem_id(1);
        exampleInvoiceInput.setQuantity(2);

        //data as it will be passed to .add method
        Invoice preparedInvoice = new Invoice(
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

        List<Invoice> iList = new ArrayList<>();
        iList.add(savedInvoice);

        //doReturn(savedInvoice).when(service).makePurchase(exampleInvoiceInput);
        doReturn(savedInvoice).when(invoiceDao).addInvoice(preparedInvoice);
        doReturn(savedInvoice).when(invoiceDao).getInvoice(1);
        doReturn(savedInvoice).when(invoiceDao).getInvoice(exampleInvoiceInput.getInvoice_id());
        //no other methods required
    }

    private void setUpTaxDaoMock(){
        taxDao = mock(TaxImplementation.class);
        Tax savedTax = new Tax("AK", 0.06f);

        doReturn(savedTax).when(taxDao).getTax("AK");
    }

    private void setUpProcessingFeeDaoMock(){
        feeDao = mock(ProcessingFeeImplementation.class);
        ProcessingFee savedFee = new ProcessingFee("Games", new BigDecimal("1.49"));

        doReturn(savedFee).when(feeDao).getProcessingFee("Games");
    }
}