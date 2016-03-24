import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class LempelZiv{
	public static FileOutputStream compression(FileInputStream fr, FileOutputStream fw){
		/*try{// On crée un tableau de byte pour indiquer le nombre de bytes lus à
						// chaque tour de boucle
						byte[] buf = new byte[8];
						// On crée une variable de type int pour y affecter le résultat de
						// la lecture
						// Vaut -1 quand c'est fini
						int n = 0;
						// Tant que l'affectation dans la variable est possible, on boucle
						// Lorsque la lecture du fichier est terminée l'affectation n'est
						// plus possible !
						// On sort donc de la boucle
						while ((n = fr.read(buf)) >= 0) {
						// On écrit dans notre deuxième fichier avec l'objet adéquat
						fw.write(buf);
						// On affiche ce qu'a lu notre boucle au format byte et au
						// format char
						for (byte bit : buf) {
						System.out.print("\t" + bit + "(" + (char) bit + ")");
						System.out.println("");
						}
						//Nous réinitialisons le buffer à vide
						//au cas où les derniers byte lus ne soient pas un multiple de 8
						//Ceci permet d'avoir un buffer vierge à chaque lecture et ne pas avoir de doublon en fin de fichier
						buf = new byte[8];
						}
						System.out.println("Copie terminée !");
		}
		catch(FileNotFoundException e) {
						// Cette exception est levée si l'objet FileInputStream ne trouve
						// aucun fichier
			e.printStackTrace();
		}
				catch(IOException e) {
						// Celle-ci se produit lors d'une erreur d'écriture ou de lecture
						e.printStackTrace();
		}*/
		System.out.println("Ziv");

		return fw;
	}

}
