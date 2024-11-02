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

		// ledsOnOff(service, scanner);
		// ledsColoredOnOff(service, scanner);

		// switchEvenOdd(service, scanner);
		switchRandom(service, scanner);

		// showDecimal(service);

		scanner.close();
	}

	// Aufgabe 1
	/**
	 * Methode zum Ein- und Ausschalten der LEDs in Sequenzen.
	 */
	private static void ledsOnOff(BoardService boardService, Scanner scanner) {
		System.out.print("Anzahl der LED-Reihen eingeben: ");
		int rows = scanner.nextInt();

		// Maximal zulässige Anzahl von Reihen überprüfen
		int maxRows = BoardService.MAX_ROWS;
		if (rows > maxRows) {
			System.out.println("Maximale Anzahl von Reihen überschritten, Setze auf Maximum.");
			rows = maxRows;
		}

		// LED-Reihen mit Standardfarbe hinzufügen
		Led[][] leds = boardService.add(rows);

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// LEDs ein- und ausschalten
		toggleLeds(boardService, leds);
	}

	/**
	 * Methode zum Ein- und Ausschalten farbiger LEDs in Sequenzen.
	 */
	private static void ledsColoredOnOff(BoardService boardService, Scanner scanner) {
		System.out.print("Anzahl der LED-Reihen und Farbe (RED, GREEN, BLUE, YELLOW) eingeben: ");
		int rows = scanner.nextInt();
		String colorInput = scanner.next().toUpperCase();

		// Farbwahl auf Enum LedColor prüfen
		LedColor color;
		try {
			color = LedColor.valueOf(colorInput);
		} catch (IllegalArgumentException e) {
			System.out.println("Ungültige Farbe, Standardfarbe ROT wird verwendet.");
			color = LedColor.RED; // Standardfarbe
		}

		// Maximal zulässige Anzahl von Reihen überprüfen
		int maxRows = BoardService.MAX_ROWS;
		if (rows > maxRows) {
			System.out.println("Maximale Anzahl von Reihen überschritten, Setze auf Maximum.");
			rows = maxRows;
		}

		// LED-Reihen mit gewählter Farbe hinzufügen
		Led[][] leds = boardService.add(rows, color);

		// Pause von 2 Sekunden
		boardService.pauseExecution(2000);

		// LEDs ein- und ausschalten
		toggleLeds(boardService, leds);
	}

	/**
	 * Methode, die das Ein- und Ausschalten der LEDs in Sequenzen durchführt.
	 * 
	 * @param boardService der Service für die LED-Steuerung
	 * @param leds         das zweidimensionale Array der LEDs
	 */
	private static void toggleLeds(BoardService boardService, Led[][] leds) {
		// LEDs von rechts nach links in jeder Reihe einschalten
		for (Led[] row : leds) {
			for (int i = row.length - 1; i >= 0; i--) {
				row[i].turnOn();
				boardService.pauseExecution(DEFAULT_PAUSE_TIME);
			}
		}

		// LEDs von links nach rechts in jeder Reihe ausschalten
		for (Led[] row : leds) {
			for (Led led : row) {
				led.turnOff();
				boardService.pauseExecution(DEFAULT_PAUSE_TIME);
			}
		}

		// Alle LEDs entfernen, um den Zustand zu bereinigen
		boardService.removeAllLeds();
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

	// Aufgabe 4

	/**
	 * Methode, die ein zufälliges Bitmuster erzeugt und es in Dezimalform ausgibt.
	 */
	private static void showDecimal(BoardService boardService) {
		int numLeds = 8; // Anzahl der LEDs in der Reihe, die das Bitmuster repräsentieren
		Led[][] leds = boardService.add(1); // Eine LED-Reihe hinzufügen
		Random random = new Random();

		int decimalValue = 0;
		boolean[] ledStates = new boolean[numLeds]; // Array zur Speicherung des LED-Zustands (an/aus)
		int displayPause = 500;

		// Durchlaufe die LEDs von links nach rechts, das höchste Bit befindet sich ganz
		// links
		for (int i = 0; i < numLeds; i++) {
			ledStates[i] = random.nextBoolean(); // Zufällig entscheiden, ob das LED ein- oder ausgeschaltet ist

			// LED-Zustand auf dem Board setzen
			if (ledStates[i]) {
				leds[0][i].turnOn();
			} else {
				leds[0][i].turnOff();
			}

			boardService.pauseExecution(displayPause);

			// Berechne den Wert des Bits und addiere ihn zur Dezimalzahl
			if (ledStates[i]) {
				decimalValue += (1 << (numLeds - 1 - i));
			}
		}

		// Überprüfung auf negative Zahl (Zweierkomplement), falls das höchstwertige Bit
		// gesetzt ist
		if (ledStates[0]) { // Höchstwertiges Bit prüfen
			decimalValue -= (1 << numLeds);
		}

		// Zeige das berechnete Dezimalergebnis an
		System.out.println("Die dargestellte Dezimalzahl ist: " + decimalValue);

		// Bereinige das LED-Board, nachdem die Zahl angezeigt wurde
		boardService.removeAllLeds();
	}

}
