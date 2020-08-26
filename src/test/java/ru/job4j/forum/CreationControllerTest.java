package ru.job4j.forum;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.08.2020
 */

@SpringBootTest(classes = ForumApplication.class)
@AutoConfigureMockMvc
@WithUserDetails("root")
public class CreationControllerTest {

    @MockBean
    private PostService posts;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Метод проверяет открытие страницы создания нового поста
     */

    @Test
    @WithMockUser
    public void checkToOpenCreationPage() throws Exception {
        this.mockMvc.perform(post("/create"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(view().name("create"));
    }

    /**
     * Метод проверяет успешность создания нового поста
     */

    @Test
    @WithMockUser
    public void checkToCreateNameAndDescriptionOfPost() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "Битва под Ржевом");
        params.add("description", "Кто победил в этой ожесточенной битве?");
        this.mockMvc.perform(post("/save")
                .params(params))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("successCreation"));
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(posts).addPost(argument.capture());
        assertThat(argument.getValue().getName(), is("Битва под Ржевом"));
        assertThat(argument.getValue().getDescription(), is("Кто победил в этой ожесточенной битве?"));
    }
}
