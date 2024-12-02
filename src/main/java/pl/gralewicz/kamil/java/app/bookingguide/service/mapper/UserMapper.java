package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private static final Logger LOGGER = Logger.getLogger(UserMapper.class.getName());
    private final ModelMapper modelMapper = new ModelMapper();

    public User from(UserEntity userEntity) {
        LOGGER.info("from(" + userEntity + ")");
        User user = modelMapper.map(userEntity, User.class);
        LOGGER.info("from(...) = " + user);
        return user;
    }

    public UserEntity from(User user) {
        LOGGER.info("from(" + user + ")");
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        LOGGER.info("from(...) = " + userEntity);
        return userEntity;
    }

    public List<User> fromEntities(List<UserEntity> userEntities) {
        LOGGER.info("fromEntities()");
        List<User> users = userEntities.stream()
                .map(this::from)
                .collect(Collectors.toList());
        LOGGER.info("fromEntities(...)= " + users);
        return users;
    }

    public List<UserEntity> fromModels(List<User> users) {
        LOGGER.info("fromModels()");
        List<UserEntity> userEntities = users.stream()
                .map(this::from)
                .collect(Collectors.toList());
        LOGGER.info("fromModels(...)= " + userEntities);
        return userEntities;
    }
}