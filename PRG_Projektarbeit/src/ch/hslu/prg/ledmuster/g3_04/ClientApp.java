package ch.hslu.prg.ledmuster.g3_04;

import java.util.Random;
import java.util.Scanner;
import ch.hslu.prg.ledboard.proxy.BoardService;
import ch.hslu.prg.ledboard.proxy.LedColor;
import ch.hslu.prg.ledboard.proxy.Led;

public class ClientApp {

	private static final int DEFAULT_PAUSE_TIME = 100; // Zeit in Millisekunden zwischen Ein- und Ausschaltaktionen

	public static void main(String[] args) {
		BoardService service = new BoardService();
		Scanner scanner = new Scanner(System.in);

		// Aufgabe 1.1 LEDs Ein- und Ausschalten in Reihen
		// ledsOnOff(service, scanner);

		// Aufgabe 1.2 LEDs mit Farbe Ein- und Ausschalten
		// ledsColoredOnOff(service, scanner);

		// Aufgabe 2:
		// switchEvenOdd(service, scanner);

		// Aufgabe 3
		// switchRandom(service, scanner);

		// Aufgabe 4: Dezimalanzeige eines zufälligen Bitmusters
		// showDecimal(service);

		// Aufgabe 5
		// drawBorder();

		// Aufgabe 6.1
		// drawSquare();

		// Aufgabe 6.2
		// drawSquareWithDiagonals();

		// Aufgabe 7
		// drawRectangle();

		// Aufgabe 8
		// drawTriangle();

		// Aufgabe 9

		// Aufgabe 10.1
		// countColors(service);

		// Aufgabe 10.2
		//countColorExt(service, scanner);

		// Aufgabe 10.3
		//countRedLedsOnDiagonals();

		scanner.close();
	}

	// Aufgabe 1: Methode zum Ein- und Ausschalten der LEDs in Reihen
	private static void ledsOnOff(BoardService boardService, Scanner scanner) // als Parameter muss boardservice und
																				// scanner übergeben werden
	{
		final int pausetime = 250; // Standard-Pause in Millisekunden (soll nicht mehr geändert werden)

		System.out.print("Anzahl der LED-Reihen eingeben: "); // Anzahl Reihen Abfragen
		int rows = scanner.nextInt(); // User-input in rows speichern

		// Maximal erlaubte Anzahl von Reihen prüfen und festlegen
		int maxRows = BoardService.MAX_ROWS; // maximale Anzahl rows als int speichern
		if (rows > maxRows) // Abfrage ob eingegebene Anzahl rows grösser als die maximale Anzahl ist
		{
			System.out.println("Maximale Anzahl von Reihen überschritten, auf Maximum gesetzt.");
			rows = maxRows; // rows wird als maximal gültige rows gespeichert
		}

		// LEDs mit Standardfarbe hinzufügen
		Led[][] leds = boardService.add(rows); // Zweidimensionales array erstellen und mit der add Methode die rows ins
												// Array speichern

		// Pause zur Anzeige der LEDs
		boardService.pauseExecution(2000);

		// LEDs ein- und ausschalten
		for (int repeat = 0; repeat < 3; repeat++) // Loop für dreifache Ausführung Reihen ein- und ausschalten (repeat
													// zählt Vorgänge)

		{
			// LEDs einschalten von rechts nach links
			for (Led[] row : leds) // LED row repräsentiert eine Zeile von LEDs auf dem Board. Der loop iteriert
									// über jede LED-Reihe im zweidimensionalen Array leds
			{
				for (int i = row.length - 1; i >= 0; i--) // Loop schaltet LEDs innerhalb jeder Zeile von rechts nach
															// links ein.
				{
					row[i].turnOn(); // Methode turnOn zum einschalten der einzelnen LEDs
					boardService.pauseExecution(pausetime); // pause von 250 milisekunden
				}
			}

			// Pause von 250 milisekunden
			boardService.pauseExecution(pausetime);

			// LEDs ausschalten von links nach rechts
			for (Led[] row : leds) // geht Zeile für Zeile durch das zweidimensionale Array leds

			{
				for (Led led : row) // durchläuft jede Zeile von row von links nach rechts, da die LEDs der Zeile in
									// ihrer natürlichen Reihenfolge durchläuft
				{
					led.turnOff(); // Methode zum ausschalten der LEDs
					boardService.pauseExecution(pausetime); // Pause von 250 Milisekunden
				}
			}

			// Weitere Pause
			boardService.pauseExecution(pausetime);
		}

		// Anzeige zurücksetzen
		boardService.removeAllLeds();
	}

