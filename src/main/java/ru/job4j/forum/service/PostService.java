package ru.job4j.forum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.AuthorityRepository;
import ru.job4j.forum.repository.CommentRepository;
import ru.job4j.forum.repository.PostRepository;
import ru.job4j.forum.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 10.08.2020
 */

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(PostRepository postRepository, CommentRepository commentRepository, AuthorityRepository authorityRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    /**
     * Метод получает список всех постов
     * @return - список всех постов
     */

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    /**
     * Метод добавляет новый пост
     * @param post - новый пост
     */

    public void addPost(Post post) {
        post.setUser(this.certainCurrentUser());
        post.setCreated(LocalDateTime.now());
        postRepository.save(post);
    }

    /**
     * Метод находит пост по идентификатору
     * @param id - идентификатор поста
     * @return - искомый объект поста
     */

    public Post findById(String id) {
        int parsedId = Integer.parseInt(id);
        return postRepository.findById(parsedId);
    }

    /**
     * Метод обновляет существующий пост
     * @param post - существующий пост
     */

    public void updatePost(Post post) {
        post.setUser(this.certainCurrentUser());
        post.setCreated(LocalDateTime.now());
        postRepository.save(post);
        commentRepository.saveCommentsForUpdatedPost(post.getId());
    }

    /**
     * Метод удаляет пост
     * @param post - пост
     */

    public void deletePost(Post post) {
        commentRepository.deleteCommentsOfPost(post.getId());
        postRepository.deletePost(post.getId());
    }

    /**
     * Метод создает комментарий под существующим постом
     * @param post - пост
     * @param comment - комментарий
     */

    public void createComment(Post post, Comment comment) {
        comment.setId(0);
        comment.setUser(this.certainCurrentUser());
        comment.setCreated(LocalDateTime.now());
        comment.setPost(post);
        commentRepository.save(comment);
    }

    /**
     * Метод определяет текущего пользователя, размещающего комментарий под постом
     * @return - пользователь
     */

    private User certainCurrentUser() {
        User electUser = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getUsername().equals(userDetails.getUsername())) {
                electUser = user;
                break;
            }
        }
        return electUser;
    }
}
