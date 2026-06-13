package be.anderlecht.ngo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ContactForm {

    @NotBlank(message = "Naam is verplicht")
    private String naam;

    @NotBlank(message = "E-mailadres is verplicht")
    @Email(message = "Voer een geldig e-mailadres in")
    private String email;

    @NotBlank(message = "Bericht is verplicht")
    private String bericht;

    public String getNaam() { return naam; }
    public void setNaam(String naam) { this.naam = naam; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBericht() { return bericht; }
    public void setBericht(String bericht) { this.bericht = bericht; }
}
