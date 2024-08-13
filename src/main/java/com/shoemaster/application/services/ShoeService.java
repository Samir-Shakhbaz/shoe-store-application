package com.shoemaster.application.services;

import com.shoemaster.application.clients.ShoeClient;
import com.shoemaster.application.dtos.Shoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoeService {

    @Autowired
    private ShoeClient shoeClient;


    public List<Shoe> getAllShoes(){

        return shoeClient.getAllShoes();

    }

    public Shoe save(Shoe newShoe) {
        return shoeClient.createClientNewShoe(newShoe);
    }


    public Shoe findById(Long shoeId) {return shoeClient.getShoeById(shoeId);}

    public void deleteById(Long shoeId){
        shoeClient.deleteById(shoeId);
    }

    public void updateShoe(Long shoeId, Shoe updatedShoe) {
        shoeClient.updateById(shoeId, updatedShoe);
    }
}
