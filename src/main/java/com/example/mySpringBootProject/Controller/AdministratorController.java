package com.example.mySpringBootProject.Controller;

import com.example.mySpringBootProject.Model.Roles;
import com.example.mySpringBootProject.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class AdministratorController{

    @Autowired
    RolesRepository rolesRepository;

    //Create
    @PostMapping("/createRole")
    public String createRole(@Valid @RequestBody Roles roles) {
        boolean userExist = rolesRepository.roleExists(roles.getRoleId());
        if(userExist)
        {
            return "Role Already Exist";
        }else {
            rolesRepository.save(roles);
            return "Role successfully saved";
        }
    }

    //Read GetAll
    @GetMapping("/roles")
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    //Read user by role id
    @GetMapping("/roles/{roleId}") //getOne user by email
    public Roles getByRoleId(@PathVariable Long roleId){
        return rolesRepository.findById(roleId).get();

    }

    //Update by emailAddress
    @PutMapping("updateRole/{roleId}")
    public String updateRole(@PathVariable Long roleId, @Valid @RequestBody Roles roles) {
        Roles updatedRole = rolesRepository.findById(roleId).get(); //find the role
        try {
            updatedRole.setRoleId(roles.getRoleId());
            updatedRole.setRoleName(roles.getRoleName());//set user parameters
            rolesRepository.save(updatedRole);
            return "Role successfully updated...";
        } catch (Exception e) {
            return ("This role does not already exists!");
        }
    }

    //Delete user by role id
    @DeleteMapping("/deleteRole/{roleId}") //delete (find role by role id)
    public String deleteRoleById(@PathVariable Long roleId) { //@PathVariable indicates that a method parameter should be bound to a URI template variable
        Roles deleteRole = rolesRepository.findById(roleId).get();
        String msg;

        if (deleteRole != null) {
            rolesRepository.delete(deleteRole);
            msg = "Role has been successfully deleted";
        } else {
            msg = "Role does not exist!";
        }

        return msg;
    }
}
