package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.SearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class ArticleSearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping
    public Result<List<Article>> findAll(){
        List<Article> articles = searchService.findAll();
        return new Result<>(true,StatusCode.OK,"查询成功",articles);
    }

    @PostMapping
    public Result save(@RequestBody Article article){
        searchService.save(article);
        return new Result(true, StatusCode.OK,"保存成功");
    }

    @GetMapping("/{keywords}/{page}/{size}")
    public Result<PageResult<Article>> findByPage(@PathVariable String keywords,
                                                  @PathVariable Integer page,
                                                  @PathVariable Integer size){
        Page<Article> pageData = searchService.findByPage(keywords,page,size);
        PageResult<Article> pageResult = new PageResult<>(pageData.getTotalElements(),pageData.getContent());
        return new Result<>(true,StatusCode.OK,"搜索成功",pageResult);
    }

}
