package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.api.RoleType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Role;

import java.util.List;

@SpringBootTest
class RoleServiceSpringIntegrationTest {

    @Autowired
    private RoleService roleService;

    @Test
    void list() {
        // given
        Role role = new Role(RoleType.ADMIN);

        // when
        Role createdRole = roleService.create(role);
        List<Role> roles = roleService.list();

        // then
        Assertions.assertAll(
                () ->Assertions.assertNotNull(roles, "roles is null"),
                () ->Assertions.assertEquals(1,roles.size(), "roles is not equals")
        );
    }
}