package pl.emil7f.courses.exception;

public enum CourseError {

    COURSE_NOT_FOUND("Course does not exists"),
    COURSE_START_DATE_IS_AFTER_END_DATE("Course start date is after end date"),
    COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED("Course participants limit is exceeded"),
    COURSE_CAN_NOT_SET_FULL_STATUS("Course can not set full status"),
    COURSE_CAN_NOT_SET_ACTIVE_STATUS("Course can not set active status"),
    COURSE_IS_NOT_ACTIVE("Course is not active");


    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
