package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "comment_reply", schema = "blog-app", catalog = "")
public class CommentReplyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
    private CommentEntity commentByCommentId;
    @ManyToOne
    @JoinColumn(name = "reply_id", referencedColumnName = "id", nullable = false)
    private CommentEntity commentByReplyId;
    @ManyToOne
    @JoinColumn(name = "replier_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByReplierId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentReplyEntity that = (CommentReplyEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public CommentEntity getCommentByCommentId() {
        return commentByCommentId;
    }

    public void setCommentByCommentId(CommentEntity commentByCommentId) {
        this.commentByCommentId = commentByCommentId;
    }

    public CommentEntity getCommentByReplyId() {
        return commentByReplyId;
    }

    public void setCommentByReplyId(CommentEntity commentByReplyId) {
        this.commentByReplyId = commentByReplyId;
    }

    public UserEntity getUserByReplierId() {
        return userByReplierId;
    }

    public void setUserByReplierId(UserEntity userByReplierId) {
        this.userByReplierId = userByReplierId;
    }
}
