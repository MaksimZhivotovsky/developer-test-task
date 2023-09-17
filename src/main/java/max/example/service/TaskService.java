package max.example.service;

import lombok.AllArgsConstructor;
import max.example.entities.Task;
import max.example.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

}
