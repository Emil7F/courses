package pl.emil7f.courses.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.emil7f.courses.model.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

}
