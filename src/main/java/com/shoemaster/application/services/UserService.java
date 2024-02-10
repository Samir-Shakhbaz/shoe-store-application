package com.shoemaster.application.services;

import com.shoemaster.application.clients.UserClient;
import com.shoemaster.application.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserClient userClient;

@Autowired
    PasswordEncoder passwordEncoder;

    public List <User> getAllUsers(){

        return userClient.getAllUsers();

    }

//    public List<User> getAllUsers() {
//        ResponseEntity<User[]> response = restTemplate.getForEntity("http://USER-MICROSERVICE/users", User[].class);
//        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            return Arrays.asList(response.getBody());
//        } else {
//            return null;
//        }
//    }

    List<Integer> integerList = new ArrayList<>();

    public User getByUsername(String username){
        return userClient.getByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userClient.getByUsername(username);
    }

    public User createNewUser(User user) {
        if(user.getAddress() == null){
            user.setAddress("not set");
        }

        if(user.getFullName() == null){
            user.setFullName("no name");
        }

        if(user.getCard() == null){
            user.setCard(0L);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        return userClient.createClientNewUser(user);

    }

}
