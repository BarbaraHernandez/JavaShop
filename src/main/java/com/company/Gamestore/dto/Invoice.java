package com.company.Gamestore.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class Invoice {

    @Digits(integer = 11, fraction = 0)
    private int invoice_id;
    @NotNull
    @Size(max = 80)
    private String name;
    @NotNull
    @Size(max = 30)
    private String street;
    @NotNull
    @Size(max = 30)
    private String city;
    @NotNull
    @Size(max = 30)
    //Order must contain a valid state code, so state should actually be passed as 2 digit code (i.e. AK)
    private String state;
    @NotNull
    @Size(max = 5)
    private String zipcode;
    @NotNull
    @Size(max = 20)
    private String item_type; //should correspond to one of three other tables
    @NotNull
    @Digits(integer = 11, fraction = 0)
    private int item_id;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal unit_price;
    @NotNull
    @Digits(integer = 11, fraction = 0)
    private int quantity;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal subtotal;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal tax;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal processing_fee;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal total;

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getProcessing_fee() {
        return processing_fee;
    }

    public void setProcessing_fee(BigDecimal processing_fee) {
        this.processing_fee = processing_fee;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoice_id == invoice.invoice_id &&
                item_id == invoice.item_id &&
                quantity == invoice.quantity &&
                Objects.equals(name, invoice.name) &&
                Objects.equals(street, invoice.street) &&
                Objects.equals(city, invoice.city) &&
                Objects.equals(state, invoice.state) &&
                Objects.equals(zipcode, invoice.zipcode) &&
                Objects.equals(item_type, invoice.item_type) &&
                Objects.equals(unit_price, invoice.unit_price) &&
                Objects.equals(subtotal, invoice.subtotal) &&
                Objects.equals(tax, invoice.tax) &&
                Objects.equals(processing_fee, invoice.processing_fee) &&
                Objects.equals(total, invoice.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice_id, name, street, city, state, zipcode, item_type, item_id, unit_price, quantity, subtotal, tax, processing_fee, total);
    }

    //version of invoice as passed to db
    public Invoice(String name, String street, String city, String state, String zipcode, String item_type, int item_id, BigDecimal unit_price, int quantity, BigDecimal subtotal, BigDecimal tax, BigDecimal processing_fee, BigDecimal total) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.item_type = item_type;
        this.item_id = item_id;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.tax = tax;
        this.processing_fee = processing_fee;
        this.total = total;
    }

    //version of invoice as passed in by client
    public Invoice(@Digits(integer = 11, fraction = 0) int invoice_id, @NotNull @Size(max = 80) String name, @NotNull @Size(max = 30) String street, @NotNull @Size(max = 30) String city, @NotNull @Size(max = 30) String state, @NotNull @Size(max = 5) String zipcode, @NotNull @Size(max = 20) String item_type, @NotNull @Digits(integer = 11, fraction = 0) int item_id, @NotNull @Digits(integer = 11, fraction = 0) int quantity) {
        this.invoice_id = invoice_id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.item_type = item_type;
        this.item_id = item_id;
        this.quantity = quantity;
    }

    public Invoice(){}
}
