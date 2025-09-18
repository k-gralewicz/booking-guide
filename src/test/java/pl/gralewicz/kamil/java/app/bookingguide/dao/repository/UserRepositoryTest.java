package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.RoleEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void create() {
        // given
        UserEntity userEntity = new UserEntity();

        // when
        UserEntity savedUserEntity = userRepository.save(userEntity);

        // then
        Assertions.assertAll(
                ()-> Assert.notNull(savedUserEntity, "SavedUserEntity is null"),
                ()-> Assert.notNull(savedUserEntity.getId(), "SavedUserEntity ID is null")
        );
    }

    @Test
    void createWithRole(){
        // given
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity roleEntity = new RoleEntity();
        UserEntity userEntity = new UserEntity();

        // when
        RoleEntity savedRoleEntity = roleRepository.save(roleEntity);
        roles.add(savedRoleEntity);
        userEntity.setRoles(roles);
        UserEntity savedUserEntity = userRepository.save(userEntity);


        // then
        Assertions.assertAll(
                ()-> Assert.notNull(savedUserEntity, "SavedUserEntity is null"),
                ()-> Assert.notNull(savedUserEntity.getId(), "SavedUserEntity ID is null"),
                ()-> Assert.notNull(savedUserEntity.getRoles().iterator(), "SavedUserEntity Role ID is null")
        );
    }
}