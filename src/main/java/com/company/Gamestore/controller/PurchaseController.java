package com.company.Gamestore.controller;

import com.company.Gamestore.dto.Invoice;
import com.company.Gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/** This controller handles all endpoints for the invoice entity.*/
@RestController
public class PurchaseController {
    @Autowired
    private ServiceLayer serviceLayer;

    /** All users are able to create a purchase in order to enable sales
     * @return Invoice object*/
    //create
    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Invoice makePurchase(@RequestBody @Valid Invoice invoice) {
        return serviceLayer.makePurchase(invoice);
    }

    /** Read endpoints allow users to view a given invoice.
     * No authentication is required for any searches.
     * The readInvoice method displays a single invoice with a matching ID.
     * @return A single invoice item with the matching id.
     * @throws \Empty Result Data Access Exception */
    @RequestMapping(value = "/purchase/{invoiceId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public Invoice readInvoice(@PathVariable int invoiceId) {
        if (serviceLayer.readInvoice(invoiceId) == null){
            throw new EmptyResultDataAccessException(1);
        } else {
            return serviceLayer.readInvoice(invoiceId);
        }
    }
}
