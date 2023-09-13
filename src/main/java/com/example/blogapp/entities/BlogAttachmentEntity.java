package com.example.blogapp.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "blog_attachment", schema = "blog-app", catalog = "")
public class BlogAttachmentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "attachment")
    private String attachment;
    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id", nullable = false)
    private BlogEntity blogByBlogId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogAttachmentEntity that = (BlogAttachmentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(attachment, that.attachment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attachment);
    }

    public BlogEntity getBlogByBlogId() {
        return blogByBlogId;
    }

    public void setBlogByBlogId(BlogEntity blogByBlogId) {
        this.blogByBlogId = blogByBlogId;
    }
}
