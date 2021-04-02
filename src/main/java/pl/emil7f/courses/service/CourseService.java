package pl.emil7f.courses.service;

import pl.emil7f.courses.model.Course;
import pl.emil7f.courses.model.Status;

import java.util.List;

public interface CourseService {
    List<Course> getCourses(Status status);

    Course getCourse(String id);

    Course addCourse(Course course);

    void deleteCourse(String id);

    Course putCourse(String id, Course course);

    Course patchCourse(String id, Course course);

    void courseEnrollment(Long studentId, String courseCode);
}
