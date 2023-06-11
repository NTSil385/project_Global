package com.example.project_Global.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Trường này không được phép bỏ trống")
    @Length(min = 3, max = 500, message = "Phải nhập tên sản phẩm")
    @Column(name = "Product_Name")
    private String name;


    @Column(name = "Product_Image")
    private String image;

    @Length(min = 3, max = 1000, message = "Phải nhập mô tả sản phẩm")
    @Column(name = "Description")
    private String des;


    @Column(name = "Product_Price")
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public Product(){}

    public Product(int id, String name, String image, String des, long price, Category category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.des = des;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", des='" + des + '\'' +
                ", price=" + price +
                '}';
    }
}
