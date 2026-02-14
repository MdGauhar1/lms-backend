package com.lms.certificate.repository;

import com.lms.certificate.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByUserId(Long userId);

    // ✅ Check if certificate already exists
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
