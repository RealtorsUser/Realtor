package com.rtp.Realestate.Service;

import com.rtp.Realestate.Model.CustomUserDetails;
import com.rtp.Realestate.Model.RealtorUser;
import com.rtp.Realestate.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealtorUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(RealtorUser realtorUser) {
        realtorUser.setPassword(passwordEncoder.encode(realtorUser.getPassword()));
        userRepository.save(realtorUser);  // corrected here
    }

    public RealtorUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<RealtorUser> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RealtorUser user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}
