package com.company.Gamestore.service;

import com.company.Gamestore.dao.*;
import com.company.Gamestore.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/** The service layer should handle all business logic.
 * It outsources database activity to DAO classes.
 */
@Component
public class ServiceLayer {
    //Properties
    private GameDao gameDao;
    private ConsoleDao consoleDao;
    private TshirtDao tshirtDao;
    private InvoiceDao invoiceDao;
    private TaxDao taxDao;
    private ProcessingFeeDao feeDao;

    //constructor
    @Autowired
    public ServiceLayer(GameDao gameDao, ConsoleDao consoleDao, TshirtDao tshirtDao, InvoiceDao invoiceDao, TaxDao taxDao, ProcessingFeeDao feeDao) {
        this.gameDao = gameDao;
        this.consoleDao = consoleDao;
        this.tshirtDao = tshirtDao;
        this.invoiceDao = invoiceDao;
        this.taxDao = taxDao;
        this.feeDao = feeDao;
    }

    //Invoice-Purchase API
    /** The makePurchase method is responsible for taking in a user invoice and applying business logic.
     * This method calculates tax and processing fee.
     * This method produces a complete invoice and makes necessary changes to databases (updating inventory, etc.)
     * */
    //Create
    public Invoice makePurchase(Invoice invoice) throws IllegalArgumentException {
        //to set
        //get unit price from respective table, using Item type and ID
        BigDecimal up;
        BigDecimal pf;

        //ensure the order contains Qty of at least 1 0
        if (invoice.getQuantity() < 1){
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }

        //determine target table and get all required information
        switch (invoice.getItem_type()){
            case "game":
            case "Game":
                //find correct game
                Game gItem = gameDao.getGame(invoice.getItem_id());
                //get price
                up = gItem.getPrice();
                //check availability
                if (invoice.getQuantity() <= gItem.getQuantity()){
                    //update quantity in database to reflect purchase
                    gItem.setQuantity(gItem.getQuantity() - invoice.getQuantity());
                    gameDao.updateGame(gItem);
                } else {
                    throw new IllegalArgumentException("Insufficient Stock");
                }
                //get processing fee object
                ProcessingFee gFee = feeDao.getProcessingFee("Games");
                //capture fee
                pf = gFee.getFee();
                break;
            case "console":
            case "Console":
                //find correct console
                Console cItem = consoleDao.getConsole(invoice.getInvoice_id());
                //get price
                up = cItem.getPrice();
                //check availability
                if (invoice.getQuantity() <= cItem.getQuantity()){
                    //update quantity in database to reflect purchase
                    cItem.setQuantity(cItem.getQuantity() - invoice.getQuantity());
                    consoleDao.updateConsole(cItem);
                } else {
                    throw new IllegalArgumentException("Insufficient Stock");
                }
                //get processing fee object
                ProcessingFee cFee = feeDao.getProcessingFee("Consoles");
                //capture fee
                pf = cFee.getFee();
                break;
            case "t-shirt":
            case "T-Shirt":
                //find correct t-shirt
                Tshirt tItem = tshirtDao.getTshirt(invoice.getInvoice_id());
                //get price
                up = tItem.getPrice();
                //check availability
                if (invoice.getQuantity() <= tItem.getQuantity()){
                    //update quantity in database to reflect purchase
                    tItem.setQuantity(tItem.getQuantity() - invoice.getQuantity());
                    tshirtDao.updateTshirt(tItem);
                } else {
                    throw new IllegalArgumentException("Insufficient Stock");
                }
                //get processing fee object
                ProcessingFee tFee = feeDao.getProcessingFee("T-Shirts");
                //capture fee
                pf = tFee.getFee();
                break;
            default:
                throw new IllegalArgumentException("Item type not found");
        }
        BigDecimal formattedUp = new BigDecimal(new DecimalFormat("0.00").format(up));
        invoice.setUnit_price(formattedUp);

        //calculate subtotal
        //-- unit price * quantity, must reconcile all as BigDecimal
        BigDecimal subT;
        int quantity = invoice.getQuantity();
        subT = formattedUp.multiply(new BigDecimal(quantity));
        BigDecimal formattedSubT = new BigDecimal(new DecimalFormat("0.00").format(subT));
        invoice.setSubtotal(formattedSubT);

        //get tax from table based on State -- (Georgia --> GA)
        BigDecimal tx;
        String stateCode = invoice.getState();
        Tax tax = new Tax();

        //Expect state code
        if (stateCode.length() <= 2){
            tax = taxDao.getTax(stateCode);
        } else {
            throw new IllegalArgumentException("State must be passed as valid two character string.");
        }

        //check that state was in the tax table and some tax object was returned
        if (tax.equals(null)){
            throw new IllegalArgumentException("Could not find State. Please provide a valid State code (i.e. AK, Al, etc.)");
        }

        //capture rate
        Float taxRate = tax.getRate();
        //calculate exact tax amount
        tx = formattedSubT.multiply(new BigDecimal(taxRate));
        //format to round down to two decimal places
        BigDecimal formattedTx = new BigDecimal(new DecimalFormat("0.00").format(tx));
        //set tax on invoice object
        invoice.setTax(formattedTx);

        //get processing fee from table based on item type, done in switch case above
        //check to see if additional fee applies
        if(invoice.getQuantity() > 10){
            pf = pf.add(new BigDecimal("15.49"));
        }

        invoice.setProcessing_fee(pf);

        //calculate total
        BigDecimal total = formattedSubT.add(pf);
        total = formattedTx.add(total);
        invoice.setTotal(total);

        //get ID on add
        //invoice = invoiceDao.addInvoice(invoice);
        invoice = invoiceDao.addInvoice(invoice);
        return invoice;
    }


