package com.shoemaster.application.dtos;

import lombok.Data;

@Data
public class CartItem {

    private Long id;

    private Long shoeId;

    private Integer amount;

}
