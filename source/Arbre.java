import java.util.*;
/**
* <b>Arbre est une classe representant un arbre binaire.</b>
* @author Lucas LABADENS Isabelle MARINO
*/ 
public class Arbre{
	/**
* Node racine
*/
    Node racine;
/**
* Constructeur Arbre.
*
* @param racine
* La racine de l'arbre.
*/ 
    public Arbre(Node racine){ 
	this.racine = racine;
    }
    
	/**
         * Renvoie la taille de l'arbre
         * 
         * @return int
         */
    public int taille(){
	return this.racine.comptePoids();
    }

	/**
         * Renvoie un tableau de code de chaque carctère lié aux feuilles de l'arbre
         * 
         * @return CodeLettre[]
         */
    public CodeLettre[] codeComp(){
	CodeLettre [] cl = new CodeLettre[256];
	ArrayList<Integer> a = new ArrayList<Integer>();
	this.racine.dfs(a,cl);
	return cl;
    }
    
}
