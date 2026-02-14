package com.lms.certificate.kafka;

import com.lms.certificate.entity.Certificate;
import com.lms.certificate.repository.CertificateRepository;
import com.lms.common.CourseCompletedEvent;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.io.FileOutputStream;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;



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

            boolean exists = repository.existsByUserIdAndCourseId(
                    event.getUserId(),
                    event.getCourseId()
            );

            if (exists) return;

            String baseDir = System.getProperty("user.dir")
                    + File.separator + "certificates";

            File dir = new File(baseDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName =
                    "user-" + event.getUserId()
                            + "-course-" + event.getCourseId() + ".pdf";

            String filePath = baseDir + File.separator + fileName;








            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

// ✅ GOLD DOUBLE BORDER
            PdfContentByte canvas = writer.getDirectContent();

            Rectangle outer = new Rectangle(20, 20, 575, 822);
            outer.setBorder(Rectangle.BOX);
            outer.setBorderWidth(6);
            outer.setBorderColor(new Color(212,175,55));
            canvas.rectangle(outer);

            Rectangle inner = new Rectangle(35, 35, 560, 807);
            inner.setBorder(Rectangle.BOX);
            inner.setBorderWidth(2);
            inner.setBorderColor(new Color(212,175,55));
            canvas.rectangle(inner);

// 🔹 Fonts
            Font titleFont = new Font(Font.HELVETICA, 32, Font.BOLD);
            Font subTitleFont = new Font(Font.HELVETICA, 18, Font.NORMAL);
            Font nameFont = new Font(Font.HELVETICA, 26, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 16, Font.NORMAL);
            Font signatureFont = new Font(Font.HELVETICA, 16, Font.BOLD);

// 🔹 Title
            Paragraph title = new Paragraph("CERTIFICATE OF COMPLETION", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(40);
            document.add(title);

// 🔹 Subtitle
            Paragraph subtitle = new Paragraph(
                    "This certificate is proudly presented to",
                    subTitleFont
            );
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

// 🔹 Student Name
            Paragraph name = new Paragraph("User ID: " + event.getUserId(), nameFont);
            name.setAlignment(Element.ALIGN_CENTER);
            name.setSpacingAfter(30);
            document.add(name);

// 🔹 Course Text
            Paragraph courseText = new Paragraph(
                    "For successfully completing the course",
                    normalFont
            );
            courseText.setAlignment(Element.ALIGN_CENTER);
            courseText.setSpacingAfter(15);
            document.add(courseText);

// 🔹 Course Name
            Paragraph courseName = new Paragraph(
                    "Course ID: " + event.getCourseId(),
                    nameFont
            );
            courseName.setAlignment(Element.ALIGN_CENTER);
            courseName.setSpacingAfter(30);
            document.add(courseName);

// 🔹 Date
            Paragraph date = new Paragraph(
                    "Date of Completion: " + event.getCompletedAt(),
                    normalFont
            );
            date.setAlignment(Element.ALIGN_CENTER);
            date.setSpacingAfter(80);
            document.add(date);

// ✅ DIGITAL SIGNATURE (TEXT ONLY)
            Paragraph signLine = new Paragraph("__________________________", normalFont);
            signLine.setAlignment(Element.ALIGN_RIGHT);
            document.add(signLine);

            Paragraph manager = new Paragraph("LMS Manager", signatureFont);
            manager.setAlignment(Element.ALIGN_RIGHT);
            document.add(manager);

            Paragraph digital = new Paragraph("Digitally Issued Certificate", normalFont);
            digital.setAlignment(Element.ALIGN_RIGHT);
            document.add(digital);

            document.close();












































//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(filePath));
//            document.open();
//            document.add(new Paragraph("🎓 COURSE COMPLETION CERTIFICATE"));
//            document.add(new Paragraph("User ID: " + event.getUserId()));
//            document.add(new Paragraph("Course ID: " + event.getCourseId()));
//            document.add(new Paragraph("Completed At: " + event.getCompletedAt()));
//            document.close();

            Certificate certificate = new Certificate();
            certificate.setUserId(event.getUserId());
            certificate.setCourseId(event.getCourseId());
            certificate.setIssuedAt(LocalDateTime.now());
            certificate.setCertificateUrl(fileName); // ✅ only file name

            repository.save(certificate);
            System.out.println("✅ Certificate generated at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}























//
//
//
//
//
//
//
//
//
//
//
//
//
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
//import java.io.File;
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
//
//            // ✅ 1️⃣ Check if certificate already generated
//            boolean exists = repository.existsByUserIdAndCourseId(
//                    event.getUserId(),
//                    event.getCourseId()
//            );
//
//            if (exists) {
//                System.out.println("⚠ Certificate already exists. Skipping generation.");
//                return; // 🔥 STOP execution
//            }
//
//
//            // 🔥 1️⃣ Ensure directory exists
//            String baseDir = System.getProperty("user.dir") + File.separator + "certificates";
//            File dir = new File(baseDir);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//
//            // 🔥 2️⃣ File path
//            String filePath = baseDir + File.separator +
//                    "user-" + event.getUserId() +
//                    "-course-" + event.getCourseId() + ".pdf";
//
//            // 🔥 3️⃣ Generate PDF
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(filePath));
//            document.open();
//            document.add(new Paragraph("🎓 COURSE COMPLETION CERTIFICATE"));
//            document.add(new Paragraph("User ID: " + event.getUserId()));
//            document.add(new Paragraph("Course ID: " + event.getCourseId()));
//            document.add(new Paragraph("Completed At: " + event.getCompletedAt()));
//            document.close();
//
//            // 🔥 4️⃣ Save certificate info in DB
//            Certificate certificate = new Certificate();
//            certificate.setUserId(event.getUserId());
//            certificate.setCourseId(event.getCourseId());
//            certificate.setIssuedAt(LocalDateTime.now());
//            certificate.setCertificateUrl(filePath);
//
//            repository.save(certificate);
//
//            System.out.println("✅ Certificate generated at: " + filePath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
