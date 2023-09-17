package max.example.service;

import max.example.entities.Task;
import max.example.exceptions.TaskNotFoundException;
import max.example.exceptions.UserNotFoundException;
import max.example.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import max.example.dto.RegistrationUserDto;
import max.example.entities.User;
import max.example.repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private  UserRepository userRepository;
    private  RoleService roleService;
    private  PasswordEncoder passwordEncoder;
    private  TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found in ID : " + userId)
        );
    }
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }
    public User setTaskResponsibleUser(
                Long userId, Principal principal, User user, Long taskId) {

        checkRightsUserChangeTask(userId, principal);
        Task task = findTaskById(taskId);
        User dataUser = userRepository.findById(user.getUserId()).get();
        List<Task> tasks = dataUser.getResponsibleTasks();

        if (!tasks.contains(task)) {
            dataUser.addResponsibleTasks(task);
            userRepository.save(dataUser);
        } else {
            throw new TaskNotFoundException("Эта задача уже назначана пользователю");
        }

        return dataUser;
    }

    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task not found ID : " + taskId)
        );
    }

    public Task saveTask(Long userId, Task task) {
        if (taskRepository.findByName(task.getName()) != null) {
            throw new TaskNotFoundException("Такое задание уже сушествует");
        }
        User user = findUserById(userId);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long userId, Principal principal, Long taskId, Task task) {
        checkRightsUserChangeTask(userId, principal);
        Task dataTask = findTaskById(taskId);
        if (dataTask != null) {
            dataTask.setName(task.getName());
            dataTask.setStatus(task.getStatus());
            taskRepository.save(dataTask);
        }
        return dataTask;
    }
    public void deleteTaskById(Long userId, Principal principal, Long taskId) {
        checkRightsUserChangeTask(userId, principal);
        taskRepository.deleteById(taskId);
    }

    private void checkRightsUserChangeTask(Long userId, Principal principal) {
        User user = findUserById(userId);
        User userPrincipal = findByUsername(principal.getName()).get();
        if(!user.getUserId().equals(userPrincipal.getUserId())) {
            throw new UserNotFoundException("Этот пользователь не создатель этой задачи");
        }
    }
}
