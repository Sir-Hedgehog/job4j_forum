package ru.job4j.forum;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.08.2020
 */

@SpringBootTest(classes = ForumApplication.class)
@AutoConfigureMockMvc
@WithUserDetails
public class UpdateControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    @MockBean
    private PostService posts;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Метод проверяет успешность удаления поста
     */

    @Test
    @WithMockUser
    public void checkToDeletePost() throws Exception {
        Post post = new Post();
        post.setId(1);
        post.setName("Битва под Ржевом");
        post.setDescription("Кто победил в этой ожесточенной битве?");
        post.setCreated(LocalDateTime.now());
        when(posts.findById(String.valueOf(post.getId()))).thenReturn(post);
        this.mockMvc.perform(get("/delete/{post_id}", post.getId()))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user_list"));
    }

    /**
     * Метод проверяет открытие страницы обновления поста
     */

    @Test
    @WithMockUser
    public void checkToOpenUpdatePage() throws Exception {
        Post post = new Post();
        post.setId(1);
        post.setName("Битва под Ржевом");
        post.setDescription("Кто победил в этой ожесточенной битве?");
        post.setCreated(LocalDateTime.now());
        when(posts.findById(String.valueOf(post.getId()))).thenReturn(post);
        this.mockMvc.perform(get("/update/{post_id}", post.getId()))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("update"));
    }

    /**
     * Метод проверяет успешность обновления поста
     */

    @Test
    @WithMockUser
    public void checkToUpdate() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "Портсмутский мир");
        params.add("description", "Итоги Второй мировой войны");
        this.mockMvc.perform(post("/fix")
                .params(params))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("successUpdate"));
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(posts).updatePost(argument.capture());
        assertThat(argument.getValue().getName(), is("Портсмутский мир"));
        assertThat(argument.getValue().getDescription(), is("Итоги Второй мировой войны"));
    }
}
