package com.lms.enrollment.kafka;

import com.lms.common.CourseEnrolledEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentProducer {

    private final KafkaTemplate<String, CourseEnrolledEvent> kafkaTemplate;

    public EnrollmentProducer(KafkaTemplate<String, CourseEnrolledEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEnrollmentEvent(CourseEnrolledEvent event) {
        kafkaTemplate.send("course-enrolled", event);
    }
}
