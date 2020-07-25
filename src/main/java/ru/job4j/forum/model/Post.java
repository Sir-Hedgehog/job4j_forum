package ru.job4j.forum.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.07.2020
 */

@ToString
@EqualsAndHashCode
public class Post {

    private int id;

    @Size(min = 5, max = 50, message = "Заголовок поста должен содержать от 5 до 40 символов!")
    private String name;

    @Size(min = 20, message = "Описание поста должно содержать не менее 20 символов!")
    private String description;

    private List<Comment> comments;

    private User user;

    private String created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
