package com.happytech.Electrostore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories")
@Builder
public class Category {

    @Column(name="catID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(name = "CatTitle")
    private String title;
    @Column(name="CatDesc")
    private String description;
    @Column(name = "CoverImage")
    private String coverImage;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "categoryId")
//    private List<Product> products;

}
