package com.aa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aa.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
