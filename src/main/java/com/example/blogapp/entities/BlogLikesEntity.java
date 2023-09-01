package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "blog_likes", schema = "blog-app", catalog = "")
public class BlogLikesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id", nullable = false)
    private BlogEntity blogByBlogId;
    @ManyToOne
    @JoinColumn(name = "liker_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByLikerId;

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
        BlogLikesEntity that = (BlogLikesEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public BlogEntity getBlogByBlogId() {
        return blogByBlogId;
    }

    public void setBlogByBlogId(BlogEntity blogByBlogId) {
        this.blogByBlogId = blogByBlogId;
    }

    public UserEntity getUserByLikerId() {
        return userByLikerId;
    }

    public void setUserByLikerId(UserEntity userByLikerId) {
        this.userByLikerId = userByLikerId;
    }
}
