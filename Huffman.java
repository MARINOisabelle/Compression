import java.io.File;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.BufferedInputStream;
import java.nio.channels.*;
import java.io.IOException;


class Huffman{
    
    public Huffman(){
	
    }
    public  int[] compteIter(LireBit fr) {
	int tab[]=new int[256];
	int oct;
	
	try{
	    oct = fr.read();
	    while(oct>=0){
		tab[oct]++;
		oct = fr.read();
	    }
	    // Test.affiche
	  
	}
	catch(Exception e){
		System.out.println("can't read file");
		e.printStackTrace();
	}
	
	
	return tab;
    }
    
     public Arbre arbreMin(int tab[]){
	int min1=-1;
	int min2=-1;
	int i1=-1;
	int i2=-1;
	for(int i=0;i<255;i++){
	    if(tab[i]!=0){
		if(min1!=-1){
		    if(tab[i]<min1){
			min1=tab[i];
			i1=i;
		    }
		    else{
			if(min2!=-1){
			    if(tab[i]<min2){
				min2=tab[i];
				i2=i;
			    }
			}
			else{
			    min2=tab[i];
			    i2=i;
			}
		    }
		}
		else{
		    min1=tab[i];
		    i1=i;
		}
	    }
	}
	
	if(i1!=-1 && i2!=-1){
	    Arbre a = new Arbre(new Node(0));
	    Feuille f1 = new Feuille(min1,new Character((char)i1));
	    Feuille f2 = new Feuille(min2,new Character((char)i2));
	    a.racine.poids=f1.poids+f2.poids;
	    a.racine.left=f1;
	    a.racine.right=f2;
	    tab[i1]=0;
	    tab[i2]=0;
	    return a;
	}
	else if(i1!=-1){
	    Arbre a = new Arbre(new Node(min1));
	    a.racine.left = new Feuille(min1,new Character((char)i1));
	    tab[i1]=0;
	    return a;
	}
	else{
	    return null;
	}
    }
     public  Feuille feuilleMin(int tab[]){
	int min1=-1;
	int i1=-1;
	for(int i=0;i<255;i++){
	    if(tab[i]!=0){
		if(min1!=-1){
		    if(tab[i]<=min1){
			min1=tab[i];
			i1=i;
		    }
		}
		else{
		    min1=tab[i];
		    i1=i;
		}
	    }
	}
	if(min1 != -1){
	    Feuille f = new Feuille(min1,new Character((char)i1));
	    tab[i1]=0;
	    return f;
	}
	else{
	    return null;
	}
	
       
    }
    public void ecrireArbre(Arbre a,EcrireBit fw,int taille)throws IOException{
	int tab[]=new int[taille];
	a.racine.tabArbre(0,0,tab,false);
	for(int i=0;i<tab.length;i++){
	    fw.write(tab[i]);
	}
    }
    public void ecrireLettre2(EcrireBit fw,Node a,char lettre){
	try{
	    int b = a.recherche(lettre);
	    int c =0;
	    while(c!=2){
		//	System.out.println(b);
		fw.writeBit(b);
		
		try{
		    
		    if(b==1){
		        c  = a.right.recherche(lettre);
			if(c!=-1 && c!=2){
			    a= (Node)a.right;
			    b = c;  
			}
		    }
		    else if(b==0){
			c = a.left.recherche(lettre);
			if(c!=-1 && c!=2){
			    a=(Node)a.left;
			    b = c;
			    
			}
		    }
		}
		catch(Exception e){}
	    }
		
	    
	}
	catch(Exception e){
	    e.printStackTrace();
	    System.out.println(e);
	}
	}
    public void ecrireLettre(CodeLettre[] cl,EcrireBit fw,int lettre){
	try{
	    if(cl[lettre].code != null){
		//	System.out.println("taille :"+cl[lettre].code.size());
			for(int i=0;i<cl[lettre].code.size();i++){
		   
		  fw.writeBit(cl[lettre].code.get(i));
		}
	    }
	}
	catch(Exception e){
	    e.printStackTrace();
	}
	    
	}
    /* public Arbre arbreCompr(LireBit fr) throws Exception{
	int tab[]=compteIter(fr);
	Arbre principal = new Arbre(null);
	Node sup = new Node(0);
	Feuille min = feuilleMin(tab);
	if(min != null){
	    Feuille min2 = feuilleMin(tab);
	    if(min2 != null){
		sup.addPoids(min,min2);
		principal.racine = sup;
		while((min=feuilleMin(tab))!=null){
		    principal.rajouteFeuille(min);
		}
		return principal;
	    }
	    else{
		sup.poids = min.poids;
		sup.left=min;
		principal.racine = sup;
		return principal;
	    }
	}
	else{
	    return null;
	}
	    
    }
    */
    public Arbre arbreCompr(LireBit fr){
	int tab[]=compteIter(fr);
	Arbre princ = arbreMin(tab);
	Arbre fus = arbreMin(tab);
	while(princ.fusionneArbre(fus)){
	    fus = arbreMin(tab);
	}
	return princ;
	   
	
    }
    public int[] tabDec(LireBit l) throws IOException{
	int taille  = l.read();
	int tab[] = new int[taille*3];
	for(int i=0;i<tab.length;i++){
	    tab[i]=l.read();
	    //System.out.println(tab[i]);
	}    
	return tab;
    }
    
	/*  fonction appeler dans les main de compresion et decompression */
    public  void compression(String fr, String fw)throws Exception{
	LireBit fl = new LireBit(new FileInputStream(new File(fr)));
	
	Arbre a = arbreCompr(fl);
	a.racine.affiche();
	fl.close();
	System.out.println("fait");
	fl =  new LireBit(new FileInputStream(new File(fr)));
	int l ;
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File(fw)));
	fe.write(1);
	int taille=a.taille();
	fe.write(taille);
	ecrireArbre(a,fe,taille*3);
	System.out.println("arbre ecrit");
	CodeLettre[] c = new CodeLettre[256];
	ArrayList<Integer> ar = new ArrayList<Integer>();
	a.racine.dfs(ar,c);
		while((l=fl.read())!=-1){
		    //System.out.println(l);
	    ecrireLettre(c,fe,l);
	    // ecrireLettre2(fe,a.racine,new Character((char)l));
	    }
	    fe.writeLastBit();
	fe.close();
	fl.close();
    }
    
    public  void decompression(LireBit fr, EcrireBit fw,long le)throws IOException{
	    int [] tab = tabDec(fr);
	    // System.out.println("on est la");
	    int placement = 0;
	    for(long i=0;i<le-tab.length-2;i++){
		int b=0;
		fr.lire();
		for(int j=0;j<8;j++){
		    //System.out.println(tab[placement]);
		    //System.out.println("le placement est :"+placement);
		    if(tab[placement]==255){
			//	System.out.println("l'octet lu est :"+fr.octet[j]);
			placement=tab[placement+1+fr.octet[j]];
		    }
		    else{
			fw.write(tab[placement]);
			//	System.out.println(tab[placement]);
			j=j-1;
			placement = 0;
		    }
		}
		//System.out.println(i);
		
	    }
	    fr.lire();
	    int d;
	    for(d=0;d<8;d++){
		if(fr.octet[7-d]==1)
		    break;
	    }
	    for(int j=0;j<8-d;j++){
		if(tab[placement]==255)
		    placement=tab[placement+1+fr.octet[j]];
		else{
		    fw.write(tab[placement]);
		    placement=0;
		    j=j-1;
		}
	    }
    }
		
    
}



