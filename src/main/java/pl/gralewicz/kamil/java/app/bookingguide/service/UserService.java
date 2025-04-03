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
import java.util.NoSuchElementException;
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

    public User findByUsername(String username) {
        LOGGER.info("findByUsername(" + username + ")");
        UserEntity userByUsername = userRepository.findByUsername(username);
        LOGGER.info("userByUsername(" + userByUsername + ")");
        User user = userMapper.from(userByUsername);
        LOGGER.info("findByUsername(...)=" + user);
        return user;
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

    public User updateUser(Long id, User updatedUserDto) { // Zmieniono nazwę parametru DTO dla jasności
        LOGGER.info("updateUser(" + id + ", " + updatedUserDto + ") using manual field update");

        // 1. Znajdź istniejącą encję w bazie lub rzuć wyjątek
        UserEntity existingUserEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)); // Można użyć bardziej specyficznego wyjątku

        // 2. Ręcznie zaktualizuj pola istniejącej encji wartościami z DTO
        existingUserEntity.setUsername(updatedUserDto.getUsername());
        existingUserEntity.setEmail(updatedUserDto.getEmail());

        // 3. Obsługa aktualizacji hasła (tylko jeśli nowe hasło zostało podane w DTO)
        //    Zakładamy, że kontroler przekazuje już ZAKODOWANE hasło w DTO, jeśli ma być zmienione.
        //    Jeśli kodowanie ma się odbyć tutaj, musisz wstrzyknąć PasswordEncoder.
        if (updatedUserDto.getPassword() != null && !updatedUserDto.getPassword().isEmpty() && !updatedUserDto.getPassword().isBlank()) {
            LOGGER.info("Updating password for user ID: " + id);
            existingUserEntity.setPassword(updatedUserDto.getPassword()); // Ustawiamy już zakodowane hasło
        } else {
            LOGGER.info("Password not provided in update DTO, keeping existing password for user ID: " + id);
            // Nie robimy nic - istniejące hasło pozostaje
        }

        // 4. Obsługa aktualizacji roli (tylko jeśli roleId zostało podane w DTO)
        //    Zakładamy, że użytkownik ma jedną rolę (lub chcemy zastąpić wszystkie jedną nową).
        if (updatedUserDto.getRoleId() != null) {
            LOGGER.info("Updating role for user ID: " + id + " to role ID: " + updatedUserDto.getRoleId());
            // Znajdź encję nowej roli
            RoleEntity roleEntity = roleRepository.findById(updatedUserDto.getRoleId())
                    .orElseThrow(() -> new NoSuchElementException("Role not found with id: " + updatedUserDto.getRoleId()));

            // Zaktualizuj kolekcję ról w encji użytkownika
            // Najprostsze podejście dla pojedynczej roli: wyczyść stare, dodaj nową
            // Upewnij się, że getRoles() zwraca modyfikowalną kolekcję (np. zainicjalizowaną jako new ArrayList<>() w UserEntity)
            existingUserEntity.getRoles().clear();
            existingUserEntity.addRole(roleEntity); // Zakładamy, że UserEntity ma metodę addRole(RoleEntity)
        } else {
            LOGGER.info("Role ID not provided in update DTO, keeping existing roles for user ID: " + id);
            // Nie robimy nic z rolami
        }

        // 5. Zapisz zaktualizowaną encję (teraz `existingUserEntity` zawiera zmiany)
        UserEntity savedUserEntity = userRepository.save(existingUserEntity);

        // 6. Zmapuj zapisaną encję z powrotem na DTO
        User savedUser = userMapper.from(savedUserEntity);
        LOGGER.info("updateUser(...) updated and saved: " + savedUser);
        return savedUser;
    }

    public User read(Long id) {
        LOGGER.info("read(" + id + ")");
        UserEntity readUserEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id)); // Rzuca wyjątek, gdy Optional jest pusty
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
}