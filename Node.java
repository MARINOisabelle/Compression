public class Node extends Poids{
    Poids right;
    Poids left;
    public Node(int a){
	this.poids = a;
	this.right= null;
	this.left = null;
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
    
}
