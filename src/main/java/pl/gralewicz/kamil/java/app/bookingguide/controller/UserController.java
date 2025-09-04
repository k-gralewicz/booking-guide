package pl.gralewicz.kamil.java.app.bookingguide.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.service.RoleService;
import pl.gralewicz.kamil.java.app.bookingguide.service.ShopService;
import pl.gralewicz.kamil.java.app.bookingguide.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ShopService shopService;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, ShopService shopService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.shopService = shopService;
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
        Set<Shop> shops = shopService.list();
        modelMap.addAttribute("shops", shops);
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

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute User user) {
        LOGGER.info("update(" + id + ", " + user + ")");
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            User existingUser = userService.read(id);
            user.setPassword(existingUser.getPassword());
        }
//        User updatedUser = userService.updateUser(id, user);
//        LOGGER.info("update(...)= " + updatedUser);
        return "redirect:/users";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("delete(" + id + ")");
        userService.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/dashboard")
    public String dashboard(ModelMap modelMap, HttpSession session) {
        LOGGER.info("dashboard()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        LOGGER.info("username= " + username);

        Set<Shop> assignedShops = userService.getShopsForUser(username);
        Set<Shop> allShops = shopService.list();

        List<Shop> unassignedShops = allShops.stream()
                .filter(shop -> !assignedShops.contains(shop))
                .toList();

        modelMap.addAttribute("shops", assignedShops);
        modelMap.addAttribute("unassignedShops", unassignedShops);

        Shop selectedShop = (Shop) session.getAttribute("selectedShop");
        modelMap.addAttribute("selectedShop", selectedShop);

        LOGGER.info("dashboard(...)");
        return "user-dashboard.html";
    }

    @PostMapping("/shops/assign")
    public String assignShopToUser(@RequestParam Long shopId) {
        LOGGER.info("assignShopToUser(" + shopId + ")");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new NoSuchElementException("Nie znaleziono użytkownika o nazwie: " + username);
        }
        userService.updateUser(user.getId(), shopId);

        LOGGER.info("assignShopToUser(...) completed");
        return "redirect:/users/dashboard";
    }
    @PostMapping("/shops/select")
    public String selectShop(@RequestParam Long shopId, HttpSession session) {
        Shop selectedShop = shopService.read(shopId);
        session.setAttribute("selectedShop", selectedShop);
        return "redirect:/users/dashboard";
    }
}