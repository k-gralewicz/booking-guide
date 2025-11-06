package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;

import java.util.Optional;

@SpringBootTest
public class ShopRepositoryIntegrationTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

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

    @Test
    void createShopWithServices(){
        // given
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName("Salon");

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Masaż");

        serviceRepository.save(serviceEntity);
        shopEntity.addService(serviceEntity);

        // when
        ShopEntity createdShopEntity = shopRepository.save(shopEntity);
        Optional<ServiceEntity> optionalServiceEntity = serviceRepository.findById(createdShopEntity.getId());

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdShopEntity, "createdShopEntity is null"),
                () -> Assertions.assertTrue(optionalServiceEntity.isPresent(), "Shop not found in DB"),
                () -> Assertions.assertEquals("Salon", optionalServiceEntity.get().getName(), "Shop name mismatch")
        );

    }
}
