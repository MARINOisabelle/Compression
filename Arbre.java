import java.util.*;

public class Arbre{
    Node racine;

    public Arbre(Node racine){
	this.racine = racine;
    }
    public Arbre(Feuille gauche,Feuille droite){
	Node racine  = new Node(gauche.poids+droite.poids,(Poids)gauche,(Poids)droite);
	this.racine = racine;
    }
    public Arbre Fusionne(Arbre a){
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

    public void addFeuille(Feuille f){
	Node r = new Node(f.poids+this.racine.poids);
	if(f.poids >= this.racine.poids){
	    r.right = f;
	    r.left = this.racine;
	}
	else{
	    r.right = this.racine;
	    r.left = f;
	}
	this.racine = r;
    }
}