	// Methode zum Ein- und Ausschalten farbiger LEDs
	private static void ledsColoredOnOff(BoardService boardService, Scanner scanner) // Parameter boardService und
																						// Scanner übergeben
	{
		final int pausetime = 250; // Standard-Pause in Millisekunden
		System.out.print("Anzahl der LED-Reihen eingeben: ");
		int rows = scanner.nextInt(); // Eingabe in rows speichern
		System.out.println("Gewünschte Farbe (RED, GREEN, BLUE, YELLOW, RANDOM) eingeben: ");
		String colorInput = scanner.next().toUpperCase(); // Eingabe wird in Grossbuchstaben in colorInput gespeichert.

		// Validierung der Farbe
		LedColor color = LedColor.RED; // Standardfarbe auf RED setzen

		// Prüfe, ob colorInput zu den gültigen Farben gehört
		if (colorInput.equals("RED") || colorInput.equals("GREEN") || colorInput.equals("BLUE")
				|| colorInput.equals("YELLOW")) {

			color = LedColor.valueOf(colorInput); // Setzt die Farbe auf den gültigen Wert
		}

		else if (colorInput.equals("RANDOM")) // Wenn der User-input "RANDOM" ist, wird die LED Farbe jeder Lampe
												// zufällig ausgewählt
		{

			color = LedColor.RANDOM;

		}

		{

			int maxRows = BoardService.MAX_ROWS; // Konstante MAX_ROWS als int speichern

			if (rows > maxRows) // Abfrage ob eingegebene Anzahl rows grösser als die maximale Anzahl ist
			{
				System.out.println("Maximale Anzahl von Reihen überschritten, auf Maximum gesetzt.");
				rows = maxRows; // rows wird als maximal gültige rows gespeichert
			}

			Led[][] leds = boardService.add(rows, color); // Hinzufügen der Anzahl Reihen und Farben in das
															// zweidimensionale Array leds
			boardService.pauseExecution(2000); // Pause von 2000 Milisekunden

			// LEDs ein- und ausschalten
			for (int repeat = 0; repeat < 3; repeat++) // Loop für dreifache Ausführung Reihen ein- und ausschalten
														// (repeat zählt Vorgänge)
			{
				for (Led[] row : leds) // geht Zeile für Zeile durch das zweidimensionale Array leds
				{
					for (int i = row.length - 1; i >= 0; i--) // Loop schaltet LEDs innerhalb jeder Zeile von rechts
																// nach links ein.
					{
						row[i].turnOn(); // Methode zum einschalten der LEDs
						boardService.pauseExecution(pausetime); // Pause von 250 Milisekunden
					}
				}

				boardService.pauseExecution(pausetime); // Nochmals eine Pause von 250 Milisekunden

				// LEDs ausschalten von links nach rechts
				for (Led[] row : leds) // geht Zeile für Zeile durch das zweidimensionale Array leds
				{
					for (Led led : row) // durchläuft jede Zeile von row von links nach rechts, da die LEDs der Zeile in
										// ihrer natürlichen Reihenfolge durchläuft
					{
						led.turnOff(); // Methode zum Ausschalten jedes LEDs
						boardService.pauseExecution(pausetime); // Pause von 250 Milisekunden
					}
				}

				boardService.pauseExecution(pausetime); // nochmal eine Pause von 250 Milisekunden
			}

			boardService.removeAllLeds(); // Entfernt LEDs
		}
	}

	// Aufgabe 2

	/**
	 * Mehtode, welche zuerst die Lampen mit der geraden Anzahl einschaltet.
	 * Anschliessend werden die ungeraden eingeschatlet.
	 * 
	 */

