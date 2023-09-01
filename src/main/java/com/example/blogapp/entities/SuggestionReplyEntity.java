package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "suggestion_reply", schema = "blog-app", catalog = "")
public class SuggestionReplyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "suggestion_id", referencedColumnName = "id", nullable = false)
    private SuggestionEntity suggestionBySuggestionId;
    @ManyToOne
    @JoinColumn(name = "replly_id", referencedColumnName = "id", nullable = false)
    private SuggestionEntity suggestionByReplyId;
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
        SuggestionReplyEntity that = (SuggestionReplyEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public SuggestionEntity getSuggestionBySuggestionId() {
        return suggestionBySuggestionId;
    }

    public void setSuggestionBySuggestionId(SuggestionEntity suggestionBySuggestionId) {
        this.suggestionBySuggestionId = suggestionBySuggestionId;
    }

    public SuggestionEntity getSuggestionByReplyId() {
        return suggestionByReplyId;
    }

    public void setSuggestionByReplyId(SuggestionEntity suggestionByReplyId) {
        this.suggestionByReplyId = suggestionByReplyId;
    }

    public UserEntity getUserByReplierId() {
        return userByReplierId;
    }

    public void setUserByReplierId(UserEntity userByReplierId) {
        this.userByReplierId = userByReplierId;
    }
}
