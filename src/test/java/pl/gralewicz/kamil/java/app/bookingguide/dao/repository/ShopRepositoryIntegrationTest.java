package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;

@SpringBootTest
public class ShopRepositoryIntegrationTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void create(){
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Ala");

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName("Salon");

        // when
        UserEntity createdUserEntity = userRepository.save(userEntity);
        shopEntity.addUser(createdUserEntity);
        ShopEntity createdShopEntity = shopRepository.save(shopEntity);

        // then
        Assertions.assertAll(
                ()-> Assert.notNull(createdShopEntity, "CreatedShopEntity is null")
        );
    }

    @Test
    void createdWithTwoUsers(){
        // given
        UserEntity userEntityOne = new UserEntity();
        userEntityOne.setUsername("Ola");
        UserEntity userEntityTwo = new UserEntity();
        userEntityTwo.setUsername("Katarzyna");

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName("Studio");

        // when
        UserEntity createdUserEntityOne = userRepository.save(userEntityOne);
        UserEntity createdUserEntityTwo = userRepository.save(userEntityTwo);
        shopEntity.addUser(createdUserEntityOne);
        shopEntity.addUser(createdUserEntityTwo);
        ShopEntity createdShopEntity = shopRepository.save(shopEntity);
        int size = createdShopEntity.getUsers().size();

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(createdShopEntity, "createdShopEntity is null"),
                ()->Assertions.assertEquals(2, size, "createdShopEntity is not equals")
        );
    }
}
