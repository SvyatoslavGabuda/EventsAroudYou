package it.epicode.eaw.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.eaw.auth.entity.ERole;
import it.epicode.eaw.auth.entity.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleName(ERole roleName);
}
