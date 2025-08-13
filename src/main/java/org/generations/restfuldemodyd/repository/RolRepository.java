package org.generations.restfuldemodyd.repository;

import org.generations.restfuldemodyd.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String nombre);
}
