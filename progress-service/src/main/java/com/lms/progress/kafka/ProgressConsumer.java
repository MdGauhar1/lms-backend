package com.lms.progress.kafka;

import com.lms.common.CourseEnrolledEvent;
import com.lms.progress.entity.Progress;
import com.lms.progress.repository.ProgressRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProgressConsumer {

    private final ProgressRepository repository;

    public ProgressConsumer(ProgressRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "course-enrolled", groupId = "progress-group")
    public void consume(CourseEnrolledEvent event) {

        // create initial progress record (mock lessonId = 1)
        Progress progress = new Progress();
        progress.setUserId(event.getUserId());
        progress.setCourseId(event.getCourseId());
        progress.setLessonId(1L);
        progress.setCompleted(false);

        repository.save(progress);
    }
}
