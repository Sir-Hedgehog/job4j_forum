package ru.job4j.forum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.User;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 10.08.2020
 */

public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Метод показывает список всех пользователей
     * @return список всех пользователей
     */

    List<User> findAll();
}
