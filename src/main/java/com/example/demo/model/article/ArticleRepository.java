package com.example.demo.model.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Integer>{
    public Long countById(Integer id);


    @Query(value = "SELECT * FROM articles WHERE "
    + "MATCH(content, title) AGAINST (?1)", nativeQuery = true)
    public List<Article> search(String keyword);

    @Query(value = "SELECT * FROM articles WHERE "
    + "id= (?1)", nativeQuery = true)
    public List<Article> searchById(Integer keyword);
}
