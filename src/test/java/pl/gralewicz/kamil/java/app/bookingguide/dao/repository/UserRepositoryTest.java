package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

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
}