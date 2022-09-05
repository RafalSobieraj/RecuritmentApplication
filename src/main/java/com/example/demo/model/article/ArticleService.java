package com.example.demo.model.article;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;



@Service
public class ArticleService {
    
    @Autowired private ArticleRepository articleRepository;

    public List<Article> articlesList(){
        return (List<Article>) articleRepository.findAll();
    }

    public Article save(Article article){
        return articleRepository.save(article);
    }

    public Article get(Integer id) throws NotFoundException{
        Optional<Article> result = articleRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new NotFoundException();
    }

    public void delete(Integer id) throws NotFoundException{
        Long count = articleRepository.countById(id);
        if(count == null || count == 0){
            throw new NotFoundException();
        }
        articleRepository.deleteById(id);
    }

    public List<Article> search(String keyword){
        return articleRepository.search(keyword);
    }

    public List<Article> searchByID(Integer keywordID){
        return articleRepository.searchById(keywordID);
    }
}
