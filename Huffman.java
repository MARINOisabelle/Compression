import java.io.File;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.BufferedInputStream;
import java.nio.channels.*;
import java.io.IOException;

/*classe contenant toute les fonctions nécessaire à la compression et décompression d'Huffman*/
class Huffman{
    
    public Huffman(){
	
    }
    public  int[] compteIter(LireBit fr) { //retourne un tableau correspondant au caractère ascii avec leur nombre d'apparition dans le fichier fr

	int tab[]=new int[256];//on crée un tableau d'entier de taille 256,les caractères ascii allant de 0 à 255
	int oct;
	try{
	    oct = fr.read();
	    while(oct>=0){//on lit tout le fichier octet par octet
		tab[oct]++;//on augmente de 1 le nombre se trouvrant dans le tableau à la position de l'octet lu
		oct = fr.read();
	    }
	}
	catch(Exception e){
		System.out.println("can't read file");
		e.printStackTrace();
	}
	
	
	return tab;
    }
    public void addTri(ArrayList<Poids> a,Poids p){//fonction qui ajoute le poids p dans l'arraylist de poids a tout en la gardant triée par ordre croissant
	if(a.size()!=0){
	    if(a.get(a.size()-1).poids<=p.poids) // si le dernier élement de la liste est plus petit ou égal on ajoute le poids
		a.add(p);
	    else{
		Poids p2 = a.remove(a.size()-1);//on enlève le dernier poids de la liste
		ArrayList<Poids> a2 = new ArrayList<Poids>();
		while(p2.poids>p.poids && a.size()!=0){//tant que celui-ci est supérieur on continue à vider la liste
		    a2.add(p2);//on ajoute les poids enlever à une autre liste
		    p2=a.remove(a.size()-1);
		}
		if(p2.poids<=p.poids){//on rajoute le dernier poids enlever et poids que l'on veut ajouter dans le bonne ordre
		    a.add(p2);
		    a.add(p);
		}
		else{
		    a.add(p);
		    a.add(p2);
		}
		while(a2.size()>0){//on rajoute les poids enlevé prédemment
		    a.add(a2.remove(a2.size()-1));		    
		}
	    }
	}
	else
	    a.add(p);
    }
    
    public void ecrireArbre(Arbre a,EcrireBit fw)throws IOException{ //fonction qui écrit l'arbre a dans le fichier fw
        ArrayList<Integer> list = new ArrayList<Integer>();
	a.racine.ListArbre(list,0,false);//récupère une liste correspondant à l'arbre
	for(int i=0;i<list.size();i++){
	    fw.write(list.get(i)/256);//une octet ne pouvant allez jusqu'a 256 on écrit le chiffre par lequel on doit multiplier l'octet suivant
	    fw.write(list.get(i));//on écrit la liste correspondant à l'arbre
	    
	}
    }
    
    public void ecrireLettre(CodeLettre[] cl,EcrireBit fw,int lettre){//fonction qui écrit le caractère lettre dans le fichier fw avec son nouveau code de compression
	try{
	    for(int i=0;i<cl[lettre].code.size();i++){
		fw.writeBit(cl[lettre].code.get(i));//écrit le code de compression correspondant au caractère lettre
		}
	}
	catch(Exception e){
	    e.printStackTrace();
	    System.out.println("can't write");
	}

    }
    
