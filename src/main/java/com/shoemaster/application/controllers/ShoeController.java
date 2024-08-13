package com.shoemaster.application.controllers;

import com.shoemaster.application.dtos.Cart;
import com.shoemaster.application.dtos.Shoe;
import com.shoemaster.application.dtos.User;
import com.shoemaster.application.services.CartService;
import com.shoemaster.application.services.ShoeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
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


    @GetMapping("/shoe-list")
    public String viewShoeList(HttpSession session, Model model) {
        List<Shoe> shoes = shoeService.getAllShoes(); // Assuming you have a method to get all shoes
        List<Shoe> cart = getSessionAttribute(session, "cart", Shoe.class);

        // Update the shoe list with the quantities in the cart
        for (Shoe shoe : shoes) {
            for (Shoe cartShoe : cart) {
                if (shoe.getShoeId().equals(cartShoe.getShoeId())) {
                    shoe.setQuantity(cartShoe.getQuantity());
                }
            }
        }

        model.addAttribute("shoes", shoes);
        return "shoe-list"; // Name of the Thymeleaf template for the shoe list
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getSessionAttribute(HttpSession session, String name, Class<T> type) {
        Object attribute = session.getAttribute(name);
        if (attribute instanceof List<?>) {
            return (List<T>) attribute;
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/shoe-list")
    public String displayShoes(Model model) {
        List<Shoe> shoes = shoeService.getAllShoes();
        model.addAttribute("shoes", shoes);
        return "shoe-list";
    }

//    @GetMapping("/create-shoe")
//    public ModelAndView showCreateShoeForm() {
//        return new ModelAndView("create-shoe");
//    }

//    @PostMapping("/create-shoe")
//    public ModelAndView createShoe(@RequestParam("make") String make,
//                                   @RequestParam("size") Float size,
//                                   @RequestParam("description") String description,
//                                   @RequestParam("price") Double price) {
//
//        Shoe newShoe = new Shoe();
//        newShoe.setMake(make);
//        newShoe.setSize(size);
//        newShoe.setDescription(description);
//        newShoe.setPrice(price);
//
//        shoeService.save(newShoe);
//
//        return new ModelAndView("redirect:/shoe-list");
//    }

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

    @PostMapping("/shoe-list/remove")
    public String removeFromShoeList(@RequestParam("shoeId") Long shoeId, HttpSession session) {
        List<Shoe> cart = com.shoemaster.application.utils.SessionUtil.getSessionAttribute(session, "cart", Shoe.class);
        if (cart != null) {
            Iterator<Shoe> iterator = cart.iterator();
            while (iterator.hasNext()) {
                Shoe s = iterator.next();
                if (s.getShoeId().equals(shoeId)) {
                    if (s.getQuantity() > 1) {
                        s.setQuantity(s.getQuantity() - 1);
                    } else {
                        iterator.remove();
                    }
                    break;
                }
            }
        }
        return "redirect:/shoe-list";
    }

//    @SuppressWarnings("unchecked")
//    private <T> List<T> getSessionAttribute(HttpSession session, String name, Class<T> type) {
//        Object attribute = session.getAttribute(name);
//        if (attribute instanceof List<?>) {
//            return (List<T>) attribute;
//        } else {
//            return new ArrayList<>();
//        }
//    }

    @PostMapping("/shoe-list/add")
    public String addToShoeList(@RequestParam("shoeId") Long shoeId, HttpSession session, Authentication authentication) {
            User user = (User)authentication.getPrincipal();

            Shoe shoe = shoeService.findById(shoeId);
            Cart cart = cartService.addShoeToCart(user.getUserId(), shoe);

        session.setAttribute("cart", cart); // Ensure cart is updated in the session
        return "redirect:/shoe-list";
    }

    @PostMapping("/shoe-list/delete")
    public String deleteShoe(@RequestParam("shoeId") Long shoeId) {
        shoeService.deleteById(shoeId);
        return "redirect:/shoe-list";
    }

    @GetMapping("/shoe-list/edit")
    public String editShoe(@RequestParam("shoeId") Long shoeId, Model model) {
        Shoe shoe = shoeService.findById(shoeId);
        model.addAttribute("shoe", shoe);
        return "shoe-form"; // Use the same form template
    }

    @PostMapping("/shoe-list/update/{shoeId}")
    public String updateShoe(@RequestParam("shoeId") Long shoeId,
                             @RequestParam("make") String make,
                             @RequestParam("size") Float size,
                             @RequestParam("description") String description,
                             @RequestParam("price") Double price) {

        Shoe updatedShoe = new Shoe();
        updatedShoe.setMake(make);
        updatedShoe.setSize(size);
        updatedShoe.setDescription(description);
        updatedShoe.setPrice(price);

        shoeService.updateShoe(shoeId, updatedShoe);

        return "redirect:/shoe-list";
    }

    @GetMapping("/create-shoe")
    public String createShoeForm(Model model) {
        model.addAttribute("shoe", null); // No shoe object for creation
        return "shoe-form"; // Use the same form template
    }

    @PostMapping("/create-shoe")
    public String createShoe(@RequestParam("make") String make,
                             @RequestParam("size") Float size,
                             @RequestParam("description") String description,
                             @RequestParam("price") Double price) {
        Shoe shoe = new Shoe();
        shoe.setMake(make);
        shoe.setSize(size);
        shoe.setDescription(description);
        shoe.setPrice(price);
        shoeService.save(shoe);
        return "redirect:/shoe-list";
    }


}
