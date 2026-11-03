# KompassGo

Desktop aplikacija za studente koja iz zadanih aktivnosti (fiksnih i fleksibilnih) generira tjedni raspored, uz uračunato vrijeme putovanja između lokacija. Projekt Tima 02 iz kolegija Programsko inženjerstvo.

## Tehnologije i verzije

| Tehnologija | Verzija | Namjena |
|---|---|---|
| Java (JDK) | 25 | Jezik i runtime |
| JavaFX | 25.0.5 | Korisničko sučelje (FXML ekrani) |
| Maven | 3.9+ (ili Maven Wrapper iz projekta) | Build i ovisnosti |
| H2 | 2.3.232 | Ugrađena baza u datotečnom načinu |
| jBCrypt | 0.4 | Hashiranje lozinki |
| JUnit | 5.12.1 | Testovi |

Verzije su definirane u `pom.xml`. Ako se ondje promijene, ažurirati i ovu tablicu.

## Potrebni alati

- **JDK 25** (`java -version` mora pokazati 25)
- **IntelliJ IDEA** (ima ugrađen Maven) ili zasebno instaliran **Maven**
- **Scene Builder** za uređivanje FXML ekrana
- **Git**

## Pokretanje

```bash
git clone <link-na-repozitorij>
cd KompassGo
mvn clean javafx:run
```

Ako Maven nije instaliran, koristi Maven Wrapper koji je već u projektu:

```bash
# Windows
mvnw.cmd clean javafx:run

# macOS / Linux
./mvnw clean javafx:run
```

Iz IntelliJ-a: **Maven → Plugins → javafx → javafx:run**.

Testovi se pokreću naredbom:

```bash
mvn verify
```

## Baza podataka

- H2 u datotečnom načinu, JDBC URL: `jdbc:h2:file:./data/raspored`
- Baza se kreira automatski pri prvom pokretanju u mapi `data/`
- Mapa `data/` nije u gitu, pa svaki član ima svoju lokalnu bazu
- Za čistu bazu dovoljno je obrisati mapu `data/` i ponovno pokrenuti aplikaciju

## Struktura projekta

```
src/main/java/hr/algebra/kompassgo/
├── model/       # klase koje odgovaraju tablicama (Korisnik, Lokacija, Aktivnost)
├── dao/         # JDBC upiti prema bazi
├── service/      # poslovna logika i validacija
├── controller/   # JavaFX kontroleri ekrana
└── util/        # pomoćne klase (spajanje na bazu, prebacivanje ekrana)
src/main/resources/hr/algebra/kompassgo/
├── view/        # FXML ekrani
└── sql/         # schema.sql i data.sql
```

Smjer ovisnosti: **kontroler → servis → dao → baza**. Kontroler ne piše SQL.

## Pravila rada

- Grana `main` je zaštićena: promjene ulaze samo kroz **pull request** uz barem **jedan review**.
- Za svaki Jira zadatak nova grana iz `main`:
  `feature/SCRUM-<broj>-kratki-opis`, npr. `feature/SCRUM-40-maven-projekt`
- Commit poruka počinje Jira ključem, npr. `SCRUM-40 dodane ovisnosti u pom.xml`, kako bi se commit vidio u Jiri.
- Prije pull requesta lokalno mora proći `mvn verify`.
- Nakon merga grana se briše, a zadatak se u Jiri pomiče u **Done**.

## Tim

KompassGo Team: Pino Banda, Karla Atlija, Iva Miloš, Andrej Vladimir Bučko
Sveučilište Algebra Bernays
