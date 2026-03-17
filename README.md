## E-Commerce Application – Exercise 1

Dieses Projekt ist eine einfache E‑Commerce‑Anwendung für die Lehrveranstaltung *Enterprise Applications – Exercise 1*.  
Die Anwendung ermöglicht es, Benutzer:innen zu verwalten, Produkte zu pflegen, Warenkörbe zu nutzen und Bestellungen zu platzieren.

---

## Features (gemäß Aufgabenstellung)

- **User Management**
  - Benutzer anlegen, lesen, aktualisieren und löschen
  - Endpunkte unter `/api/users`

- **Product Management (CRUD mit Lagerbestand)**
  - Produkte anlegen, lesen, aktualisieren und löschen
  - Verwaltung von Preis und Lagerbestand (Stock)
  - Endpunkte unter `/api/products`

- **Shopping Cart pro Benutzer**
  - Produkte in den Warenkorb legen, entfernen, Menge ändern, Warenkorb leeren
  - Endpunkte unter `/api/carts/{userId}`

- **Orders**
  - Bestellung aus dem aktuellen Warenkorb eines Users auslösen (inkl. Lagerbestandsprüfung)
  - Bestellhistorie eines Users anzeigen
  - Bestelldetails zu einer einzelnen Bestellung anzeigen
  - Endpunkte unter `/api/orders`

---

## Technischer Überblick

- **Sprache / Framework**
  - Java 17
  - Spring Boot 3 (Web, Data JPA)
- **Build Tool**
  - Gradle
- **Persistenz**
  - H2 In‑Memory Datenbank (nur zur Laufzeit im Speicher)
  - Schema wird beim Start automatisch erzeugt (`spring.jpa.hibernate.ddl-auto=create`)

Die wichtigsten Schichten sind:

- `presentation.controller` – REST‑Controller (API Layer)
- `application.service` / `application.service.impl` – Application Services (Businesslogik / Use Cases)
- `application.dto` & `application.mapper` – DTOs und Mapper zwischen Domain und API
- `domain.model` – Domain‑Entitäten
- `domain.repository` – Domain‑Repository‑Interfaces
- `infrastructure.persistence` – Spring‑Data‑JPA‑Implementierungen der Repositories

---

## Starten der Anwendung

Voraussetzungen:

- Java 17 installiert
- Gradle Wrapper wird im Projekt mitgeliefert

### Mit Gradle Wrapper (empfohlen)

Im Projektroot (`E-Commerce-App-EX1`) ausführen:

```bash
./gradlew bootRun
```

Unter Windows (PowerShell/CMD):

```bash
gradlew.bat bootRun
```

Die Anwendung startet standardmäßig auf `http://localhost:8080`.

---

## Datenbank (H2)

Konfiguration (siehe `application.properties`):

- URL: `jdbc:h2:mem:testdb`
- Benutzername: `sa`
- Passwort: *(leer)*

Die H2‑Konsole ist aktiviert und erreichbar unter:

- `http://localhost:8080/h2-console`

JPA/Hibernate erstellt das Schema automatisch beim Start.

---

## OpenAPI / Swagger UI

Die REST‑APIs werden mit **springdoc-openapi** dokumentiert.  
Nach dem Start der Anwendung ist die Swagger UI erreichbar unter:

- `http://localhost:8080/swagger-ui/index.html`

Dort können alle Endpunkte interaktiv betrachtet und getestet werden.

---

## Beispiel‑Ressourcen & typische Aufrufe

- **Users**
  - `POST /api/users` – Benutzer erstellen
  - `GET /api/users` – alle Benutzer auflisten
  - `GET /api/users/{id}` – einzelnen Benutzer holen

- **Products**
  - `POST /api/products` – Produkt erstellen
  - `GET /api/products` – alle Produkte auflisten
  - `PUT /api/products/{id}` – Produkt aktualisieren

- **Cart**
  - `GET /api/carts/{userId}` – Warenkorb eines Users abrufen
  - `POST /api/carts/{userId}/add` – Produkt in den Warenkorb legen
  - `POST /api/carts/{userId}/remove?productId=...` – Produkt entfernen
  - `PUT /api/carts/{userId}/update?productId=...&quantity=...` – Menge im Warenkorb ändern
  - `DELETE /api/carts/{userId}/clear` – Warenkorb leeren

- **Orders**
  - `POST /api/orders/{userId}/place` – Bestellung aus dem Warenkorb des Users auslösen
  - `GET /api/orders/user/{userId}` – Bestellhistorie des Users
  - `GET /api/orders/{orderId}` – Details zu einer einzelnen Bestellung

---

## Hinweise

- Das Projekt ist als Übung konzipiert und nicht für den produktiven Einsatz gedacht.
- Der Fokus liegt auf einer sinnvollen Domain‑Modellierung, Businesslogik (z.B. Lagerbestandsprüfungen) und einer klaren Schichtenarchitektur.

