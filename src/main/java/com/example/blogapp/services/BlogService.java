package com.example.blogapp.services;

import com.example.blogapp.entities.BlogEntity;

import java.util.ArrayList;
import java.util.List;

public interface BlogService {
    BlogEntity getBlog(int id);

    String addBlog(BlogEntity blogEntity);

    String editBlog(int id, BlogEntity blogEntity);

    String reportBlog(int id);

    List<BlogEntity> getAllBlogs();
}
