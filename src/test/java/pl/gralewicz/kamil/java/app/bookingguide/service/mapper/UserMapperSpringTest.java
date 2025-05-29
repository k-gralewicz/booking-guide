package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.api.RoleType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Role;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserMapperSpringTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void from() {
        // given
        User user = new User();
        user.setRoleId(1L);

        // when
        UserEntity userEntity = userMapper.from(user);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(userEntity, "userEntity is null")
//                ()-> Assertions.assertNotNull(userEntity.getRoleId(), "userEntity roleId is null")
        );
    }

    @Test
    void fromEntities(){
        // given
        User user = new User();
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(RoleType.ADMIN));
        user.setRoles(roles);

        // when
        UserEntity userEntity = userMapper.from(user);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(userEntity, "userEntity is null"),
                ()-> Assertions.assertNotNull(userEntity.getRoles(), "userEntity roles is null"),
                ()-> Assertions.assertEquals(1, userEntity.getRoles().size(), "userEntity size is not equals")
        );

//        napisać test który sprawdzi czy dla jednego użytkownika dodaje więcej niż jedną rolę i sprawdza
//        czy role zostały przypisane z modelu na encję.
    }
}