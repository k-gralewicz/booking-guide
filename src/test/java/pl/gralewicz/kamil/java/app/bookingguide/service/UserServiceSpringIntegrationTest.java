package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.api.RoleType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Role;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;

import java.util.List;

//@Transactional
@SpringBootTest
class UserServiceSpringIntegrationTest {

   @Autowired
   private UserService userService;

   @Autowired
   private RoleService roleService;

   @Autowired
   private ShopService shopService;

    @Test
    void create() {
        // given
        User user = new User();

        // when
        User createdUser = userService.create(user);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(createdUser, "createdUser is null"),
                ()-> Assertions.assertNotNull(createdUser.getId(), "cretedUser id is null")
        );
    }



    @Test
    void createWithRole () {
        // given
        User user = new User();
        List<Role> roles = roleService.list();
        Role role = roles.get(0);
        user.setRoleId(role.getId());

        int size = roles.size();

        // when
        User createdUser = userService.create(user);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(createdUser, "createdUser is null"),
                ()-> Assertions.assertEquals(1, createdUser.getRoles().size(), "roles is not equals")
        );
    }

    @Test
    void createWithShop (){
        // given
        User user = new User();
        Shop shop = new Shop();
        shop.setName("Salon");
        user.getShops().add(shop);

        // when
        User createdUser = userService.create(user);

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(createdUser, "createdUser is null")
        );

    }

    @Test
    void createWithShops (){
        // given
        User user = new User();
        Shop shop = new Shop();
        shop.setName("Salon");
        Shop secondShop = new Shop();
        secondShop.setName("Studio");
        user.getShops().add(shop);
        user.getShops().add(secondShop);

        // when
        User createdUser = userService.create(user);

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(createdUser, "createdUser is null"),
                ()->Assertions.assertEquals(2, createdUser.getShops().size(), "createdUser is not equals")
        );

    }

    @Test
    void createWithPassword (){
        // given
        User user = new User();
        List<Role> roles = roleService.list();
        Role role = roles.get(0);
        user.setRoleId(role.getId());
        user.setPassword("password");

        // when
        User createdUser = userService.create(user);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(createdUser, "createdUser is null"),
                ()-> Assertions.assertEquals("password",createdUser.getPassword(), "createdUser password is not equals")
        );
    }

    @Test
    void  findByUsernameTest(){
        // given
        User user = new User();
        user.setUsername("Katarzyna");

        // when
        User createdUser = userService.create(user);
        User foundUser = userService.findByUsername(createdUser.getUsername());

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(foundUser, "foundUser is null")
        );
    }

    @Test
    void  findByUsernameWithRolesTest(){
        // given
        User user = new User();
        user.setUsername("Katarzyna");
        Role role = new Role();
        role.setName(RoleType.CLIENT);
        Role createdRole = roleService.create(role);
        user.setRoles(List.of(createdRole));
        user.setRoleId(createdRole.getId());

        // when
        User createdUser = userService.create(user);
        User foundUser = userService.findByUsername(createdUser.getUsername());

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(foundUser, "foundUser is null")
        );

    }

    @Test
    void getShopsForUser(){
        // given
        User user = new User();
        user.setUsername("Franek");
        Shop firstShop = new Shop();
        firstShop.setName("Akademia Piękna");
        Shop secondShop = new Shop();
        secondShop.setName("Studio Wizażu");



        // when
        Shop createdFirstShop = shopService.create(firstShop);
        Shop createdSecondShop = shopService.create(secondShop);
        user.getShops().add(createdFirstShop);
        user.getShops().add(createdSecondShop);

        User createdUser = userService.create(user);

        List<Shop> shopsForUser = userService.getShopsForUser(createdFirstShop.getName());

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(shopsForUser, "shopsForUser is null"),
                ()->Assertions.assertEquals(2, createdUser.getShops().size(), "createdUser is not equals")
        );
    }
}