package com.shoemaster.application.controllers;

import com.shoemaster.application.dtos.User;
import com.shoemaster.application.services.LoginService;
import com.shoemaster.application.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//@RestController
@Controller
//@RequestMapping("/register")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List <User> getAllUsers(){

        return userService.getAllUsers();
    }

//    @GetMapping("/username/{username}")
//    public User getUsername(@PathVariable String username) {
//        return userService.getByUsername(username);
//    }

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "registration-page";
//    }


    @GetMapping("registration-page")
    public String showRegistrationForm2(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.createNewUser(user);

            return new ModelAndView("redirect:/login");
        } catch (Exception e) {
            e.printStackTrace();
            // Log error and handle it, e.g., show an error message on registration page
            redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
            return new ModelAndView("redirect:/register");
        }
    }

    @GetMapping("user-list")
    public String userListView(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @PostMapping("/user-list")
    public String userListViewPost(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/register")
    public String createaccount(Model model) {
        //Here we are creating a new user object
        User user = new User();
        //Once the object is created it's being stored in model
        model.addAttribute("user", user);
        //and then returned to the view
        return "create-account";
    }

//    @GetMapping("/logout")
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        // Perform any pre-logout actions here if needed
//
//        // Manual triggering of Spring Security logout
//        new SecurityContextLogoutHandler().logout(request, response, null);
//
//        // Redirect to your desired page after logout
//        return "redirect:/";
//    }

/*
    @GetMapping("/login?logout")
    public String logout() {
        return "/";
    }
*/

//    @PostMapping("/create-account")
//    public String createaccountPost(Model model) {
//        //Here we are creating a new user object
//        User user = new User();
//        //Once the object is created it's being stored in model
//        model.addAttribute("user", user);
//        //and then returned to the view
//        return "create-account";
//    }

}
