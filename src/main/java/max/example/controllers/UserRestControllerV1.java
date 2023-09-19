package max.example.controllers;

import lombok.AllArgsConstructor;
import max.example.entities.Task;
import max.example.entities.User;
import max.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
@AllArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> findById(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }



    @PostMapping(value = "/{userId}/tasks")
    public ResponseEntity<Task> saveTask(
            @PathVariable("userId") Long userId, @RequestBody Task task
    ) {
        return new ResponseEntity<>(userService.saveTask(userId, task), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("userId") Long userId, @RequestBody Task task,
            Principal principal, @PathVariable("taskId") Long taskId
    ) {
        return new ResponseEntity<>(
                userService.updateTask(userId, principal, taskId, task), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}/tasks/{taskId}")
    public void deleteTaskById(
            @PathVariable("userId") Long userId, Principal principal,
            @PathVariable("taskId") Long taskId
    ) {
        userService.deleteTaskById(userId, principal, taskId);
    }


    @PatchMapping(value = "/{userId}/tasks/{taskId}")
    public void addTaskResponsibleUser(
            @PathVariable("userId") Long userId, Principal principal,
             @RequestBody User responsibleUser, @PathVariable("taskId") Long taskId) {

        userService.setTaskResponsibleUser(userId, principal, responsibleUser, taskId);
    }
}