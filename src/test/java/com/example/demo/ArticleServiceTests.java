package com.example.demo;


import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.article.Article;
import com.example.demo.model.article.ArticleRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleServiceTests {

	@Autowired private ArticleRepository articleRepository;

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
}
