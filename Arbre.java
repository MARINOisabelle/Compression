import java.util.*;

public class Arbre{
    Node racine;

    public Arbre(Node racine){ 
	this.racine = racine;
    }
    
    public int taille(){//renvoie la taille de l'arbre
	return this.racine.comptePoids();
    }

    public CodeLettre[] codeComp(){//renvoie un tableau de code de chaque carctère lié aux feuilles de l'arbre
	CodeLettre [] cl = new CodeLettre[256];
	ArrayList<Integer> a = new ArrayList<Integer>();
	this.racine.dfs(a,cl);
	return cl;
    }
    
}
