# Aplikacija za upravljanje fakultete
Aplikacija je namenjena demonstraciji uporabe razširitve [KumuluzEE GraphQL](https://github.com/kumuluz/kumuluzee-graphql). 
Razširitev je uporabljena kot agregator za več mikrostoritev - celoten podatkovni model treh mikrostoritev je predstavljen v enotni shemi GraphQL.

## Struktura
Aplikacija je sestavljena iz štirih mikrostoritev:
- Mikrostoritev prostori vsebuje logiko za dodajanje, spreminjanje in brisanje prostorov (dva tipa prostorov: kabinet in predavalnica).
- Mikrostoritev predmeti vsebuje logiko za dodajanje, spreminjanje in brisanje predmetov (pri dodajanju predmeta preveri v mikrostoritvi prostori, če podana predavalnica obstaja).
- Mikrostoritev uporabniki vsebuje logiko za dodajanje, spreminjanje in brisanje študentov in profesorjev (pri dodajanju profesorja preveri v mikrostoritvi prostori, če podan kabinet obstaja). Omogoča dodajanje in odstranjevanje študentovih predmetov (preveri v mikrostoritvi predmeti) ter preverjanje, kateri študenti so izbrali določen predmet.
- Mikrostoritev GraphQL agregator vsebuje celotno podatkovno shemo, ki smo jo definirali pri predstavitvi GraphQL, z izjemo, da vsebuje vse bralne in pisne operacije, ki jih omogočajo naše mikrostoritve.

## Zagon projekta
Pred zagonom je potrebno konfiguricija Keycloaka (primer konfiguracije je prikazan na projektu [KumuluzEE Samples](https://github.com/kumuluz/kumuluzee-samples/tree/master/kumuluzee-security-keycloak#configure-keycloak)). Po uspešni konfiguraciji je potrebna sprememba datoteke config.yaml v mikrostoritvi GraphQL agregator (da se mikrostoritev lahko poveže na Keycloak). 

Ko smo končali s Keycloak nastavitvami, lahko projekt zaženemo z ukazom
`docker-compose up -d`.
