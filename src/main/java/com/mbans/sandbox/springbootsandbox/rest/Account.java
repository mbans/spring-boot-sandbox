package com.mbans.sandbox.springbootsandbox.rest;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lumarmacy1 on 14/11/2018.
 */
@Getter
@Setter
public class Account {
    private Integer id;
    private String name;

    public Account(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
}
