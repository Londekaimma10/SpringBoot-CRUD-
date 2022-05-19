package com.example.mySpringBootProject.Controller;

import com.example.mySpringBootProject.Model.Roles;
import com.example.mySpringBootProject.Repository.DataCaptuererRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataCapturerController {
    @Autowired
    DataCaptuererRepo dataCaptuererRepo;

    @PostMapping("/createRole")
    public String createRole(@Valid @RequestBody Roles roles) {
        boolean userExist = dataCaptuererRepo.roleExists(roles.getRoleId());
        if(userExist)
        {
            return "Role Already Exist";
        }else {
            dataCaptuererRepo.save(roles);
            return "Role successfully saved";
        }
    }

    //Read GetAll
    @GetMapping("/roles")
    public List<Roles> getAllRoles() {
        return dataCaptuererRepo.findAll();
    }

    //Read user by role id
    @GetMapping("/roles/{roleId}") //getOne user by email
    public Roles getByRoleId(@PathVariable Long roleId){
        return dataCaptuererRepo.findById(roleId).get();

    }
}
