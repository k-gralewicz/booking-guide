package pl.gralewicz.kamil.java.app.bookingguide.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;

import java.util.logging.Logger;

@Service
public class BookingGuideUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(BookingGuideUserDetailsService.class.getName());

    @Autowired

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);
        if (username == null) {
            throw new UsernameNotFoundException(username);
        }

        UserDetails userDetails = User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles("ADMIN", "USER")
                .build();

        return userDetails;
    }
}
