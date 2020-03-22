# Team 1896 - Kommunikation von Testergebnissen
Backend zum Thema Kommunikation von Testergebnissen. Hier soll der Kommunikationsfluss von Testergebnissen zurück zu den Patienten 
erleichtert und beschleunigt werden. 
## Idee
Integration eines Informations services in die bestehenden Prozesse der Test Stationen. Der Fokus liegt auf einfacher Bedienbarkeit 
für medizinisches Personal und Patienten. 
Ein besonderer Punkt ist die Lösung des Problems, dass aktuell Patienten mit negativem Testergebnis aktuell keine aktive 
Benachrichtigung erhalten. Dies führt zu erheblichen Unsicherheiten bei den Patienten.

## Umsetzung

### Schnittstellen
* REST API zum Hinterlegen von anonymisierten Testergebnissen (nicht öffentlich, für medizinische Einrichtungen)
* REST API zum Erfragen von Testergebnissen (anonyme Nutzung durch Web UI)

### Funktionen
Zusätzlich zu den oben genannten APIs soll der Service auch ermöglichen automatisiert Benachrichtigungen an die Patienten zu versenden
(sofern diese sich mit Kontaktinformationen beim Test angemeldet haben). Aktuell prototypisch implementiert sind: 
* Webseite
* EMail
* SMS

weitere Kanäle in Planung
* Telefon (via automatisiertem Anruf)

#### Workflow
* Patient meldet sich beim medizinischen Personal an (mit Stammdaten und evtl. Kontaktdaten)
* Patient erhält seine Probennummer (erstellt vom medizinischen Personal/Labor)
* Test wird vom Labor durchgeführt und rückgemeldet
* via hashing wird eine anonymisierte Id erstellt (enthält: Geburtsdatum, Name, Probennummer)
* Testergebnis wird an den Service übermittelt (anonyme Id + Ergebnis + Kontaktinformation falls vorhanden)
* Testergebnis wird unter anonymisierter Id gespeichert, falls Kontaktinformation vorhanden wird eine Nachricht gesendet
* Testergebnis ist nun via Webseite abrufbar (unter Angabe von Geburtsdatum, Name, Probennummer)

### Deployment/Services
* backend Service: MS Azure
* database: MongoDB Cloud Atlas
* EMail: SendGrid
* SMS: SMS4, Vonage

### Systemübersicht

* client für medizinisches Personal (Excel)
* backend (dieser service)
* frontend (Website für Zugriff auf Informationen zum Testergebnis)

# Links
* hackathon https://wirvsvirushackathon.org/
* topics https://airtable.com/shrs71ccUVKyvLlUA/tbl6Br4W3IyPGk1jt/viw7AlEju6qFtXJqL?blocks=hide
