# Work flow manager

![image](https://user-images.githubusercontent.com/45602824/112956200-7dd55700-9140-11eb-8d01-da1960d78cc1.png)

L’applicazione è denominata “Workflow manager” per indicare la sua funzione principale, ovvero gestire  la  rappresentazione  grafica  e  il  salvataggio  in  formato  XML  dei  dati  contenuti  in  un workflow.  Il  software  permette  infatti  di  digitalizzare  un  qualsiasi  processo  e  rappresentarlo visivamente attraverso un diagramma a stati con due componenti: nodi e transizioni.  
I  nodi  rappresentano  uno  stato  del  processo,  le  transizioni  le  condizioni  che  generano  un cambiamento di stato.  
Tutte le informazioni possono essere inserite in più lingue per consentire la comprensione del contenuto al maggior numero di persone possibile senza al momento prevedere una traduzione automatica e un controllo ortografico.  

Il prodotto si inserisce nella famiglia di software per la scrittura di dati in formato XML con alcune caratteristiche  che  altri  software  però  non  presentano.  I  due  aspetti  caratterizzanti  sono  il formato proprietario con cui vengono salvati i dati e  un’interfaccia grafica specifica che permette di  visualizzare  i  dati  attraverso  apposite  rappresentazioni,  selezionare  la  lingua  e  decidere l’organizzazione spaziale delle informazioni. 
 
Il prodotto è autonomo nel svolgere le sue funzioni e non necessita dell’interazione con altre piattaforme o applicazioni. Tuttavia i file xml prodotti si prestano ad essere utilizzati anche da altre applicazioni per lavorare sui dati che contengono. In particolare possono essere inseriti nei sistemi gestionali dell’azienda per creare WF che possono essere consultati grazie a servizi web da dipendenti e clienti.  

![image](https://user-images.githubusercontent.com/45602824/112959616-e7a33000-9143-11eb-824e-eb8e2f6ce0d8.png)


Realizzato su commessa di:

![image](https://user-images.githubusercontent.com/45602824/112956248-8b8adc80-9140-11eb-8b93-8025c0e53dca.png)
<br>
ABACO è il player di riferimento europeo nella fornitura di soluzioni software per la gestione e il controllo delle risorse territoriali, orientate principalmente all’agricoltura di precisione e alla sostenibilità ambientale.
<br>
https://www.abacogroup.eu/it/perche-abaco/chisiamo.html
<br>
info@abacogroup.eu

## Funzionamento

![image](https://user-images.githubusercontent.com/45602824/112957813-1a4c2900-9142-11eb-9169-282ef20dd27f.png)

### Classe nodo

A livello logico, ogni nodo è un oggetto della classe Node contenuta nel package elements. È possibile creare un nodo tramite il menù Insert o tramite il tasto N della toolbar.
Il codice di un nodo può essere confermato tramite il tasto ENTER. Nel momento in cui il codice del nodo viene confermato, il nodo diventa "definitivo" e può essere salvato; nel caso si tentasse di salvare il workflow con un nodo non definitivo, verrà chiesto all’utente di inserirne il codice, altrimenti il nodo non verrà salvato.
Non possono essere cancellati nodi origine o destinazione di transizione: il toolTextField avviserà l’utente di tale divieto ogni volta che l’utente tenterà di cancellare un nodo coinvolto in una transizione.

![image](https://user-images.githubusercontent.com/45602824/112958858-2684b600-9143-11eb-9c6c-cbbc63179ae2.png)

### Classe transizione

Una transizione viene creata tramite il menù Insert o il tasto T della toolbar.
Una volta che viene selezionato il nodo origine della transizione, nel WorkFlowPanel viene disegnata una freccia avente come coda il nodo origine stesso e come coda il cursore del mouse che permette all’utente di scegliere facilmente il nodo destinazione.
È possibile annullare la creazione della transizione dopo aver selezionato il primo nodo tramite il tasto ESC: la freccia che guida l’utente nella creazione della transizione viene cancellata e la situazione precedente viene ripristinata.

![image](https://user-images.githubusercontent.com/45602824/112958728-0e149b80-9143-11eb-9272-31034166ff83.png)

### Salvataggio in xml

Nel momento in cui si decide di salvare il WorkFlow nel formato XML, il passo più importante da fare è quello di convertire gli oggetti Java WorkFlow, Nodi e Transizioni in XML.
L’operazione di conversione avviene tramite un serializzatore di oggeti Java, il Marshaller, che partendo da un albero di oggetti JAXB crea il corrispondente documento XML.
Nel momento in cui dal menu si seleziona l’opzione apri, l’applicazione è in grado di aprire file salvati nel formato XML realizzati tramite il WorkFlow Manager.
L’operazione di apertura del file e di conversione da XML a oggetti Java avviene tramite l’Unmarshaller, che compie quindi il lavoro opposto a quello del Marshaller

![image](https://user-images.githubusercontent.com/45602824/112959224-7f544e80-9143-11eb-8d19-9451d08253f7.png)



## Class diagram

![image](https://user-images.githubusercontent.com/45602824/112959561-d65a2380-9143-11eb-933b-344091c7d7ff.png)

## Sequence diagram

![image](https://user-images.githubusercontent.com/45602824/112959736-043f6800-9144-11eb-8bbb-dfb9e033d790.png)

## Timeline del progetto

* INCONTRO CONOSCITIVO: 11 luglio 2018
* SPECIFICA DEI REQUISITI: 20 luglio 2018
* CONSEGNA DOCUMENTO DI PROGETTO: 3 agosto 2018
* APPROVAZIONE DOCUMENTO DI PROGETTO AZIENDA E PROFESSORE: settembre 2018
* INIZIO IMPLEMENTAZIONE: novembre 2018
* INCONTRO INTERMEDIO DI VERIFICA: 3 dicembre 2018
* CONSEGNA E PRESENTAZIONE: 11 gennaio 2019


## Requisiti da analizzare in futuro 
Possibili ulteriori funzionalità che potranno essere implementate sono: 
* Traduzione automatica e controllo grammatica e ortografia del contenuto dei WF; 
* Possibilità di esportare i WF in altri formati come ad esempio pdf per facilitare la condivisione e la stampa; 
* Possibilità di integrare in un unico file WF salvati su file diversi; 
* Possibilità di aprire e gestire più file contemporaneamente mediante l’utilizzo di tab. 
