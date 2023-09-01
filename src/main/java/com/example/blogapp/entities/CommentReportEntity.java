package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "comment_report", schema = "blog-app", catalog = "")
public class CommentReportEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "complaint")
    private String complaint;
    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
    private CommentEntity commentByCommentId;
    @ManyToOne
    @JoinColumn(name = "reporter_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByReporterId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentReportEntity that = (CommentReportEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(complaint, that.complaint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, complaint);
    }

    public CommentEntity getCommentByCommentId() {
        return commentByCommentId;
    }

    public void setCommentByCommentId(CommentEntity commentByCommentId) {
        this.commentByCommentId = commentByCommentId;
    }

    public UserEntity getUserByReporterId() {
        return userByReporterId;
    }

    public void setUserByReporterId(UserEntity userByReporterId) {
        this.userByReporterId = userByReporterId;
    }
}