	private static void switchEvenOdd(BoardService boardService, Scanner scanner) {

		// Eingabe
		System.out.print("Anzahl der LED-Reihen eingeben: ");
		int rows = scanner.nextInt();

		// Maximal zulässige Anzahl von Reihen überprüfen
		int maxRows = BoardService.MAX_ROWS;
		if (rows > maxRows) {
			System.out.println("Maximale Anzahl von Reihen überschritten, Setze auf Maximum.");
			rows = maxRows;
		}

		// Anzeigen der LEDs
		Led[][] leds = boardService.add(rows);

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// Alles 3 Mal wiederholen
		for (int i = 0; i <= 3; i++) {
			// Gerade Anzahl LEDs einschalten
			for (int y = 0; y < rows; y++) { // Schleife für y Koordinate
				for (int x = 0; x < leds[y].length; x++) { // Schleife für X Koordinate
					if (leds[y][x].getLedId() % 2 == 0) { // Abfrage ob ID des LEDS gerade ist
						leds[y][x].turnOn(); // Einschalten wenn LED gerade ist
						boardService.pauseExecution(300); // Warten
					}
				}
			}

			// Pause von einer Sekunden
			boardService.pauseExecution(1000);

			for (int y = 0; y < rows; y++) { // Schleife für y Koordinate
				for (int x = 0; x < leds[y].length; x++) { // Schleife für X Koordinate
					if (leds[y][x].isOn()) { // Abfrage ob das LED eingeschaltet ist
						leds[y][x].turnOff(); // LED ausschalten
					} else {
						leds[y][x].turnOn(); // Ansonsten LED einschalten
					}
					boardService.pauseExecution(300); // Warten damit das Programm nicht zu schnell läuft

				}
			}

		}

		// Abschalten aller LEDs
		for (int y = 0; y < rows; y++) { // Schleife für y Koordinate
			for (int x = 0; x < leds[y].length; x++) { // Schleife für X Koordinate
				leds[y][x].turnOff(); // LED ausschalten

			}
		}

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// Alle LEDs entfernen, um den Zustand zu bereinigen
		boardService.removeAllLeds();

	}

	// Aufgabe 3

	/**
	 * Methode welche Lampen mit zufälligen Farben einfügt und anschliessend die
	 * Hälte der Lampen zufällig einschaltet. Anschliessend invertiert es die Lampen
	 * mehrfach. Javu util Random im Einsatz
	 */

	private static void switchRandom(BoardService boardService, Scanner scanner) {

		// Eingabe
		System.out.print("Anzahl der LED-Reihen eingeben: ");
		int rows = scanner.nextInt();

		// Maximal zulässige Anzahl von Reihen überprüfen
		int maxRows = BoardService.MAX_ROWS;
		if (rows > maxRows) {
			System.out.println("Maximale Anzahl von Reihen überschritten, Setze auf Maximum.");
			rows = maxRows;
		}

		// Anzeigen der LEDs
		LedColor color = LedColor.RANDOM;
		Led[][] leds = boardService.add(rows, color);

		// Random
		Random random = new Random();

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// Array grenzen definieren
		rows = leds.length; // Anzahl rows definieren
		int cols = leds[0].length; // Anzahl cols definieren
		int ledCount = rows * cols; // Anzahl LEDs definieren
		int ledsToTurnOn = ledCount / 2; // Anzahl der LEDs definieren, die eingeschaltet werden müssen

		int ledsOn = 0; // Startwert definieren

		while (ledsOn < ledsToTurnOn) { // Solange ledsOn kleiner ist als ledsToTurnOn wird die Schleife ausgeführt
			// Generieren von zufälligen Indizes innerhalb der Array-Grenzen
			int randomRow = random.nextInt(rows); // zufällige Row Nummer
			int randomCol = random.nextInt(cols); // zufällige Col Nummer

			// Schalten Sie die LED nur ein, wenn sie noch ausgeschaltet ist
			if (!leds[randomRow][randomCol].isOn()) { // Abfrage ob LED nicht ausgeschalte ist
				leds[randomRow][randomCol].turnOn(); // LED einschalten
				ledsOn++; // ledsOn Copunter erhöhen
			}
		}

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// Code 3 Mal ausführen um es besser anzuzeigen
		for (int i = 0; i <= 3; i++) {
			// LEDs umschalten (invertieren)
			for (int y = 0; y < rows; y++) { // Schleife für y Koordinate
				for (int x = 0; x < leds[y].length; x++) { // Schleife für X Koordinate
					if (leds[y][x].isOn()) { // Abfrage ob das LED eingeschaltet ist
						leds[y][x].turnOff(); // LED ausschalten
					} else {
						leds[y][x].turnOn(); // Ansonsten LED einschalten
					}

				}
			}
			// Pause von 0.5 Sekunden
			boardService.pauseExecution(500);
		}

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// Alle LEDs entfernen, um den Zustand zu bereinigen
		boardService.removeAllLeds();

	}

