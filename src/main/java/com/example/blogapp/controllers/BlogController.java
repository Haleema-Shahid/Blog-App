package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.*;
import com.example.blogapp.entities.*;
import com.example.blogapp.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs")
@CrossOrigin(origins = {"http://localhost:3000"},methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE},allowCredentials = "true", maxAge = 3600)
public class BlogController {


    private final BlogService blogService;

    private final ModelMapper modelMapper;

    @GetMapping("/tiny")
    public String getMessage() {
        return "hello tiny";
    }

    @GetMapping("/all")
    public ResponseEntity<BlogsGetDTO> getAllBlogs() {
        try {
            List<BlogEntity> blogs = blogService.getAllBlogs();
            List<BlogDTO> blogsDTO = new ArrayList<>();
            for (BlogEntity blog : blogs) {
                UserWithBlogDTO user = modelMapper.map(blog.getUserByUserId(), UserWithBlogDTO.class);
                BlogDTO blogDTO = modelMapper.map(blog, BlogDTO.class);
                blogDTO.setUser(user);
                blogsDTO.add(blogDTO);
            }
            BlogsGetDTO blogsGetDTO = new BlogsGetDTO();
            blogsGetDTO.setBlogs(blogsDTO);
            return new ResponseEntity<>(blogsGetDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new BlogsGetDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlog(@PathVariable int id) {
        try {
            BlogEntity blog = blogService.getBlog(id);
            BlogDTO blogDTO = modelMapper.map(blog, BlogDTO.class);
            Set<BlogAttachmentEntity> attachmentEntitySet = blog.getBlogAttachmentsById();

            List<String> attachments = new ArrayList<>();
            for (BlogAttachmentEntity attachment : attachmentEntitySet) {
                attachments.add(attachment.getAttachment());
            }
            blogDTO.setAttachments(attachments);

            List<CommentDTO> blogComments = blogService.getAllComments(id);
            blogDTO.setBlogComments(blogComments);
            UserEntity user = blog.getUserByUserId();
            UserWithBlogDTO userWithBlogDTO = modelMapper.map(user, UserWithBlogDTO.class);
            System.out.println("blogger: "+userWithBlogDTO.getId());
            blogDTO.setUser(userWithBlogDTO);
            return new ResponseEntity<>(blogDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new BlogDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-blog")
    public ResponseEntity<String> addBlog(@RequestBody BlogPostDTO blog) {

        System.out.println(blog.getIsApproved());
        //BlogEntity blogEntity = modelMapper.map(blog, BlogEntity.class);
        try {
            String response = blogService.addBlog(blog);
            System.out.println("In add blog: " + response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("In add blog: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<String> editBlog(@PathVariable int id, @RequestBody BlogPostDTO blog) {
        BlogEntity blogEntity = modelMapper.map(blog, BlogEntity.class);
        try {
            String response = blogService.editBlog(id, blogEntity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likeBlog(@PathVariable int id, @RequestBody BlogLikePostDTO blogLikePostDTO) {
        try {
            Integer likedBlogId = blogLikePostDTO.getBlogId();
            String response;
            System.out.println("blogId: " + blogLikePostDTO.getBlogId());
            System.out.println("likerId: " + blogLikePostDTO.getLikerId());
            if (likedBlogId == id) {
                Integer likerId = blogLikePostDTO.getLikerId();
                BlogLikesEntity like = blogService.addLike(likerId, likedBlogId);
                response = "success";
            } else {
                response = "blogId doesnt match.";
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<String> unLikeBlog(@PathVariable int id, @RequestBody BlogLikePostDTO blogLikePostDTO) {
        try {
            Integer likedBlogId = blogLikePostDTO.getBlogId();
            String response;
            if (likedBlogId == id) {
                Integer likerId = blogLikePostDTO.getLikerId();
                blogService.removeLike(likerId, likedBlogId);
                response = "success";
            } else {
                response = "blogId doesnt match.";
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/user/{userId}/comment")
    public ResponseEntity<String> addComment(@PathVariable int id, @PathVariable int userId, @RequestBody CommentPostDTO commentDTO) {

        try {
            Integer blogId = id;
            Integer commenterId = userId;
            String response = blogService.addComment(blogId, commenterId, commentDTO, -1);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getBlogComments(@PathVariable int id) {
        try {
            List<CommentDTO> comments = blogService.getAllComments(id);

            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<String> reportBlog(@PathVariable int id, @RequestBody BlogReportDTO reportDTO) {
        try {
            Integer blogId = reportDTO.getBlogId();
            Integer reporterId = reportDTO.getReporterId();
            String response = blogService.reportBlog(blogId, reporterId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteBlog(@PathVariable int id) {
        try {
            String response = blogService.deleteBlog(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{id}/comment/{commentId}/edit")
    public ResponseEntity<String> editComment(@PathVariable int id, @PathVariable int commentId, @RequestBody CommentPostDTO commentPostDTO) {
        try {
            String response = blogService.editComment(commentId, commentPostDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/user/{userId}/comment/{commentId}/reply")
    public ResponseEntity<String> replyToComment(@PathVariable int id, @PathVariable int userId, @PathVariable int commentId, @RequestBody CommentPostDTO commentPostDTO) {
        try {
            String added = blogService.addComment(id, userId, commentPostDTO, commentId);
            return new ResponseEntity<>(added, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/comment/{id}/replies")
    public ResponseEntity<List<CommentDTO>> getReplies(@PathVariable int id){
        int commentId = id;
        try{
            List<CommentDTO> replies = blogService.getReplies(commentId);
            return new ResponseEntity<>(replies, HttpStatus.OK);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

}
