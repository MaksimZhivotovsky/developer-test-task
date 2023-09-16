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

    public Task findById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new RuntimeException("Task not found ID : " + taskId)
        );
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task update(Long taskId, Task task) {
        Task dataTask = findById(taskId);
        if (dataTask != null) {
            dataTask.setName(task.getName());
            dataTask.setStatus(task.getStatus());
            taskRepository.save(dataTask);
        }
        return dataTask;
    }
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

}
