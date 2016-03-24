import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner; 

class Compression{
	public static void main(String[]args){
		System.out.println("Quel est le fichier que vous voulez compresser ?");
		Scanner sc= new Scanner(System.in);
		String in = sc.nextLine();
		System.out.println("Quel est le nouveau nom du fichier?");
		String out = sc.nextLine();
		out=out+".txt";
		System.out.println("Vous souhaitez le compresser avec Huffman (1) ou LempelZiv(2)?");
		int methode = sc.nextInt();
		
		FileInputStream fr = null;
		FileOutputStream fw = null;
		try {
				// On instancie nos objets :
				// fr va lire le fichier
				// fw va écrire dans le nouveau !
			fr = new FileInputStream(new File(in));
			fw = new FileOutputStream(new File(out));

			if(methode==1){

			// fw=Huffman.compression(fr, fw);

			}
			if(methode==2){

			 fw= LempelZiv.compression(fr,fw);
			}
			else{
				System.out.println("Vous n'avez pas choisit de méthode de compression.");

			}
		}
		catch(FileNotFoundException e) {
						// Cette exception est levée si l'objet FileInputStream ne trouve
						// aucun fichier
			e.printStackTrace();
		}
		finally{
					// On ferme nos flux de données dans un bloc finally pour s'assurer
					// que ces instructions seront exécutées dans tous les cas même si
					// une exception est levée !
			try {
				if(fr != null)
					fr.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			try{
				if(fw != null)
					fw.close();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}

}
