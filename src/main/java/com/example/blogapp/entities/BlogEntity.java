package com.example.blogapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
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
    @Column(name = "content", columnDefinition="TEXT")
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
    @JsonBackReference
    private UserEntity userByUserId;
    @OneToMany(mappedBy = "blogByBlogId", cascade = CascadeType.ALL)
    private Set<BlogLikesEntity> blogLikesById;
    @OneToMany(mappedBy = "blogByBlogId", cascade = CascadeType.ALL)
    private Set<BlogReportEntity> blogReportsById;
    @OneToMany(mappedBy = "blogByBlogId", cascade = CascadeType.ALL)
    private Set<CommentEntity> commentsById;
    @OneToMany(mappedBy = "blogByBlogId", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private Set<SuggestionEntity> suggestionsById;
    @Basic
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Basic
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "blogByBlogId", cascade = CascadeType.ALL)
    private Set<BlogAttachmentEntity> blogAttachmentsById;

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


    public void setIsReported(boolean isReported) {
        if(isReported){
            this.isReported = 1;
        }
        else{
            this.isReported = 0;
        }
    }

    public Byte getIsApproved() {
        return isApproved;
    }


    public void setIsApproved(boolean isApproved) {
        if(isApproved){
            this.isApproved = 1;
        }
        else{
            this.isApproved = 0;
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
            this.isHidden = 0;
        }
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<BlogAttachmentEntity> getBlogAttachmentsById() {
        return blogAttachmentsById;
    }

    public void setBlogAttachmentsById(Set<BlogAttachmentEntity> blogAttachmentsById) {
        this.blogAttachmentsById = blogAttachmentsById;
    }

}
