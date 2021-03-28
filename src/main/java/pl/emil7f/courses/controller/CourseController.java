package pl.emil7f.courses.controller;


import org.springframework.web.bind.annotation.*;
import pl.emil7f.courses.model.Course;
import pl.emil7f.courses.model.Status;
import pl.emil7f.courses.service.CourseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false) Status status) {
        return courseService.getCourses(status);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable String id) {
        return courseService.getCourse(id);
    }

    @PostMapping
    public Course addCourse(@Valid @RequestBody Course course){
        return courseService.addCourse(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    }

    @PutMapping("/{id}")
    public Course putCourse(@PathVariable String id, @RequestBody Course course) {
        return courseService.putCourse(id, course);
    }

    @PatchMapping("/{id}")
    public Course patchCourse(@PathVariable String id, @RequestBody Course course) {
        return courseService.patchCourse(id, course);
    }


}
