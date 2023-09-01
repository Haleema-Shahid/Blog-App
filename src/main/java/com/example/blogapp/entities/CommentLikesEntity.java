package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "comment_likes", schema = "blog-app", catalog = "")
public class CommentLikesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "liker_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByLikerId;
    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
    private CommentEntity commentByCommentId;

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
        CommentLikesEntity that = (CommentLikesEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public UserEntity getUserByLikerId() {
        return userByLikerId;
    }

    public void setUserByLikerId(UserEntity userByLikerId) {
        this.userByLikerId = userByLikerId;
    }

    public CommentEntity getCommentByCommentId() {
        return commentByCommentId;
    }

    public void setCommentByCommentId(CommentEntity commentByCommentId) {
        this.commentByCommentId = commentByCommentId;
    }
}
