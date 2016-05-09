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
    public int recherche(char l){
	if(this.right !=null){
	    int i1 = this.right.recherche(l);
	    int i2 = this.left.recherche(l);
	    if((i1==1 || i1==0)&& i2 == -1)
		return 1;
	    if(((i2==1) || i2==0) && i1==-1)
		return 0;
	    if(i1==2)
		return 1;
	    else
		return 0;
	}
	else
	    return 0;
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
    public Node(Feuille droit,Feuille gauche){
	this.poids = droit.poids+gauche.poids;
	this.right=droit;
	this.left=gauche;
    }
    public Node(int a,Poids l,Poids r){
	this.poids = a;
	this.left =l;
	this.right = r;
    }
    public int getPoids(){
	return this.poids;
    }
    public void setPoids(int a){
	this.poids = a;
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
    public void addPoids(Poids a,Poids b){
	this.poids = a.poids+b.poids;
	if(a.poids>=b.poids){
	    this.left = b;
	    this.right = a;
	}
	else{
	    this.left=a;
	    this.right = b;
	}
    }
    
    public void affiche(){
	System.out.println(super.poids);
	if(this.left !=null){
	    System.out.print("left");
	    this.left.affiche();
	    
	}
	if(this.right !=null){
	    System.out.print("right");
	    this.right.affiche();
	}
    }
    public int comptePoids(){
	if(this.right != null )
	    return 1+this.right.comptePoids()+this.left.comptePoids();
	else
	    return 1+this.left.comptePoids();
	
    }
    public int  tabArbre(int pl,int pere,int []tab,boolean gauche){
	tab[pl]=-1;
	if(pl!=0){
	    if(gauche)
		tab[pere+1]=pl;
	    else
		tab[pere+2]=pl;
	}
	pere=pl;
	pl=pl+3;
	pl=this.left.tabArbre(pl,pere,tab,true);
	if(this.right != null){
	    pl=pl+3;
	   pl =  this.right.tabArbre(pl,pere,tab,false);
	}
	else{
	    pl=pl+3;
	}
	return pl;
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
