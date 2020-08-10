package ru.job4j.forum.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.forum.model.Comment;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 10.08.2020
 */

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    /**
     * Метод показывает список всех комментариев
     * @return список всех комментариев
     */

    List<Comment> findAll();

    /**
     * Метод удаляет комментарии поста, также запланированного на удаление
     * @param postId - идентификатор запланированного на удаление поста
     */

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comments WHERE post_id = :postId", nativeQuery = true)
    void deleteCommentsOfPost(@Param("postId") int postId);

    /**
     * Метод прикрепляет существующие комментарии к обновленному посту
     * @param postId - идентификатор поста
     */

    @Modifying
    @Transactional
    @Query(value = "UPDATE comments SET post_id = :postId WHERE post_id IS NULL;", nativeQuery = true)
    void saveCommentsForUpdatedPost(@Param("postId") int postId);
}
