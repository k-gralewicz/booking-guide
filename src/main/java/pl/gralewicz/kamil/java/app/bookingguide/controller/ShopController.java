package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.service.ShopService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/shops")
//@SessionAttributes("SHOP_ID_SESSION")
public class ShopController {
    private static Logger LOGGER = Logger.getLogger(ShopController.class.getName());

    public ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public String list(String name, ModelMap modelMap) {
        LOGGER.info("list(" + name + ")");
        List<Shop> shops = shopService.list();
        modelMap.addAttribute("shops", shops);
        LOGGER.info("list(...)= ");
        return "shop-dashboard";
    }

    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("details(" + id + ")");
//        modelMap.addAttribute("SHOP_ID_SESSION", id);
        modelMap.addAttribute("shopId", id);
        LOGGER.info("details(...)= ");
        return "shop-details";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap){
        LOGGER.info("createView()");

        modelMap.addAttribute("createMassage", "Fill out the form fields");
        modelMap.addAttribute("shop", new Shop());
        LOGGER.info("createView(...)= ");
        return "shop-create";
    }

    @PostMapping
    public String create(@ModelAttribute Shop shop) {
        LOGGER.info("create(" + shop + ")");
        Shop createdShop = shopService.create(shop);
        LOGGER.info("create(...)= " + createdShop);
        return "redirect:/shops";
    }

    @GetMapping(value = "/clients/{id}")
    public String clients(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("clients(" + id + ")");
//        Object shopIdSession = modelMap.getAttribute("SHOP_ID_SESSION");
//        LOGGER.info("shopIdSession: " + shopIdSession);
        return "shop-details";
    }
}

// TODO: 21.08.2024 co to są servlety, przeczytać, nauczyć się