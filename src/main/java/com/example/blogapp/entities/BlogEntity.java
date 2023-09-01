package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "blog", schema = "blog-app", catalog = "")
public class BlogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "likes")
    private Integer likes;
    @Basic
    @Column(name = "comments")
    private Integer comments;
    @Basic
    @Column(name = "is_reported")
    private Byte isReported;
    @Basic
    @Column(name = "is_approved")
    private Byte isApproved;
    @Basic
    @Column(name = "is_hidden")
    private Byte isHidden;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByUserId;
    @OneToMany(mappedBy = "blogByBlogId")
    private Set<BlogLikesEntity> blogLikesById;
    @OneToMany(mappedBy = "blogByBlogId")
    private Set<BlogReportEntity> blogReportsById;
    @OneToMany(mappedBy = "blogByBlogId")
    private Set<CommentEntity> commentsById;
    @OneToMany(mappedBy = "blogByBlogId")
    private Set<SuggestionEntity> suggestionsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Byte getIsReported() {
        return isReported;
    }

    public void setIsReported(Byte isReported) {
        this.isReported = isReported;
    }

    public Byte getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Byte isApproved) {
        this.isApproved = isApproved;
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
        BlogEntity that = (BlogEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(likes, that.likes) && Objects.equals(comments, that.comments) && Objects.equals(isReported, that.isReported) && Objects.equals(isApproved, that.isApproved) && Objects.equals(isHidden, that.isHidden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, likes, comments, isReported, isApproved, isHidden);
    }

    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Set<BlogLikesEntity> getBlogLikesById() {
        return blogLikesById;
    }

    public void setBlogLikesById(Set<BlogLikesEntity> blogLikesById) {
        this.blogLikesById = blogLikesById;
    }

    public Set<BlogReportEntity> getBlogReportsById() {
        return blogReportsById;
    }

    public void setBlogReportsById(Set<BlogReportEntity> blogReportsById) {
        this.blogReportsById = blogReportsById;
    }

    public Set<CommentEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Set<CommentEntity> commentsById) {
        this.commentsById = commentsById;
    }

    public Set<SuggestionEntity> getSuggestionsById() {
        return suggestionsById;
    }

    public void setSuggestionsById(Set<SuggestionEntity> suggestionsById) {
        this.suggestionsById = suggestionsById;
    }
}
