package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;
import javax.validation.Valid;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.07.2020
 */

@Controller
public class CommentController {
    private final PostService posts;
    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    public CommentController(PostService posts) {
        this.posts = posts;
    }

    /**
     * Метод открывает страницу с возможностью комментировать пост
     * @param id - идентификатор поста
     * @param model - модель
     * @return - фронт страницы
     */

    @RequestMapping("/comment/{post_id}")
    public String openCommentForm(@PathVariable(name = "post_id") String id, Model model) {
        Post post = posts.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("newComment", new Comment());
        return "comment";
    }

    /**
     * Метод открывает форму сохранения нового комментария и перезагружает текущую страницу
     * @param id - идентификатор поста
     * @param newComment - новый комментарий
     * @return - перезагрузка текущей страницы
     */

    @GetMapping("/commit")
    public String openFormWithNewComment(@ModelAttribute("id") String id, @Valid @ModelAttribute("contain") Comment newComment, BindingResult bindingResult) {
        Post post = posts.findById(id);
        if (!bindingResult.hasErrors()) {
            posts.createComment(post, newComment);
        }
        return "redirect:/comment/" + post.getId();
    }
}
