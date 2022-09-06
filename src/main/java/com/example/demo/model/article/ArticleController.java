package com.example.demo.model.article;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@RestController
public class ArticleController {
    
    @Autowired private ArticleService articleService;
    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/")
    public ModelAndView homePage(Model model){
        return viewPage(model, "publishDate", "asc");
    }

    @GetMapping("/sort")
    public ModelAndView viewPage(Model model,
        @Param("field") String field,
        @Param("sortDirection") String sortDirection) {
     
        List<Article> articleList = articleService.articlesList(field, sortDirection);
       
        model.addAttribute("articleList", articleList);
        model.addAttribute("field", field);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSort", sortDirection.equals("asc") ? "desc" : "asc");
     

        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@Param("keyword") String keyword, Model model){
        List<Article> resultList = articleService.search(keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchResult", "Znalezione elementy dla '" + keyword + "'");
        model.addAttribute("resultList", resultList);

        modelAndView.setViewName("result_search.html");
        return modelAndView;
    }

    @GetMapping("/searchID")
    public ModelAndView searchByID(@Param("keywordID") Integer keywordID, Model model){
        List<Article> resultList = articleService.searchByID(keywordID);
        model.addAttribute("keyword", keywordID);
        model.addAttribute("searchResult", "Znalezione elementy dla ID = '" + keywordID + "'");
        model.addAttribute("resultList", resultList);
        
        modelAndView.setViewName("result_search.html");
        return modelAndView;
    }

    @GetMapping("/article/{id}")
    public ModelAndView selectArticle(@PathVariable("id") Integer id, Model model, RedirectAttributes re){
        try{
            Article article = articleService.get(id);
            model.addAttribute("articleContent", article.getContent());
            model.addAttribute("articleAuthor", article.getAuthor());
            model.addAttribute("articleTitle", article.getTitle());

            modelAndView.setViewName("article_select.html");
            return modelAndView;
        } catch(NotFoundException a){
            re.addFlashAttribute("message", a.getMessage());
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

    }
    
    @GetMapping("/article/delete/{id}")
    public ModelAndView deleteArticle(@PathVariable("id") Integer id, RedirectAttributes re, Article article, Model model){

        try {
            articleService.delete(id);
            re.addFlashAttribute("message", "Artykuł został pomyślnie usunięty");
        } catch (NotFoundException e) {
            re.addFlashAttribute("message", "Wystąpił błąd przy usuwaniu.");
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/article/new")
    public ModelAndView addNewArticle(Model model){
        model.addAttribute("article", new Article());
        model.addAttribute("title", "Tworzenie nowego artykułu");
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        model.addAttribute("timestamp", timestamp);

        modelAndView.setViewName("article_form.html");
        return modelAndView;
        }

    @PostMapping("/article/save")
    public ModelAndView articleSave(@ModelAttribute(name = "article") Article article,
    RedirectAttributes re, Model model){

        articleService.save(article);
        re.addFlashAttribute("message", "Pomyślnie dodano artykuł");

        modelAndView.setViewName("redirect:/");
        return modelAndView;

    }

    @GetMapping("/article/edit/{id}")
    public ModelAndView articleEdit(@PathVariable("id") Integer id, Model model, RedirectAttributes re){
        try {
            Article article = articleService.get(id);
            model.addAttribute("article", article);
            model.addAttribute("title", "Edycja artykułu o id " + id);

            modelAndView.setViewName("article_form.html");
            return modelAndView;
        } catch (NotFoundException e) {
            re.addFlashAttribute("message", "Wystąpił błąd");

            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

    }

}
