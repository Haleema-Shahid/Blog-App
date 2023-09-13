package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "comment", schema = "blog-app", catalog = "")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "is_reported")
    private Byte isReported;
    @Basic
    @Column(name = "is_hidden")
    private Byte isHidden;
    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id", nullable = false)
    private BlogEntity blogByBlogId;
    @ManyToOne
    @JoinColumn(name = "commenter_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByCommenterId;
    @OneToMany(mappedBy = "commentByCommentId", cascade= CascadeType.ALL)
    private Set<CommentLikesEntity> commentLikesById;
    @OneToMany(mappedBy = "commentByCommentId", cascade= CascadeType.ALL)
    private Set<CommentReportEntity> commentReportsById;
    @OneToMany(mappedBy = "commentByCommentId", cascade= CascadeType.ALL)
    private Set<CommentAttachmentEntity> commentAttachmentsById;
    @Basic
    @Column(name = "likes")
    private Integer likes;
    @Basic
    @Column(name = "replies")
    private Integer replies;
    @Basic
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Basic
    @Column(name = "reply_to")
    private Integer replyTo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Byte getIsReported() {
        return isReported;
    }

    public void setIsReported(boolean isReported) {
        if(isReported){
            this.isReported = 1;
        }
        else{
            this.isReported=0;
        }
    }

    public Byte getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        if(isHidden){
            this.isHidden = 1;
        }
        else{
            this.isHidden=0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(comment, that.comment) && Objects.equals(isReported, that.isReported) && Objects.equals(isHidden, that.isHidden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, isReported, isHidden);
    }

    public BlogEntity getBlogByBlogId() {
        return blogByBlogId;
    }

    public void setBlogByBlogId(BlogEntity blogByBlogId) {
        this.blogByBlogId = blogByBlogId;
    }

    public UserEntity getUserByCommenterId() {
        return userByCommenterId;
    }

    public void setUserByCommenterId(UserEntity userByCommenterId) {
        this.userByCommenterId = userByCommenterId;
    }

    public Set<CommentLikesEntity> getCommentLikesById() {
        return commentLikesById;
    }

    public void setCommentLikesById(Set<CommentLikesEntity> commentLikesById) {
        this.commentLikesById = commentLikesById;
    }

    public Set<CommentReportEntity> getCommentReportsById() {
        return commentReportsById;
    }

    public void setCommentReportsById(Set<CommentReportEntity> commentReportsById) {
        this.commentReportsById = commentReportsById;
    }

    public Set<CommentAttachmentEntity> getCommentAttachmentsById() {
        return commentAttachmentsById;
    }

    public void setCommentAttachmentsById(Set<CommentAttachmentEntity> commentAttachmentsById) {
        this.commentAttachmentsById = commentAttachmentsById;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getReplies() {
        return replies;
    }

    public void setReplies(Integer replies) {
        this.replies = replies;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Integer replyTo) {
        this.replyTo = replyTo;
    }
}
