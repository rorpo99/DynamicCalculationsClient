package com.example.dynamic.calculations.client.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Formula  implements Serializable {

    private int id;
    private String formulaString;
    private Integer x1;
    private Integer x2;
    private Integer x3;
    private Integer x4;
    private Integer x5;
    private Integer result;
    
}
