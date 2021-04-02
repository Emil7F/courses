package pl.emil7f.courses.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.emil7f.courses.model.dto.Student;

@FeignClient(name = "SIMPLE-API-SERVICE")
@RequestMapping("/students")
public interface StudentServiceClient {

    @GetMapping("/{studentId}")
    Student getStudentById(@PathVariable Long studentId);
}
