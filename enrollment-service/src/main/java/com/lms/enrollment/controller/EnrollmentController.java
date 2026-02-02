package com.lms.enrollment.controller;

import com.lms.common.CourseEnrolledEvent;
import com.lms.enrollment.entity.Enrollment;
import com.lms.enrollment.kafka.EnrollmentProducer;
import com.lms.enrollment.repository.EnrollmentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository repository;
    private final EnrollmentProducer producer;

    public EnrollmentController(EnrollmentRepository repository,
                                EnrollmentProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    // POST /enrollments
    @PostMapping
    public Enrollment enroll(@RequestParam Long userId,
                             @RequestParam Long courseId) {

        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(userId);
        enrollment.setCourseId(courseId);
        enrollment.setStatus("ENROLLED");
        enrollment.setEnrolledAt(LocalDateTime.now());

        Enrollment saved = repository.save(enrollment);

        // Send Kafka event
        producer.sendEnrollmentEvent(
                new CourseEnrolledEvent(userId, courseId)
        );

        return saved;
    }

    // GET /enrollments/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Enrollment> getUserEnrollments(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }
}
