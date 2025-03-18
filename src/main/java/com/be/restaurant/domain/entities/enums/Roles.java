package com.be.restaurant.domain.entities.enums;

public enum Roles {
    ADMIN("Admin"),
    USER("User");

    public final String val;

    private Roles(String label) {
        val = label;
    }
}
