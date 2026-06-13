package be.anderlecht.ngo.controller;

import be.anderlecht.ngo.form.EvenementForm;
import be.anderlecht.ngo.model.Evenement;
import be.anderlecht.ngo.model.Locatie;
import be.anderlecht.ngo.repository.EvenementRepository;
import be.anderlecht.ngo.repository.LocatieRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/evenementen")
public class EvenementController {

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private LocatieRepository locatieRepository;

    @GetMapping("/nieuw")
    public String nieuwFormulier(Model model) {
        model.addAttribute("evenementForm", new EvenementForm());
        model.addAttribute("locaties", locatieRepository.findAll());
        return "new";
    }

    @PostMapping("/nieuw")
    public String opslaan(@Valid @ModelAttribute("evenementForm") EvenementForm form,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("locaties", locatieRepository.findAll());
            return "new";
        }

        Locatie locatie = locatieRepository.findById(form.getLocatieId()).orElse(null);
        if (locatie == null) {
            bindingResult.rejectValue("locatieId", "error.form", "Selecteer een geldige locatie");
            model.addAttribute("locaties", locatieRepository.findAll());
            return "new";
        }

        Evenement evenement = new Evenement();
        evenement.setTijdstip(form.getTijdstip());
        evenement.setTitel(form.getTitel());
        evenement.setOmschrijving(form.getOmschrijving());
        evenement.setOrganisatie(form.getOrganisatie());
        evenement.setContactMail(form.getContactMail());
        evenement.setLocatie(locatie);

        evenementRepository.save(evenement);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Evenement evenement = evenementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Onbekend evenement: " + id));
        model.addAttribute("evenement", evenement);
        return "details";
    }
}
