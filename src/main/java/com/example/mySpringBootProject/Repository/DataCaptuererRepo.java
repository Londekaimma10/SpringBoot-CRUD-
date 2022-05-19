package com.example.mySpringBootProject.Repository;

import com.example.mySpringBootProject.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataCaptuererRepo extends JpaRepository<Roles, Long> {
    @Query("select case when (count (r)>0) then true else false end from Roles r where r.roleId=?1")
    boolean roleExists(Long roleId);
}

