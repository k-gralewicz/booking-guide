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

import java.util.Set;
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
        Set<Shop> shops = shopService.list();
        modelMap.addAttribute("shops", shops);
        LOGGER.info("list(...)= ");
        return "shop-dashboard";
    }

    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("details(" + id + ")");
//        modelMap.addAttribute("SHOP_ID_SESSION", id);
//        na podstawie Long id pobrać szczegóły sklepu
//        ze szczegółow sklepu pobrać nazwę i wstawić jako atrybut na widok
        modelMap.addAttribute("shopId", id);
        Shop readShop = shopService.read(id);
        String readShopName = readShop.getName();
        modelMap.addAttribute("shopName", readShopName);
        LOGGER.info("details(...)= ");
        return "shop-details";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("createMassage", "Fill out the form fields");
        modelMap.addAttribute("shop", new Shop());
        modelMap.addAttribute("isEdit", false);
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

    @GetMapping(value = "/{id}")
//    public String read(@PathVariable Long id, String name, String description, String phoneNumber, String address, ModelMap modelMap) {
    public String read(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
//        LOGGER.info("read(" + name + ")");
//        LOGGER.info("read(" + description + ")");
//        LOGGER.info("read(" + phoneNumber + ")");
//        LOGGER.info("read(" + address + ")");
        Shop readShop = shopService.read(id);
        modelMap.addAttribute("shop", readShop);
        modelMap.addAttribute("createMessage", "This is shop: " + readShop);
        boolean isEdit = true;
        modelMap.addAttribute("isEdit", isEdit);
        LOGGER.info("read(...)= ");
        return "shop-read.html";
    }

    @GetMapping(value = "/clients/{id}")
    public String clients(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("clients(" + id + ")");
//        Object shopIdSession = modelMap.getAttribute("SHOP_ID_SESSION");
//        LOGGER.info("shopIdSession: " + shopIdSession);
        return "shop-details";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("updateView()");
        Shop readShop = shopService.read(id);
        modelMap.addAttribute("shop", readShop);
        modelMap.addAttribute("isEdit", true);
        LOGGER.info("updateView(...)= " + readShop);
        return "shop-create";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Shop shop) {
        LOGGER.info("update(" + id + "," + shop + ")");
        Shop updatedShop = shopService.update(id, shop);
        LOGGER.info("update(...)= " + updatedShop);
        return "redirect:/shops";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id) {
        LOGGER.info("delete(" + id + ")");
        shopService.delete(id);
        return "redirect:/shops";
    }
}

// TODO: 21.08.2024 co to są servlety, przeczytać, nauczyć się