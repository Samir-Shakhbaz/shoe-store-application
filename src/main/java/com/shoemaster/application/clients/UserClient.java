package com.shoemaster.application.clients;

import com.shoemaster.application.dtos.Shoe;
import com.shoemaster.application.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class UserClient {

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

//    public List <User> getAllUsers(){
//
//        ResponseEntity <String> response = restTemplate.getForEntity("http://user-microservice/users", String.class);
//        System.out.println(response);
//        return null;
//    }

    public User getByUsername(String username) {
        try {
            ResponseEntity<User> response = restTemplate.getForEntity("http://user-microservice/users/username/" + username, User.class);
//            if (response.getStatusCode() == HttpStatusCode.valueOf(404)) {
//                return null;
//            }
            return response.getBody(); // This will be an array of User objects.

        }
        catch(HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatusCode.valueOf(404)){
                return null;
            }
            throw e;
        }


    }

    public List<User> getAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity("http://user-microservice/users", User[].class);
        User[] usersArray = response.getBody(); // This will be an array of User objects.

        // Convert the array to a List...
        List<User> usersList = Arrays.asList(usersArray);

        // Return the List instead of null....
        return usersList;
    }

    public User createClientNewUser(User user){

        ResponseEntity<User> response = restTemplate.postForEntity("http://user-microservice/users/create-account", user, User.class);
       return response.getBody();
    }

    public void deleteById(Long userId) {
        String url = "http://user-microservice/users/delete/" + userId;
        restTemplate.delete(url);
    }
}
