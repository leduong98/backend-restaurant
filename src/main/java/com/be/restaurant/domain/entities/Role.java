package com.be.restaurant.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "role")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Id
    private long id;

    private String name;

    private String description;

}
