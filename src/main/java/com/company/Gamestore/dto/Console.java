package com.company.Gamestore.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class Console {

    @Digits(integer = 11, fraction = 0)
    private int console_id;
    @NotNull
    @Size(max = 50)
    private String model;
    @NotNull
    @Size(max = 50)
    private String manufacturer;
    @Size(max = 20)
    private String memory_amount;
    @Size(max = 20)
    private String processor;
    @NotNull
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;
    @NotNull
    @Digits(integer = 11, fraction = 0)
    private int quantity;

    public int getConsole_id() {
        return console_id;
    }

    public void setConsole_id(int console_id) {
        this.console_id = console_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMemory_amount() {
        return memory_amount;
    }

    public void setMemory_amount(String memory_amount) {
        this.memory_amount = memory_amount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return quantity == console.quantity &&
                model.equals(console.model) &&
                manufacturer.equals(console.manufacturer) &&
                memory_amount.equals(console.memory_amount) &&
                processor.equals(console.processor) &&
                price.equals(console.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, manufacturer, memory_amount, processor, price, quantity);
    }

    public Console(String model, String manufacturer, String memory_amount, String processor, BigDecimal price, int quantity) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.memory_amount = memory_amount;
        this.processor = processor;
        this.price = price;
        this.quantity = quantity;
    }

    public Console(){}
}
