package com.shoemaster.application.clients;

import com.shoemaster.application.dtos.Shoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ShoeClient {

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public List<Shoe> getAllShoes() {
        ResponseEntity<Shoe[]> response = restTemplate.getForEntity("http://shoe-microservice/shoes", Shoe[].class);
        Shoe[] shoesArray = response.getBody(); // This will be an array of Shoe objects.

        // Convert the array to a List.
        List<Shoe> shoesList = Arrays.asList(shoesArray);

        // Return the List instead of null.
        return shoesList;
    }

    public Shoe createClientNewShoe(Shoe newShoe) {
        ResponseEntity<Shoe> response = restTemplate.postForEntity("http://shoe-microservice/shoes/create", newShoe, Shoe.class);
        return response.getBody();
    }


    public Shoe getShoeById(Long shoeId) {
        try {
            ResponseEntity<Shoe> response = restTemplate.getForEntity(
                    "http://shoe-microservice/shoes/" + shoeId,
                    Shoe.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception e) {
            // Handle exceptions
        }
        return null; // Return null or throw an exception if the shoe is not found
    }

}
