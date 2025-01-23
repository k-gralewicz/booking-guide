package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;

class UserMapperTest {

    @Test
    void from() {
        // given
        UserMapper userMapper = new UserMapper();
        User user = new User();

        // when
        UserEntity userEntity = userMapper.from(user);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(userEntity, "userEntity is null")
        );
    }

    @Test
    void fromWithPassword (){
        // given
        UserMapper userMapper = new UserMapper();
        User user = new User();
        user.setPassword("password");
        // when
        UserEntity userEntity = userMapper.from(user);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(userEntity, "userEntity is null"),
                ()-> Assertions.assertEquals(userEntity.getPassword(), "password", "userEntity password is not equals")
        );

    }
}