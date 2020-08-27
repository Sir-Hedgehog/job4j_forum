package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;
import javax.validation.Valid;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 25.08.2020
 */

@Controller
public class CreationController {
    private final PostService posts;
    private static final Logger LOG = LoggerFactory.getLogger(CreationController.class);

    public CreationController(PostService posts) {
        this.posts = posts;
    }

    /**
     * Метод открывает страницу добавления нового поста
     * @param model - модель
     * @return - страница добавления нового поста
     */

    @PostMapping("/create")
    public String openCreationForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "create";
    }

    /**
     * Метод открывает форму с проверкой нового поста на валидность и сохранением его в случае успешной валидации
     * @param post - новый пост
     * @param bindingResult - проверка ошибок
     * @param model - модель
     * @return - открытие страницы в зависимости от успешности валидации
     */

    @PostMapping("/save")
    public String openValidationOrSaveForm(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult, Model model) {
        String result = "create";
        if (!bindingResult.hasErrors()) {
            posts.addPost(post);
            model.addAttribute("postName", post.getName());
            result = "successCreation";
        }
        return result;
    }
}
