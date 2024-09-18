package com.eni.pizzaWebsite.bo;

public class State {
    private Long id_state;
    private String name;

    public State() {
        super();
    }

    public State(Long id_state, String name) {
        this.id_state = id_state;
        this.name = name;
    }

    public Long getId_state() {
        return id_state;
    }

    public String getName() {
        return name;
    }


    public void setId_state(Long id_state) {
        this.id_state = id_state;
    }

    public void setName(String name) {
        this.name = name;
    }
}
