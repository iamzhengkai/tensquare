package com.tensquare.search.service;

import com.tensquare.search.dao.SearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private IdWorker idWorker;


    public void save(Article article){
        String id = "" + idWorker.nextId();
        article.setId(id);
        searchDao.save(article);
    }


    public Page<Article> findByPage(String keywords, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return searchDao.findByTitleOrContentLike(keywords,keywords,pageRequest);
    }

    public List<Article> findAll() {
        List<Article> result = new ArrayList<>();
        searchDao.findAll().forEach(result::add);
        return result;
    }
}
