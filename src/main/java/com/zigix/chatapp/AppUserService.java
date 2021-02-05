package com.zigix.chatapp;

import com.zigix.chatapp.entity.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

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

    @Transactional
    public AppUser saveAppUser(AppUser appUser) {
        return appUserRepository.saveAndFlush(appUser);
    }

    @Transactional
    public int enableAppUserAccount(String email) {
        return appUserRepository.enableAppUserAccount(email);
    }
}
