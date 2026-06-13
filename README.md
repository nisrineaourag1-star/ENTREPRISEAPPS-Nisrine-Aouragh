# Anderlecht Gemeenschapshuis – Website Prototype

Dit project is een website prototype voor het Anderlecht Gemeenschapshuis, een NGO die zich inzet voor gemeenschapsbouwen en ondersteuning van mensen in Anderlecht die het wat moeilijker hebben.

---

## Inhoudsopgave

1. [Architectuuroverzicht](#architectuuroverzicht)
2. [Projectstructuur](#projectstructuur)
3. [Gebruikte technologieën en libraries](#gebruikte-technologieën-en-libraries)
4. [Tutorials en documentatie](#tutorials-en-documentatie)
5. [Gebruik van AI](#gebruik-van-ai)
6. [Hoe het project opstarten](#hoe-het-project-opstarten)

---

## Architectuuroverzicht

De applicatie volgt het klassieke MVC-patroon van Spring Boot. Hieronder zie je hoe de verschillende lagen met elkaar verbonden zijn:

```
Browser
   │
   │  HTTP-request (GET / POST)
   ▼
Controller-laag
   ├── IndexController       → haalt de 10 laatste evenementen op en toont index.html
   ├── EvenementController   → verwerkt het formulier voor nieuw evenement + toont details
   ├── ContactController     → verwerkt het contactformulier en roept MailService aan
   └── AboutController       → toont statische about-pagina
   │
   │  Roept service/repository aan
   ▼
Service-laag
   └── MailService           → bouwt een e-mail op en stuurt via SMTP (Mailtrap)
   │
   ▼
Repository-laag (Spring Data JPA)
   ├── EvenementRepository   → CRUD + findTop10ByOrderByTijdstipDesc
   └── LocatieRepository     → opzoeken van locaties voor het formulier
   │
   ▼
H2 In-memory database
   ├── Tabel: evenement      → id, tijdstip, titel, omschrijving, organisatie, mail, locatie_id
   └── Tabel: locatie        → id, naam, adres, capaciteit
```

### Formulierflow nieuw evenement

```
Gebruiker vult formulier in (new.html)
   │
   ▼
POST /evenementen/nieuw
   │
   ├── Validatie mislukt?  → Formulier opnieuw tonen met foutmeldingen (new.html)
   │
   └── Validatie geslaagd? → Evenement opgeslagen in database
                                │
                                ▼
                           Redirect naar / (index)
```

### Formulierflow contactpagina

```
Gebruiker vult contactformulier in (contact.html)
   │
   ▼
POST /contact
   │
   ├── Validatie mislukt?  → Formulier opnieuw tonen met foutmeldingen
   │
   └── Validatie geslaagd? → MailService verstuurt e-mail via Mailtrap SMTP
                                │
                                ├── Versturen gelukt?   → Bevestigingsmelding getoond
                                └── Versturen mislukt?  → Foutmelding getoond
                                    (app blijft verder werken voor alle andere pagina's)
```

---

## Projectstructuur

De applicatie is opgebouwd met Spring Boot en Thymeleaf. Hieronder een overzicht van hoe de mappen en bestanden georganiseerd zijn:

```
src/
└── main/
    ├── java/be/anderlecht/ngo/
    │   ├── AnderlechtNgoApplication.java     ← Spring Boot hoofdklasse
    │   ├── DataInitializer.java              ← Vult de database met voorbeelddata bij opstarten
    │   ├── model/
    │   │   ├── Evenement.java                ← JPA-entity voor evenementen
    │   │   └── Locatie.java                  ← JPA-entity voor locaties
    │   ├── form/
    │   │   ├── EvenementForm.java            ← DTO voor het evenementenformulier
    │   │   └── ContactForm.java              ← DTO voor het contactformulier
    │   ├── repository/
    │   │   ├── EvenementRepository.java      ← Spring Data JPA repository
    │   │   └── LocatieRepository.java        ← Spring Data JPA repository
    │   ├── controller/
    │   │   ├── IndexController.java          ← GET /
    │   │   ├── EvenementController.java      ← GET/POST /evenementen/nieuw, GET /evenementen/{id}
    │   │   ├── AboutController.java          ← GET /about
    │   │   └── ContactController.java        ← GET/POST /contact
    │   └── service/
    │       └── MailService.java              ← E-mail verzenden via Mailtrap
    └── resources/
        ├── application.properties            ← Database- en mailconfiguratie
        └── templates/
            ├── fragments/
            │   └── layout.html               ← Herbruikbare navigatie en footer
            ├── index.html                    ← Overzicht van de 10 laatste evenementen
            ├── new.html                      ← Formulier om een nieuw evenement toe te voegen
            ├── details.html                  ← Detailpagina van een evenement
            ├── about.html                    ← Over de NGO
            └── contact.html                  ← Contactformulier met e-mailverzending
```

### Pagina-overzicht

| URL | Beschrijving |
|-----|-------------|
| `/` | Startpagina met de 10 laatste evenementen |
| `/evenementen/nieuw` | Formulier om een nieuw evenement aan te maken |
| `/evenementen/{id}` | Detailpagina van een specifiek evenement |
| `/about` | Informatie over de NGO |
| `/contact` | Contactformulier dat een e-mail verstuurt via Mailtrap |

---

## Gebruikte technologieën en libraries

### Backend

| Library / Framework | Versie | Waarvoor |
|---------------------|--------|----------|
| Spring Boot | 3.2.0 | Hoofdframework van de applicatie |
| Spring MVC | 6.x | HTTP-routing, controllers en formulierverwerking |
| Spring Data JPA | 3.x | Databasetoegang via repositories |
| Spring Boot Validation | 3.2.0 | Formuliervalidatie met annotaties zoals @NotBlank en @Email |
| Spring Boot Mail | 3.2.0 | E-mailverzending via SMTP (Mailtrap) |
| Hibernate | 6.x | JPA-implementatie, maakt automatisch het databaseschema aan |
| H2 Database | 2.x | In-memory database voor het prototype |
| Thymeleaf | 3.x | Server-side HTML-templates |

### Frontend

| Library | Versie | Waarvoor |
|---------|--------|----------|
| Tailwind CSS | CDN (Play) | Opmaak en layout van de pagina's |

Tailwind wordt ingeladen via CDN: `<script src="https://cdn.tailwindcss.com"></script>`

### Externe dienst

| Dienst | Waarvoor |
|--------|----------|
| Mailtrap | Testen van e-mailverzending via SMTP, zonder echte mails te sturen |

---

## Tutorials en documentatie

Tijdens het project heb ik de volgende bronnen geraadpleegd:

| Bron | Waarvoor gebruikt |
|------|-------------------|
| [Spring Boot documentatie](https://docs.spring.io/spring-boot/docs/3.2.0/) | Configuratie, starters en autoconfiguratie |
| [Spring MVC documentatie](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html) | Controllers, formulierverwerking en BindingResult |
| [Spring Data JPA documentatie](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/) | Repository-methoden zoals findTop10ByOrderBy |
| [Thymeleaf documentatie](https://www.thymeleaf.org/documentation.html) | Template-syntax, th:fragment, th:replace en validatiefouten tonen |
| [Bean Validation (Jakarta)](https://jakarta.ee/specifications/bean-validation/) | Validatie-annotaties |
| [Tailwind CSS documentatie](https://tailwindcss.com/docs) | Utility classes voor layout en stijl |
| [Mailtrap documentatie](https://mailtrap.io/blog/spring-send-email/) | Configuratie van SMTP met Spring Boot |

---

## Gebruik van AI

| Tool | Waarvoor |
|------|----------|
| Claude (Anthropic) | Code review: Spring Boot structuur en JPA-entities gecontroleerd |
| Claude (Anthropic) | Code review: Thymeleaf template syntax nagekeken (th:field, th:errors, th:fragment) |

AI werd uitsluitend gebruikt om zelfgeschreven code na te kijken op fouten en correctheid( ook om mijn readme beter te maken voor taal fouten en betere structuur), niet om alle codes te genereren.

---

## Hoe het project opstarten

### Wat je nodig hebt

| Vereiste | Minimale versie |
|----------|-----------------|
| Java JDK | 17 |
| Apache Maven | 3.6+ |

Je kan controleren of alles geïnstalleerd is met:

```bash
java -version
mvn -version
```

### Stap 1: Mailtrap instellen (voor het contactformulier)

1. Maak een gratis account aan op [https://mailtrap.io](https://mailtrap.io)
2. Ga naar **Email Testing → Inboxes → jouw inbox → SMTP Settings**
3. Kies **Spring Boot** als integratie en kopieer de credentials
4. Open `src/main/resources/application.properties` en vul in:

```properties
spring.mail.username=JOUW_MAILTRAP_USERNAME
spring.mail.password=JOUW_MAILTRAP_PASSWORD
```

Let op: als je geen Mailtrap-account instelt, werkt de rest van de applicatie gewoon verder. Alleen het contactformulier zal een foutmelding tonen bij het versturen.

### Stap 2: Project opstarten

```bash
cd pad/naar/ENREPRISEAPP-PROJECT
mvn spring-boot:run
```

Of via een gecompileerde JAR:

```bash
mvn clean package
java -jar target/anderlecht-ngo-0.0.1-SNAPSHOT.jar
```

### Stap 3: De website openen

Open je browser en ga naar:

```
http://localhost:8080
```

### Beschikbare pagina's

| Pagina | URL |
|--------|-----|
| Startpagina | http://localhost:8080/ |
| Nieuw evenement | http://localhost:8080/evenementen/nieuw |
| Over ons | http://localhost:8080/about |
| Contact | http://localhost:8080/contact |
| H2 Console (database) | http://localhost:8080/h2-console |

Via de H2 Console kan je de in-memory database rechtstreeks bekijken. Gebruik als JDBC URL: `jdbc:h2:mem:anderlechtdb`.

### Voorbeelddata

Bij het opstarten wordt de database automatisch gevuld met:

- 4 locaties: Campus Kaai, Astridpark, Gemeentehuis en Sporthal
- 10 evenementen die meteen zichtbaar zijn op de startpagina

---

## Auteur

**Nisrine Aouragh**
Opleiding: Graduaat Programmeren
Academiejaar: 2025-2026
