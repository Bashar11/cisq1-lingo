1.    Wat houdt de vulnerability in? 
2.    Hoe groot is het risico voor deze kwetsbaarheid binnen het project?
      a.Wat als we authenticatie en autorisatie toevoegen?     
3.    Hoe wordt dit risico tegengegaan binnen het project?
      a.Als je denkt dat (een onderdeel van) een library of framework dit voor je oplost,
      probeer dan uit te zoeken welk onderdeel dit is en hoe deze dat tegengaat.


# Vulnerability Analysis
## A2:2017 Broke Authentication
### Description

Verbroken Authenticatie is dat aanvallers toegang krijgen tot honderden geldige gebruikersnaam-
en wachtwoordcombinaties voor het opvullen van inloggegevens.
Aanvallers kunnen gebroken authenticatie met handmatige middelen detecteren 
en deze misbruiken met behulp van geautomatiseerde tools 
met wachtwoordlijsten en woordenboekaanvallen.

### Risk

Als een hacker toegang krijgt naar een admin account kan hij 
alle andere accounts stelen en het systeem in gevaar brengen 
zoals bijvoorbeeld gevoelige informatie vrijgeven

### Counter-measures
-Het broken authentication is te voor komen door het genereren van multi factor authenticatie
om aanvallen te voorkomen. 
-Controles maken over de zwakker wachtwoorden
- Een wachtwoordebeleid stellen 
- Beperken van loginpogingen
-Ingebouwde sessiemanager gebruikern die na inloggen een nieuwe willekeurige sessie-ID genereert 
  met een hoge entropie
  

