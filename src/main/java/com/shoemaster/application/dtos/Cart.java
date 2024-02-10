package com.shoemaster.application.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Cart {

    private Long id;

    private Long userId;

    private List<CartItem> shoes;

}
