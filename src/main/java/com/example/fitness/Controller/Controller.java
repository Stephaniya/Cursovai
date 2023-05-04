package com.example.fitness.Controller;


import com.example.fitness.Entity.User;
import com.example.fitness.Repository.UserRepository;
import com.example.fitness.UserService.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;




    @GetMapping("/")
    public String start(Model model){
        return "index";
    }


    @GetMapping("/signUp")
    public String signUp(){
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUpPost(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password, Model model){

        if(userRepository.existsById(email)){
            model.addAttribute("error", "Пользователь с таким email существует");

            return "signUp";
        }else {
            if (email.endsWith("@gmail.com")||email.endsWith("@mail.ru")||email.endsWith("@yandex.ru")) {
                User user = new User(firstname, lastname, email, password);
                userService.createUser(user);
                return "redirect:/login";
            } else {
                model.addAttribute("valid", "Некорректный email. Должно содержать @gmail.com, @mail.ru, @yandex.ru");
                return "signUp";
            }


        }


    }

    @GetMapping("/booking")
    public String booking(){
        return "booking";
    }

    @GetMapping("/personal")
    public String personal(Model model, Authentication authentication, String email){
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        email = auth.getName();
        User user = userService.getMemberByEmail(email);
        System.out.println(user.getRoles());
        model.addAttribute("user", user);
        return "personal";
    }

    @PostMapping("/q")
    public String loginMember(
            @RequestParam String email

    ){
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        email = auth.getName();
        System.out.println(auth.getAuthorities());

        System.out.println("Personal " + email);
        User user = userRepository.getUserByEmail(email);
        if(user.getStatus().equals("active")){
            return "redirect:/personal";
        }else {
            return "redirect:/block";
        }

    }


    @GetMapping("/block")
    public String block(){
        return "block";
    }





}