    public ArrayList<Poids> listeFeuille(LireBit fr){//renvoie une liste trier de poids des feuilles correspondant au caractère du fichier
	int tab[]=compteIter(fr);
	ArrayList<Poids> a = new ArrayList<Poids>();
	for(int i=0;i<tab.length;i++){
	    if(tab[i]!=0){
		addTri(a,new Feuille(tab[i],i));
	    }
	}
	return a;
    }
    public Arbre arbreCompr(LireBit fr){//fonction qui renvoie l'arbre de compression
	ArrayList<Poids> a = listeFeuille(fr);
	int taille = a.size();
	Arbre arbre = new Arbre(new Node(0)) ;//crée un arbre de racine avec un noeud unique de poids 0
	while(taille>0){//répète l'opération tant que la liste n'est pas vide
	    Poids p1 = a.remove(0);//on récupère le premier Poids de la liste
	    if(taille==1){//il s'agit du cas ou le fichier n'à qu'un caractère
		Node racine = new Node(p1.poids);//on crée une racine de poids égal au poids de la feuille lié au caractère unique
		racine.left=p1;//on associe cette feuille comme fils gauche
		arbre.racine =racine;//on défine ce poids comme racine
		break;
	    }
	    
	    else{
		taille--;
		Poids p2=a.remove(0);//on récupère le deuxième poids le plus faible de la liste
		taille--;
		Node racine = new Node(p1.poids+p2.poids);//on créee un Node de poids égal à la somme des deux poids enlever
		if(p1.poids>p2.poids){//on relie les deux poids enlever à ce Node
		    racine.right =p2;
		    racine.left = p1;
		}
		else{
		    racine.right =p1;
		    racine.left = p2;
		}
		if(taille==0){//la liste est vide
		    arbre.racine=racine;//on considère le dernier poids de la liste comme racine de l'arbre
		    break;
		}
		else{   
		    addTri(a,racine);
		    taille++;
		}
	    }
	}
	return arbre;//on revoie l'arbre
	
    }
	
	


    
    public ArrayList<Integer> listDec(LireBit l) throws IOException{//fonction qui revoie une liste contenant l'arbre de compression
	int taille =l.read();//on lit la taille du fichier
	while(taille%255==0){//celle ci peut être codé sur 3 octets
	    taille=taille+l.read();
	}
	ArrayList<Integer> list = new ArrayList<Integer>();
	int tab[]=new int[3];
	for(int i=0;i<taille*3;i++){
	    int mult = l.read();//on regarde par combien on doit multiplier l'octet suivant
	   int j =l.read()+256*mult;
	   list.add(j);
	   
	}
	return list;
    }
    
	/*  fonction pour compresser avec l'algorithme d'Huffman */
    public  void compression(String fr, String fw)throws Exception{
	LireBit fl = new LireBit(new FileInputStream(new File(fr)));
	Arbre a = arbreCompr(fl);//premiere lecture du fichier pour construire l'arbre de compression
	fl.close();
	fl =  new LireBit(new FileInputStream(new File(fr)));
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File(fw)));
	fe.write(1);//permet de préciser que le fichier est compresser avec Huffman
	int taille=a.taille();//recupere la taille de l'arbre
	while(taille>=255){//écrit la taille de l'arbre dans le fichier
	    fe.write(255);
	    taille=taille-255;
	}
	fe.write(taille);
	ecrireArbre(a,fe);//écrit l'arbre dans le fichier
	CodeLettre[] c = a.codeComp();//récupèration des codes bit à bit de chaque lettre
	int l;
	while((l=fl.read())!=-1 ){
	    ecrireLettre(c,fe,l);
	    }
	    fe.writeLastBit();
	fe.close();
	fl.close();
    }
    /*fonction pour décompresser un fichier compressé avec l'algorithme d'Huffman*/
    public  void decompression(LireBit fr, EcrireBit fw,long le)throws IOException{
	ArrayList<Integer> list = listDec(fr);
	int placement = 0;
	int taille =  list.size()/(255*3);
	for(long i=0;i<le-list.size()*2-2-taille;i++){
	    int b=0;
	    fr.lire();
	    for(int j=0;j<8;j++){
		if(list.get(placement+1)!=255 || list.get(placement+2)!=255){			
		    placement=list.get(placement+1+fr.octet[j]);
		}
		else{
		    fw.write(list.get(placement));
			j=j-1;
			placement = 0;
		}
	    }
	}
	    fr.lire();
	    int d;
	    for(d=0;d<8;d++){
		if(fr.octet[7-d]==1)
		    break;
	    }
	    for(int j=0;j<8-d;j++){
		if(list.get(placement)==255)
		    placement=list.get(placement+1+fr.octet[j]);
		else{
		    fw.write(list.get(placement));
		    placement=0;
		    j=j-1;
		}
	    }
    }
}



