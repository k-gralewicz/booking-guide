package pl.gralewicz.kamil.java.app.bookingguide.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;

import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class BookingGuideUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(BookingGuideUserDetailsService.class.getName());

    @Autowired

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        String[] roles = userEntity.getRoles().stream()
                .map(role -> role.getName().name()).toList()
                .toArray(new String[0]);
        LOGGER.info("####roles" + Arrays.toString(roles));

        UserDetails userDetails = User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(roles)
                .build();

        return userDetails;
    }
}
