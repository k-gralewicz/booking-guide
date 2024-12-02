package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.UserMapper;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<User> list() {
        LOGGER.info("list()");
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> users = userEntities.stream().map(userMapper::from).collect(Collectors.toList());
        LOGGER.info("list(...)= " + users);
        return users;
    }

    public User saveUser(User user) {
        LOGGER.info("saveUser(" + user + ")");
        UserEntity userEntity = userMapper.from(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        User savedUser = userMapper.from(savedUserEntity);
        LOGGER.info("saveUser(...)= " + savedUser);
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