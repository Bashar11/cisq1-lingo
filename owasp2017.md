1.    Wat houdt de vulnerability in? 
2.    Hoe groot is het risico voor deze kwetsbaarheid binnen het project?
      a.Wat als we authenticatie en autorisatie toevoegen?     
3.    Hoe wordt dit risico tegengegaan binnen het project?
      a.Als je denkt dat (een onderdeel van) een library of framework dit voor je oplost,
      probeer dan uit te zoeken welk onderdeel dit is en hoe deze dat tegengaat.


# Vulnerability Analysis


## A2:2017 Broken Authentication
### Description

Verbroken Authenticatie houdt in dat applicaties met betrekkingen tot authenticaties en session manaement 
vaak niet niet correct toegepast worden, waarodoor hackers toegang 
kunnen krijgen tot wachtwoorden en tokens van gebruikers

### Risk

risico's kunnen zijn dat hackers opgeslaagde wachtwoorden over kunnen 
nemen, waardoor ze toegang tot de account van de gebruikers hebben en 
de data van de gebruikers openbaar maken



### Counter-measures

Rollen aanwijzen, bijv iemand die gegevens kan aanpassen niet
zelf de aanpassingen laten geodkeuren. Multi factor authenticatie gebruiken.Hiermee
laat je de gebruiker met twee of meerdere verschillende manieren
authenticeren inplaats van met alleen maar wachtwoord.





## A3:2017 Sensitive Data Exposer

### Description
Gevoelige data net als persoonsgegevens , medische informatie
of financiele informatie worden niet goed veilig opgeslagen 
waardoor een hacker deze gegevens kan stelen of fraude plegen.


### Risk
Hackers kunnen toegang krijgen tot data en deze stelen en doorverkopen 
of gebruiken voor andere doeleinden.


### Counter-measures
gevoelige data goed opslaan en deze goed versleutelen en beveiligen. 
Allen maar. Campagnes uitvoeren om gebruikers wijzer te maken om 
verdacht websites niet gebruiken , of verdachte mails niet openen. 




## A5:2017 Broken Access Control


### Description
Beperkingen op wat geautoriseerde gebruikers mogen doen 
worden vaak noet goed genoeg vastgesteld.Dit kan gevolgen hebben
dat hackers toegang te krijgen tot functionaliteit en / of gegevens.Bijv
toegang tot accounts.


### Risk
hackers krijgen toegang tot accounts van andere gebruikers,
bekijken van gevoelige bestanden, wijzigen van gegevens van andere gebruikers,
wijzigen van toegangsrechten. 


### Counter-measures
De identificatie en inloggegevens van werknemers nauwlettend in de gaten houden, JWT-tokens na uitloggen op de server ongeldig maken,
Loggen en monitoren,Creëren van meerlagige inlogprocessen en toegankelijkheid van de workflow.
Bewaken van het opnieuw instellen, hergebruiken van wachtwoorden
