package com.shoemaster.application.controllers;

import com.shoemaster.application.dtos.Shoe;
import com.shoemaster.application.dtos.User;
import com.shoemaster.application.services.CartService;
import com.shoemaster.application.services.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ShoeController {

    @Autowired
    private ShoeService shoeService;

    @Autowired
    private CartService cartService;

//    @PostMapping("/shoes")
//    public String displayShoes(Model model) {
//        model.addAttribute("shoes", shoeService.getAllShoes());
//        return "shoe-list";
//    }

    @GetMapping("shoe-list")
    public String userListView(Model model) {
        List<Shoe> shoes = shoeService.getAllShoes();
        model.addAttribute("shoes", shoes);
        return "shoe-list";
    }

    @PostMapping("/shoe-list")
    public String displayShoes(Model model) {
        List<Shoe> shoes = shoeService.getAllShoes();
        model.addAttribute("shoes", shoes);
        return "shoe-list";
    }

    @GetMapping("/create-shoe")
    public ModelAndView showCreateShoeForm() {
        return new ModelAndView("create-shoe");
    }

    @PostMapping("/create-shoe")
    public ModelAndView createShoe(@RequestParam("make") String make,
                                   @RequestParam("size") Float size,
                                   @RequestParam("description") String description) {

        Shoe newShoe = new Shoe();
        newShoe.setMake(make);
        newShoe.setSize(size);
        newShoe.setDescription(description);

        shoeService.save(newShoe); // Save the new shoe to the database

        return new ModelAndView("redirect:/shoe-list");
    }

    @PostMapping("/add")
    public ModelAndView addShoeToCart(@RequestParam("username") Long userId, @RequestBody Shoe shoe) {
        ModelAndView modelAndView = new ModelAndView("cart"); // Replace "cartView" with your view name
        try {
            cartService.addShoeToCart(userId, shoe); // Assuming your service has this method
            modelAndView.addObject("message", "Shoe added to cart successfully!");
        } catch (Exception e) {
            modelAndView.addObject("error", "Error adding shoe to cart: " + e.getMessage());
        }
        return modelAndView;
    }


}
