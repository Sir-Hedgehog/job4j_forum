package ru.job4j.forum.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.forum.model.Post;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 10.08.2020
 */

public interface PostRepository extends CrudRepository<Post, Integer> {

    /**
     * Метод показывает список всех постов
     * @return список всех постов
     */

    List<Post> findAll();

    /**
     * Метод находит пост по его идентификатору
     * @param id - идентификатор
     * @return - пост
     */

    Post findById(int id);

    /**
     * Метод удаляет пост из базы данных
     * @param postId - идентификатор поста
     */

    @Modifying
    @Transactional
    @Query(value = "DELETE from posts WHERE id = :postId", nativeQuery = true)
    void deletePost(@Param("postId") int postId);
}