	// Aufgabe 4: Zufälliges Bitmuster und Dezimalanzeige
	private static void showDecimal(BoardService boardService) {
		// Füge eine LED-Reihe hinzu und bestimme die Anzahl der LEDs in dieser Reihe
		Led[][] leds = boardService.add(1); // Eine Reihe hinzufügen
		int numLeds = leds[0].length; // Anzahl der LEDs in der ersten (und einzigen) Reihe speichern
		Random random = new Random(); // Neues Objekt der Klasse Random erstellen

		int decimalValue = 0; // Initialisierung dezimal-Wert
		boolean[] ledStates = new boolean[numLeds]; // Array zur Speicherung des LED-Zustands erstellen
		int pausetime = 500; // Initialisierung Pause von 500 Milisekunden

		// Bitmuster generieren
		for (int i = 0; i < numLeds; i++) // Loop für die Anzahl LEDs
		{
			ledStates[i] = random.nextBoolean(); // Jede stelle des Arrays ledStates wird zufällig auf true oder false
													// gesetzt und in ledStates gespeichert

			// LED ein- oder ausschalten
			if (ledStates[i]) {
				leds[0][i].turnOn(); // Wenn einzelne Stelle des Arrays auf true ist, schalte Lampe an
			} else {
				leds[0][i].turnOff(); // Wenn einzelne Stelle des Arrays auf false ist, schalte Lampe aus
			}

			boardService.pauseExecution(pausetime); // Pause von 500 Milisekunden

			// Berechne den Dezimalwert basierend auf dem Bitmuster
			if (ledStates[i]) // Wenn LED (i) auf true ist dann...
			{
				decimalValue += (1 << (numLeds - 1 - i)); // Umrechnung von Binär in Dezimal: 0 + (1<<(anzahl LEDs - 1 -
															// position des LEDs))
				// 1<< Schiebeoperatoren verschieben ihren ersten Operanden um die Anzahl von
				// Positionen nach links (<<) also quasi 2^(x)
			}
		}

		// Prüfen auf negative Zahl, falls Most Significant Bit gesetzt ist
		// (Zweierkomplement)
		if (ledStates[0]) // Array stelle 0 ist das MSB. Wenn es true ist dann...
		{
			decimalValue -= (1 << numLeds); // subtrahiere von 0: 2^numLeds = Dezimalzahl
		}

		System.out.println("Die dargestellte Dezimalzahl ist: " + decimalValue); // Ausgabe der Dezimalzahl

		// Anzeige zurücksetzen
		boardService.removeAllLeds();
	}

	// Aufgabe 5

	public static void drawBorder() {
		BoardService boardService = new BoardService();

		// Maximale Anzahl LED-Reihen dem Board hinzfügen
		Led[][] led = boardService.add(BoardService.MAX_ROWS);

		// Für 2 Sekunden halten
		boardService.pauseExecution(2000);

		// Schaltet alle Rand Leds an

		// Schleife um alle Reihen zwischen den Rand-Reihen herauszufiltern
		for (int reihe = 0; reihe < BoardService.MAX_ROWS; reihe++) {
			// Schleife um alle Kollonen zwischen den Rand-Kollonen herauszufiltern
			for (int kollone = 0; kollone < BoardService.LEDS_PER_ROW; kollone++) {
				// Nur die Ränder werden angeschalten
				if (reihe == 0 || reihe == BoardService.MAX_ROWS - 1 || kollone == 0
						|| kollone == BoardService.LEDS_PER_ROW - 1) {
					led[reihe][kollone].turnOn();

				}
			}
		}

		// Für 2 Sekunden halten
		boardService.pauseExecution(2000);

		// Schaltet alle Rand Leds aus (Gleich wie anschalten nur am Schluss .turnOff)
		// Schleife um alle Reihen zwischen den Rand-Reihen herauszufiltern
		for (int reihe = 0; reihe < BoardService.MAX_ROWS; reihe++) {
			// Schleife um alle Kollonen zwischen den Rand-Kollonen herauszufiltern
			for (int kollone = 0; kollone < BoardService.LEDS_PER_ROW; kollone++) {
				// Nur die Ränder werden ausgeschalten
				if (reihe == 0 || reihe == BoardService.MAX_ROWS - 1 || kollone == 0
						|| kollone == BoardService.LEDS_PER_ROW - 1) {
					led[reihe][kollone].turnOff();

				}
			}
		}

		// Für 2 Sekunden halten
		boardService.pauseExecution(2000);

		// Anzeige zurückschalten
		boardService.removeAllLeds();
	}

