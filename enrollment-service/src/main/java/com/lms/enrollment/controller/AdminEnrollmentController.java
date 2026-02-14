package com.lms.enrollment.controller;

import com.lms.enrollment.entity.Enrollment;
import com.lms.enrollment.repository.EnrollmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/enrollments")
public class AdminEnrollmentController {

    private final EnrollmentRepository repository;

    public AdminEnrollmentController(EnrollmentRepository repository) {
        this.repository = repository;
    }

    // ✅ ADMIN sees all enrollments
    @GetMapping
    public List<Enrollment> allEnrollments() {
        return repository.findAll();
    }

    // ✅ ADMIN marks completed
    @PutMapping("/{id}/complete")
    public Enrollment markCompleted(@PathVariable Long id) {
        Enrollment enrollment = repository.findById(id).orElseThrow();
        enrollment.setStatus("COMPLETED");
        return repository.save(enrollment);
    }
}