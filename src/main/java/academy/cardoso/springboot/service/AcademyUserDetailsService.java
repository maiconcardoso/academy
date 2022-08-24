package academy.cardoso.springboot.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import academy.cardoso.springboot.repository.AcademyUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcademyUserDetailsService implements UserDetailsService{

    private final AcademyUserRepository academyUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.of(academyUserRepository.findByUsername(username))
            .orElseThrow(() -> new UsernameNotFoundException("Academy User not found"));
    }
    
}
