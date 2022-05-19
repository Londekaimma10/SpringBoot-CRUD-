package com.example.mySpringBootProject.Repository;

//import com.example.mySpringBootProject.Model.UserPK;
import com.example.mySpringBootProject.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
      Users findByEmailAddress(String emailAddress2);

      @Query("select case when (count (u)>0) then true else false end from Users u where u.emailAddress=?1")
      boolean usersEmailAddressExists(String emailAddress2);
      Optional<Users> findUserById(Long id); //Optional is used in case it doesn't find the user
      void  deleteUserById(Long id);
      @Query("SELECT u FROM Users u WHERE  u.emailAddress=?1")
      Users getUserByUsername(String username);
      @Query("select case when (count (u)>0) then true else false end from Users u where u.password=?1 AND u.emailAddress=?2")
      boolean passwordIsCorrect(String password, String username);

}


