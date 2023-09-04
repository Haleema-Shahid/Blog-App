package com.example.blogapp.services;

import com.example.blogapp.entities.BlogEntity;

public interface BlogService {
    BlogEntity getBlog(int id);

    String addBlog(BlogEntity blogEntity);

    String editBlog(int id, BlogEntity blogEntity);

    String reportBlog(int id);
}
