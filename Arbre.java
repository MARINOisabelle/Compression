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

    public void addNode(int number,int poids, Node n){
	if(number==0 && n.left==null){
	    n.left=new Node(poids);
	}
	else if(number==1 && n.right==null){
	    n.right=new Node(poids);
	}
	else{
	    System.out.println("Exception: Number est ni 1 ni 0");
	}
	
	
    }
    public ArrayList<int[]> tabArbre(){
	int []tab = new int[3];
	ArrayList<int[]> list = new ArrayLis();
	
	
    }
    
}
