import java.util.*;

public class Feuille extends Poids{
    char letter;
    public Feuille(int p,char l){
	this.poids = p;
	this.letter = l;
    }
    public int getPoids(){
	return this.poids;
    }
    public void affiche(){
	System.out.println(this.letter+" "+this.poids);
    }
    public char getId(){
	return letter;
    }
}
