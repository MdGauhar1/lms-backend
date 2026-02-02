package com.lms.certificate.controller;

import com.lms.certificate.entity.Certificate;
import com.lms.certificate.repository.CertificateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateRepository repository;

    public CertificateController(CertificateRepository repository) {
        this.repository = repository;
    }

    // GET /certificates/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Certificate> getUserCertificates(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }
}
