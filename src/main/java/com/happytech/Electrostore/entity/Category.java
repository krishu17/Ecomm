package com.happytech.Electrostore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories")
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

}
