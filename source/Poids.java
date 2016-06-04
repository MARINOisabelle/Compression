import java.io.IOException;
import java.util.*;

/**
* <b>Poids est une classe representant un poids de l'arbre.</b>
* @author Lucas LABADENS Isabelle MARINO
*/ 
public abstract class Poids{
/**
* L'entier poids
*/ 
    protected int poids;
	 /**
         * Affiche l'arbre binaire en DFS
         */
    abstract public void affiche();
 	/**
         *Remplit un tableau avec tout les codes de compression de chaque caractère 
         */
    abstract public void dfs(ArrayList<Integer> a, CodeLettre[] cl);
	/**
         *Ecrit l'abre binaire de compression dans une liste
         * @return un entier.
         */
    abstract public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche);
	/**
         *Renvoie le nombre de poids à partir de ce poids
	 * @return Le valeur du comptage sous forme d'entier.
         */
    abstract public int comptePoids();
}
