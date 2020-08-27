package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;
import javax.validation.Valid;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 25.08.2020
 */

@Controller
public class UpdateController {
    private final PostService posts;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateController.class);

    public UpdateController(PostService posts) {
        this.posts = posts;
    }

    /**
     * Метод открывает форму удаления одного из размещенных постов пользователя
     * @param id - идентификатор поста
     * @return - страница с обновленным списком постов пользователя
     */

    @RequestMapping(value = "/delete/{post_id}", method = RequestMethod.GET)
    public String openDeleteForm(@PathVariable(name = "post_id") String id) {
        posts.deletePost(posts.findById(id));
        return "redirect:/user_list";
    }

    /**
     * Метод открывает страницу редактирования одного из размещенных постов пользователя
     * @param id - идентификатор поста
     * @param model - модель
     * @return - страница редактирования одного из размещенных постов пользователя
     */

    @RequestMapping(value = "/update/{post_id}", method = RequestMethod.GET)
    public String openUpdateForm(@PathVariable(name = "post_id") String id, Model model) {
        Post post = posts.findById(id);
        model.addAttribute("post", post);
        return "update";
    }

    /**
     * Метод открывает форму с проверкой поста на валидность и сохранением его в случае успешной валидации
     * @param post - выбранный пост для обновления
     * @param bindingResult - проверка ошибок
     * @param model - модель
     * @return - открытие страницы в зависимости от успешности валидации
     */

    @PostMapping("/fix")
    public String openFixForm(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult, Model model) {
        String result = "update";
        if (!bindingResult.hasErrors()) {
            posts.updatePost(post);
            model.addAttribute("postName", post.getName());
            result = "successUpdate";
        }
        return result;
    }
}