	// Aufgabe 6.1
	// Öffentliche Statische Methode ohne Rückgabewert
	public static void drawSquare() {
		// Erstellung neues Objekt für die Verwendung der Variablen aus dem BoardService
		BoardService boardService = new BoardService();
		// Erstellung Scanner-Objekt
		Scanner scanner = new Scanner(System.in);
		// Array erstellen mit der maximalen Anzahl LED-Reihen dem Board hinzfügen
		Led[][] led = boardService.add(BoardService.MAX_ROWS);

		// Engabe der x-Koordinaten
		System.out.println("x-Koordinaten: ");
		int koordinatex = scanner.nextInt();

		// Engabe der y-Koordinaten
		System.out.println("y-Koordinaten: ");
		int koordinatey = scanner.nextInt();

		// Eingabe der Länge
		System.out.println("Länge: ");
		int lange = scanner.nextInt();

		// Bedingung für die Einschaltung
		if (koordinatex + lange <= BoardService.MAX_ROWS && koordinatey + lange <= BoardService.LEDS_PER_ROW) {
			// Länge des Quadrats
			for (int lange01 = koordinatex; lange01 < (koordinatex + lange); lange01++) {
				// Breite des Quadrats
				for (int breite01 = koordinatey; breite01 < (koordinatey + lange); breite01++) {
					// Einschalten der LED auf der gegeben Grösse
					if (lange01 == koordinatex || lange01 == (koordinatex + lange - 1) || breite01 == koordinatey
							|| breite01 == (koordinatey + lange - 1)) {
						// Die trunOn Methode ruft die Klasse led auf
						led[lange01][breite01].turnOn();
					}
				}
			}
			// Meldung falls die Eingabe ungültig ist
		} else {
			System.out.println("Diese Eingabe ist ungültig und befindet sich nicht mehr auf dem LED-Board");
		}

		// Für 2 Sekunden halten
		boardService.pauseExecution(2000);

		// Alle LEDs entfernen, um den Zustand zu bereinigen
		boardService.removeAllLeds();
	}

	// Aufgabe 6.2
	public static void drawSquareWithDiagonals() {
		// Erstellung neues Objekt für die Verwendung der Variablen aus dem BoardService
		BoardService boardService = new BoardService();
		// Erstellung Scanner-Objekt
		Scanner scanner = new Scanner(System.in);
		// Array erstellen mit der maximalen Anzahl LED-Reihen dem Board hinzfügen
		Led[][] led = boardService.add(BoardService.MAX_ROWS);

		// Engabe der x-Koordinaten
		System.out.println("x-Koordinaten: ");
		int koordinatex = scanner.nextInt();

		// Engabe der y-Koordinaten
		System.out.println("y-Koordinaten: ");
		int koordinatey = scanner.nextInt();

		// Eingabe der Länge
		System.out.println("Länge: ");
		int lange = scanner.nextInt();

		// Bedingung für die Einschaltung
		if (koordinatex + lange <= BoardService.MAX_ROWS && koordinatey + lange <= BoardService.LEDS_PER_ROW) {
			// Länge des Quadrats
			for (int lange01 = koordinatex; lange01 < (koordinatex + lange); lange01++) {
				// Breite des Quadrats
				for (int breite01 = koordinatey; breite01 < (koordinatey + lange); breite01++) {
					// Einschalten der LED auf der gegeben Grösse
					if (lange01 == koordinatex || lange01 == (koordinatex + lange - 1) || breite01 == koordinatey
							|| breite01 == (koordinatey + lange - 1)) {
						led[lange01][breite01].turnOn();
					}
				}
			}
			// Meldung falls die Eingabe ungültig ist
		} else {
			System.out.println("Diese Eingabe ist ungültig und befindet sich nicht mehr auf dem LED-Board");
		}
		// For Schleife
		for (int i = 0; i < lange; i++) {
			// Erstellung der Diagonalen
			led[koordinatex + i][koordinatey + i].turnOn();
			// Erstellung Gegendiagonale
			led[koordinatex + i][koordinatey + lange - 1 - i].turnOn();
		}

		// Für 2 Sekunden halten
		boardService.pauseExecution(10000);

		// Anzeige zurückschalten
		boardService.removeAllLeds();

	}

