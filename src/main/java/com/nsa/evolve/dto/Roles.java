package com.nsa.evolve.dto;

public enum Roles {

    ROLE_ADMIN (1), ROLE_USER (2), ROLE_COMPANY (3), ROLE_CUSTOMER (4), ROLE_ASSESSOR (5);

    private final int id;

    Roles(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
