package com.lms.course.controller;

import com.lms.course.entity.Course;
import com.lms.course.entity.Lesson;
import com.lms.course.repository.CourseRepository;
import com.lms.course.repository.LessonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepo;
    private final LessonRepository lessonRepo;

    public CourseController(CourseRepository courseRepo, LessonRepository lessonRepo) {
        this.courseRepo = courseRepo;
        this.lessonRepo = lessonRepo;
    }

    // GET /courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    // GET /courses/{id}
    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseRepo.findById(id).orElseThrow();
    }

    // GET /courses/{id}/lessons
    @GetMapping("/{id}/lessons")
    public List<Lesson> getLessons(@PathVariable Long id) {
        return lessonRepo.findByCourseId(id);
    }
}
