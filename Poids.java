import java.io.IOException;
import java.util.*;
public abstract class Poids{
    protected int poids;
    abstract public void affiche();
    abstract public int recherche(char l);
    abstract public int comptePoids();
    abstract public int tabArbre(int pl,int pere,int []tab,boolean gauche);
    abstract public void dfs(ArrayList<Integer> a, CodeLettre[] cl);
    abstract public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche);
}
