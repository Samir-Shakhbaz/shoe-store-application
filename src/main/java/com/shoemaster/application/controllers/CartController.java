package com.shoemaster.application.controllers;

import com.shoemaster.application.dtos.Cart;
import com.shoemaster.application.dtos.CartItem;
import com.shoemaster.application.dtos.Shoe;
import com.shoemaster.application.dtos.User;
import com.shoemaster.application.models.UserCartItem;
import com.shoemaster.application.services.CartService;
import com.shoemaster.application.services.ShoeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Iterator;
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

//    private List<Shoe> cart = new ArrayList<>();

//    @GetMapping("/cart")
//    public String viewCart(HttpSession session, Model model) {
//        List<Shoe> cart = (List<Shoe>) session.getAttribute("cart");
//
//        // Calculate total price
//        double totalPrice = 0;
//        if (cart != null) {
//            for (Shoe shoe : cart) {
//                totalPrice += shoe.getPrice() * shoe.getQuantity();
//            }
//        }
//        String formattedTotalPrice = String.format("%.2f", totalPrice);
//        model.addAttribute("cartItems", cart);
//        model.addAttribute("totalPrice", formattedTotalPrice);
//        return "cart";
//    }
//
//    @GetMapping("/cart")
//    public String viewCart(HttpSession session, Model model) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        List<Shoe> shoes = new ArrayList<>();
//        double totalPrice = 0;
//
//        if (cart != null) {
//            for (CartItem cartItem : cart.getShoes()) {
//                Shoe shoe = shoeService.findById(cartItem.getShoeId());
//                shoes.add(shoe);
//                totalPrice += shoe.getPrice() * cartItem.getAmount();
//            }
//        }
//
//        String formattedTotalPrice = String.format("%.2f", totalPrice);
//        model.addAttribute("cartItems", cart != null ? cart.getShoes() : new ArrayList<>());
//        model.addAttribute("shoes", shoes);
//        model.addAttribute("totalPrice", formattedTotalPrice);
//        return "cart";
//    }


    @GetMapping("/cart")
    public String viewCart(HttpSession session, Authentication authentication, Model model) {
//        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User)authentication.getPrincipal();
        Cart cart = cartService.getByUserId(user.getUserId());
        List<UserCartItem> userCartItems = new ArrayList<>();
        double totalPrice = 0;

        if (cart != null && user != null) {
            for (CartItem cartItem : cart.getShoes()) {
                Shoe shoe = shoeService.findById(cartItem.getShoeId());
                int amount = cartItem.getAmount();
                UserCartItem userCartItem = new UserCartItem(user, shoe, amount, cartItem.getId());
                userCartItems.add(userCartItem);
                totalPrice += shoe.getPrice() * amount;
            }

        }

        String formattedTotalPrice = String.format("%.2f", totalPrice);
        model.addAttribute("userCartItems", userCartItems);
        model.addAttribute("totalPrice", formattedTotalPrice);
        return "cart";
    }

    @PostMapping("/cart/delete")
    public String deleteCartItem(@RequestParam("cartItemId") Long cartItemId) {
        cartService.deleteById(cartItemId);
        return "redirect:/cart";
    }

//    @PostMapping("/cart/add")
//    public String addToShoeList(@RequestParam("shoeId") Long shoeId, HttpSession session, Authentication authentication) {
//        User user = (User)authentication.getPrincipal();
//
//        Shoe shoe = shoeService.findById(shoeId);
//        Cart cart = cartService.addShoeToCart(user.getUserId(), shoe);
//
//        return "redirect:/cart";
//    }

//    @PostMapping("/cart/update")
//    public String removeFromCart(Authentication authentication,
//                                 @RequestParam("cartItemId") Long cartItemId,
//                                 @RequestParam Integer amount) {
//
//        System.out.println("cartItemId: " + cartItemId + ", amount: " + amount);
//
//        User user = (User)authentication.getPrincipal();
//        Cart cart = cartService.removeShoeFromCart(user.getUserId(), cartItemId, amount);
//
//        return "redirect:/cart";
//    }

    @PostMapping("/cart/update")
    public String updateCartItem(
            @RequestParam ("cartItemId") Long cartItemId,
            @RequestParam boolean increase,
            Authentication authentication) {

        User user = (User)authentication.getPrincipal();
        // Retrieve the cart for the user
        Cart cart = cartService.getByUserId(user.getUserId());

        // Update the cart item quantity
        if (cart != null) {
            cartService.updateCartItemQuantity(user.getUserId(), cartItemId, increase);
        }

        // Redirect to the cart view or update the model accordingly
        return "redirect:/cart";
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


}
