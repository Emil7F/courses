package pl.emil7f.courses.service;

import org.springframework.stereotype.Service;
import pl.emil7f.courses.exception.CourseError;
import pl.emil7f.courses.exception.CourseException;
import pl.emil7f.courses.model.Course;
import pl.emil7f.courses.model.CourseMember;
import pl.emil7f.courses.model.Status;
import pl.emil7f.courses.model.dto.Student;
import pl.emil7f.courses.repository.CourseRepository;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;

    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
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
    public void courseEnrollment(String courseCode, Long studentId) {
        Course course = getCourse(courseCode);
        validateCourseStatus(course);
        Student studentById = studentServiceClient.getStudentById(studentId);
        validateStudentBeforeCourseEnrollment(course, studentById);

        course.incrementParticipantNumber();

        course.getCourseMembers().add(new CourseMember(studentById.getEmail()));
        courseRepository.save(course);
    }

    private void validateStudentBeforeCourseEnrollment(Course course, Student studentById) {
        if (!Student.StudentStatus.ACTIVE.equals(studentById.getStatus())) {
            throw new CourseException(CourseError.STUDENT_IS_NOT_ACTIVE);
        }
        if (
                course.getCourseMembers().stream()
                        .anyMatch(member -> studentById.getEmail().equals(member.getEmail()))) {
            throw new CourseException(CourseError.STUDENT_ALREADY_ENROLLED);
        }
    }

    private void validateCourseStatus(Course course) {
        if (!Status.ACTIVE.equals(course.getStatus())) {
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }
    }


}
