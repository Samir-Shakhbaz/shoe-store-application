package com.shoemaster.application.dtos;

import lombok.Data;

@Data
public class Shoe {
    private Long shoeId;
    private String make;
    private Float size;
    private String description;
    private Double price;
    private int quantity;
}
