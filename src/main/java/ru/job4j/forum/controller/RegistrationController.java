package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.PostService;
import javax.validation.Valid;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 11.08.2020
 */

@Controller
public class RegistrationController {
    private final PostService posts;

    public RegistrationController(PostService posts) {
        this.posts = posts;
    }

    /**
     * Метод переводит пользователя на страницу регистрации
     * @param model - модель
     * @return - страница регистрации
     */

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    /**
     * Метод сохраняет данные пользователя на странице регистрации
     * @param user - данные пользователя
     * @param bindingResult - проверка на валидность
     * @return - редирект в зависимости от результата проверки на валидность
     */

    @PostMapping("/registration")
    public String save(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        String result = "/registration";
        if (!bindingResult.hasErrors()) {
            posts.addUser(user);
            result = "redirect:/login";
        }
        return result;
    }
}
