package pl.emil7f.courses.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.emil7f.courses.exception.CourseError;
import pl.emil7f.courses.exception.CourseException;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class Course {

    @Id
    private String code;
    @NotBlank
    private String name;
    private String descriptions;
    @NotNull
    @Future
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;
    @Min(0)
    private Long participantsLimit;
    @NotNull
    @Min(0)
    private Long participantsNumber;

    @NotNull
    private Status status;


    private List<CourseMember> courseMembers = new ArrayList<>();


    public void validateCourse() {
        validateCourseDate();
        validateParticipantsLimit();
        validateStatus();
    }


    private void validateCourseDate() {
        if (startDate.isAfter(endDate)) {
            throw new CourseException(CourseError.COURSE_START_DATE_IS_AFTER_END_DATE);
        }
    }

    private void validateParticipantsLimit() {
        if (participantsNumber > participantsLimit) {
            throw new CourseException(CourseError.COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED);
        }
    }

    private void validateStatus() {
        if (Status.FULL.equals(status) &&
                !participantsNumber.equals(participantsLimit)) {
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_FULL_STATUS);
        }
        if(Status.ACTIVE.equals(status)&&
        participantsNumber.equals(participantsLimit)){
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_ACTIVE_STATUS);
        }
    }

    public void incrementParticipantNumber(){
        participantsNumber++;
        if (participantsNumber.equals(participantsLimit)) {
            setStatus(Status.FULL);
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getParticipantsLimit() {
        return participantsLimit;
    }

    public void setParticipantsLimit(Long participantsLimit) {
        this.participantsLimit = participantsLimit;
    }

    public Long getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(Long participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public List<CourseMember> getCourseMembers() {
        return courseMembers;
    }

    public void setCourseMembers(List<CourseMember> courseMembers) {
        this.courseMembers = courseMembers;
    }
}
