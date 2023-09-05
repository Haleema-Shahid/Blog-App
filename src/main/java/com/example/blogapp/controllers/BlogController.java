package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.BlogPostDTO;
import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.services.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/")
    public ResponseEntity<List<BlogEntity>> getAllBlogs(){
        try{
            List<BlogEntity> blogs = blogService.getAllBlogs();
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getBlog(@PathVariable int id){
        try{
            BlogEntity blog = blogService.getBlog(id);
            return new ResponseEntity<>("fetched blog: "+blog.toString(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/blog")
    public ResponseEntity<String> addBlog(@RequestBody BlogPostDTO blog){
        BlogEntity blogEntity = modelMapper.map(blog, BlogEntity.class);
        try{
            String response = blogService.addBlog(blogEntity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<String> editBlog(@PathVariable int id, @RequestBody BlogPostDTO blog){
        BlogEntity blogEntity = modelMapper.map(blog, BlogEntity.class);
        try{
            String response = blogService.editBlog(id,  blogEntity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
