package pl.gralewicz.kamil.java.app.bookingguide.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.api.RoleType;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.RoleEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        userEntity.setUsername("Jacek3");
        userEntity.setPassword("jacek3");

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

    @Test
    @Transactional
    void loadUserByUsernameWithRole() {
        // given
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(RoleType.USER);

        RoleEntity secondRoleEntity = new RoleEntity();
        secondRoleEntity.setName(RoleType.ADMIN);

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleEntity);
        roles.add(secondRoleEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Janek2");
        userEntity.setPassword("janek2");
        userEntity.setRoles(roles);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        String username = savedUserEntity.getUsername();

        // when
        UserDetails userDetails = bookingGuideUserDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // then
    }
}