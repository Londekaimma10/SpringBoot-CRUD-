package com.example.mySpringBootProject.Controller;

import com.example.mySpringBootProject.Model.AuthenticationRequest;
import com.example.mySpringBootProject.Model.AuthenticationResponse;
import com.example.mySpringBootProject.Repository.UsersRepository;
import com.example.mySpringBootProject.Services.JwtUtil;
import com.example.mySpringBootProject.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired //Bind repository with controller
    UsersRepository usersRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        //  try {
        boolean correctPassword = usersRepository.passwordIsCorrect(authenticationRequest.getPassword(), authenticationRequest.getUsername());

        if (correctPassword) {
            final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }
        else{
            return ResponseEntity.ok("Incorrect username or password");
        }
    }
}
