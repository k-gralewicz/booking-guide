package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Role;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.RoleEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    private static final Logger LOGGER = Logger.getLogger((RoleMapper.class.getName()));
    private final ModelMapper modelMapper = new ModelMapper();

    public Role from(RoleEntity roleEntity) {
        LOGGER.info("from(" + roleEntity + ")");
        Role role = modelMapper.map(roleEntity, Role.class);
        LOGGER.info("from(...)= " + role);
        return role;
    }

    public RoleEntity from(Role role) {
        LOGGER.info("from(" + role + ")");
        RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);
        LOGGER.info("from(...)= " + roleEntity);
        return roleEntity;
    }

    public List<Role> fromEntities(List<RoleEntity> roleEntities){
        LOGGER.info("from()");
        List<Role> roles = roleEntities.stream().map(this::from).collect(Collectors.toList());
        LOGGER.info("from(...)= " + roles);
        return roles;
    }

    public List<RoleEntity> fromModels(List<Role> roles){
        LOGGER.info("from()");
        List<RoleEntity> roleEntities = roles.stream().map(this::from).collect(Collectors.toList());
        LOGGER.info("from(...)= " + roleEntities);
        return roleEntities;
    }
}
