import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;


class LempelZiv{

	Arbre a;
	int nextPoids;

	public LempelZiv(){
		this.a =new Arbre(new Node(0));
		this.nextPoids=1;
	}

	public static void testcopie(FileInputStream fr, FileOutputStream fw){
		try{// On crée un tableau de byte pour indiquer le nombre de bytes lus à
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
		}
	}


	public  Node analyseOctet(LireBit l, Node n, int i){
		if(i<8){
			System.out.println("octet " + l.octet[i]);
			if(l.octet[i]==0 && n.left==null){
				this.a.addNode(l.octet[i], this.nextPoids, n);
				System.out.println("ajout 0");
				return analyseOctet(l, (Node)this.a.racine, i+1);

			}
			else if(l.octet[i]==0 && n.left!=null){
					return analyseOctet(l, (Node)n.left, i+1);

			}
			else if(l.octet[i]==1 && n.right==null){
				this.a.addNode(l.octet[i], this.nextPoids, n);	
				System.out.println("ajout 1");
				return analyseOctet(l, (Node)this.a.racine, i+1);
			}
			else if(l.octet[i]==1 && n.right!=null){
				return analyseOctet(l, (Node)n.right, i+1);
			}
		}
		return n;

	}

	public  void lireFichier(FileInputStream fr){
			LireBit l = new LireBit(fr);
			Node n = (Node)this.a.racine;
			try{
				while(l.lire()!=false){	
				//	l.lire();
					n=analyseOctet(l, n, 0);
					for(int i=0;i<8;i++){
						System.out.println(l.octet[i]);
					}
					

				}
				l.close();
			}
			catch(IOException e){
			System.out.println("faux");
			e.printStackTrace();
			}
    }

	
	public static FileOutputStream ecrireFichier(FileOutputStream fw){

		return fw;
	}

	public static FileOutputStream compression(FileInputStream fr, FileOutputStream fw){
		LempelZiv lz= new LempelZiv();
		lz.lireFichier(fr);
		fw=ecrireFichier(fw);
		System.out.println("Ziv");
	
		return fw;
	}

}
