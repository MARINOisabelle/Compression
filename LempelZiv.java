import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;

class LempelZiv {

	Arbre a; // arbre de compression 
	int nextPoids; // poids pour le prochain Node a ajouter
	int nbBit; // nb de Node dans l'arbre possible pour la puissance
	int puissance; // puissance de 2 utile por coder les poids des node
	ArrayList<Integer[]> list; // list de décompression 
	int node; // noeud pour la ddécompression 
	int power; // puissance maximale

	public LempelZiv() {
		// création de l'arbre de compression avec initialisation de la racine et des champs nécéssaires
		this.a = new Arbre(new Node(0)); 
		this.nextPoids = 1;
		this.nbBit = 1;
		this.puissance = 0;
		// création de la liste de décompression avec initialisation de la racine et des champs nécéssaires
		this.list = new ArrayList<Integer[]>();
		Integer[] tab = { -1, -1 };
		list.add(0, tab);
		this.power=32;
	}

	/* Fonctions pour la compression */

	// fonction qui transcrit un int en valeur binaire dans un tableau de 32 cases  
	public int[] int2tab(int o) { // sur 32 byte
		if (o == -1)
			return null;
		long mask = (1073741824) * 2L;
		int octet[] = new int[32];

		for (int i = 0; i < power; i++) {
			if ((o & mask) == 0) {
				octet[i] = 0;
				mask = mask >> 1;
			} else {
				octet[i] = 1;
				mask = mask >> 1;
			}
		}
		return octet;
	}

	// écrire la compression
	public void ecrireComp(EcrireBit e, int poids, int bit) throws IOException {
		// transforme le poids du noeud en tableau de O/1
		int[] octet = int2tab(poids);
		// change la puissance si le prochain poids ne peux s'écrire sur le nombre de bit actuel 
		if (this.nextPoids > this.nbBit) {
			this.puissance++;
			this.nbBit = this.nbBit * 2;
		}
		// écris le poids en faisant attention au nombre de bit
		if (power - puissance >= 0) {
			for (int i = power - this.puissance; i < power; i++) {
				e.writeBit(octet[i]);
			}
		}
		// écris le dernier bit lu 
		if (bit == 1 || bit == 0) {
			e.writeBit(bit);
		}

	}

	//analyse du fichier à compresser de façon réccursive
	public Node analyseSource(LireBit l, EcrireBit e, Node n, int i) throws IOException {

		if (i < 8) {
			if (l.octet[i] == 0 && n.left == null) { // si le bit lu est 0 est le fils gauche est null
				//ajout du nouveau fils gauche avec nextPoids
				n.addNode(l.octet[i], this.nextPoids);
				// écriture du noeuds dans le fichier compressé
				this.ecrireComp(e, n.poids, 0);
				//incrémentation de poids
				this.nextPoids++;
				// analyse du prochain bit en allant à la racine
				return analyseSource(l, e, this.a.racine, i + 1);

			} else if (l.octet[i] == 0 && n.left != null) { // si le bit lu est 0 est le fils gauche est non null
				// analyse du prochain bit en allant sur le fils gauche 
				return analyseSource(l, e, (Node) n.left, i + 1);

			} else if (l.octet[i] == 1 && n.right == null) {// si le bit lu est 1 est le fils droit est null				//ajout du nouveau fils droit avec nextPoids
				n.addNode(l.octet[i], this.nextPoids);	
				// écriture du noeuds dans le fichier compressé
				this.ecrireComp(e, n.poids, 1);	
				//incrémentation de poids
				this.nextPoids++;
				// analyse du prochain bit en allant à la racine
				return analyseSource(l, e, this.a.racine, i + 1);

			} else if (l.octet[i] == 1 && n.right != null) {// si le bit lu est 1 est le fils droit est non null
				// analyse du prochain bit en allant sur le fils droit 
				return analyseSource(l, e, (Node) n.right, i + 1);
			}
		}
		return n;

	}

