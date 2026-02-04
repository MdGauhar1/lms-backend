package com.lms.progress.service;


import com.lms.progress.entity.Progress;
import com.lms.progress.kafka.ProgressProducer;
import com.lms.progress.repository.ProgressRepository;
import org.springframework.stereotype.Service;


@Service
public class ProgressService {

    private final ProgressRepository repository;
    private final ProgressProducer producer;

    public ProgressService(ProgressRepository repository,
                           ProgressProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public void completeLesson(Long userId, Long courseId, Long lessonId) {

        Progress progress = repository
                .findByUserIdAndCourseIdAndLessonId(userId, courseId, lessonId)
                .orElseThrow();

        progress.setCompleted(true);
        repository.save(progress);

        long completedLessons =
                repository.countByUserIdAndCourseIdAndCompletedTrue(userId, courseId);

        long totalLessons =
                repository.countByUserIdAndCourseId(userId,courseId);

        if (completedLessons == totalLessons) {
            producer.sendCourseCompleted(userId, courseId);
        }
    }
}


