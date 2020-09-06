import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
public class ServerFileDemo {
	public static void main(String args[]) {
		String s = "";
		Scanner inputStream = null;				//Oggetto per lo stream di lettura
		PrintWriter outputStream = null;		//Oggetto per lo stream di scrittura
		ServerSocket serverSocket = null;		//Canale per la comunicazione
		try {
			System.out.println("In attesa di una connessione...");
			serverSocket = new ServerSocket(6789);			//Creazione del socket sulla porta 6789 tramite costruttore
			Socket socket = serverSocket.accept();			//Impostazione del socket del server pronto ad accettare connessioni
			
			inputStream = new Scanner(new InputStreamReader(socket.getInputStream()));		//Inizializzato l'oggetto per leggere dati dal socket
			outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream())); //Inizializzato l'oggetto per mandare dati tramite quel socket
			PrintWriter streamTesto = null;													//Inizializzato l'oggetto per scrivere sul file
			
			//Lettura del nome del file da aprire, tramite oggetto inputStream
			String nomeFile = inputStream.nextLine(); 
			System.out.println("Apertura del file: "+nomeFile);
			try {
				streamTesto = new PrintWriter(nomeFile);	//Oggetto per la gestione del file
			} catch(FileNotFoundException e) {
				System.out.println("Errore nell'apertura del file "+ nomeFile);
				System.exit(0);
			}
			
			
			//Lettura dei dati in arrivo dal socket fino a quando non viene letta la stringa "fine"
			while (!s.equalsIgnoreCase("fine")) {
				s = inputStream.nextLine();
				streamTesto.println(s);		//Scrittura sul file della stringa letta
			}
			
			//Scrittura sul socket in uscita e invio dati tramite "outputStream.flush()"
			outputStream.println("Bene, ");
			outputStream.flush();
			outputStream.println("Dati scritti correttamente sul file");
			outputStream.flush();

			System.out.println("Chiusura della connessione da "+socket.getRemoteSocketAddress());
			//Chiusura degli oggetti di stream
			outputStream.close();
			inputStream.close();
			streamTesto.close();

		}catch(Exception e) {
			System.out.println("Errore "+e);
		}
	}
}