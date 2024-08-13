package com.shoemaster.application.controllers;

import com.shoemaster.application.dtos.Shoe;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.shoemaster.application.utils.SessionUtil.getSessionAttribute;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        List<Shoe> cart = (List<Shoe>) session.getAttribute("cart");

        // Calculate total price
        double totalPrice = 0;
        if (cart != null) {
            for (Shoe shoe : cart) {
                totalPrice += shoe.getPrice() * shoe.getQuantity();
            }
        }

        model.addAttribute("totalAmount", totalPrice);
        return "checkout"; // Name of the Thymeleaf template for the checkout
    }



}
