import java.io.IOException;
import java.util.*;

/**
* <b>Noeud est une classe representant noeud de l'arbre, c'est une extension d'un Poids.</b>
* @author Lucas LABADENS Isabelle MARINO
*/ 
public class Node extends Poids{
/**
* Poids droit
*/ 
    Poids right;
/**
* Poids gauche
*/
    Poids left;
/**
* Constructeur Noeud.
*
* @param a
* Le poids du Node.
*/ 
    public Node(int a){
	this.poids = a;
	this.right= null;
	this.left = null;
    }
    

 	/**
         * Recherche en profondeur qui permet de récuper le code d'une lettre
         * 
	 * @param a
	 * @param cl
         */
    public void dfs(ArrayList<Integer> a,CodeLettre [] cl){
	ArrayList<Integer> g = copieList(a);//récupère le chemin parcouru jusqu'au noeud pour la recherche du fils gauche
	ArrayList<Integer> d = copieList(a);//de même pour la recherche du fils droit
	g.add(0);//on ajoute 0 pour indiquer que l'on va à gauche à l'arraylist g
	this.left.dfs(g,cl);//on rapelle la fonction sur le fils gauche avec la nouvelle arraylist g
	if(this.right!=null){//on verifie que le fils droit n'est pas nul pour le cas ou le fichier n'à qu'un seul caractère
	    d.add(1);//on ajoute 1 pour indiquer que l'on va à droite à l'arraylist d
	    this.right.dfs(d,cl);////on rapelle la fonction sur le fils droit avec la nouvelle arraylist d
	}
    }
   	/**
         * Constructeur de Node qui lui attribut un fils droit et gauche passé en argument
         *
	* @param droit
	* Le poids de droit.
	* @param gauche
	* Le poids de gauche. 
         */
    public Node(Poids droit,Poids gauche){
	this.poids = droit.poids+gauche.poids;//le poids du node est égal à la somme du poids de son fils droit et de son fils gauche
	this.right=droit;
	this.left=gauche;
    }
   
	/**
         * Ajout d'un noeud dans un arbre pour Lempel-Ziv
         * 
  	* @param number
	* @param poids
         */
    public void addNode(int number,int poids){
	if(number==0 && this.left==null){ // ajout à gauche si 0 et si aucun Node à gauche
	    this.left=new Node(poids);
	}
	else if(number==1 && this.right==null){// ajout à gauche si 0 et si aucun Node à gauche
	    this.right=new Node(poids);
	}
	else{
	    System.out.println("Exception: Number est ni 1 ni 0");
	}
    }
   
    /**
         * Affiche les caractèristique du node et renvoie la fonction sur ses fils gauche et droit
         */
    public void affiche(){
	System.out.println(super.poids);
	if(this.left !=null){
	    System.out.print("left node ");
	    this.left.affiche();
	    
	}
	if(this.right !=null){
	    System.out.print("right node ");
	    this.right.affiche();
	}
    } 
	/**
         * Renvoie le nombre de poids à partir de ce node
         * 
         * @return int
         */
    public int comptePoids(){//
	if(this.right != null )
	    return 1+this.right.comptePoids()+this.left.comptePoids();
	else
	    return 1+this.left.comptePoids();
	
    }
    
    /**
         * Ajoute le noeud à la liste correspondant à l'arbre avec son fils gauche et son  fils droit
         * 
	 * @param l
	 * @param pere
	 * @param gauche
         * @return int
         */
    public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche){
	int place = l.size();
	l.add(-1);//on ajoute -1 ce qui correspond à un node
	l.add(0);//on ajoute la place du fils gauche et droit
	l.add(0);
	int gauch= this.left.ListArbre(l,0,true);//on récupère la position du fils gauche dans la liste
	l.set(place+1,gauch);//on ajoute la position du fils gauche juste après le node
	if(this.right != null){
	    int droit =this.right.ListArbre(l,0,false);////on récupère la position du fils droit dans la liste
	    l.set(place+2,droit);////on ajoute la position du fils droit juste après la postion du fils gauche
	}
	return place;//on renvoie la position du node dans la liste
    }

	/**
         * Renvoie une arraylist d'entier de même contenue
         * 
	 * @param a	
         * @return ArrayList<Integer>
         */
    public ArrayList<Integer> copieList(ArrayList<Integer> a){
	ArrayList<Integer> n = new ArrayList<Integer>();
	for(int i=0;i<a.size();i++){
	    n.add(a.get(i));
	}
	return n;
    }

}
