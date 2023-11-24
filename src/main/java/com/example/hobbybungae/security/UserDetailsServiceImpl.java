package com.example.hobbybungae.security;

import com.example.hobbybungae.domain.user.UserRepository;
import com.example.hobbybungae.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String idName) throws UsernameNotFoundException {
        User user = userRepository.findByIdName(idName)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + idName));

        return new UserDetailsImpl(user);
    }
}
