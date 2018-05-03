## Progetto Cinema
Di seguito troverete tutti i dettagli del progetto, sviluppato durante il corso ITS Padova

### Obiettivo: 

* Sviluppare un app mobile per sistemi operativi Android per la prenotazione dei posti di un cinema dedicato.

### Requisiti:
 
La classe è stata divisa in 5 gruppi, ognuno dei quali ha dovuto realizzare un'activity:

* Gruppo 1: Activity login e registrazione
* Gruppo 2: Activity lista film
* Gruppo 3: Activity dettaglio fIlm con selezione orario e sala
* Gruppo 4: Activity Selezione dei posti in sala
* Gruppo 5: Activity riepilogo dell'ordine

Per rendere il tutto omogeneo ed uniforme sono state definite due linee guida principali:

* L'applicazione utilizzerà un database interno, salvato in locale
* Sono stati definiti degli standard riguardanti la grafica (colori, bottoni, spazi, ...)
* Passati 5 minuti dalla sessione dell'utente, sarà visualizzata una schermata di sessione scaduta, nel quale l'utente dovrà effettuare l'accesso nuovamente

### Activity 1 - LOGIN e REGISTRAZIONE

La prima activity consiste nella parte di login per gli utenti già registrati nel database, altrimenti è presente una sezione dedicata alla registrazione dei nuovi utenti.
Il login è effettuato tramite mail e password forniti dall'utente al momento della registrazione.
Subito dopo il login l'utente passa alla schermata con la lista dei film.

### Activity 2 - LISTA FILM

Questa seconda activity visualizza a display la lista di tutti i film proiettati dal cinema, con titolo, immagine, genere, durata e voto in stelle.
Alla pressione di un film, viene aperta l'activity successiva.

### Activity 3 - DETTAGLIO FILM

Sono presentati tutti i dettagli del film, compresa la descrizione.
In basso a sinistra sono presenti due pulsanti che aprono dei POP-UP diversi, uno per la selezione del giorno e l'altro per la selezione dell'orario.
Una volta selezionati entrambi i dati, un pulsante blu in basso a destra diventerà cliccabile e si passerà al prossimo passaggio.

### Activity 4 - SELEZIONE POSTI

All'avvio della schermata, l'utente sarà invitato a selezionare i posti in sala, scegliendo solo tra quelli liberi (color grigio chiaro). Quelli occupati (grigi scuri) non saranno selezionabili.
Ogni utente potrà selezionare fino ad un massimo di 4 posti. 
Una volta cliccato il pulsante in basso, sarà possibile accedere all'activity del riepilogo.

### Activity 5 - RIEPILOGO

L'ultima parte mostra all'utente tutti i dettagli del film desiderato, degli orari, dei posti selezionati e un ID sessione da presentare all'assistenza in caso di problemi tecnici. 
Il cliente confermerà la prenotazione con un pulsante, e verrà terminata la sessione.
