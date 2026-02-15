package com.lms.certificate.controller;

import com.lms.certificate.entity.Certificate;
import com.lms.certificate.repository.CertificateRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateRepository repository;

    public CertificateController(CertificateRepository repository) {
        this.repository = repository;
    }

    // ✅ Get certificates by user
    @GetMapping("/user/{userId}")
    public List<Certificate> getUserCertificates(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }









    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable Long id)
            throws Exception {

        Certificate certificate = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));

        Path path = Paths.get(System.getProperty("user.dir"))
                .resolve("certificates")
                .resolve(certificate.getCertificateUrl())
                .normalize();

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found: " + path);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + certificate.getCertificateUrl() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(resource);
    }

















//    // ✅ Secure download (Gateway already validates JWT)
//    @GetMapping("/download/{id}")
//    public ResponseEntity<Resource> downloadCertificate(@PathVariable Long id)
//            throws Exception {
//
//        Certificate certificate = repository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Certificate not found"));
//
//        Path path = Paths.get("certificates")
//                .resolve(certificate.getCertificateUrl());
//
//        Resource resource = new UrlResource(path.toUri());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "inline; filename=\"" + certificate.getCertificateUrl() + "\"")
//                .body(resource);
//    }
}






















//
//
//
//
//
//
//package com.lms.certificate.controller;
//
//import com.lms.certificate.entity.Certificate;
//import com.lms.certificate.repository.CertificateRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/certificates")
//public class CertificateController {
//
//    private final CertificateRepository repository;
//
//    public CertificateController(CertificateRepository repository) {
//        this.repository = repository;
//    }
//
//    // GET /certificates/user/{userId}
//    @GetMapping("/user/{userId}")
//    public List<Certificate> getUserCertificates(@PathVariable Long userId) {
//        return repository.findByUserId(userId);
//    }
//}