	// Aufgabe 7
	public static void drawRectangle() {
		// Erstellung neuer Instanz von der Klasse BoardService
		BoardService boardService = new BoardService();
		// Erstellung neuer Instanz von der Klasse Scanner
		Scanner scanner = new Scanner(System.in);
		// Maximale Anzahl LED-Reihen dem Board hinzfügen
		Led[][] led = boardService.add(BoardService.MAX_ROWS);

		// Engabe der x-Koordinaten
		System.out.println("x-Koordinaten vom Punkt 1: ");
		int xkoordinateP1 = scanner.nextInt();

		// Engabe der y-Koordinaten
		System.out.println("y-Koordinaten vom Punkt 1: ");
		int ykoordinateP1 = scanner.nextInt();

		// Engabe der x-Koordinaten
		System.out.println("x-Koordinaten vom Punkt 2: ");
		int xkoordinateP2 = scanner.nextInt();

		// Engabe der y-Koordinaten
		System.out.println("y-Koordinaten vom Punkt 2: ");
		int ykoordinateP2 = scanner.nextInt();

		int differenzlange = xkoordinateP2 - xkoordinateP1;
		int differenzbreite = ykoordinateP2 - ykoordinateP1;

		// Erstellung gefülltes Quadrat
		// bedinung - muss auf dem Board sein
		if (xkoordinateP1 <= xkoordinateP2 && ykoordinateP1 <= ykoordinateP2 && xkoordinateP2 <= BoardService.MAX_ROWS
				&& ykoordinateP2 <= BoardService.LEDS_PER_ROW) {
			// Schleife für das gefüllte Quadrat
			for (int lange = xkoordinateP1; lange <= xkoordinateP2; lange++) {
				for (int breite = ykoordinateP1; breite <= ykoordinateP2; breite++) {
					// Anschalten der LEDs für gefülltes Quadrat
					led[lange][breite].turnOn();
				}
			}
		} else {
			System.out.println("fehler - Eingabe ungültig");
		}
		// 3 Sekunden warten
		boardService.pauseExecution(3000);

		// Schleife für das quardat
		// der innere Teil wird wider gelöscht - nur der Rahme bleibt
		for (int lange01 = xkoordinateP1 + 1; lange01 <= xkoordinateP2 - 1; lange01++) {
			for (int breite01 = ykoordinateP1 + 1; breite01 <= ykoordinateP2 - 1; breite01++) {
				led[lange01][breite01].turnOff();
			}
		}
		// 3 Sekunden warten
		boardService.pauseExecution(3000);
		// Der Rahme erhält einen neue Farbe von LEDs - Blau
		for (int lange02 = xkoordinateP1; lange02 <= xkoordinateP2; lange02++) {
			for (int breite02 = ykoordinateP1; breite02 <= ykoordinateP2; breite02++) {
				if (lange02 == xkoordinateP1 || lange02 == xkoordinateP2 || breite02 == ykoordinateP1
						|| breite02 == ykoordinateP2) {
					led[lange02][breite02].turnOn();
					boardService.replace(led[lange02][breite02], LedColor.BLUE);
				}
			}
		}
		// 3 Sekunden warten
		boardService.pauseExecution(3000);

		// Anzeige zurückschalten
		boardService.removeAllLeds();

	}

	// Aufgabe 8
	public static void drawTriangle() {
		// Erstellung neuer Instanz von der Klasse BoardService
		BoardService boardService = new BoardService();
		// Erstellung neuer Instanz von der Klasse Scanner
		Scanner scanner = new Scanner(System.in);
		// Maximale Anzahl LED-Reihen dem Board hinzfügen
		Led[][] led = boardService.add(BoardService.MAX_ROWS);

		// Engabe der höhe
		System.out.println("Geben sie die Höhe ein: ");
		int hohe = scanner.nextInt();
		// Bedingung befinieren
		if (hohe >= 2 && hohe <= (boardService.MAX_ROWS / 2)) {
			// For Schelife für die Höhe im Dreieck
			for (int i = 0; i < hohe; i++) {
				// Erstellung der Breite anhand der höhe Variable
				int breite = (i * 2) + 1;
				// Startbedingnung ab wann die Beite zunimmt
				int start = (hohe - 1) - i;
				// For Schleife für die Breite im Dreieck
				for (int j = start; j < start + breite; j++) {
					// Einschalten des Dreiecks
					led[i][j].turnOn();
				}
			}
		} else {
			// Fehler bei ungültiger Eingabe
			System.out.println("Die Eingabe ist ungültig");
		}
		// warten con 10sek
		boardService.pauseExecution(10000);
		// Löschen von allen LEDs
		boardService.removeAllLeds();

	}

