package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.07.2020
 */

@Controller
public class IndexController {
    private final PostService posts;
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    public IndexController(PostService posts) {
        this.posts = posts;
    }

    /**
     * Метод открывает страницу со всеми размещенными постами форума
     * @param model - модель
     * @return - страница со всеми размещенными постами форума
     */

    @GetMapping("/")
    public String openBasicListForm(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("posts", posts.getAll());
        return "index";
    }

    /**
     * Метод открывает страницу со списком всех постов пользователя
     * @param model - модель
     * @return - страница со списком всех постов пользователя
     */

    @GetMapping("/user_list")
    public String openUserListForm(Model model) {
        List<Post> electPosts = new ArrayList<>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Post post : posts.getAll()) {
            if (post.getUser().getUsername().equals(userDetails.getUsername()) || userDetails.getUsername().equals("admin")) {
                electPosts.add(post);
            }
        }
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("electPosts", electPosts);
        return "userList";
    }
}
