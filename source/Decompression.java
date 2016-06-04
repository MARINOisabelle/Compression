import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
/**
* <b>Compression est une classe qui permet de compresser un fichier.</b>
* @author Lucas LABADENS Isabelle MARINO
*/
class Decompression {
	/**
	* Main
	*/ 
	public static void main(String[] args) throws IOException {
		String in = "";
		String out = "";
		// méthode par défaut 
		if (args.length == 0) {
			System.out.println("Quel est le fichier que vous voulez décompresser ?");
			Scanner sc = new Scanner(System.in);
			in = sc.nextLine();

		}
		// nom du fichier dans la commande
		else {
			in = args[0];
		}
		// On instancie nos objets :
		// fr va lire le fichier
		// fw va écrire dans le nouveau !
		out = in + ".decomp";
		LireBit fr = null;
		EcrireBit fw = null;
		try {
			//ouverture du fichier compressé
			File i = new File(in);
			//stockage de son nombre d'octet
			long tailleIn = i.length() - 1;
			fr = new LireBit(new FileInputStream(i));
			fw = new EcrireBit(new FileOutputStream(new File(out)));
			//lecture du premier octet contenant le code de la méthode de compression
			int methode = fr.read();
			
			//méthode Huffman
			if (methode == 1) {
				System.out.println("Huffman");
				long debut = System.currentTimeMillis();
				Huffman h = new Huffman();
				h.decompression(fr, fw, tailleIn);
				System.out.println(System.currentTimeMillis()-debut + " time" ); 

			}
			//méthode LempelZiv
			else if (methode == 2) {
				long debut = System.currentTimeMillis();
				LempelZiv.decompression(fr, fw);
				System.out.println(System.currentTimeMillis()-debut + " time" ); 
			}
			//fichier non compressé
			else if (methode !=2 && methode !=1) {
				System.out.println("Ce fichier n'a pas été compresser");

			}

		} catch (StackOverflowError e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// Cette exception est levée si l'objet FileInputStream ne trouve
			// aucun fichier
			e.printStackTrace();
		} finally {
			// On ferme nos flux de données dans un bloc finally pour s'assurer
			// que ces instructions seront exécutées dans tous les cas même si
			// une exception est levée !
			try {
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
