package com.happytech.Electrostore.dto;

import com.happytech.Electrostore.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityDto {

    private LocalDate create;

    private LocalDate update;

    private String isActive;
}
