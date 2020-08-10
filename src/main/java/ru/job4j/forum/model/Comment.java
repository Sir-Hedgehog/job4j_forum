package ru.job4j.forum.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 10.08.2020
 */

@Entity(name = "Comments")
@Table(name = "comments")
@EqualsAndHashCode(exclude = {"user", "post"})
@ToString(exclude = {"user, post"})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Size(min = 1)
    @Column(name = "contain", nullable = false)
    private String contain;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getCreated() {
        return created.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
