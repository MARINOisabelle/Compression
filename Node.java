public class Node extends Poids{
    Poids right;
    Poids left;
    public Node(int a){
	this.poids = a;
	this.right= null;
	this.left = null;
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
    
    
    public void affiche(){
	System.out.print(super.poids);
	if(this.left !=null)
	    this.left.affiche();
	if(this.right !=null)
	    this.right.affiche();
    }
}
