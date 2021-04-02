package pl.emil7f.courses.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class CourseMember {
    @NotNull
    private LocalDateTime enrollmentDate;
    @NotNull
    private String email;

    public CourseMember(@NotNull String email) {
        this.enrollmentDate = LocalDateTime.now();
        this.email = email;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public String getEmail() {
        return email;
    }
}
