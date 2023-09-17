package max.example.controllers;

import lombok.AllArgsConstructor;
import max.example.entities.Task;
import max.example.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tasks")
@AllArgsConstructor
public class TaskRestControllerV1 {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> findAllTasks() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }
}
