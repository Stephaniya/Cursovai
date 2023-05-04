package com.example.fitness.Controller;


import com.example.fitness.Entity.Haircut;
import com.example.fitness.Entity.HaircutDescription;
import com.example.fitness.Entity.User;
import com.example.fitness.Repository.HaircutDescriptionRepository;
import com.example.fitness.Repository.HaircutRepository;
import com.example.fitness.UserService.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    private final HaircutDescriptionRepository haircutDescriptionRepository;

    private final HaircutRepository haircutRepository;

    @PostMapping("/update")
    public String update(Model model, Authentication authentication, String email,
                         @RequestParam String phone,
                         @RequestParam String firstname,
                         @RequestParam String lastname
    ) throws IOException {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        email = auth.getName();
        User user = userService.getMemberByEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setNumber(phone);

        userService.update(user);

        model.addAttribute("user", user);
        return "redirect:/user/personal";
    }

    @GetMapping("/personal")
    public String personal(Model model, Authentication authentication, String email) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        email = auth.getName();
        User user = userService.getMemberByEmail(email);
        model.addAttribute("user", user);

        Iterable<Haircut> haircuts = haircutRepository.getAllByUserEmail(email);
        model.addAttribute("haircuts", haircuts);
        model.addAttribute("email", email);
        return "personal";
    }

    @PostMapping("/q")
    public String loginMember(
            @RequestParam String email

    ) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        email = auth.getName();
        System.out.println("Personal " + email);
        return "redirect:/personal";
    }

    @GetMapping("/booking")
    public String course(Model model) {

        return "booking";
    }

    @PostMapping("/booking")
    public String coursePost(Model model,
                             @RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String phone,
                             @RequestParam String date,
                             @RequestParam String type) {

        if (email.endsWith("@gmail.com")||email.endsWith("@mail.ru")||email.endsWith("@yandex.ru")) {
            Haircut haircut1 = new Haircut();
            haircut1.setName(name);
            haircut1.setEmail(email);
            haircut1.setPhone(phone);
            haircut1.setDate(date);
            haircut1.setType(type);
            if(type.equals("men")||type.equals("women")){
                haircut1.setPrice("20р");
            }else {
                haircut1.setPrice("15р");
            }
            haircutRepository.save(haircut1);
            return "redirect:/user/personal";
        } else {
            model.addAttribute("valid", "Некорректный email. Должно содержать @gmail.com, @mail.ru, @yandex.ru");
            return "booking";
        }

    }


}
