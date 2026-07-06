package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private static final Logger LOGGER = Logger.getLogger(UserMapper.class.getName());
    private final ModelMapper modelMapper = new ModelMapper();

    public User from(UserEntity userEntity) {
        LOGGER.info("from(" + userEntity + ")");
        if (userEntity == null) {
            return null;
        }

        User user = modelMapper.map(userEntity, User.class);

        // Jawne mapowanie relacji w stronę Modelu:
        if (userEntity.getClient() != null) {
            user.setClient(modelMapper.map(userEntity.getClient(), Client.class));
        } else {
            user.setClient(null);
        }

        LOGGER.info("from(...) = " + user);
        return user;
    }

    public UserEntity from(User user) {
        LOGGER.info("from(" + user + ")");
        if (user == null) {
            return null;
        }

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        // Jawne mapowanie relacji w stronę Encji (zapobiega gubieniu klienta przez automatyczny ModelMapper):
        if (user.getClient() != null) {
            ClientEntity clientEntity = modelMapper.map(user.getClient(), ClientEntity.class);
            userEntity.setClient(clientEntity);
        } else {
            userEntity.setClient(null);
        }

        LOGGER.info("from(...) = " + userEntity);
        return userEntity;
    }

    public List<User> fromEntities(List<UserEntity> userEntities) {
        LOGGER.info("fromEntities()");
        if (userEntities == null) {
            return List.of();
        }
        List<User> users = userEntities.stream()
                .map(this::from)
                .collect(Collectors.toList());
        LOGGER.info("fromEntities(...)= " + users);
        return users;
    }

    public List<UserEntity> fromModels(List<User> users) {
        LOGGER.info("fromModels()");
        if (users == null) {
            return List.of();
        }
        List<UserEntity> userEntities = users.stream()
                .map(this::from)
                .collect(Collectors.toList());
        LOGGER.info("fromModels(...)= " + userEntities);
        return userEntities;
    }
}