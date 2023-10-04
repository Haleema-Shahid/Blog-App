package com.example.blogapp.entities;

import com.example.blogapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @JsonManagedReference
    @JsonIgnore
    private Set<BlogEntity> blogsById;
    @OneToMany(mappedBy = "userByLikerId")
    private Set<BlogLikesEntity> blogLikesById;
    @OneToMany(mappedBy = "userByReporterId")
    private Set<BlogReportEntity> blogReportsById;
    @OneToMany(mappedBy = "userByCommenterId")
    private Set<CommentEntity> commentsById;
    @OneToMany(mappedBy = "userByLikerId")
    private Set<CommentLikesEntity> commentLikesById;

    @OneToMany(mappedBy = "userByReporterId")
    private Set<CommentReportEntity> commentReportsById;
    @OneToMany(mappedBy = "userBySuggesterId")
    @JsonManagedReference
    @JsonIgnore
    private Set<SuggestionEntity> suggestionsById;

    @Basic
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    @Basic
    @Column(name = "is_enabled")
    private Byte isEnabled;
    @Basic
    @Column(name = "dob")
    private Date dob;
    @Basic
    @Column(name = "incorrect_attempts")
    private Integer incorrectAttempts;
    @Basic
    @Column(name = "disable_start")
    private Timestamp disableStart;

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
        // Convert the user's role(s) into a collection of GrantedAuthority objects
        Collection<? extends GrantedAuthority> authorities = Stream.of(this.role)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        return authorities;
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
        return isEnabled != 0;
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

    public Set<BlogEntity> getBlogsById() {
        return blogsById;
    }

    public void setBlogsById(Set<BlogEntity> blogsById) {
        this.blogsById = blogsById;
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

    public Set<CommentLikesEntity> getCommentLikesById() {
        return commentLikesById;
    }

    public void setCommentLikesById(Set<CommentLikesEntity> commentLikesById) {
        this.commentLikesById = commentLikesById;
    }

    public Set<CommentReportEntity> getCommentReportsById() {
        return commentReportsById;
    }

    public void setCommentReportsById(Set<CommentReportEntity> commentReportsById) {
        this.commentReportsById = commentReportsById;
    }

    public Set<SuggestionEntity> getSuggestionsById() {
        return suggestionsById;
    }

    public void setSuggestionsById(Set<SuggestionEntity> suggestionsById) {
        this.suggestionsById = suggestionsById;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Byte getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Byte isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        if(isEnabled){

            System.out.println("SETTING AS ENABLED");
            this.isEnabled = 1;
        }
        else{
            this.isEnabled = 0;
        }

    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getIncorrectAttempts() {
        return incorrectAttempts;
    }

    public void setIncorrectAttempts(Integer incorrectAttempts) {
        this.incorrectAttempts = incorrectAttempts;
    }

    public Timestamp getDisableStart() {
        return disableStart;
    }

    public void setDisableStart(Timestamp disableStart) {
        this.disableStart = disableStart;
    }
}
