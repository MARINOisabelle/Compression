import java.io.File;
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
	int tab[]=new int[255];
	int oct;
	
	try{
	    oct = fr.read();
	    while(oct>=0){
		tab[oct]++;
		oct = fr.read();
	    }
	 
	  
	}
	catch(Exception e){
		System.out.println("can't read file");
		e.printStackTrace();
	}
	
	
	return tab;
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
    public void ecrireLettre(EcrireBit fw,Node a,char lettre){
	try{
	    int b = a.recherche(lettre);
	    int c =0;
	    while(c!=2){
		System.out.println(b);
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
    public Arbre arbreCompr(LireBit fr) throws Exception{
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
	fl.close();
	fl =  new LireBit(new FileInputStream(new File(fr)));
	int l ;
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File(fw)));
	fe.write(1);
	int taille=a.taille();
	fe.write(taille);
	ecrireArbre(a,fe,taille*3);
	System.out.println("pas gentil");
	while((l=fl.read())!=-1){
	    ecrireLettre(fe,a.racine,new Character((char)l));
	}
	fe.writeLastBit();
	fe.close();
	fl.close();
    }
    
    public  void decompression(LireBit fr, EcrireBit fw,long le)throws IOException{
	    int [] tab = tabDec(fr);
	    System.out.println("on est la");
	    int placement = 0;
	    for(long i=0;i<le-tab.length-3;i++){
		int b=0;
		fr.lire();
		for(int j=0;j<8;j++){
		    //System.out.println(tab[placement]);
		    System.out.println("le placement est :"+placement);
		    if(tab[placement]==255){
			System.out.println("l'octet lu est :"+fr.octet[j]);
			placement=tab[placement+1+fr.octet[j]];
		    }
		    else{
			fw.write(tab[placement]);
			System.out.println(tab[placement]);
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



