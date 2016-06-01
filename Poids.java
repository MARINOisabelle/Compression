import java.io.IOException;
import java.util.*;
public abstract class Poids{
    protected int poids;
    abstract public void affiche();//affiche l'arbre binaire en dfs
    abstract public void dfs(ArrayList<Integer> a, CodeLettre[] cl);//remplit un tableau avec tout les codes de compression de chaque caractère
    abstract public int ListArbre(ArrayList<Integer> l,int pere,boolean gauche);//écrit l'abre binaire de compression dans une liste
    abstract public int comptePoids();
}
