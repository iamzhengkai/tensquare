package com.tensquare.comment.dao;

import com.tensquare.comment.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentDao extends MongoRepository<Comment,String> {
    Page<Comment> findByArticleid(String articleId, Pageable pageable);
}