	// Aufgabe 10.1

	/**
	 * In dieser Methode wird zuerst die maximale Anzahl LEDs mit zufälliger Farbe
	 * angezeigt. Anschliessend wärden die einzelnen Farben gezählt und ausgegeben.
	 */

	private static void countColors(BoardService boardService) {

		// Maximal zulässige Zeilen
		int maxRows = BoardService.MAX_ROWS;

		// Maximale Anzahl LEDs mit zufälliger Farbe einfügen
		LedColor color = LedColor.RANDOM;
		Led[][] leds = boardService.add(maxRows, color);

		// LEDs einschalten
		for (int y = 0; y < maxRows; y++) { // Schleife für y Koordinate
			for (int x = 0; x < leds[y].length; x++) { // Schleife für X Koordinate
				leds[y][x].turnOn();
			}
		}

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// LEDS zählen
		int red = 0;
		int green = 0;
		int yellow = 0;
		int blue = 0;

		for (int y = 0; y < maxRows; y++) { // Schleife für y Koordinate
			for (int x = 0; x < leds[y].length; x++) { // Schleife für X Koordinate
				switch (leds[y][x].getColor()) { // Abfrage welche Farbe LED hat
				case RED:
					// Falls rot, rot Counter erhöhen
					red++;
					break;
				case GREEN:
					// Falls grün, grün counter erhöhen
					green++;
					break;
				case YELLOW:
					// Falls gelb, gelb Counter erhöhen
					yellow++;
					break;
				case BLUE:
					// Falls blau, blau Counter erhöhen
					blue++;
					break;

				}

			}
		}

		// Ausgabe:
		System.out.println("RED: " + red + " " + "LEDs");
		System.out.println("GREEN: " + green + " " + "LEDs");
		System.out.println("YELLOW: " + yellow + " " + "LEDs");
		System.out.println("BLUE: " + blue + " " + "LEDs");

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// Alle LEDs entfernen, um den Zustand zu bereinigen
		boardService.removeAllLeds();

	}

