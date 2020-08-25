package ru.job4j.forum;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 25.07.2020
 */

@SpringBootTest(classes = ForumApplication.class)
@AutoConfigureMockMvc
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Метод проверяет работу стартовой страницы приложения
     */

    @Test
    @WithMockUser
    public void checkIndexPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    /**
     * Метод проверяет работу страницы с постами пользователя
     */

    @Test
    @WithMockUser
    public void checkUserPage() throws Exception {
        this.mockMvc.perform(get("/user_list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("userList"));
    }

    /**
     * Метод проверяет аутентификацию существующего пользователя
     */

    @Test
    @WithMockUser
    public void checkRedirectFromLoginPage() throws Exception {
        this.mockMvc.perform(formLogin().user("root").password("responsibility"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
