import java.io.*;
import java.util.*;
public class Test{
    public static void main(String[]args) throws Exception{
	try{
	    LireBit fl = new LireBit(new FileInputStream(new File("cv.pages")));
	    try{
		/*Huffman huffman = new Huffman();
		
		Arbre a= huffman.arbreCompr(fl);
		CodeLettre[] c = new CodeLettre[256];
		ArrayList<Integer> ar = new ArrayList<Integer>();
		a.racine.dfs(ar,c);
		System.out.println(c[69].code.size());
		afficheList(c[69].code);
		System.out.println("l'autre");
		afficheList(c[127].code);*/
		ArrayList <Integer> array = new ArrayList<Integer>();
		array.add(5);
		array.add(7);
		int n = array.remove(0);
		System.out.println(array.size());
		System.out.println(n);
		fl.close();
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
    public static void afficheList(ArrayList<Integer> a){
	for(int i=0;i<a.size();i++){
	    if(i%3==0)
	    System.out.println("("+i+")");
		
	    System.out.print(a.get(i)+" ");
	    
	}
	System.out.println("fin de liste");
    }
    public static void afficheListP(ArrayList<Poids> a){
	for(int i=0;i<a.size();i++){
	    if(a.get(i) instanceof Feuille)
		System.out.println(a.get(i).poids+"/"+((Feuille)a.get(i)).letter);
	    
	}
	System.out.println("fin de liste");
    }
}
