package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.demo.model.article.Article;
import com.example.demo.model.article.ArticleRepository;
import com.example.demo.model.article.ArticleService;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleServiceTests {

	@Mock
	private ArticleRepository articleRepository;

	@Mock
	private ArticleService articleService;

	@BeforeEach
 	public void init() {
    	Mockito.doNothing().when(articleRepository).delete(any());
 }

	@Test
	void articleServiceGetAllArticlesTest() {
		Iterable<Article> articles = articleRepository.findAll();

		Assertions.assertThat(articles).hasSizeGreaterThan(0);
	}

	@Test
	void articleServiceSaveTest(){
		Article article = new Article();
		article.setTitle("Title1");
		article.setAuthor("Author");
		article.setContent("Some content");
		article.setId(100);
		article.setMagazineName("Magazine");
		article.setPublishDate("2013-08-20");
		article.setTimestamp("2022-09-06");

		Article savedArticle = articleRepository.save(article);

		Assertions.assertThat(savedArticle).isNotNull();

	}

	@Test
	void articleServiceGetArticleTest(){
		
		Optional<Article> article = articleRepository.findById(51);
	
		Assertions.assertThat(article).isNotNull();
	}

	@Test
	void articleServiceSearchTest(){

		String keyword = "Test";

		List<Article> found = articleRepository.search(keyword);

		assertEquals(found.size(), 1);

	}

	@Test
	void articleServiceSearchByIDTest(){

		Integer keyword = 51;

		List<Article> found = articleRepository.searchById(keyword);

		assertEquals(found.size(), 1);
	}

	@Test
	void articleServiceDeleteTest() throws NotFoundException{

		Article article = articleService.get(51);
	
		articleRepository.delete(article);

		Assertions.assertThat(article).isNull();
	}
}