	//lecture du fichier source
	public void lireSource(LireBit l, EcrireBit eb) throws IOException {
		Node n = (Node) this.a.racine;
		try {
  			// on lit le fichier tant que l'on est pas à la fin
			while (l.lire() != false) {
				// analyse de l'octet lu 
				n = analyseSource(l, eb, n, 0);
			}
			// écriture du dernier noeud lu même si on n'est pa en bout d'arbre
			this.ecrireComp(eb, n.poids, l.octet[7]);
			// ériture du dernier bit
			eb.writeLastBit();
			l.close();
		} catch (IOException e) {
			System.out.println("faux");
			e.printStackTrace();
		}
	}

	//fonction prinicipal pour la compression de LempelZiv
	public static void compression(LireBit fr, EcrireBit fw) throws IOException {
		// initialisation des champs et variable
		LempelZiv lz = new LempelZiv();
		// lecture du fichier source
		lz.lireSource(fr, fw);
		System.out.println("Compression : Ziv");
	}

	/* Fonctions pour la décompression */

	// écriture d'un list de bit dans le fichier décompressé
	public static void ecrireList(ArrayList<Integer> a, EcrireBit e)throws IOException {
		//parcours de la liste
		for (int i = a.size(); i > 0; i--) {
			// écriture du bit i
			e.writeBit(a.get(i - 1));
		}

	}

	// transformation d'un tableau de 0/1 en int 
	public int tab2int(int[] tab) {
		int res = 0;
		//parcours du tableau
		for (int i = 0; i < puissance; i++) {
			if (tab[i] == 1) {
				int j = i + 1;
				while (j < puissance) {
					//valeur mise à la puissance de 2
					tab[i] = tab[i] * 2;
					j++;
				}
				//ajout des valeurs
				res = res + tab[i];
			}
		}
		return res;
	}

	//écriture de la décompression 
	public void ecrireDecomp(int pere, int bit, EcrireBit e) throws IOException {
		if (pere >= 0 && (bit == 0 || bit == 1)) {
			//création du nouveau noeud 
			Integer t[] = new Integer[2];
			t[0] = pere;
			t[1] = bit;
			Integer i = pere;
			ArrayList<Integer> ar = new ArrayList<Integer>();
			//ajout du nouveau noeud dans la liste
			list.add(list.size(), t);
			//parcours de la liste
			while (list.get(i)[0] != -1) {
				// ajout du bit correspondant 
				ar.add(this.list.get(i)[1]);
				// incrémentation pour aller au prédécésseur
				i = list.get(i)[0];
			}
			// écriture de la liste des bit correspondant au prédécésseur 
			ecrireList(ar, e);
			// écriture du dernier bit lu 
			e.writeBit(bit);
		}

	}

	public int analyseComp(LireBit l, EcrireBit e, int[] tab) throws IOException {
		if (this.nextPoids > this.nbBit) {
		// change la puissance si le prochain poids ne peux s'écrire sur le nombre de bit actuel 
			this.puissance++;
			this.nbBit = this.nbBit * 2;
		}
		//ré-initialisation de tab
		tab = new int[puissance];
		// lecture de puissance bit 
		for (int i = 0; i < puissance; i++) {
			tab[i] = l.lireBit();
			if (tab[i] == -1) {
				// fin de fichier compressé
				return -1;
			}
		}
		// transformation du tableau en un int 
		node = tab2int(tab);
		// lecture d'un bit
		int k = l.lireBit();
		//écriture de la décompession
		this.ecrireDecomp(node, k, e);
		//incrémentation du prochain poids
		nextPoids++;
		return k;

	}

	//lecture du fichier compressé
	public void lireFichierCompressé(LireBit l, EcrireBit eb) throws IOException {
		//initialisation de tab
		int tab[] = new int[puissance];
		try {
			//lecture du premier octet (numéro de la méthode) 
			l.lire();
			int k = 0;
			while (k != -1) {
				// analyse de la compression tant que le fichier n'est pas fini
				k=this.analyseComp(l, eb,tab);
			}
		} catch (IOException e) {
			System.out.println("faux");
			e.printStackTrace();
		}
	}

	//fonction prinicipal pour la décompression de LempelZiv
	public static void decompression(LireBit fr, EcrireBit fw) throws IOException {	
		// initialisation des champs et variable
		LempelZiv lz = new LempelZiv();
		//lecture du fichier compressé
		lz.lireFichierCompressé(fr, fw);
		System.out.println("Decompression : Ziv");
	}

}
