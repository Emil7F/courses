package pl.emil7f.courses.service;

import org.springframework.stereotype.Service;
import pl.emil7f.courses.exception.CourseError;
import pl.emil7f.courses.exception.CourseException;
import pl.emil7f.courses.model.Course;
import pl.emil7f.courses.model.Status;
import pl.emil7f.courses.repository.CourseRepository;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCourses(Status status) {
        if (status != null) {
            return courseRepository.findAllByStatus(status);
        }
        return courseRepository.findAll();
    }


    @Override
    public Course getCourse(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course addCourse(Course course) {
        course.validateCourse();
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
        courseRepository.delete(course);
    }

    @Override
    public Course putCourse(String id, Course course) {
        course.validateCourse();
        return courseRepository.findById(id)
                .map(courseFromDb -> {
                    courseFromDb.setName(course.getName());
                    courseFromDb.setDescriptions(course.getDescriptions());
                    courseFromDb.setStartDate(course.getStartDate());
                    courseFromDb.setEndDate(course.getEndDate());
                    courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    return courseRepository.save(courseFromDb);
                })
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course patchCourse(String id, Course course) {
        course.validateCourse();
        return courseRepository.findById(id)
                .map(courseFromDb -> {
                    if (!course.getName().isEmpty() && course.getName() != null) {
                        courseFromDb.setName(course.getName());
                    }
                    if (!course.getDescriptions().isEmpty() && course.getDescriptions() != null) {
                        courseFromDb.setDescriptions(course.getDescriptions());
                    }
                    if (course.getStartDate() != null) {
                        courseFromDb.setStartDate(course.getStartDate());
                    }
                    if (course.getEndDate() != null) {
                        courseFromDb.setEndDate(course.getEndDate());
                    }
                    if (course.getParticipantsLimit() != null) {
                        courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    }
                    if (course.getParticipantsNumber() != null) {
                        courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    }
                    return courseRepository.save(courseFromDb);
                })
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public void courseEnrollment(Long studentId, String courseCode) {
        Course course = getCourse(courseCode);
        if (!Status.ACTIVE.equals(course.getStatus())) {
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }

    }
}
