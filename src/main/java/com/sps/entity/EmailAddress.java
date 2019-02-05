package com.sps.entity;

import lombok.Value;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class EmailAddress {

    private int priority;
    private String name;
}