	// Aufgabe 10.2
	private static void countColorExt(BoardService boardService, Scanner scanner) {

		// Maximal zulässige Zeilen
		int maxRows = BoardService.MAX_ROWS; // die maximale Anzahl Reihen in int maxRows speichern

		// Maximale Anzahl LEDs mit zufälliger Farbe einfügen
		LedColor randomColor = LedColor.RANDOM; // Aus der Klasse LedColor ein Objekt namens randomColor erstellen (es
												// hat eine zufällige Farbe)
		Led[][] leds = boardService.add(maxRows, randomColor); // zweidimensionales Array namens leds erstellen
		// mit add Methdode maximale anzahl Reihen und zufällig gefärbte LEDs einfügen

		// LEDs einschalten
		for (int y = 0; y < maxRows; y++) { // Schleife für y Koordinate
			for (int x = 0; x < leds[y].length; x++) { // Schleife für X Koordinate
				leds[y][x].turnOn();// Methode zum LEDs einschalten
			}

		}

		// Arrays zur Speicherung der maximalen Anzahl LEDs pro Farbe und Zeile
		int[] maxCountPerColor = new int[LedColor.values().length]; // Array, das für jede Farbe die maximale Anzahl von
																	// LEDs in einer Zeile speichert.
		// Jede Farbe hat einen bestimmten Index (z.B. rot = 0, grün = 1)

		int[] maxRowPerColor = new int[LedColor.values().length];
		// Array, das für jede Farbe die Zeilennummer speichert, in der die maximale
		// Anzahl LEDs dieser Farbe gefunden wurde.

		// Schleife zur Zählung der LEDs pro Zeile und Farbe
		for (int y = 0; y < maxRows; y++) // Iteriert über jede Zeile im leds-Array
		{
			int[] colorCount = new int[LedColor.values().length]; // Zähler pro Farbe für die aktuelle Zeile

			for (int x = 0; x < leds[y].length; x++) // Innere Schleife, welche durch jede LED in der aktuellen Zeile
														// (y) geht

			{
				LedColor color = leds[y][x].getColor(); // liefert die Farbe der LED von jeweiliger Position (x/y)
				colorCount[color.ordinal()]++; // Zählt eine LED in der colorCount-Liste bei dem Index, der der Farbe
												// entspricht (z.B. rot = 0)

				// Nach dieser Schleife enthält das Array colorCount die Anzahl LEDs pro Farbe
				// für die aktuelle Zeile
			}

			// Aktualisiere maximale Zählwerte pro Farbe, wenn erforderlich
			for (LedColor color : LedColor.values()) // Loop, welcher durch alle Werte des enums LedColor iteriert
			// LedColor.values() gibt Array aller Werte aus Enum zurück, also RED, GREEN,
			// BLUE und YELLOW
			{
				int colorIndex = color.ordinal();// ordinal Methode gibt für jeden Wert der Farbe eine Zahl zurück z.B.
													// RED.ordinal() = 0, GREEN.ordinal() = 1
				if (colorCount[colorIndex] > maxCountPerColor[colorIndex])
				// Anzahl der LEDs mit aktueller Farbe wird verglichen ob er grösser ist als der
				// bisherige Maximalwert
				{
					maxCountPerColor[colorIndex] = colorCount[colorIndex];
					// Wenn aktuelle Zeile mehr LEDs einer bestimmten Farbe hat, wird dieser Wert
					// als neuer maxCountPerColor gesetzt

					maxRowPerColor[colorIndex] = y;
					// Die aktuelle Zeilennummer (mit der höchsten Anzahl der jeweiligen farbe) wird
					// in maxCountPerColor gespeichert
				}
			}
		}

		{
			// Ergebnisse ausgeben
			// Ausgabe für RED

			System.out.println("RED: " + maxCountPerColor[LedColor.RED.ordinal()] + // Array maxCountPerColor wird der
																					// Inhalt von der jeweiligen Farbe
																					// ausgegeben
					" LEDs in der Zeile-Nr. " + maxRowPerColor[LedColor.RED.ordinal()]);// ordinal dient

			// Ausgabe für GREEN
			System.out.println("GREEN: " + maxCountPerColor[LedColor.GREEN.ordinal()] + " LEDs in der Zeile-Nr. "
					+ maxRowPerColor[LedColor.GREEN.ordinal()]);

			// Ausgabe für BLUE
			System.out.println("BLUE: " + maxCountPerColor[LedColor.BLUE.ordinal()] + " LEDs in der Zeile-Nr. "
					+ maxRowPerColor[LedColor.BLUE.ordinal()]);

			// Ausgabe für YELLOW
			System.out.println("YELLOW: " + maxCountPerColor[LedColor.YELLOW.ordinal()] + " LEDs in der Zeile-Nr. "
					+ maxRowPerColor[LedColor.YELLOW.ordinal()]);

		}
	}

	// Aufgabe 10.3
	public static void countRedLedsOnDiagonals() {
		BoardService boardService = new BoardService();

		// Maximale Anzahl LED-Reihen dem Board hinzfügen einer beliebigen Farbe
		Led[][] led = boardService.add(BoardService.MAX_ROWS, LedColor.GREEN);

		// Alle Leds aktivieren
		for (int reihe = 0; reihe < BoardService.MAX_ROWS; reihe++) {
			for (int kollone = 0; kollone < BoardService.LEDS_PER_ROW; kollone++) {
				led[reihe][kollone].turnOn();
			}
		}

		// Initialisierung der Parameter um die Anzahl roter LEDs zu zählen
		int countRedDiagonal = 0;
		int countRedHilfsdiagonal = 0;

		// Alle roten Leds auf der Haputdiagonale (von oben links nach unten rechts)
		// werden gezählt
		for (int reihe = 0; reihe < BoardService.MAX_ROWS; reihe++) {
			if (led[reihe][reihe].getColor().toString() == "RED") {
				countRedDiagonal++;
			}
		}

		// Alle roten Leds auf der Hilfsdiagonale (von unten links nach oben rechts)
		// werden gezählt
		for (int reihe = BoardService.MAX_ROWS; reihe >= 0; reihe--) {
			for (int kollone = 0; kollone < BoardService.MAX_ROWS; kollone++) {
				if (reihe == kollone && led[reihe][kollone].getColor().toString() == "RED") {
					countRedHilfsdiagonal++;

				}
			}
		}

		// Resultate ausgeben
		System.out.println("RED LEDs Haupt-Diagonale: " + countRedDiagonal);
		System.out.println("RED LEDs Hilfs-Diagonale: " + countRedHilfsdiagonal);

	}
}
