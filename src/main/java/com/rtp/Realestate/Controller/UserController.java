package com.rtp.Realestate.Controller;

import com.rtp.Realestate.Model.RealtorUser;
import com.rtp.Realestate.Service.RealtorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class UserController {

    @Autowired
    private RealtorUserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("realtorUser", new RealtorUser());
        return "registration";
    }

    @PostMapping("/submit_registration")
    public String registerUser(RealtorUser realtorUser) {
        userService.saveUser(realtorUser);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/submit_login")
    public String loginUser(String identifier, String password, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("Attempting login for identifier: " + identifier);

        RealtorUser realtorUser = userService.findByEmail(identifier);
        if (realtorUser != null) {
            System.out.println("User found: " + realtorUser.getEmail());
            boolean passwordMatch = new BCryptPasswordEncoder().matches(password, realtorUser.getPassword());
            System.out.println("Password match: " + passwordMatch);


            if (passwordMatch) {
                System.out.println("Login successful, redirecting to /home");
                return "redirect:/userlist";
            }
        }

        System.out.println("Login failed, invalid credentials");
        model.addAttribute("error", "Invalid ID/Email or Password");
        return "login";
    }


    @GetMapping("/userlist")
    public String showUserList(Model model) {
        List<RealtorUser> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userlist";
    }

   /* @GetMapping("/home")
    public String showHomePage(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("username", user.getName());
        // Assuming you have a method to get featured listings
        model.addAttribute("listings", getFeaturedListings());
        return "home";
    }

    public List<Listing> getFeaturedListings() {
        // Mock data, replace with actual database call
        return List.of(
                new Listing("Beautiful Family Home", "4 beds, 3 baths", 500000),
                new Listing("Modern Apartment", "2 beds, 2 baths", 300000),
                new Listing("Cozy Cottage", "3 beds, 2 baths", 200000)
        );
    }*/
}



