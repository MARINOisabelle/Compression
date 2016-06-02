import java.util.*;
import java.io.*;
public class Feuille extends Poids{
    int letter;
    public Feuille(int p,int l){//une feuille possède un poids et une lettre
	this.poids = p;
	this.letter = l;
    }
    public int getPoids(){
	return this.poids;
    }
    public void affiche(){//affiche les caractèristique de la feuille
	System.out.println("Feuille" +this.letter+" "+this.poids);
    }
    public int recherche(int l){
	if(this.letter == l){
	    return 2;
	}
	else{
	    return -1;
	}
    }
    public int comptePoids(){//une feuille n'ayant pas de fils elle renvoie un
	return 1;
    }
    public void dfs(ArrayList<Integer> a, CodeLettre[] cl){//retourne le code d'une feuille
	cl[(int)letter]= new CodeLettre(a);//a est l'arrayList correspondant au chemin de la feuille dans l'arbre
    }
   
    
    public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche){
	l.add((int)this.letter);//ajoute la lettre dans la liste correspondant à l'abre de compression
	l.add(-1);//écrit -1 pour signaler qu'il s'agit d'une feuille donc pas fils
	l.add(-1);
	return l.size()-3;//retourne l'emplacement de la feuille dans la liste
	 
    }
   
}
