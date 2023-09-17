package max.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import max.example.entities.Role;
import max.example.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
