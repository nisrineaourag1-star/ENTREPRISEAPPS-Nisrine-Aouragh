package be.anderlecht.ngo;

import be.anderlecht.ngo.model.Evenement;
import be.anderlecht.ngo.model.Locatie;
import be.anderlecht.ngo.repository.EvenementRepository;
import be.anderlecht.ngo.repository.LocatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LocatieRepository locatieRepository;

    @Autowired
    private EvenementRepository evenementRepository;

    @Override
    public void run(String... args) {
        Locatie kaai = new Locatie("Campus Kaai", "Quai du Hainaut 29, 1080 Anderlecht", 200);
        Locatie park = new Locatie("Astridpark Anderlecht", "Rue de Fiennes, 1070 Anderlecht", 500);
        Locatie gemeentehuis = new Locatie("Gemeentehuis Anderlecht", "Place du Conseil 1, 1070 Anderlecht", 150);
        Locatie sporthal = new Locatie("Sporthal Veeweyde", "Rue Crockaert 2, 1070 Anderlecht", 300);
        locatieRepository.saveAll(List.of(kaai, park, gemeentehuis, sporthal));

        maakEvenement("Buurtmaaltijd Cureghem",
                "Een gezellige maaltijd voor de bewoners van Cureghem. Samen eten, samen leven!",
                "Anderlecht Gemeenschapshuis", "info@anderlecht-ngo.be",
                LocalDateTime.of(2026, 6, 20, 18, 0), kaai);

        maakEvenement("Taalcafé Frans & Nederlands",
                "Wekelijks taalcafé waar bewoners hun taalvaardigheden kunnen oefenen in een informele sfeer.",
                "Eigen beheer", "taalcafe@anderlecht-ngo.be",
                LocalDateTime.of(2026, 6, 25, 14, 0), gemeentehuis);

        maakEvenement("Zomerfeest Park Anderlecht",
                "Groot zomerfeest voor alle bewoners van Anderlecht met muziek, dans en eten.",
                "Cultuurhuis Westland", "zomerfeest@cultuurhuis.be",
                LocalDateTime.of(2026, 7, 4, 15, 0), park);

        maakEvenement("Workshop Digitale Vaardigheden",
                "Leer hoe je veilig het internet gebruikt, e-mails verstuurt en online formulieren invult.",
                "Eigen beheer", "digitaal@anderlecht-ngo.be",
                LocalDateTime.of(2026, 7, 10, 10, 0), kaai);

        maakEvenement("Kleding- en Voedselbank Inzamelactie",
                "Breng uw ongebruikte kleding en niet-bederfelijke voedingsmiddelen mee voor mensen in nood.",
                "Eigen beheer", "inzamel@anderlecht-ngo.be",
                LocalDateTime.of(2026, 7, 15, 9, 0), gemeentehuis);

        maakEvenement("Sporttornooi voor Jongeren",
                "Voetbal-, basketbal- en volleybaltornooi voor jongeren tussen 12 en 18 jaar. Inschrijving verplicht.",
                "Sportfederatie Anderlecht", "sport@anderlecht.be",
                LocalDateTime.of(2026, 7, 22, 9, 0), sporthal);

        maakEvenement("Infosessie Sociale Rechten",
                "Kom alles te weten over uw rechten: werkloosheidsuitkering, huursubsidies, kinderbijslag en meer.",
                "OCMW Anderlecht", "ocmw@anderlecht.be",
                LocalDateTime.of(2026, 8, 5, 13, 0), gemeentehuis);

        maakEvenement("Intercultureel Kookfestival",
                "Proef gerechten uit meer dan 20 landen, bereid door de bewoners zelf. Gratis toegang!",
                "Eigen beheer", "koken@anderlecht-ngo.be",
                LocalDateTime.of(2026, 8, 14, 11, 0), park);

        maakEvenement("Ouder-Kind Creatieve Namiddag",
                "Teken, knutsel en schilder samen met uw kind. Materiaal wordt voorzien.",
                "Krea-vzw", "info@krea-vzw.be",
                LocalDateTime.of(2026, 8, 20, 14, 0), kaai);

        maakEvenement("Buurtopruimdag Molenbeek-Anderlecht",
                "Doe mee en maak samen uw buurt proper. Handschoenen en zakken worden voorzien.",
                "Leefmilieu Brussel", "contact@leefmilieu.be",
                LocalDateTime.of(2026, 9, 5, 9, 0), park);
    }

    private void maakEvenement(String titel, String omschrijving, String organisatie,
                                String mail, LocalDateTime tijdstip, Locatie locatie) {
        Evenement e = new Evenement();
        e.setTitel(titel);
        e.setOmschrijving(omschrijving);
        e.setOrganisatie(organisatie);
        e.setContactMail(mail);
        e.setTijdstip(tijdstip);
        e.setLocatie(locatie);
        evenementRepository.save(e);
    }
}
