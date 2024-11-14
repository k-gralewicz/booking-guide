package pl.gralewicz.kamil.java.app.bookingguide.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;

import java.util.Collection;

@SpringBootTest
class BookingGuideUserDetailsServiceSpringTest {

    @Autowired
    private BookingGuideUserDetailsService bookingGuideUserDetailsService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Jacek");
        userEntity.setPassword("jacek");

        // when
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserDetails userDetails = bookingGuideUserDetailsService.loadUserByUsername("Jacek");
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        System.out.println(authorities);

        // then
        Assertions.assertAll(
                () -> Assert.notNull(userDetails, "UserDetails is null"),
                () -> Assertions.assertTrue(authorities.stream()
                                .allMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_USER")),
                        "All authorities should be either 'ROLE_ADMIN' or 'ROLE_USER'")
        );
    }
}