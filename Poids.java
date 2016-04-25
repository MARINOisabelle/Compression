import java.io.IOException;
public abstract class Poids{
    protected int poids;
    abstract public void affiche();
    abstract public int recherche(char l);
    abstract public int comptePoids();
    abstract public int tabArbre(int pl,int pere,int []tab,boolean gauche);
}
