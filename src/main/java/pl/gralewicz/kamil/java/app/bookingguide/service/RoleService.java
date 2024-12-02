package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Role;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.RoleEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.RoleRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.RoleMapper;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private static final Logger LOGGER = Logger.getLogger((RoleService.class.getName()));

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> list() {
        LOGGER.info("list()");
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<Role> roles = roleEntities.stream().map(roleMapper::from).collect(Collectors.toList());
        LOGGER.info("list(...)= " + roles);
        return roles;
    }

    public Role create(Role role) {
        LOGGER.info("create()");
        RoleEntity roleEntity = roleMapper.from(role);
        RoleEntity savedRoleEntity = roleRepository.save(roleEntity);
        Role savedRole = roleMapper.from(savedRoleEntity);
        LOGGER.info("create(...)= " + savedRole);
        return savedRole;
    }

    public Role read(Long id) {
        LOGGER.info("read(" + id + ")");
        RoleEntity readRoleEntity = roleRepository.findById(id).orElseThrow(null);
        Role readRole = roleMapper.from(readRoleEntity);
        LOGGER.info("read(...)= " + readRole);
        return readRole;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        roleRepository.deleteById(id);
        LOGGER.info("delete(...)= ");
    }
}
