package pl.emil7f.courses.exception;

import org.checkerframework.checker.units.qual.C;

public enum  CourseError {

    COURSE_NOT_FOUND("Course does not exists"),
    COURSE_START_DATE_IS_AFTER_END_DATE("Course start date is after end date");


    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