    //Read (not in spec)
    public Invoice readInvoice(int invoice_id){
        return invoiceDao.getInvoice(invoice_id);
    }

    //========================================================

    //Game
    //C
    public Game createGame(Game game){
        return gameDao.addGame(game);
    }

    //R
    //all
    public List<Game> readAllGames(){
        return gameDao.getAllGames();
    }
    //one
    public Game readGame(int game_id){
        return gameDao.getGame(game_id);
    }

    //U
    public void updateGame(Game game){
        gameDao.updateGame(game);
    }

    //D
    public void deleteGame(int game_id){
        gameDao.deleteGame(game_id);
    }

    //get by studio
    public List<Game> readGameByStudio(String studio){
        return gameDao.getGamesByStudio(studio);
    }

    //get by ESRB
    public List<Game> readGameByESRB(String esrb){
        return gameDao.getGamesByESRB(esrb);
    }

    //get by Title
    public List<Game> readGameByTitle(String title){
        return gameDao.getGamesByTitle(title);
    }

    //========================================================

    //Console
    //C
    public Console createConsole(Console console){
        return consoleDao.addConsole(console);
    }

    //R
    //all
    public List<Console> readAllConsoles(){
        return consoleDao.getAllConsoles();
    }
    //one
    public Console readConsole(int console_id){
        return consoleDao.getConsole(console_id);
    }

    //U
    public void updateConsole(Console console){
        consoleDao.updateConsole(console);
    }

    //D
    public void deleteConsole(int console_id){
        consoleDao.deleteConsole(console_id);
    }

    //get by manufacturer
    public List<Console> readConsolesByManufacturer(String manufacturer){
        return consoleDao.getConsolesByManufacturer(manufacturer);
    }

    //========================================================

    //T-shirt
    //C
    public Tshirt createTshirt(Tshirt tshirt){
        return tshirtDao.addTshirt(tshirt);
    }

    //R
    //all
    public List<Tshirt> readAllTshirts(){
        return tshirtDao.getAllTshirts();
    }
    //one
    public Tshirt readTshirt(int tId){
        return tshirtDao.getTshirt(tId);
    }

    //U
    public void updateTshirt(Tshirt tshirt){
        tshirtDao.updateTshirt(tshirt);
    }

    //D
    public void deleteTshirt(int tId){
        tshirtDao.deleteTshirt(tId);
    }

    //get by color
    public List<Tshirt> readTshirtByColor(String color){
        return tshirtDao.getTshirtByColor(color);
    }

    //get by size
    public List<Tshirt> readTshirtBySize(String size){
        return tshirtDao.getTshirtBySize(size);
    }
}
