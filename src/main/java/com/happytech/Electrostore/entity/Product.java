package com.happytech.Electrostore.entity;

import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String title;
    @Column(length = 10000)
    private String description;
    private Double price;
    private Integer quantity;
    private boolean stock;
    @Column(name="added_date",updatable = false)
    @CreationTimestamp
    private Date addedDate;
    private String brand;
    private boolean live;
    private double rating;

    private String productImageName;

    @ManyToOne
    private Category category;
//    private List<String> reviews;
//    private List<String> features;
//    private List<String> tags;

}
