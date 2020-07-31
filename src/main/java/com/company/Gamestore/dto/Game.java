package com.company.Gamestore.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class Game {

    @Digits(integer = 11, fraction = 0)
    private int game_id;
    @NotNull
    @Size(max = 50)
    private String title;
    @NotNull
    @Size(max = 50)
    private  String esrb_rating;
    @NotNull
    @Size(max = 255)
    private String description;
    @NotNull
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;
    @NotNull
    @Size(max = 50)
    private String studio;
    private int quantity;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEsrb_rating() {
        return esrb_rating;
    }

    public void setEsrb_rating(String esrb_rating) {
        this.esrb_rating = esrb_rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
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
        Game game = (Game) o;
        return quantity == game.quantity &&
                title.equals(game.title) &&
                esrb_rating.equals(game.esrb_rating) &&
                description.equals(game.description) &&
                price.equals(game.price) &&
                studio.equals(game.studio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, esrb_rating, description, price, studio, quantity);
    }

    public Game(String title, String esrb_rating, String description, BigDecimal price, String studio, int quantity) {
        this.title = title;
        this.esrb_rating = esrb_rating;
        this.description = description;
        this.price = price;
        this.studio = studio;
        this.quantity = quantity;
    }

    public Game(){}
}
