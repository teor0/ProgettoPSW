# README.md
Progetto per il corso Piattaforme Software per Applicazioni Web
Il progetto si basa su un backend ed un frontend che comunicano tramite Rest API, inoltre viene assicurata l'autenticazione degli utenti tramite un identity and access manager.
Lo scopo principale è quello di gestire le transazioni all'interno di un e-commerce.
## Backend
Il backend è basato su Spring + PostgreSQL, in particolare:

- Versione Spring: 3.0.6
- Versione Java: 19.0.2
- Versione PostgreSQL: 15.0.2

## Frontend
Il frontend è realizzato tramite Angular 15.2.0 per maggiori informazioni sulle dipendenze consultare il file [package.json](https://github.com/teor0/ProgettoPSW/blob/master/frontend/package.json).

L'interfaccia risulta "spartana" data la sua importanza "secondaria".

## Autenticazione
Per quanto riguarda l'autenticazione degli utenti si utilizza Keycloak versione 22.0.4, in particolare la registrazione/login di un utente è gestita tramite [keycloak-angular](https://github.com/mauriciovigolo/keycloak-angular).

**N.B. Il progetto sfrutta codice deprecato.**
