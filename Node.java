import java.io.IOException;
import java.util.*;
public class Node extends Poids{
    Poids right;
    Poids left;
    public Node(int a){
	this.poids = a;
	this.right= null;
	this.left = null;
    }
    
    public void dfs(ArrayList<Integer> a,CodeLettre [] cl){//recherche en profondeur qui permet de récuper le code d'une lettre
	ArrayList<Integer> g = copieList(a);//récupère le chemin parcouru jusqu'au noeud pour la recherche du fils gauche
	ArrayList<Integer> d = copieList(a);//de même pour la recherche du fils droit
	g.add(0);//on ajoute 0 pour indiquer que l'on va à gauche à l'arraylist g
	this.left.dfs(g,cl);//on rapelle la fonction sur le fils gauche avec la nouvelle arraylist g
	if(this.right!=null){//on verifie que le fils droit n'est pas nul pour le cas ou le fichier n'à qu'un seul caractère
	    d.add(1);//on ajoute 1 pour indiquer que l'on va à droite à l'arraylist d
	    this.right.dfs(d,cl);////on rapelle la fonction sur le fils droit avec la nouvelle arraylist d
	}
    }
   
    public Node(Poids droit,Poids gauche){//constructeur de Node qui lui attribut un fils droit et gauche passé en argument
	this.poids = droit.poids+gauche.poids;//le poids du node est égal à la somme du poids de son fils droit et de son fils gauche
	this.right=droit;
	this.left=gauche;
    }
   
    public void addNode(int number,int poids){
	if(number==0 && this.left==null){
	    this.left=new Node(poids);
	}
	else if(number==1 && this.right==null){
	    this.right=new Node(poids);
	}
	else{
	    System.out.println("Exception: Number est ni 1 ni 0");
	}
    }
   
    
    public void affiche(){//affiche les caractèristique du node et renvoie la fonction sur ses fils gauche et droit
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
    public int comptePoids(){//renvoie le nombre de poids à partir de ce node
	if(this.right != null )
	    return 1+this.right.comptePoids()+this.left.comptePoids();
	else
	    return 1+this.left.comptePoids();
	
    }
    
    
    public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche){//ajoute le noeud à la liste correspondant à l'arbre avec son fils gauche et son  fils droit
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
    public ArrayList<Integer> copieList(ArrayList<Integer> a){//renvoie une arraylist d'entier de même contenue
	ArrayList<Integer> n = new ArrayList<Integer>();
	for(int i=0;i<a.size();i++){
	    n.add(a.get(i));
	}
	return n;
    }

}
