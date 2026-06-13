package be.anderlecht.ngo.controller;

import be.anderlecht.ngo.form.ContactForm;
import be.anderlecht.ngo.service.MailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private MailService mailService;

    @GetMapping
    public String contactFormulier(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

    @PostMapping
    public String verstuurBericht(@Valid @ModelAttribute("contactForm") ContactForm form,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            return "contact";
        }

        try {
            mailService.stuurContactMail(form);
            model.addAttribute("succes", true);
        } catch (Exception e) {
            model.addAttribute("fout", "Er is een fout opgetreden bij het verzenden. Probeer het later opnieuw.");
        }

        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }
}
