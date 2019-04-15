package com.tensquare.comment.controller;

import com.tensquare.comment.pojo.Comment;
import com.tensquare.comment.service.CommentService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public Result<Comment> findById(@PathVariable String id){
        Comment comment = commentService.findById(id);
        return new Result<>(true, StatusCode.OK,"查询成功",comment);
    }

    @GetMapping
    public Result<List<Comment>> findAll(){
        List<Comment> comments = commentService.findAll();
        return new Result<>(true, StatusCode.OK,"查询成功",comments);
    }

    @PostMapping
    public Result save(@RequestBody Comment comment){
        commentService.save(comment);
        return new Result(true, StatusCode.OK,"保存成功");
    }

    @GetMapping("/article/{articleid}/{page}/{size}")
    public Result<PageResult<Comment>> findByArticleId(@PathVariable String articleid,
                                                      @PathVariable Integer page,
                                                      @PathVariable Integer size){
        Page<Comment> pageData = commentService.findByArticleId(articleid, page, size);
        PageResult<Comment> pageResult = new PageResult<>(pageData.getTotalElements(),pageData.getContent());
        return new Result<>(true,StatusCode.OK,"查询成功",pageResult);
    }

}
