package com.pavel.covhelper.security;

import com.pavel.covhelper.persistencelayer.entities.User;
import com.pavel.covhelper.persistencelayer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username);
       if (user == null){
           throw new UsernameNotFoundException(username + " not found");
       }
       return new SecurityUser(user.getUsername(),user.getPassword(), user.getDepartmentId(),
               List.of(new SimpleGrantedAuthority(user.getAuthority())));
    }



}
