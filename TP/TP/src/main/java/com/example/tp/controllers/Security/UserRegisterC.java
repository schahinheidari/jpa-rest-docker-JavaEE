package com.example.tp.controllers.Security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tp.entities.User;
import com.example.tp.services.UserService;
import com.example.tp.controllers.dto.DtoUserRegisterC;

@Controller
@RequestMapping("/registration")
public class UserRegisterC {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public DtoUserRegisterC userRegisterDto() {
        return new DtoUserRegisterC();
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        return "user/registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid DtoUserRegisterC userDto,
                                      BindingResult result){

        User existing = userService.getUser(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account with that email");
        }

        if (result.hasErrors()){
            return "user/registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }

}