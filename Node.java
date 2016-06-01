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
    
    public void dfs(ArrayList<Integer> a,CodeLettre [] cl){
	ArrayList<Integer> g = copieList(a);
	ArrayList<Integer> d = copieList(a);
	g.add(0);
	this.left.dfs(g,cl);
	if(this.right!=null){
	    d.add(1);
	    this.right.dfs(d,cl);
	}
    }
   
    public Node(Poids droit,Poids gauche){
	this.poids = droit.poids+gauche.poids;
	this.right=droit;
	this.left=gauche;
    }
    public Node(int a,Poids l,Poids r){
	this.poids = a;
	this.left =l;
	this.right = r;
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
    public int comptePoids(){
	if(this.right != null )
	    return 1+this.right.comptePoids()+this.left.comptePoids();
	else
	    return 1+this.left.comptePoids();
	
    }
    
    
    public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche){
	int place = l.size();
	l.add(-1);
	l.add(0);
	l.add(0);
	int gauch= this.left.ListArbre(l,0,true);
	l.set(place+1,gauch);
	if(this.right != null){
	    int droit =this.right.ListArbre(l,0,false);
	    l.set(place+2,droit);
	}
	return place;
    }
    public ArrayList<Integer> copieList(ArrayList<Integer> a){
	ArrayList<Integer> n = new ArrayList<Integer>();
	for(int i=0;i<a.size();i++){
	    n.add(a.get(i));
	}
	return n;
    }

}
