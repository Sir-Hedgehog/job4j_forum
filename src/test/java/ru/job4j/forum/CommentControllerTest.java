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
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 25.08.2020
 */

@SpringBootTest(classes = ForumApplication.class)
@AutoConfigureMockMvc
@WithUserDetails("root")
public class CommentControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(CommentControllerTest.class);

    @MockBean
    private PostService posts;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Метод проверяет открытие страницы с комментариями
     */

    @Test
    @WithMockUser
    public void checkToOpenCommentPage() throws Exception {
        Post post = new Post();
        post.setId(1);
        post.setName("Битва под Ржевом");
        post.setDescription("Кто победил в этой ожесточенной битве?");
        post.setCreated(LocalDateTime.now());
        when(posts.findById(String.valueOf(post.getId()))).thenReturn(post);
        this.mockMvc.perform(get("/comment/{post_id}", post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("comment"));
    }

    /**
     * Метод проверяет успешность сохранения комментария
     */

    @Test
    @WithMockUser
    public void checkToSaveComment() throws Exception {
        Post post = new Post();
        post.setId(1);
        post.setName("Битва под Ржевом");
        post.setDescription("Кто победил в этой ожесточенной битве?");
        posts.addPost(post);
        when(posts.findById(String.valueOf(post.getId()))).thenReturn(post);
        this.mockMvc.perform(get("/commit")
                .param("id", "1")
                .param("contain", "Немцы сдержали оборону, но, вдальнейшем, им пришлось отступить, так как был риск попасть в окружение"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comment/" + post.getId()));
        ArgumentCaptor<Post> argumentOfPost = ArgumentCaptor.forClass(Post.class);
        ArgumentCaptor<Comment> argumentOfComment = ArgumentCaptor.forClass(Comment.class);
        verify(posts).createComment(argumentOfPost.capture(), argumentOfComment.capture());
        assertThat(argumentOfPost.getValue().getName(), is("Битва под Ржевом"));
        assertThat(argumentOfPost.getValue().getDescription(), is("Кто победил в этой ожесточенной битве?"));
        assertThat(argumentOfComment.getValue().getContain(), is("Немцы сдержали оборону, но, вдальнейшем, им пришлось отступить, так как был риск попасть в окружение"));
    }
}