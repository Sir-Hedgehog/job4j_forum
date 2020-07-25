package ru.job4j.forum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.07.2020
 */

@Service
public class PostService {
    private static int generatedIdForPost = 0;
    private static int generatedIdForComment = 0;
    private final List<Post> posts = new ArrayList<>();
    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    /**
     * Метод получает список всех постов
     * @return - список всех постов
     */

    public List<Post> getAll() {
        return posts;
    }

    /**
     * Метод добавляет новый пост
     * @param post - новый пост
     */

    public void addPost(Post post) {
        post.setId(++generatedIdForPost);
        post.setUser(this.certainCurrentUser());
        this.generateTime(post);
        posts.add(post);
    }

    /**
     * Метод находит пост по идентификатору
     * @param id - идентификатор поста
     * @return - искомый объект поста
     */

    public Post findById(String id) {
        Post result = null;
        int parsedId = Integer.parseInt(id);
        for (Post post : posts) {
            if (post.getId() == parsedId) {
                result = post;
                break;
            }
        }
        return result;
    }

    /**
     * Метод обновляет существующий пост
     * @param post - существующий пост
     */

    public void updatePost(Post post) {
        for (Post element : posts) {
            if (post.getId() == element.getId()) {
                element.setName(post.getName());
                element.setDescription(post.getDescription());
                this.generateTime(element);
                break;
            }
        }
    }

    /**
     * Метод удаляет пост
     * @param post - пост
     */

    public void deletePost(Post post) {
        posts.remove(post);
    }

    /**
     * Метод создает комментарий под существующим постом
     * @param post - пост
     * @param comment - комментарий
     */

    public void createComment(Post post, Comment comment) {
        List<Comment> comments;
        if (post.getComments() == null) {
            comments = new ArrayList<>();
        } else {
            comments = post.getComments();
        }
        comment.setId(++generatedIdForComment);
        this.generateTime(comment);
        comment.setUser(this.certainCurrentUser());
        comments.add(comment);
        post.setComments(comments);
    }

    /**
     * Метод генерирует текущую дату создания поста
     * @param post - пост
     */

    private void generateTime(Post post) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
        Date date = new Date();
        post.setCreated(dateFormat.format(date));
    }

    /**
     * Метод генерирует текущую дату создания комментария
     * @param comment - комментарий
     */

    private void generateTime(Comment comment) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
        Date date = new Date();
        comment.setCreated(dateFormat.format(date));
    }

    /**
     * Метод определяет текущего пользователя, размещающего комментарий под постом
     * @return - пользователь
     */

    private User certainCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setEnabled(userDetails.isEnabled());
        return user;
    }
}
