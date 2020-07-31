package com.company.Gamestore.dto;

import java.util.Objects;

public class Tax {

    private String state;
    private float rate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax that = (Tax) o;
        return Float.compare(that.rate, rate) == 0 &&
                state.equals(that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, rate);
    }

    public Tax(String state, float rate) {
        this.state = state;
        this.rate = rate;
    }

    public Tax(){}
}
