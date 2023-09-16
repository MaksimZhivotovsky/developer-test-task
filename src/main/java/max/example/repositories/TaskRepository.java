package max.example.repositories;

import max.example.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
//    Task findByName(String name);
//    List<Task> findAllByTaskCreator_UserId(Long userId);
}
