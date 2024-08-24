package com.shoemaster.application.clients;

import com.shoemaster.application.dtos.Cart;
import com.shoemaster.application.dtos.Shoe;
import com.shoemaster.application.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CartClient {
    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public Cart getByUserId(Long userId) {
        ResponseEntity<Cart> response = restTemplate.getForEntity("http://cart-microservice/carts/" + userId, Cart.class);
        Cart cart = response.getBody(); // This will be an array of User objects.
        return cart;
    }


    public Cart addShoeToCart(Long userId, Shoe shoe){
        ResponseEntity<Cart> response = restTemplate.postForEntity("http://cart-microservice/carts/"  + userId + "/shoes", shoe, Cart.class);
        return response.getBody();
    }

    public Cart removeShoeFromCart(Long userId, Long cartItemId, Integer amount) {

        String url = "http://cart-microservice/carts/" + userId + "/" + cartItemId + "?amount=" + amount;

        ResponseEntity<Cart> response = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                null,
                Cart.class);

        return response.getBody();
    }

    public Cart updateCartItemQuantity(Long userId, Long cartItemId, boolean increase) {
        String url = "http://cart-microservice/carts/" + userId + "/" + cartItemId + "?increase=" + increase;

        ResponseEntity<Cart> response = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                null,
                Cart.class);

        return response.getBody();

    }

    public void deleteById(Long cartItemId) {
        String url = "http://cart-microservice/carts/delete/" + cartItemId;
        restTemplate.delete(url);
    }

}
