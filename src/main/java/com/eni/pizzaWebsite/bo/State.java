package com.eni.pizzaWebsite.bo;

public class State {
    private Long id_state;
    private String state;

    public State() {
        super();
    }

    public State(Long id_state, String state) {
        this.id_state = id_state;
        this.state = state;
    }

    public Long getId_state() {
        return id_state;
    }

    public String getState() {
        return state;
    }


    public void setId_state(Long id_state) {
        this.id_state = id_state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
