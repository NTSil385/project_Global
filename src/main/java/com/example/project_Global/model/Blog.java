package com.example.project_Global.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Integer Id;
    @Column(name = "blog_title")
    private String Title;
    @Column(name = "blog_describe")
    private String Describe;
    @Column (name = "author")
    private String Author;
    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @Column(name = "blog_image")
    private String Image;

    @Column(name = "url")
    private String Url;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Blog(Integer id, String title, String describe, String author, Date createDate, String image, String url) {
        Id = id;
        Title = title;
        Describe = describe;
        Author = author;
        this.createDate = createDate;
        Image = image;
        Url = url;
    }

    public Blog() {
    }
}
