package com.example.user_service.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String role;

    private String duration;

    @Column(length = 2000)
    private String description;
}