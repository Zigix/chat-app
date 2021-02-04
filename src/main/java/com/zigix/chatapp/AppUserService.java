package com.zigix.chatapp;

import com.zigix.chatapp.entity.AppUser;
import com.zigix.chatapp.entity.AppUserRole;
import com.zigix.chatapp.registration.AppUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with email " + email + " not found"));
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByEmail(username);
    }


    public void signUpUser(AppUserDTO appUserDTO) {
        AppUser appUser = new AppUser();

        appUser.setEmail(appUserDTO.getEmail());
        appUser.setPassword(encoder.encode(appUserDTO.getPassword()));
        appUser.setAuthority(AppUserRole.USER);

        // TODO: send email with confirmation token

        appUserRepository.save(appUser);
    }
}
