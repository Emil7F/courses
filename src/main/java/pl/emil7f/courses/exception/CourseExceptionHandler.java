package pl.emil7f.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {

    public ResponseEntity<ErrorInfo> handleException(CourseException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getCourseError().getMessage()));
    }
}
