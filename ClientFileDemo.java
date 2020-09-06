import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
public class ClientFileDemo {
	public static void main(String args[]) {
		String s;
		Scanner inputStream = null;
		PrintWriter outputStream = null;
		try {
			Socket clientSocket = new Socket("localhost",6789);			//Creazione del socket all'indirizzo "localhost" sulla porta "6789", dove è in ascolto il server
			
			inputStream = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
			outputStream = new PrintWriter(new DataOutputStream(clientSocket.getOutputStream()));
			
			String testo = "";
			Scanner tastiera = new Scanner(System.in);
			System.out.println("Inserire il nome del file:");
			String nomeFile = tastiera.nextLine();				//Input nome del file da mandare al server
			outputStream.println(nomeFile);						//Invio al socket di stream del nome del file
			outputStream.flush();
	
			//ciclo while per l'input da tastiera delle stringhe da inviare al server fino a quando non si inserisce la stringa "fine"
			while(!testo.equalsIgnoreCase("fine")) {
				testo = tastiera.nextLine();
				outputStream.println(testo);
				outputStream.flush();	
			}
			
			//ciclo while per la lettura dei dati in arrivo sulla socket in entrata fino a quando si ha una riga da leggere.
			while(inputStream.hasNextLine()) {
				s = inputStream.nextLine();
				System.out.println(s);
			}
			//chiusura degli oggetti di stream
			inputStream.close();
			outputStream.close();

		}catch(Exception e) {
			System.out.println("Errore " + e);
		}
	}
}