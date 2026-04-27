package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;

import java.util.List;

@SpringBootTest
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Roman");

        // when
        UserEntity createdUserEntity = userRepository.save(userEntity);
        UserEntity foundUserEntity = userRepository.findByUsername(createdUserEntity.getUsername());

        // then
        Assertions.assertAll(
                ()-> Assert.notNull(foundUserEntity, "FoundUserEntity is null")
        );
    }

    @Test
    void findShopsByUserName(){
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Franek");

        // when
        UserEntity createdUserEntity = userRepository.save(userEntity);
        List<ShopEntity> foundUserEntity = userRepository.findShopsByUsernameIgnoreCase(createdUserEntity.getUsername());

        // then
        Assertions.assertAll(
                ()-> Assert.notNull(foundUserEntity, "FoundUserEntity is null")
        );
    }
}