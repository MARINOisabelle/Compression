import java.util.*;
import java.io.*;

/**
* <b>Feuille est une classe representant une feuille de l'arbre, c'est une extension d'un Poids.</b>
* @author Lucas LABADENS Isabelle MARINO
*/ 
public class Feuille extends Poids{
	/**
	* letter sur la feuille
	**/
    int letter;
/**
* Constructeur Feuille.
*
* @param p
* Le poids de la Feuille.
* @param l
* Le code de la lettre.
*/
    public Feuille(int p,int l){//une feuille possède un poids et une lettre
	this.poids = p;
	this.letter = l;
    }

	/**
	*getteur d'un Poids 
	*@return int
	*/
    public int getPoids(){
	return this.poids;
    }

	/**
	*affiche les caractèristique de la feuille
	*/
    public void affiche(){
	System.out.println("Feuille" +this.letter+" "+this.poids);
    }
	/**
	*Recherche d'une lettre dans un arbre
	*@return int
	*/
    public int recherche(int l){
	if(this.letter == l){
	    return 2;
	}
	else{
	    return -1;
	}
    }

	/**
	*ComptePoids d'une feuille
	*@return int
	*/
    public int comptePoids(){//une feuille n'ayant pas de fils elle renvoie un
	return 1;
    }

	/**
	*retourne le code d'une feuille
	*@param a
	*@param cl
	*/
    public void dfs(ArrayList<Integer> a, CodeLettre[] cl){
	cl[(int)letter]= new CodeLettre(a);//a est l'arrayList correspondant au chemin de la feuille dans l'arbre
    }
   
    /**
	*Retourne l'emplacement de la feuille dans la liste 
	*@param l
	*@param pere
	*@param gauche
	*@return int
	*/
    public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche){
	l.add((int)this.letter);//ajoute la lettre dans la liste correspondant à l'abre de compression
	l.add(-1);//écrit -1 pour signaler qu'il s'agit d'une feuille donc pas fils
	l.add(-1);
	return l.size()-3;//retourne l'emplacement de la feuille dans la liste
	 
    }
   
}
