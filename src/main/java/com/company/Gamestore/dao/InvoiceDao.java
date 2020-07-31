package com.company.Gamestore.dao;

import com.company.Gamestore.dto.Invoice;

import java.util.List;

public interface InvoiceDao {

    //C
    Invoice addInvoice(Invoice invoice);

    //R
    //all
    List<Invoice> getAllInvoices();
    //one
    Invoice getInvoice(int invoice_id);

    //no need to update. Invoices will not be updated after purchase.
    //data about returns should be stored separately, but is out of scope of current specs

    //D
    void deleteInvoice(int invoice_id);
}
