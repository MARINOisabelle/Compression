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
    
    public int tabArbre(int pl,int pere,int []tab,boolean gauche){
	tab[pl]=this.letter;
	tab[pl+1]=-1;
	tab[pl+2]=-1;
	if(gauche)
	    tab[pere+1]=pl;
	else
	    tab[pere+2]=pl;
	return 0;
    }
}
