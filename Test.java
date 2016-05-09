import java.io.*;
import java.util.*;
public class Test{
    public static void main(String[]args) throws Exception{
	try{
	    LireBit fl = new LireBit(new FileInputStream(new File("cv.pages")));
	    try{
		Huffman huffman = new Huffman();
		
		Arbre a= huffman.arbreCompr(fl);
		CodeLettre[] c = new CodeLettre[256];
		ArrayList<Integer> ar = new ArrayList<Integer>();
		a.racine.dfs(ar,c);
		afficheList(c[97].code);
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
}
