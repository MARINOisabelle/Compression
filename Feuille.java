import java.util.*;
import java.io.*;
public class Feuille extends Poids{
    char letter;
    public Feuille(int p,char l){
	this.poids = p;
	this.letter = l;
    }
    public int getPoids(){
	return this.poids;
    }
    public void affiche(){
	System.out.println(this.letter+" "+this.poids);
    }
    public int recherche(char l){
	if(this.letter == l){
	    return 2;
	}
	else{
	    return -1;
	}
    }
    public int comptePoids(){
	return 1;
    }
    public void dfs(ArrayList<Integer> a, CodeLettre[] cl){
	
	//System.out.print(letter+" ");
	cl[(int)letter]= new CodeLettre(a);
	if((int)letter==255)
	    System.out.println("la");
	//System.out.println(cl[letter].code.size());
	//Test.afficheList(cl[letter].code);
	
    }
    public ArrayList<Integer> copieList(ArrayList<Integer> a){
	ArrayList<Integer> n = new ArrayList<Integer>();
	for(int i=0;i<a.size();i++){
	    n.add(a.get(i));
	}
	return n;
    }
    public int tabArbre(int pl,int pere,int []tab,boolean gauche){
	tab[pl]=this.letter;
	tab[pl+1]=-1;
	tab[pl+2]=-1;
	if(gauche)
	    tab[pere+1]=pl;
	else
	    tab[pere+2]=pl;
	return pl;
    }
    public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche){
	l.add((int)this.letter);
	l.add(-1);
	l.add(-1);
	return l.size()-3;
	 
    }
}
