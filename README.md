# Prime Visualiser

Prime Visualiser erstellt zwei Gruppen von Bildern Bilder.
- Bilder die zur Darstellung des Siebs des Eratosthenes dienen. 
- Bilder die die Verteilung der Primzahlen visuell ansprechend darstellen.

## Voraussetzungen
- Java 23
- Build-Tool: Maven
- Betriebssystem: macOS
- Erstellt in IntelliJ

## Projektstruktur

Das Projekt besteht aus drei Dateien:

1. **PrimeVisualiser.java** (Main)
    - Hält Einstellungen
    - Generiert die Daten die als Grundlage der Bilder dienen.

2. **SieveOfEratosthenes.java**
    - Implementiert das Sieb des Eratosthenes-Algorithmus mit Multi-Threading.
    - Ermöglicht eine Rückgabe der Daten in mehreren nützlichen Datentypen.

3. **ImageCreator.java**
   - Nimmt unterschiedliche Datentypen und stellt sie visuell dar.


### Kompilieren

Befinde dich mit dem Terminal im Projekt Ordner. "aufgabe2ext"

```bash
mvn compile
```

### Ausführen

Nutze dann diesen Befehl, um das Programm auszuführen:

```bash
mvn exec:java -Dexec.mainClass="org.example.PrimeVisualiser" 
```

### Dateipfad für Export der Bilder
``PrimeVisualiseriser`` biete eine Variable BASE_PATH, in dieser kann der Pfad gespeichert werden, an dem die Bilder erstellt werden sollen.


### Erklärung der Bilder
- **sieveAlgoOverTime**  
Zeigt Bilder in dem Order zeigen wie sich der *SieveOfEratosthenes* Algorithmus auf die Zahlen Menge auswirkt.
In den Ersten Bilder sind klare Muster und die größten Veränderungen zu erkennen.
  

- **primesGrid.png**  
Zeigt die Verteilung der Primzahlen in einer Rasteransicht.
  

- **polarXXX_XXX.png**  
Zeigt die Primzahlen bis Obergrenze X in einer Polardarstellung.
Für jedes x: Radius = Theta = x  
Je nachdem wie man die Obergrenze wählt ergeben sich verschiedene Muster.
Die Zahl den "Arme" in der Spirale nehmen zu.  
Die Idee für diese Darstellung habe ich aus diesem YT-Video, das die Mathematik hinter den Bildern erklärt:  
https://www.youtube.com/watch?v=EK32jo7i5LQ&t=628s
