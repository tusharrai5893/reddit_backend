package com.reddit.backend.service;

import com.reddit.backend.models.User;
import com.reddit.backend.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> singleUser = userRepo.findByUserName(userName);
        User user1 = singleUser.orElseThrow(() -> new UsernameNotFoundException("No User found for Username : " + userName));

        return (UserDetails) new UserDetailsImpl(user1);
    }

}
