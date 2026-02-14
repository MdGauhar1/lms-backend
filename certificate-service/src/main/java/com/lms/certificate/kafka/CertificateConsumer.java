//package com.lms.certificate.kafka;
//
//import com.lms.certificate.entity.Certificate;
//import com.lms.certificate.repository.CertificateRepository;
//import com.lms.common.CourseCompletedEvent;
//import com.lowagie.text.Document;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.pdf.PdfWriter;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.io.FileOutputStream;
//import java.time.LocalDateTime;
//
//@Service
//public class CertificateConsumer {
//
//    private final CertificateRepository repository;
//
//    public CertificateConsumer(CertificateRepository repository) {
//        this.repository = repository;
//    }
//
//    @KafkaListener(topics = "course-completed", groupId = "certificate-group")
//    public void consume(CourseCompletedEvent event) {
//
//        try {
//            String filePath =
//                    "certificates/user-" + event.getUserId()
//                            + "-course-" + event.getCourseId() + ".pdf";
//
//            // Generate PDF
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(filePath));
//            document.open();
//            document.add(new Paragraph("🎓 COURSE COMPLETION CERTIFICATE"));
//            document.add(new Paragraph("User ID: " + event.getUserId()));
//            document.add(new Paragraph("Course ID: " + event.getCourseId()));
//            document.add(new Paragraph("Completed At: " + event.getCompletedAt()));
//            document.close();
//
//            // Save to DB
//            Certificate certificate = new Certificate();
//            certificate.setUserId(event.getUserId());
//            certificate.setCourseId(event.getCourseId());
//            certificate.setIssuedAt(LocalDateTime.now());
//            certificate.setCertificateUrl(filePath);
//
//            repository.save(certificate);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

















package com.lms.certificate.kafka;

import com.lms.certificate.entity.Certificate;
import com.lms.certificate.repository.CertificateRepository;
import com.lms.common.CourseCompletedEvent;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

@Service
public class CertificateConsumer {

    private final CertificateRepository repository;

    public CertificateConsumer(CertificateRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "course-completed", groupId = "certificate-group")
    public void consume(CourseCompletedEvent event) {

        try {

            // ✅ 1️⃣ Check if certificate already generated
            boolean exists = repository.existsByUserIdAndCourseId(
                    event.getUserId(),
                    event.getCourseId()
            );

            if (exists) {
                System.out.println("⚠ Certificate already exists. Skipping generation.");
                return; // 🔥 STOP execution
            }


            // 🔥 1️⃣ Ensure directory exists
            String baseDir = System.getProperty("user.dir") + File.separator + "certificates";
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 🔥 2️⃣ File path
            String filePath = baseDir + File.separator +
                    "user-" + event.getUserId() +
                    "-course-" + event.getCourseId() + ".pdf";

            // 🔥 3️⃣ Generate PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("🎓 COURSE COMPLETION CERTIFICATE"));
            document.add(new Paragraph("User ID: " + event.getUserId()));
            document.add(new Paragraph("Course ID: " + event.getCourseId()));
            document.add(new Paragraph("Completed At: " + event.getCompletedAt()));
            document.close();

            // 🔥 4️⃣ Save certificate info in DB
            Certificate certificate = new Certificate();
            certificate.setUserId(event.getUserId());
            certificate.setCourseId(event.getCourseId());
            certificate.setIssuedAt(LocalDateTime.now());
            certificate.setCertificateUrl(filePath);

            repository.save(certificate);

            System.out.println("✅ Certificate generated at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
