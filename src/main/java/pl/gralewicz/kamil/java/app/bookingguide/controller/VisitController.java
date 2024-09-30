package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value="/visits")
public class VisitController {
    private static final Logger LOGGER = Logger.getLogger(VisitController.class.getName());

    @GetMapping
    public String list(ModelMap modelMap){
        LOGGER.info("list()");
        List<Visit> visits = new ArrayList<>();
        visits.add(new Visit());
        visits.add(new Visit());
        modelMap.addAttribute("visits", visits);
        LOGGER.info("list(...)= " + visits);
        return "visits";
    }
}
