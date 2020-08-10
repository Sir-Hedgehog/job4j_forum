package ru.job4j.forum.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 10.08.2020
 */

@Entity(name = "Posts")
@Table(name = "posts")
@EqualsAndHashCode(exclude = {"user", "comments"})
@ToString(exclude = {"user", "comments"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Size(min = 5, max = 40, message = "Заголовок поста должен содержать от 5 до 40 символов!")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 20, message = "Описание поста должно содержать не менее 20 символов!")
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created")
    private LocalDateTime created;

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
        return created.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
