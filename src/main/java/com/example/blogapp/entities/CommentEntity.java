package com.example.blogapp.entities;

import jakarta.persistence.*;

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
    @OneToMany(mappedBy = "commentByCommentId")
    private Set<CommentLikesEntity> commentLikesById;
    @OneToMany(mappedBy = "commentByCommentId")
    private Set<CommentReplyEntity> commentRepliesById;
    @OneToMany(mappedBy = "commentByReplyId")
    private Set<CommentReplyEntity> commentRepliesById_0;
    @OneToMany(mappedBy = "commentByCommentId")
    private Set<CommentReportEntity> commentReportsById;

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

    public void setIsReported(Byte isReported) {
        this.isReported = isReported;
    }

    public Byte getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Byte isHidden) {
        this.isHidden = isHidden;
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

    public Set<CommentReplyEntity> getCommentRepliesById() {
        return commentRepliesById;
    }

    public void setCommentRepliesById(Set<CommentReplyEntity> commentRepliesById) {
        this.commentRepliesById = commentRepliesById;
    }

    public Set<CommentReplyEntity> getCommentRepliesById_0() {
        return commentRepliesById_0;
    }

    public void setCommentRepliesById_0(Set<CommentReplyEntity> commentRepliesById_0) {
        this.commentRepliesById_0 = commentRepliesById_0;
    }

    public Set<CommentReportEntity> getCommentReportsById() {
        return commentReportsById;
    }

    public void setCommentReportsById(Set<CommentReportEntity> commentReportsById) {
        this.commentReportsById = commentReportsById;
    }
}
