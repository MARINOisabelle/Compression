import java.io.*;
public class Test{
    public static void main(String[]args) throws Exception{
	try{
	    
	    try{
		Huffman huffman = new Huffman();
		LireBit fl = new LireBit(new FileInputStream(new File("je")));
		//Arbre a = huffman.arbreCompr(fl);
		//a.racine.affiche();
		/*int nb  = a.racine.comptePoids();
		int tab[] = new int [nb*3];
		a.racine.tabArbre(0,0,tab,false);
		for(int i=0;i<tab.length;i++){
		  
		    System.out.println(tab[i]);
		    
		    
		    }*/
		huffman.compression("je","mycomp");   
		fl.close();
		//huffman.compression("je","mycomp");
		
		
	    }
	    catch(IOException e){
		System.out.println("faux");
		e.printStackTrace();
	    }
	}
	catch(FileNotFoundException e){
	    e.printStackTrace();
	    System.out.println("ne marche a");
	}
    }
    public static void afficheTab(int tab[]){
	for(int i=97;i<=127;i++){
	    System.out.println(tab[i]);
	}
    }
}
