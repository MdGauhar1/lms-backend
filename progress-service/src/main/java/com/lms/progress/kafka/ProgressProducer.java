package com.lms.progress.kafka;

import com.lms.common.CourseCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProgressProducer {

    private final KafkaTemplate<String, CourseCompletedEvent> kafkaTemplate;

    public ProgressProducer(KafkaTemplate<String, CourseCompletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCourseCompleted(Long userId, Long courseId) {
        kafkaTemplate.send(
                "course-completed",
                new CourseCompletedEvent(userId, courseId, LocalDateTime.now())
        );
    }
}
