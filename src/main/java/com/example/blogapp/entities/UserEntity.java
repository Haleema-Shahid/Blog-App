package com.example.blogapp.entities;

import com.example.blogapp.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "blog-app", catalog = "")
public class UserEntity implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "firstname")
    private String firstname;
    @Basic
    @Column(name = "lastname")
    private String lastname;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Basic
    @Column(name = "bio")
    private String bio;
    @Basic
    @Column(name = "profile_image")
    private String profileImage;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<BlogEntity> blogsById;
    @OneToMany(mappedBy = "userByLikerId")
    private Collection<BlogLikesEntity> blogLikesById;
    @OneToMany(mappedBy = "userByReporterId")
    private Collection<BlogReportEntity> blogReportsById;
    @OneToMany(mappedBy = "userByCommenterId")
    private Collection<CommentEntity> commentsById;
    @OneToMany(mappedBy = "userByLikerId")
    private Collection<CommentLikesEntity> commentLikesById;
    @OneToMany(mappedBy = "userByReplierId")
    private Collection<CommentReplyEntity> commentRepliesById;
    @OneToMany(mappedBy = "userByReporterId")
    private Collection<CommentReportEntity> commentReportsById;
    @OneToMany(mappedBy = "userBySuggesterId")
    private Collection<SuggestionEntity> suggestionsById;
    @OneToMany(mappedBy = "userByReplierId")
    private Collection<SuggestionReplyEntity> suggestionRepliesById;
    @Basic
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    @Basic
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Basic
    @Column(name = "dob")
    private Date dob;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        System.out.println("received role from db: "+role);
        this.role = role;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(role, that.role) && Objects.equals(bio, that.bio) && Objects.equals(profileImage, that.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email, password, role, bio, profileImage);
    }

    public Collection<BlogEntity> getBlogsById() {
        return blogsById;
    }

    public void setBlogsById(Collection<BlogEntity> blogsById) {
        this.blogsById = blogsById;
    }

    public Collection<BlogLikesEntity> getBlogLikesById() {
        return blogLikesById;
    }

    public void setBlogLikesById(Collection<BlogLikesEntity> blogLikesById) {
        this.blogLikesById = blogLikesById;
    }

    public Collection<BlogReportEntity> getBlogReportsById() {
        return blogReportsById;
    }

    public void setBlogReportsById(Collection<BlogReportEntity> blogReportsById) {
        this.blogReportsById = blogReportsById;
    }

    public Collection<CommentEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<CommentEntity> commentsById) {
        this.commentsById = commentsById;
    }

    public Collection<CommentLikesEntity> getCommentLikesById() {
        return commentLikesById;
    }

    public void setCommentLikesById(Collection<CommentLikesEntity> commentLikesById) {
        this.commentLikesById = commentLikesById;
    }

    public Collection<CommentReplyEntity> getCommentRepliesById() {
        return commentRepliesById;
    }

    public void setCommentRepliesById(Collection<CommentReplyEntity> commentRepliesById) {
        this.commentRepliesById = commentRepliesById;
    }

    public Collection<CommentReportEntity> getCommentReportsById() {
        return commentReportsById;
    }

    public void setCommentReportsById(Collection<CommentReportEntity> commentReportsById) {
        this.commentReportsById = commentReportsById;
    }

    public Collection<SuggestionEntity> getSuggestionsById() {
        return suggestionsById;
    }

    public void setSuggestionsById(Collection<SuggestionEntity> suggestionsById) {
        this.suggestionsById = suggestionsById;
    }

    public Collection<SuggestionReplyEntity> getSuggestionRepliesById() {
        return suggestionRepliesById;
    }

    public void setSuggestionRepliesById(Collection<SuggestionReplyEntity> suggestionRepliesById) {
        this.suggestionRepliesById = suggestionRepliesById;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
