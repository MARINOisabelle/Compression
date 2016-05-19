import java.util.*;

public class Arbre{
    Node racine;

    public Arbre(Node racine){ 
	this.racine = racine;
    }
    
    public Arbre(Poids gauche,Poids droit){
	Node racine  = new Node(gauche.poids+droit.poids,(Poids)gauche,(Poids)droit);
	this.racine = racine;
    }
    public void fusionne(Arbre a){ //permet de fusionner this avec a
	if(this.racine != null){
	    Node racine = new Node(a.racine.poids+this.racine.poids);//crée la racine du nouvel arbre 
	    if(a.racine.poids >= this.racine.poids){//compare le poids des racines pour mettre la plus lourde à droite et l'autre à gauche
		racine.right = a.racine;
		racine.left = this.racine;
	    }
	    else{
		racine.right = this.racine;
		racine.left = this.racine;
	    }
	    this.racine = racine;
	    
	}
	else{
	     this.racine =a.racine;
	}
    }
    public void rajouteFeuille(Feuille f){
	Node rac = new Node(f.poids+this.racine.poids);
	if(this.racine.poids >= f.poids){
	    rac.right = this.racine;
	    rac.left = f;
	}
	else{
	    rac.right = f;
	    rac.left = this.racine;
	}
	this.racine = rac;
    }
    public int taille(){
	return this.racine.comptePoids();
    }
    public CodeLettre newLettre(int l){
	ArrayList<Integer> a = new ArrayList<Integer>();
	CodeLettre c=new CodeLettre(renverse(a));
	return c;
	
    }
    public boolean fusionneArbre(Arbre a){
	if(a != null){
	    Node r = new Node(a.racine.poids+this.racine.poids);
	    if( a.racine.right!=null){
		if(a.racine.poids>this.racine.poids){
		    r.right = a.racine;
		    r.left = this.racine;
		}
		else{
		    r.right=this.racine;
		    r.left=a.racine;
		}
	    }
	    else {
		if(a.racine.poids>this.racine.poids){
		    r.right = a.racine.left;
		    r.left = this.racine;
		}
		else{
		    r.right=this.racine;
		    r.left=a.racine.left;
		}
	    }
	    this.racine = r;
	    return true;
	}
	else{
	    return false;
	}
    }
     public  ArrayList<Integer> renverse(ArrayList<Integer> a){
	ArrayList<Integer> b=new ArrayList<Integer>();
	for(int i = a.size()-1;i>=0;i++){
	    b.add(a.get(i));
	}
	return b;
    }
}
