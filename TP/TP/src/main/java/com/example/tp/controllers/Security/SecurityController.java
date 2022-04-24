package com.example.tp.controllers.Security;

import java.security.Principal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tp.dao.MonumentRepository;
import com.example.tp.dao.UserRepository;
import com.example.tp.services.AppService;
import com.example.tp.services.UserService;
import com.example.tp.entities.User;


@Controller
public class SecurityController {

    @Autowired
    private AppService appService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value= {"/","/home","/index"})
    public String home(Model model, Principal principal) {

        String email = principal.getName();
        User user = userService.getUser(email);

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();


        model.addAttribute("user", user);
        model.addAttribute("mode", "details");
        model.addAttribute("principal", principal);
        model.addAttribute("authorities", authorities);


        return "navbar";
    }

    @GetMapping("/403")
    public String login() {
        return "error/403";
    }


    @GetMapping("profile")
    public String profile(Model model, Principal principal) {

        String email = principal.getName();
        User user = userService.getUser(email);

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();


        model.addAttribute("user", user);
        model.addAttribute("mode", "details");
        model.addAttribute("principal", principal);
        model.addAttribute("authorities", authorities);
        return "user/profile";
    }

    @GetMapping("toast")
    public String test(Model model) {
        model.addAttribute("name", "Shahin HEIDARI");

        return "toast";
    }


    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("users")
    public String getAllUsers(Model model) {
        List<User> users = appService.getAllUsers();
        model.addAttribute("users", users);
        return "user/all";
    }


    @GetMapping("/login")
    public String login(Model model) {
//		model.addAllAttributes("user",user);
        return "user/login";
    }




// ----------------------------



    //  User New Form
//  User update form
//  User Details Form
    @GetMapping(value = {"users/{mode}", "users/{mode}/{id}"})
    public String signupForm(Model model, @ModelAttribute("user") User user, @PathVariable(value="id") Optional<String> id, @PathVariable(value="mode") String mode) {

        try {
            if(id.isPresent()) {
                model.addAttribute("id", id.get());
                User existedSampleEntity = appService.getUser(id.get());
                model.addAttribute("user",existedSampleEntity);
            }
            else {
                model.addAttribute("id","none");
                model.addAttribute("state","add");
                model.addAttribute("user",new User());

            }

        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        model.addAttribute("mode", mode);

        return "user/update";

    }


    //	User New
//	User Update
    @PostMapping("users")
    public String saveUser(Model model, @Valid @NotNull @ModelAttribute("user") User user, BindingResult result, RedirectAttributes ra){

        userService.save(user);
        if(result.hasErrors()){
            ra.addAttribute("message", result.getAllErrors());
            return "user/update";
        }
        ra.addFlashAttribute("user", user);
        ra.addAttribute("message", user.getEmail() + " updated successfully");

        return "redirect:/users";
    }



    //		User delete
    @Secured(value = { "ROLE_ADMIN"})
    @GetMapping("users/delete/{id}")
    public String deleteUser(Model model, @PathVariable String id) {
        try {
            appService.deleteUser(id);
            model.addAttribute("message",id +" is successfully deleted");
        } catch (Exception e) {
            model.addAttribute("message",e);
        }
        return "redirect:/users";
    }

















}
