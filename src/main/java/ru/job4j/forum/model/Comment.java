package ru.job4j.forum.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.07.2020
 */

@ToString
@EqualsAndHashCode
public class Comment {
    private int id;

    @Size(min = 1)
    private String contain;

    private User user;

    private String created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
