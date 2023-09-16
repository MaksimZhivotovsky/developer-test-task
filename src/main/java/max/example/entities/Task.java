package max.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Getter
@Setter
@ToString(of = "name")
@EqualsAndHashCode(of = "taskId")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "responsibleTasks")
    @JsonIgnore
    private List<User> responsibleUsers;

//    public User addResponsibleUser(User responsibleUser) {
//        responsibleUsers.add(responsibleUser);
//        return responsibleUser;
//    }
}
