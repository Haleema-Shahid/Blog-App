package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "suggestion", schema = "blog-app", catalog = "")
public class SuggestionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "suggestion")
    private String suggestion;
    @Basic
    @Column(name = "is_rejected")
    private Byte isRejected;
    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id", nullable = false)
    private BlogEntity blogByBlogId;
    @ManyToOne
    @JoinColumn(name = "suggester_id", referencedColumnName = "id", nullable = false)
    private UserEntity userBySuggesterId;
    @OneToMany(mappedBy = "suggestionBySuggestionId")
    private Set<SuggestionReplyEntity> suggestionRepliesById;
    @OneToMany(mappedBy = "suggestionByReplyId")
    private Set<SuggestionReplyEntity> suggestionRepliesById_0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Byte getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Byte isRejected) {
        this.isRejected = isRejected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuggestionEntity that = (SuggestionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(suggestion, that.suggestion) && Objects.equals(isRejected, that.isRejected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, suggestion, isRejected);
    }

    public BlogEntity getBlogByBlogId() {
        return blogByBlogId;
    }

    public void setBlogByBlogId(BlogEntity blogByBlogId) {
        this.blogByBlogId = blogByBlogId;
    }

    public UserEntity getUserBySuggesterId() {
        return userBySuggesterId;
    }

    public void setUserBySuggesterId(UserEntity userBySuggesterId) {
        this.userBySuggesterId = userBySuggesterId;
    }

    public Set<SuggestionReplyEntity> getSuggestionRepliesById() {
        return suggestionRepliesById;
    }

    public void setSuggestionRepliesById(Set<SuggestionReplyEntity> suggestionRepliesById) {
        this.suggestionRepliesById = suggestionRepliesById;
    }

    public Set<SuggestionReplyEntity> getSuggestionRepliesById_0() {
        return suggestionRepliesById_0;
    }

    public void setSuggestionRepliesById_0(Set<SuggestionReplyEntity> suggestionRepliesById_0) {
        this.suggestionRepliesById_0 = suggestionRepliesById_0;
    }
}
