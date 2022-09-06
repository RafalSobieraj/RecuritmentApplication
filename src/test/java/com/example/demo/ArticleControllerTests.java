package com.example.demo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.article.Article;
import com.example.demo.model.article.ArticleController;
import com.example.demo.model.article.ArticleRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleControllerTests {
    
    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private Model model;

    @Mock
    RedirectAttributes re;
    

    @BeforeEach
    public void setup() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


    }

    @Test
    public void getHomePageTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void articleControllerArticleViewTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/article/{id}", 51))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("article_select.html"));
    }

    @Test
    public void articleControllerDeleteViewTest() throws Exception{
        Article article = new Article();
		article.setTitle("Title1");
		article.setAuthor("Author");
		article.setContent("Some content");
        article.setId(1);
		article.setMagazineName("Magazine");
		article.setPublishDate("2013-08-20");
		article.setTimestamp("2022-09-06");

		articleRepository.save(article);
       
        System.out.println(article);

        mockMvc.perform(MockMvcRequestBuilders.get("/article/delete/{id}", article.getId()))
        .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
}
