package com.example.blogapp.services;

import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogRepository blogRepository;
    @Override
    public BlogEntity getBlog(int id) {
        return blogRepository.findById(id).orElseThrow(()-> new RuntimeException("Blog not found!"));
    }

    @Override
    public String addBlog(BlogEntity blogEntity) {
        try{
        blogRepository.save(blogEntity);
        return "succesfully added blog";
        }
        catch(Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String editBlog(int id, BlogEntity blogEntity) {
        try{
            BlogEntity blog = blogRepository.findById(id).orElseThrow(()-> new RuntimeException("blog not found!"));
            blog.setContent(blogEntity.getContent());
            //what to do with attachments?
        }
        catch(Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String reportBlog(int id) {
        try {
            BlogEntity blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("blog not found"));
            //blog.setIsReported(true);
            return "reported";
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
}
