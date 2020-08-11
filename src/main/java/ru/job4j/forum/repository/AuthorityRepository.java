package ru.job4j.forum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.Authority;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 11.08.2020
 */

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

    /**
     * Метод устанавливает роль пользователю по ее имени из базы данных
     * @param authority - имя роли из базы данных
     * @return - объект роли
     */

    Authority findByAuthority(String authority);
}
