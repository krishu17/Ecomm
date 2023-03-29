package com.happytech.Electrostore.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "User_Table")
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "User_name")
    private String name;
    @Column(name = "User_gender")
    private String gender;
    @Column(name = "User_Email", unique = true)
    private String emailId;

    @Column(name = "User_Password")
    private String password;

    @Column(name = "User_about", length = 1000)
    private String about;

    @Column(name = "User_Image")
    private String imageName;



}
