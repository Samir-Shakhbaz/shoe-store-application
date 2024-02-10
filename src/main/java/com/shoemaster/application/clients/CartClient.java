package com.shoemaster.application.clients;

import com.shoemaster.application.dtos.Cart;
import com.shoemaster.application.dtos.Shoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CartClient {
    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public Cart getByUserName(String username) {
        ResponseEntity<Cart> response = restTemplate.getForEntity("http://cart-microservice/carts/" + username, Cart.class);
        Cart cart = response.getBody(); // This will be an array of User objects.
        return cart;
    }


    public Cart addShoeToCart(Long userId, Shoe shoe){
        ResponseEntity<Cart> response = restTemplate.postForEntity("http://cart-microservice/carts/"  + userId + "/shoes", shoe, Cart.class);
        return response.getBody();
    }

    public Cart removeShoeFromCart(String username, Long shoeId) {

        ResponseEntity<Cart> response = restTemplate.postForEntity("http://cart-microservice/carts/" + username + "/shoes/remove", shoeId, Cart.class);

        return response.getBody();
    }

}
