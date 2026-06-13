package be.anderlecht.ngo.controller;

import be.anderlecht.ngo.repository.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private EvenementRepository evenementRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("evenementen", evenementRepository.findTop10ByOrderByTijdstipDesc());
        return "index";
    }
}
