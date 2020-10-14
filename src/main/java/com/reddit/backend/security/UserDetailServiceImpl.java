package com.reddit.backend.security;

import com.reddit.backend.models.User;
import com.reddit.backend.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> singleUser = userRepo.findByUserName(userName);
        User user1 = singleUser.orElseThrow(() -> new UsernameNotFoundException("No User found for Username : " + userName));


        return new UserDetailsImpl(user1);

    }

}
