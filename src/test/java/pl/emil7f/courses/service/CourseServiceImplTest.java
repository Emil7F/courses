package pl.emil7f.courses.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import pl.emil7f.courses.exception.CourseError;
import pl.emil7f.courses.exception.CourseException;
import pl.emil7f.courses.model.Course;
import pl.emil7f.courses.repository.CourseRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseServiceImpl courseService;

    private Course courseTest;

    @BeforeEach
    void setup() {
        courseTest = new Course();

        courseTest.setCode("code");
        courseTest.setName("name");
        courseTest.setDescriptions("description");
        courseTest.setStartDate(LocalDateTime.of(2021, Month.APRIL, 1, 16, 0));
        courseTest.setEndDate(LocalDateTime.of(2021, Month.MAY, 10, 20, 0));
        courseTest.setParticipantsLimit(15L);
        courseTest.setParticipantsNumber(10L);

    }

    @Test
    @DisplayName("when getCourse by id should return course")
    void test1() {
        // given
        Mockito.when(courseRepository.findById("code"))
                .thenReturn(Optional.ofNullable(courseTest));
        // when
        Course course = courseService.getCourse("code");
        //then
        assertThat(course).isNotNull();
        assertEquals(courseTest, course);

    }

    @Test
    @DisplayName("should throw not found exception")
    void test2() {
        // given
        Mockito.when(courseRepository.findById("ids"))
                .thenThrow(new CourseException(CourseError.COURSE_NOT_FOUND));
        // when
        //then
        assertThrows(CourseException.class, () -> courseService.getCourse("ids"));
    }

    @Test
    @DisplayName("should return courses list")
    void test3(){
        // given
        Mockito.when(courseRepository.findAll())
                .thenReturn(Arrays.asList(new Course(), new Course(), new Course()));
        // when
        List<Course> courses = courseService.getCourses();
        //then
        Mockito.verify(courseRepository, Mockito.times(1)).findAll();
        assertEquals(3, courses.size());
    }

    @Test
    @DisplayName("when addCourse should return course")
    void test4(){
        // given
        Mockito.when(courseRepository.save(courseTest))
                .thenReturn(courseTest);
        // when
        Course course = courseService.addCourse(courseTest);
        //then
        assertEquals(courseTest, course);
        assertThat(course).isNotNull();
    }




}
