package com.example.mySpringBootProject.Controller;

import com.example.mySpringBootProject.Model.Users;
import com.example.mySpringBootProject.Repository.UsersRepository;
import com.example.mySpringBootProject.Services.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired //Bind repository with controller
    UsersRepository usersRepository;

    @Autowired
    private EmailSender emailSender;

    //Create
    @PostMapping("/create")
    public String createUser(@Valid @RequestBody Users users) throws MessagingException, IOException {
            boolean userExist = usersRepository.usersEmailAddressExists(users.getEmailAddress());
            if(userExist)
            {
                return "User Already Exist";
            }else {
                usersRepository.save(users);
                emailSender.sendmail(users.getEmailAddress(), users.getPassword());
                return "User successfully saved";
            }
    }

    //Read GetAll
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    //Read user by emailAddress
    @GetMapping("/users/{emailAddress}") //getOne user by email
    public Users getByEmail(@PathVariable String emailAddress){
        return usersRepository.findByEmailAddress(emailAddress);
    }

    //Update by emailAddress
    @PutMapping("update/{emailAddress}")
    public String updateUser(@PathVariable String emailAddress, @Valid @RequestBody Users user) {
        Users updatedUser = usersRepository.findByEmailAddress(emailAddress); //find the user
        try {
            updatedUser.setEmailAddress(user.getEmailAddress());//set user parameters
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPassword(user.getPassword());
            usersRepository.save(updatedUser);
            return "User successfully updated...";
        } catch (Exception e) {
            return ("User with email address already exists!");
        }
    }

    //Delete user by emailAddress
    @DeleteMapping("/delete/{emailAddress}") //delete (find user by email)
    public String deleteUserByEmail(@PathVariable String emailAddress) {                //@PathVariable indicates that a method parameter should be bound to a URI template variable
        Users deleteUser = usersRepository.findByEmailAddress(emailAddress);
        String msg;

        if (deleteUser != null) {
            usersRepository.delete(deleteUser);
            msg = "User has been successfully deleted";
        } else {
            msg = "User does not exist!";
        }

        return msg;
    }
}





