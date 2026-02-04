package com.lms.progress.controller;

import com.lms.progress.entity.Progress;
import com.lms.progress.service.ProgressService;
import com.lms.progress.repository.ProgressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;
    private final ProgressRepository repository;

    public ProgressController(ProgressService progressService,
                              ProgressRepository repository) {
        this.progressService = progressService;
        this.repository = repository;
    }

    // ✅ COMPLETE ONLY ONE LESSON
    @PostMapping("/lesson-complete")
    public ResponseEntity<Void> completeLesson(
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @RequestParam Long lessonId) {

        // 🔥 CALL SERVICE (IMPORTANT)
        progressService.completeLesson(userId, courseId, lessonId);

        return ResponseEntity.ok().build();
    }

    // GET /progress/{userId}/{courseId}
    @GetMapping("/{userId}/{courseId}")
    public List<Progress> getProgress(
            @PathVariable Long userId,
            @PathVariable Long courseId) {

        return repository.findByUserIdAndCourseId(userId, courseId);
    }
}






























//package com.lms.progress.controller;
//
//import com.lms.progress.entity.Progress;
//import com.lms.progress.kafka.ProgressProducer;
//import com.lms.progress.repository.ProgressRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RestController
//@RequestMapping("/progress")
//public class ProgressController {
//
//    private final ProgressRepository repository;
//    private final ProgressProducer producer;
//
//    public ProgressController(ProgressRepository repository,
//                              ProgressProducer producer) {
//        this.repository = repository;
//        this.producer = producer;
//    }
//
//    // POST /progress/lesson-complete
//    @PostMapping("/lesson-complete")
//    public ResponseEntity<Void> completeLesson(@RequestParam Long userId,
//                                                 @RequestParam Long courseId,
//                                                 @RequestParam Long lessonId) {
//
//        List<Progress> list =
//                repository.findByUserIdAndCourseId(userId, courseId);
//
//        list.forEach(p -> {
//            p.setCompleted(true);
//            p.setCompletedAt(LocalDateTime.now());
//            repository.save(p);
//        });
//
//        // mock: course completed after lesson
//        producer.sendCourseCompleted(userId, courseId);
//
//        return ResponseEntity.ok().build();
//    }
//
//    // GET /progress/{userId}/{courseId}
//    @GetMapping("/{userId}/{courseId}")
//    public List<Progress> getProgress(@PathVariable Long userId,
//                                      @PathVariable Long courseId) {
//        return repository.findByUserIdAndCourseId(userId, courseId);
//    }
//}
