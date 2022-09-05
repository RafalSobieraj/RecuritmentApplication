package com.example.demo.model.article;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class ArticleController {
    
    @Autowired private ArticleService articleService;

    @GetMapping("")
    public String homePage(Model model){
        List<Article> articleList = articleService.articlesList();
        model.addAttribute("articleList", articleList);

        return "index";
    }

    @GetMapping("/search")
    public String search(@Param("keyword") String keyword, Model model){
        List<Article> resultList = articleService.search(keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchResult", "Znalezione elementy dla '" + keyword + "'");
        model.addAttribute("resultList", resultList);

        return "result_search";
    }

    @GetMapping("/searchID")
    public String searchByID(@Param("keywordID") Integer keywordID, Model model){
        List<Article> resultList = articleService.searchByID(keywordID);
        model.addAttribute("keyword", keywordID);
        model.addAttribute("searchResult", "Znalezione elementy dla ID = '" + keywordID + "'");
        model.addAttribute("resultList", resultList);

        return "result_search";
    }

    @GetMapping("/article/{id}")
    public String selectArticle(@PathVariable("id") Integer id, Model model, RedirectAttributes re){
        try{
            Article article = articleService.get(id);
            model.addAttribute("articleContent", article.getContent());
            model.addAttribute("articleAuthor", article.getAuthor());
            model.addAttribute("articleTitle", article.getTitle());
            return "article_select";
        } catch(NotFoundException a){
            re.addFlashAttribute("message", a.getMessage());
            return "redirect:/";
        }

    }
    
    @GetMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable("id") Integer id, RedirectAttributes re, Article article, Model model){

        try {
            articleService.delete(id);
            re.addFlashAttribute("message", "Artykuł został pomyślnie usunięty");
        } catch (NotFoundException e) {
            re.addFlashAttribute("message", "Wystąpił błąd przy usuwaniu.");
        }
        return "redirect:/";
    }

    @GetMapping("/article/new")
    public String addNewArticle(Model model){
        model.addAttribute("article", new Article());
        model.addAttribute("title", "Tworzenie nowego artykułu");
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        model.addAttribute("timestamp", timestamp);
        return "article_form";
        }

    @PostMapping("/article/save")
    public String articleSave(@ModelAttribute(name = "article") Article article,
    RedirectAttributes re, Model model){

        articleService.save(article);
        re.addFlashAttribute("message", "Pomyślnie dodano artykuł");

        return "redirect:/";

    }

    @GetMapping("/article/edit/{id}")
    public String articleEdit(@PathVariable("id") Integer id, Model model, RedirectAttributes re){
        try {
            Article article = articleService.get(id);
            model.addAttribute("article", article);
            model.addAttribute("title", "Edycja artykułu o id " + id);
            return "article_form";
        } catch (NotFoundException e) {
            re.addFlashAttribute("message", "Wystąpił błąd");
            return "redirect:/";
        }

    }

}
