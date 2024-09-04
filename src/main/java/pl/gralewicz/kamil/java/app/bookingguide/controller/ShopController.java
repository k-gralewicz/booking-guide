package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.service.ShopService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/shops")
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

        LOGGER.info("details(...)= ");
        return "shop-details";
    }
}

// TODO: 21.08.2024 co to są servlety, przeczytać, nauczyć się