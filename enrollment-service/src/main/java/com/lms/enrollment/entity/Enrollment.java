package com.lms.enrollment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;



@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"userId", "courseId"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long courseId;

    private String status; // ENROLLED, COMPLETED

    private LocalDateTime enrolledAt;

    @PrePersist
    public void onCreate() {
        this.enrolledAt = LocalDateTime.now();
        this.status = "ENROLLED";
    }
}























//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Enrollment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long userId;
//    private Long courseId;
//
//    private String status; // ENROLLED, COMPLETED
//
//    private LocalDateTime enrolledAt;
//}
