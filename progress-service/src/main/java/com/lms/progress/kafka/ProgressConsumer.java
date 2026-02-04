package com.lms.progress.kafka;

import com.lms.common.CourseEnrolledEvent;
import com.lms.progress.dto.LessonDto;
import com.lms.progress.entity.Progress;
import com.lms.progress.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ProgressConsumer {

    private final ProgressRepository repository;
    private final WebClient webClient;

    @Value("${course.service.base-url}")
    private String courseServiceBaseUrl;

    public ProgressConsumer(ProgressRepository repository,
                            WebClient.Builder builder) {
        this.repository = repository;
        this.webClient = builder.build();
    }

    @KafkaListener(topics = "course-enrolled", groupId = "progress-group")
    public void consume(CourseEnrolledEvent event) {

        // 1️⃣ Call COURSE-SERVICE to get lessons
        List<LessonDto> lessons = webClient
                .get()
                .uri(courseServiceBaseUrl + "/courses/{id}/lessons",
                        event.getCourseId())
                .retrieve()
                .bodyToFlux(LessonDto.class)
                .collectList()
                .block();

        // 2️⃣ Create one progress row PER lesson
        for (LessonDto lesson : lessons) {

            Progress progress = new Progress();
            progress.setUserId(event.getUserId());
            progress.setCourseId(event.getCourseId());
            progress.setLessonId(lesson.getId());
            progress.setCompleted(false);

            repository.save(progress);
        }
    }
}



























//package com.lms.progress.kafka;
//
//import com.lms.common.CourseEnrolledEvent;
//import com.lms.progress.entity.Progress;
//import com.lms.progress.repository.ProgressRepository;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProgressConsumer {
//
//    private final ProgressRepository repository;
//
//    public ProgressConsumer(ProgressRepository repository) {
//        this.repository = repository;
//    }
//
//    @KafkaListener(topics = "course-enrolled", groupId = "progress-group")
//    public void consume(CourseEnrolledEvent event) {
//
//        // create initial progress record (mock lessonId = 1)
//        Progress progress = new Progress();
//        progress.setUserId(event.getUserId());
//        progress.setCourseId(event.getCourseId());
//        progress.setLessonId(1L);
//        progress.setCompleted(false);
//
//        repository.save(progress);
//    }
//}
