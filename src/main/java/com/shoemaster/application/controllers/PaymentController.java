package com.shoemaster.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping("/payment-success")
    public String paymentSuccess(Model model) {
        // Add any data you want to display on the success page
        model.addAttribute("message", "Thank you for your purchase! Your payment was successful.");
        return "payment-success"; // This should be the name of your Thymeleaf template
    }
}
