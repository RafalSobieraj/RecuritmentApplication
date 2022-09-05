package com.example.demo.model.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name= "articles")
@Data
@NoArgsConstructor
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "publish_date")
    private String publishDate;

    @Column(nullable = false, name = "magazine_name")
    private String magazineName;

    @Column(nullable = false, name = "author")
    private String author;

    @Column(nullable = false, name = "timestamp")
    private String timestamp;
}