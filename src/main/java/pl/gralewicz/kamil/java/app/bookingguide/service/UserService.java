package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.RoleEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.RoleRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.RoleMapper;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Autowired
    private UserMapper userMapper;
    private RoleMapper roleMapper;

    public List<User> list() {
        LOGGER.info("list()");
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> users = userEntities.stream().map(userMapper::from).collect(Collectors.toList());
        LOGGER.info("list(...)= " + users);
        return users;
    }

    public User create(User user) {
        LOGGER.info("create(" + user + ")");
        Long userRoleId = user.getRoleId();
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(userRoleId);
        RoleEntity roleEntity = optionalRoleEntity.orElseThrow();
        LOGGER.info("create(...)= " + optionalRoleEntity);
        UserEntity userEntity = userMapper.from(user);
        userEntity.addRole(roleEntity);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        User savedUser = userMapper.from(savedUserEntity);
        LOGGER.info("create(...)= " + savedUser);
        return savedUser;
    }

    public User updateUser(Long id, User updatedUser) {
        LOGGER.info("updateUser(" + id + ", " + updatedUser + ")");
        UserEntity existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        UserEntity userEntity = userMapper.from(updatedUser);

        UserEntity savedUserEntity = userRepository.save(existingUser);
        User savedUser = userMapper.from(savedUserEntity);
        LOGGER.info("updateUser(...)= " + savedUser);
        return savedUser;
    }

    public User read(Long id) {
        LOGGER.info("read(" + id + ")");
        UserEntity readUserEntity = userRepository.findById(id).orElse(null);
        User readUser = userMapper.from(readUserEntity);
        LOGGER.info("read(...)= " + readUser);
        return readUser;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        userRepository.deleteById(id);
        LOGGER.info("delete(...)= ");
    }
}