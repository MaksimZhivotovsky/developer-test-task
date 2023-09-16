package max.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import max.example.entities.Role;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
}
