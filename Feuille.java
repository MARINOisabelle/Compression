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
}
