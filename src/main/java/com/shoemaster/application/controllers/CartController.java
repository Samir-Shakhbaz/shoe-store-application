package com.shoemaster.application.controllers;

import com.shoemaster.application.dtos.Cart;
import com.shoemaster.application.dtos.Shoe;
import com.shoemaster.application.dtos.User;
import com.shoemaster.application.services.CartService;
import com.shoemaster.application.services.ShoeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ShoeService shoeService;

    //this Restful API
//    @GetMapping("/carts/{username}")
//    public Cart getCart(@PathVariable String username) {
//        return cartService.getByUsername(username);
//    }

    private List<Shoe> cart = new ArrayList<>();

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<Shoe> cart = (List<Shoe>) session.getAttribute("cart");
        model.addAttribute("cartItems", cart);
        return "cart"; // Name of the Thymeleaf template for the cart
    }


    @PostMapping("/cart")
    public String updateCart(Model model, @RequestParam("action") String action, @RequestParam("index") int index) {
        if ("remove".equals(action) && index >= 0 && index < cart.size()) {
            cart.remove(index);
        }

        model.addAttribute("cartItems", cart);
        return "cart";
    }

//    @PostMapping("/cart/add")
//    public String addToCart(@RequestParam("shoeId") Long shoeId, RedirectAttributes redirectAttributes) {
//        Shoe shoe = shoeService.findById(shoeId); // Assuming you have a service to find a shoe by ID
//        if (shoe != null) {
//            cart.add(shoe);
//            redirectAttributes.addFlashAttribute("successMessage", "Item added to cart!");
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "Item not found.");
//        }
//        return "redirect:/shoe-list"; // Redirect back to the shoe list page
//    }
@PostMapping("/cart/add")
public String addToCart(@RequestParam("shoeId") Long shoeId,
                        @RequestParam("make") String make,
                        @RequestParam("description") String description,
                        @RequestParam("size") Float size,
                        HttpSession session) {

        // Create a new Shoe object or fetch it from the service
    Shoe shoe = new Shoe();
    shoe.setMake(make);
    shoe.setDescription(description);
    shoe.setSize(size);
    // Add the shoe to the cart (which might be stored in the session)
    List<Shoe> cart = (List<Shoe>) session.getAttribute("cart");
    if (cart == null) {
        cart = new ArrayList<>();
        session.setAttribute("cart", cart);
    }
    cart.add(shoe);
    // Redirect to the cart page or shoe list page
    return "redirect:/shoe-list";
}


}
