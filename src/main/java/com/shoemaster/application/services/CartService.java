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

    public Cart getByUsername(String username) {
        return cartClient.getByUserName(username);
    }

    public Cart addShoeToCart(Long userId, Shoe shoe) {return cartClient.addShoeToCart(userId, shoe);
    }

    public Cart removeShoeFromCart(String username, Long shoeId) {
        return cartClient.removeShoeFromCart(username, shoeId);
    }

}
