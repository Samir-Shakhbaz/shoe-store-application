package com.shoemaster.application.services;

import com.shoemaster.application.clients.CartClient;
import com.shoemaster.application.dtos.Cart;
import com.shoemaster.application.dtos.Shoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartClient cartClient;

    public Cart getByUserId(Long userId) {
        return cartClient.getByUserId(userId);
    }

    public Cart addShoeToCart(Long userId, Shoe shoe) {return cartClient.addShoeToCart(userId, shoe);
    }

    public Cart removeShoeFromCart(Long userId, Long cartItemId, Integer amount) {
        return cartClient.removeShoeFromCart(userId, cartItemId, amount);
    }

}
