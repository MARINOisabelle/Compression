import java.util.*;

public class Arbre{
    Poids racine;

    public Arbre(Poids racine){
	this.racine = racine;
    }
    
    public Arbre(Feuille gauche,Feuille droite){
	Node racine  = new Node(gauche.poids+droite.poids,(Poids)gauche,(Poids)droite);
	this.racine = racine;
    }
    public Arbre fusionne(Arbre a){
	Node racine = new Node(a.racine.poids+this.racine.poids);
	if(a.racine.poids >= this.racine.poids){
	    racine.right = a.racine;
	    racine.left = this.racine;
	}
	else{
	    racine.right = this.racine;
	    racine.left = this.racine;
	}
	return new Arbre(racine);
    }
    
}
