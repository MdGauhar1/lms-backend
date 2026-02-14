package com.lms.enrollment.controller;

import com.lms.common.CourseEnrolledEvent;
import com.lms.enrollment.entity.Enrollment;
import com.lms.enrollment.kafka.EnrollmentProducer;
import com.lms.enrollment.repository.EnrollmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/enrollments")
public class UserEnrollmentController {

    private final EnrollmentRepository repository;
    private final EnrollmentProducer producer;

    public UserEnrollmentController(
            EnrollmentRepository repository,
            EnrollmentProducer producer
    ) {
        this.repository = repository;
        this.producer = producer;
    }

    // ✅ USER enrolls himself
    @PostMapping("/{courseId}")
    public Enrollment enroll(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long courseId
    ) {
        if (repository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new RuntimeException("Already enrolled");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(userId);
        enrollment.setCourseId(courseId);

        Enrollment saved = repository.save(enrollment);

        // ✅ Kafka event
        producer.sendEnrollmentEvent(
                new CourseEnrolledEvent(userId, courseId)
        );

        return saved;
    }

    // ✅ USER sees only his enrollments
    @GetMapping
    public List<Enrollment> myEnrollments(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        return repository.findByUserId(userId);
    }
}