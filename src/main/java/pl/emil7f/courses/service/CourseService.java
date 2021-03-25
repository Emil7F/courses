package pl.emil7f.courses.service;

import pl.emil7f.courses.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourses();

    Course getCourse(String id);

    Course addCourse(Course course);

    void deleteCourse(String id);

    Course putCourse(String id, Course course);

    Course patchCourse(String id, Course course);
}
