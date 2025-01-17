# Infowijs Code Assignment

> Deze opdracht wordt toegepast voor het aannemen van iedereen binnen het Infowijs Engineering team. Deze opdracht is speciaal gemaakt om verschillende rollen de ervaring te geven zoals wij als multidisciplinair team ook met elkaar samenwerken.

## Welkom! 👋

Hoy! Wat leuk dat je al zover gekomen bent. Wij willen graag een goed beeld van jouw manier van werken als engineer en hebben daarom deze opdracht voor je gemaakt.

De applicatie waar je mee aan de slag gaat is een web applicatie met een simpele berichten interface die net een week live staat. Echter zitten er hier en daar nog wat bugs in en missen we nog wat features. De frontend maakt verbinding met de backend via HTTP API's met een [JSON API formaat](http://jsonapi.org). Delen van de API zullen hardcoded gedefinieerd staan in de DAO's voor de simpelheid van deze opdracht. Het staat je vrij die data te verplaatsen naar een database en dit te tonen in je opdracht.

We willen jou vragen of jij ons een handje wilt helpen en wat backlog items op wilt pakken. De backlog items vind je als [Github Issues](https://github.com/infowijs/code-assignment/issues) in deze repository.

## De repository

In deze repository hebben wij het project voor je klaar gezet. Je vindt in [deze repository](https://github.com/infowijs/code-assignment) 2 mappen:

### `backend`

De backend is op [Vert.x](https://vertx.io) gebaseerd project geschreven in Kotlin. Gezien de polyglot eigenschappen van zowel Vert.x als de JVM, mag je jouw code ook in Java opleveren.
Om het project niet uitgebreider te maken dan nodig hebben we ervoor gekozen de data niet uit een database te halen, maar hardcoded in de DAO's te zetten. Deze DAO's bevatten wel wat timers om een netwerk-call naar een database te simuleren.

### `frontend`

De frontend is een React client met Tailwind CSS voor de styling. De code is grotendeels geschreven in Typescript, dit zijn ook de technieken die je bij ons zal tegenkomen.

Beide projecten bevatten (in een wat abstractere vorm) de technieken die wij bij Infowijs intern ook toepassen en geeft je ook alvast een kijkje in de keuken hoe wij de structuur van onze applicaties opbouwen.

## Development en oplevering

Voor je begint met het project, fork dan [deze repository](https://github.com/infowijs/code-assignment) in je eigen account en maak dat project publiek of nodig ons uit tot je private repo zodat wij 'm ook kunnen bekijken als je klaar bent. Meer informatie over hoe je de projecten lokaal kan draaien staan in de desbetreffende mappen individuele `README.md` bestanden.

## Product Backlog Items

Je vindt de Product Backlog voor dit project in de [Github Issues](https://github.com/infowijs/code-assignment/issues) van deze repository, echter zijn ze nog niet op prioriteit gesorteerd.
Ons advies is om dus *maximaal* 30 minuten te besteden om de backlog items op de juiste volgorde te zetten. Voor de hele opdracht willen we je vragen om 6 tot 8 uur te besteden.

Uiteraard is een Product Backlog nooit compleet, dus mocht je nog vragen hebben stel ze gerust!

