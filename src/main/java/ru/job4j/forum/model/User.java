package ru.job4j.forum.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.07.2020
 */

public class User {
    private int id;

    @NotEmpty(message = "Введите символы!")
    private String username;

    @Size(min = 8, message = "Не менее 8 символов!")
    private String password;

    private Set<Post> posts;

    private boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Post> getPost() {
        return posts;
    }

    public void setPost(Set<Post> posts) {
        this.posts = posts;
    }
}
