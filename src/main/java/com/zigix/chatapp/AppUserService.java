package com.zigix.chatapp;

import com.zigix.chatapp.entity.AppUser;
import com.zigix.chatapp.entity.AppUserRole;
import com.zigix.chatapp.registration.token.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with username " + username + " not found"));
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Transactional
    public AppUser saveAppUser(AppUser appUser) {
        return appUserRepository.saveAndFlush(appUser);
    }

    @Transactional
    public int confirmUserEmail(String email) {
        return appUserRepository.confirmUserEmail(email);
    }
}
