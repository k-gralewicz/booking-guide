package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.RoleEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.RoleRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.RoleMapper;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.UserMapper;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ShopRepository shopRepository;
    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private ShopMapper shopMapper;
    private ShopService shopService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ShopRepository shopRepository, UserMapper userMapper, RoleMapper roleMapper, ShopMapper shopMapper, ShopService shopService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.shopRepository = shopRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.shopMapper = shopMapper;
        this.shopService = shopService;
    }

    public List<User> list() {
        LOGGER.info("list()");
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> users = userEntities.stream().map(userMapper::from).collect(Collectors.toList());
        LOGGER.info("list(...)= " + users);
        return users;
    }

    @Transactional
    public User findByUsername(String username) {
        LOGGER.info("findByUsername(" + username + ")");
        List<UserEntity> byUsernameIgnoreCase = userRepository.findByUsernameIgnoreCase(username);
        LOGGER.info("byUsernameIgnoreCase: " + byUsernameIgnoreCase);
        UserEntity userByUsername = userRepository.findByUsername(username);
        LOGGER.info("userByUsername: " + userByUsername);
        User user = userMapper.from(userByUsername);
        LOGGER.info("findByUsername(...)=" + user);
        return user;
    }

    public User create(User user) {
        LOGGER.info("create(" + user + ")");
        UserEntity userEntity = userMapper.from(user);

        if (user.getRoleId() != null) {
            RoleEntity roleEntity = roleRepository.findById(user.getRoleId())
                    .orElseThrow(() -> new NoSuchElementException("Role not found"));
            userEntity.addRole(roleEntity);
        }

        if (user.getShopId() != null) {
            ShopEntity shopEntity = shopRepository.findById(user.getShopId())
                    .orElseThrow(() -> new NoSuchElementException("Shop not found"));
            userEntity.addShop(shopEntity);
        }

        UserEntity saved = userRepository.save(userEntity);
        return userMapper.from(saved);
    }

    @Transactional
    public User updateUser(Long userId, Long shopId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        UserEntity userEntity = optionalUserEntity.orElseThrow(
                () -> new NoSuchElementException("User not found"));

        Optional<ShopEntity> optionalShopEntity = shopRepository.findById(shopId);
        ShopEntity shopEntity = optionalShopEntity.orElseThrow(
                () -> new NoSuchElementException("Shop not found"));

        userEntity.addShop(shopEntity);
        User user = userMapper.from(userEntity);
        return user;
    }

    public User updateUser(Long id, User updatedUser) {
        LOGGER.info("updateUser(" + id + ", " + updatedUser + ")");
//        UserEntity existingUser = userRepository.findById(id).orElseThrow(
//                () -> new RuntimeException("User not found"));
        
        UserEntity userEntity = userMapper.from(updatedUser);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        User savedUser = userMapper.from(savedUserEntity);
        LOGGER.info("updateUser(...)= " + savedUser);
        return savedUser;
    }

    public User read(Long id) {
        LOGGER.info("read(" + id + ")");
        UserEntity readUserEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        User readUser = userMapper.from(readUserEntity);
        LOGGER.info("read(...)= " + readUser);
        return readUser;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        if (!userRepository.existsById(id)) {
            LOGGER.warning("Attempt to delete non-existent user with ID: " + id);
            return;
        }
        userRepository.deleteById(id);
        LOGGER.info("delete(...) completed for ID: " + id);
    }

    public Set<Shop> getShopsForUser(String username) {
        LOGGER.info("getShopsForUser(" + username + ")");
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            Set<ShopEntity> shopEntities = userEntity.getShops();
            Set<Shop> shops = shopMapper.fromEntities(shopEntities);
            LOGGER.info("getShopsForUser(...)= " + shops);
            return shops;
        }
        return new HashSet<>();
    }

    @Transactional
    public void assignShopToUser(Long userId, Long shopId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        ShopEntity shopEntity = shopRepository.findById(shopId)
                .orElseThrow(() -> new NoSuchElementException("Shop not found with id: " + shopId));

        if (!userEntity.getShops().contains(shopEntity)) {
            userEntity.addShop(shopEntity);
            userRepository.save(userEntity);
        }
    }
}