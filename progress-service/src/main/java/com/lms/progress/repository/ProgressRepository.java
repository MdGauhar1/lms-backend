package com.lms.progress.repository;

import com.lms.progress.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findByUserIdAndCourseId(Long userId, Long courseId);
}
