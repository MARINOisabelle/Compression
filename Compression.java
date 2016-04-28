import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner; 

class Compression{
	public static void main(String[]args) throws IOException{
		int methode=0;
		String in="";
		String out="";
		if(args[0].equals("-i")){ 
			System.out.println("Quel est le fichier que vous voulez compresser ?");
			Scanner sc= new Scanner(System.in);
			in = sc.nextLine();
			System.out.println("Vous souhaitez le compresser avec Huffman (1) ou LempelZiv(2)?");
			methode = sc.nextInt();
		}
		else{
			if(args[0].equals("-lz")){ methode =2;}
			if(args[0].equals("-h")){ methode =1;}
			in = args[1];
		}	
		out=in+".comp";
		LireBit fr = null;
		EcrireBit fw = null;
		try {
				// On instancie nos objets :
				// fr va lire le fichier
				// fw va écrire dans le nouveau !
			if(methode==1){
				//fw.write(1);
				
				//Huffman.compression(in, out);

			}
			else if(methode==2){
			    	fr =new LireBit( new FileInputStream(new File(in)));
				fw = new EcrireBit( new FileOutputStream(new File(out)));
				fw.write(2);
				long debut = System.currentTimeMillis();
			 	LempelZiv.compression(fr,fw);
				System.out.println(System.currentTimeMillis()-debut + " time" ); 
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
