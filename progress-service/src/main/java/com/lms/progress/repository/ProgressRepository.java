package com.lms.progress.repository;

import com.lms.progress.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findByUserIdAndCourseId(Long userId, Long courseId);

    Optional<Progress> findByUserIdAndCourseIdAndLessonId(
            Long userId,
            Long courseId,
            Long lessonId
    );

    // ✅ count completed lessons for THIS user & course
    long countByUserIdAndCourseIdAndCompletedTrue(Long userId, Long courseId);

    // ✅ count total lessons for THIS user & course
    long countByUserIdAndCourseId(Long userId, Long courseId);
}













//package com.lms.progress.repository;
//
//import com.lms.progress.entity.Progress;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface ProgressRepository extends JpaRepository<Progress, Long> {
//
//    List<Progress> findByUserIdAndCourseId(Long userId, Long courseId);
//
//    Optional<Progress> findByUserIdAndCourseIdAndLessonId(
//            Long userId,
//            Long courseId,
//            Long lessonId
//    );
//
//    long countByUserIdAndCourseIdAndCompletedTrue(Long userId, Long courseId);
//
//    long countByCourseId(Long courseId);
//}
