package pl.emil7f.courses.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "SIMPLE-API-SERVICE")
@RequestMapping("/students")
public interface StudentServiceClient {


}
