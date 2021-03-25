package pl.emil7f.courses.exception;

public enum  CourseError {

    COURSE_NOT_FOUND("Course does not exists");


    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
