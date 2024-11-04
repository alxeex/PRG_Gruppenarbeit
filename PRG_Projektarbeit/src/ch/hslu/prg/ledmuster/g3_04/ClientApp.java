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

		//Aufgabe 1.1 LEDs Ein- und Ausschalten in Reihen
		//ledsOnOff(service, scanner);

		// Aufgabe 1.2 LEDs mit Farbe Ein- und Ausschalten
		//ledsColoredOnOff(service, scanner);
		
		// Aufgabe 2:
		//switchEvenOdd(service, scanner);

		//Aufgabe 3
		//switchRandom(service, scanner);
		
		// Aufgabe 4: Dezimalanzeige eines zufälligen Bitmusters
		//showDecimal(service);

		
		

		//Aufgabe 10.1
		//countColors(service);


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
		 * Methode welche Lampen mit zufälligen Farben einfügt und anschliessend die Hälte der Lampen
		 * zufällig einschaltet.
		 * Anschliessend invertiert es die Lampen mehrfach.
		 * Javu util Random im Einsatz
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
		System.out.println("RED: " + red + "LEDs");
		System.out.println("GREEN: " + green + "LEDs");
		System.out.println("YELLOW: " + yellow + "LEDs");
		System.out.println("BLUE: " + blue + "LEDs");

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// Alle LEDs entfernen, um den Zustand zu bereinigen
		boardService.removeAllLeds();

	}

}
