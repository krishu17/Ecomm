package com.happytech.Electrostore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @Column(name="created_date",updatable = false)
    @CreationTimestamp
    private LocalDate create;

    @Column(name="updated_date",insertable = false)
    @UpdateTimestamp
    private LocalDate update;

    @Column(name = "isActiveSwitch")
    private String isActive;



}
