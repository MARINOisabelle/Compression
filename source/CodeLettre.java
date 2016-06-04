import java.util.*;

/**
* <b>Arbre est une classe representant une structure qui a pour attribut une arraylist correspondant au code ici de l'arbre de compression d'un caractère.</b>
* @author Lucas LABADENS Isabelle MARINO
*/
public class CodeLettre{
	/**
*ArrayList<Integer> Code 
*/
    ArrayList<Integer> code;
/**
* Constructeur CodeLettre.
*
* @param c
* Arraylit de Integer representant le code de la lettre.
*/
    public CodeLettre(ArrayList<Integer> c){
	code=c;
    }
	/**
         * Affiche le code de la lettre
         */
    public void afficheCode(){
	for(int i=0;i<code.size();i++){
	    System.out.print(code.get(i));
	}
	System.out.println(" ");
    }
}
