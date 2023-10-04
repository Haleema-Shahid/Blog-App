package com.example.blogapp.services;

import com.example.blogapp.DTOs.BlogDTO;
import com.example.blogapp.DTOs.BlogPostDTO;
import com.example.blogapp.DTOs.CommentDTO;
import com.example.blogapp.DTOs.CommentPostDTO;
import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.entities.BlogLikesEntity;

import java.util.ArrayList;
import java.util.List;

public interface BlogService {
    BlogEntity getBlog(int id) throws Exception;

    BlogEntity getUnapprovedBlogById(int id) throws Exception;

    BlogEntity getReportedBlogById(int id) throws Exception;

    String addBlog(BlogPostDTO blogDTO);

    String editBlog(int id, BlogEntity blogEntity);

    String reportBlog(int blogId, int reporterId) throws Exception;

    String deleteBlog(int blogId) throws Exception;

    BlogLikesEntity addLike(Integer likerId, Integer blogId) throws Exception;

    void removeLike(Integer likerId, Integer blogId) throws Exception;
    List<BlogEntity> getAllBlogs();

    List<CommentDTO> getAllComments(int blogId) throws Exception;
    String addComment(int blogId, int commenterId, CommentPostDTO commentPostDTO, int replyTo) throws Exception;
    String editComment(int commentId, CommentPostDTO updatedComment) throws Exception;

    List<CommentDTO> getReplies(int commentId);

    List<BlogDTO> getBlogs(int userId) throws Exception;

    String approveBlog(int id) throws Exception;

    List<BlogEntity> getUnapprovedBlogs();
    List<BlogEntity> getReportedBlogs();
}
