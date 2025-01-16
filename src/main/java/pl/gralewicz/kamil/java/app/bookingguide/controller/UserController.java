package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.service.RoleService;
import pl.gralewicz.kamil.java.app.bookingguide.service.UserService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String list(ModelMap modelMap) {
        LOGGER.info("list()");
        List<User> users = userService.list();
        modelMap.addAttribute("users", users);
        LOGGER.info("list(...)= " + users);
        return "users";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("createMessage", "Fill out the form fields");
        modelMap.addAttribute("user", new User());
        modelMap.addAttribute("isEdit", false);
        modelMap.addAttribute("roles", roleService.list());
        LOGGER.info("createView(...)= ");
        return "user-create.html";
    }

    @PostMapping
    public String create(@ModelAttribute User user) {
        LOGGER.info("create(" + user + ")");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userService.create(user);
        LOGGER.info("create(...)= " + createdUser);
        return "redirect:/users";
    }

    @GetMapping(value = "/{id}")
    public String read(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
        User readUser = userService.read(id);
        modelMap.addAttribute("createMessage", "This is user: " + readUser);
        boolean isEdit = true;
        modelMap.addAttribute("isEdit", isEdit);
        LOGGER.info("read(...)= ");
        return "user-read";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("updateView()");
        User readUser = userService.read(id);
        modelMap.addAttribute("user", readUser);
        modelMap.addAttribute("isEdit", true);
        modelMap.addAttribute("roles", roleService.list());
        LOGGER.info("updateView(...)= " + readUser);
        return "user-create.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("delete(" + id + ")");
        userService.delete(id);
        return "redirect:/users";
    }
}