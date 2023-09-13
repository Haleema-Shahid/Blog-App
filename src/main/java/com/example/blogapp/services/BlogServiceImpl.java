package com.example.blogapp.services;

import com.example.blogapp.DTOs.BlogPostDTO;
import com.example.blogapp.DTOs.CommentDTO;
import com.example.blogapp.DTOs.CommentPostDTO;
import com.example.blogapp.DTOs.UserDTO;
import com.example.blogapp.entities.*;
import com.example.blogapp.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    final private BlogRepository blogRepository;

    final private UserRepository userRepository;

    final private BlogAttachmentRepository attachmentRepository;

    final private BlogLikesRepository blogLikesRepository;

    final private BlogReportRepository blogReportRepository;

    final private CommentRepository commentRepository;

    final private CommentAttachmentRepository commentAttachmentRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public BlogEntity getBlog(int id) {
        return blogRepository.findByIdAndIsHidden(id, (byte) 0).orElseThrow(() -> new RuntimeException("Blog not found!"));
    }

    @Override
    public String addBlog(BlogPostDTO blogDTO) {
        try {
            Integer userId = blogDTO.getUserId();
            BlogEntity blogEntity = modelMapper.map(blogDTO, BlogEntity.class);
            System.out.println("In addblog isapproved: " + blogEntity.getIsApproved());
            System.out.println("In addblog isreported: " + blogEntity.getIsReported());
            System.out.println("In addblog title: " + blogEntity.getTitle());

            UserEntity user = userRepository.findById(userId).orElseThrow(() -> new Exception("user not found."));
            blogEntity.setUserByUserId(user);
            blogEntity.setCreationDate(new Timestamp(new Date().getTime()));
            BlogEntity savedBlog = blogRepository.save(blogEntity);

            //after saving blog successfully, save attachments
            System.out.println(blogDTO.getAttachments());
            if (blogDTO.getAttachments() != null) {
                List<String> attachmentUrls = blogDTO.getAttachments();
                for (String attachmentUrl : attachmentUrls) {
                    BlogAttachmentEntity attachmentEntity = new BlogAttachmentEntity();
                    attachmentEntity.setAttachment(attachmentUrl);
                    attachmentEntity.setBlogByBlogId(savedBlog);
                    attachmentRepository.save(attachmentEntity);
                }
            }
            return "succesfully added blog";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String editBlog(int id, BlogEntity blogEntity) {
        try {
            BlogEntity blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("blog not found!"));
            blog.setContent(blogEntity.getContent());
            //what to do with attachments?
            return "updated blog successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String reportBlog(int blogId, int reporterId) throws Exception {
        try {
            UserEntity reporter = userRepository.findById(reporterId).orElseThrow(
                    () -> new Exception("User(reporter) not found")
            );
            BlogEntity blog = blogRepository.findById(blogId).orElseThrow(
                    () -> new Exception("blog(reported) not found")
            );

            BlogReportEntity report = new BlogReportEntity();
            report.setBlogByBlogId(blog);
            report.setUserByReporterId(reporter);

            blogReportRepository.save(report);


            blog.setIsReported(true);
            blogRepository.save(blog);
            return "reported";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String deleteBlog(int blogId) throws Exception {
        BlogEntity blog = blogRepository.findById(blogId).orElseThrow(()-> new Exception("blog not found"));
        blog.setIsHidden(true);
        blogRepository.save(blog);
        return "deleted blog successfully";
    }

    @Override
    public BlogLikesEntity addLike(Integer likerId, Integer blogId) throws Exception {


        UserEntity liker = userRepository.findById(likerId).orElseThrow(() -> new Exception("User(liker) not found."));
        BlogEntity likedBlog = blogRepository.findById(blogId).orElseThrow(() -> new Exception("Blog(liked) not found."));

        Optional<BlogLikesEntity> exists = blogLikesRepository.findByUserByLikerIdAndBlogByBlogId(liker, likedBlog);
        if (exists.isEmpty()) {


            BlogLikesEntity blogLike = new BlogLikesEntity();
            blogLike.setBlogByBlogId(likedBlog);
            blogLike.setUserByLikerId(liker);

            BlogLikesEntity like = blogLikesRepository.save(blogLike);
            likedBlog.setLikes(likedBlog.getLikes() + 1);
            blogRepository.save(likedBlog);

            return like;
        } else {
            return exists.orElse(new BlogLikesEntity());
        }
    }

    @Override
    public void removeLike(Integer likerId, Integer blogId) throws Exception {
        UserEntity liker = userRepository.findById(likerId).orElseThrow(() -> new Exception("User(liker) not found."));
        BlogEntity likedBlog = blogRepository.findById(blogId).orElseThrow(() -> new Exception("Blog(liked) not found."));

        BlogLikesEntity blogLike = blogLikesRepository.findByUserByLikerIdAndBlogByBlogId(liker, likedBlog).orElseThrow(
                () -> new Exception("Like not found.")
        );
        blogLikesRepository.delete(blogLike);
        likedBlog.setLikes(likedBlog.getLikes() - 1);
        blogRepository.save(likedBlog);
    }

    @Override
    public List<BlogEntity> getAllBlogs() {
        List<BlogEntity> blogs = blogRepository.findAllByIsHidden((byte)0);
        return blogs;
    }

    @Override
    public List<CommentDTO> getAllComments(int blogId) throws Exception {
        BlogEntity blog = blogRepository.findById(blogId).orElseThrow(() -> new Exception("blog(for comments) not found."));

        List<CommentEntity> comments = commentRepository.findCommentEntitiesByBlogByBlogId(blog).orElseThrow(() -> new Exception("comments not found."));

        for (CommentEntity comment : comments) {
            System.out.println("comment: " + comment.getComment());
        }
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (CommentEntity comment : comments) {
            if (comment.getReplyTo() == null && comment.getIsHidden() == 0) {
                System.out.println("i loooop");
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(comment.getId());
                commentDTO.setLikes(comment.getLikes());
                commentDTO.setReplies(comment.getReplies());
                commentDTO.setComment(comment.getComment());
                UserDTO commenter = modelMapper.map(comment.getUserByCommenterId(), UserDTO.class);
                commentDTO.setCommenter(commenter);
                Set<CommentAttachmentEntity> attachmentEntitySet = comment.getCommentAttachmentsById();
                List<String> attachments = new ArrayList<>();
                for (CommentAttachmentEntity attachment : attachmentEntitySet) {
                    System.out.println("i inner loooop");
                    System.out.println("comment attachment: " + attachment.getAttachment());
                    attachments.add(attachment.getAttachment());
                }
                commentDTO.setAttachments(attachments);
                commentDTOS.add(commentDTO);
            }
        }
        return commentDTOS;
    }

    @Override
    public String addComment(int blogId, int commenterId, CommentPostDTO commentPostDTO, int replyTo) throws Exception {
        BlogEntity blog = blogRepository.findById(blogId).orElseThrow(() -> new Exception("Blog(commented) not found."));
        UserEntity commenter = userRepository.findById(commenterId).orElseThrow(() -> new Exception("User(commenter) not found."));


        String comment = commentPostDTO.getComment();
        List<String> attachments = commentPostDTO.getAttachments();

//        CommentEntity commentEntity = new CommentEntity();
//        BeanUtils.copyProperties(commentPostDTO,commentEntity);
        CommentEntity commentEntity = modelMapper.map(commentPostDTO, CommentEntity.class);
        System.out.println("ishidden: " + commentEntity.getIsHidden());
        commentEntity.setBlogByBlogId(blog);
        commentEntity.setUserByCommenterId(commenter);

        Set<CommentAttachmentEntity> attachmentEntitySet = new HashSet<>();

        if (commentPostDTO.getAttachments() != null) {
            List<String> attachmentUrls = commentPostDTO.getAttachments();
            for (String attachmentUrl : attachmentUrls) {
                CommentAttachmentEntity attachmentEntity = new CommentAttachmentEntity();
                attachmentEntity.setAttachment(attachmentUrl);
                attachmentEntity.setCommentByCommentId(commentEntity);
                attachmentEntitySet.add(attachmentEntity);
            }
            System.out.println("commentattachment: " + attachmentEntitySet);
            commentEntity.setCommentAttachmentsById(attachmentEntitySet);
        }


        commentEntity.setCreationDate(new Timestamp(new Date().getTime()));
        if (replyTo != -1 && commentRepository.existsById(replyTo)) {
            CommentEntity parent = commentRepository.findById(replyTo).orElseThrow(() -> new Exception("comment not found"));
            if (parent.getReplyTo() == null) {
                commentEntity.setReplyTo(replyTo);
                parent.setReplies(parent.getReplies() + 1);
                commentRepository.save(parent);
            }
        }
        commentRepository.save(commentEntity);

        blog.setComments(blog.getComments() + 1);
        blogRepository.save(blog);
        return "comment added!";
    }

    public String editComment(int commentId, CommentPostDTO updatedComment) throws Exception {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception("Comment not found."));

        // Update the comment content
        comment.setComment(updatedComment.getComment());

        // Update comment attachments
        List<String> updatedAttachments = updatedComment.getAttachments();
        if (updatedAttachments != null) {
            Set<CommentAttachmentEntity> updatedAttachmentEntities = new HashSet<>();
            for (String attachmentUrl : updatedAttachments) {
                CommentAttachmentEntity attachmentEntity = new CommentAttachmentEntity();
                attachmentEntity.setAttachment(attachmentUrl);
                attachmentEntity.setCommentByCommentId(comment);
                updatedAttachmentEntities.add(attachmentEntity);
            }
            comment.setCommentAttachmentsById(updatedAttachmentEntities);
        }

        // Save the updated comment
        commentRepository.save(comment);

        return "Comment updated!";
    }

    @Override
    public List<CommentDTO> getReplies(int commentId) {
        List<CommentEntity> replies = commentRepository.findByReplyTo(commentId);

        System.out.println("replies: " + replies);
        return getCommentDTOS(replies);
    }

    private List<CommentDTO> getCommentDTOS(List<CommentEntity> replies) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (CommentEntity comment : replies) {
            if (comment.getIsHidden() == 0) {
                System.out.println("i loooop");
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(comment.getId());
                commentDTO.setLikes(comment.getLikes());
                commentDTO.setReplies(comment.getReplies());
                commentDTO.setComment(comment.getComment());
                UserDTO commenter = modelMapper.map(comment.getUserByCommenterId(), UserDTO.class);
                commentDTO.setCommenter(commenter);
                Set<CommentAttachmentEntity> attachmentEntitySet = comment.getCommentAttachmentsById();
                List<String> attachments = new ArrayList<>();
                for (CommentAttachmentEntity attachment : attachmentEntitySet) {
                    System.out.println("i inner loooop");
                    System.out.println("comment attachment: " + attachment.getAttachment());
                    attachments.add(attachment.getAttachment());
                }
                commentDTO.setAttachments(attachments);
                commentDTOS.add(commentDTO);
            }
        }
        return commentDTOS;
    }

}
