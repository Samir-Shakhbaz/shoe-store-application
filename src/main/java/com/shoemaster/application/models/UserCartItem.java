package com.shoemaster.application.models;

import com.shoemaster.application.dtos.Shoe;
import com.shoemaster.application.dtos.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCartItem {
    private User user;
    private Shoe shoe;
    private int amount;
    private Long cartItemId;
}
