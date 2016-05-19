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
	for(int i=0;i<256;i++){
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
	    Feuille f1 = new Feuille(min1,i1);
	    Feuille f2 = new Feuille(min2,i2);
	    a.racine.poids=f1.poids+f2.poids;
	    a.racine.left=f1;
	    a.racine.right=f2;
	    tab[i1]=0;
	    tab[i2]=0;
	    return a;
	}
	else if(i1!=-1){
	    Arbre a = new Arbre(new Node(min1));
	    a.racine.left = new Feuille(min1,i1);
	    tab[i1]=0;
	    return a;
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
    public void ecrireArbre2(Arbre a,EcrireBit fw)throws IOException{
        ArrayList<Integer> list = new ArrayList<Integer>();
	a.racine.ListArbre(list,0,false);
	for(int i=0;i<list.size();i++){
	    fw.write(list.get(i));
	}
    }
    public void ecrireLettre(CodeLettre[] cl,EcrireBit fw,int lettre){
	try{
	    for(int i=0;i<cl[lettre].code.size();i++){
		    fw.writeBit(cl[lettre].code.get(i));
		}
	}
	catch(Exception e){
	    e.printStackTrace();
	    System.out.println("can't write");
	}
	    
    }
  
    public Arbre arbreCompr(LireBit fr){
	int tab[]=compteIter(fr);
	Arbre princ = arbreMin(tab);
	Arbre fus = arbreMin(tab);
	while(princ.fusionneArbre(fus)){
	    fus = arbreMin(tab);
	}
	return princ;
    }

    /*fonction qui récupère l'arbre de compression*/
    public ArrayList<Integer> listDec(LireBit l) throws IOException{
	int taille =l.read();
	while(taille%255==0){
	    taille=taille+l.read();
	}
	ArrayList<Integer> list = new ArrayList<Integer>();
	int tab[]=new int[3];
	for(int i=0;i<taille*3;i=i+3){
	    for(int j=0;j<3;j++){
		tab[j] =l.read();
	    }
	    list.add(tab[0]);
	    if(tab[1]!=tab[2] && tab[0]==255){
		while(tab[1]<i+1){
		    tab[1]=tab[1]+256;
		}
		
		while(tab[2]<i+2){
		    tab[2]=tab[2]+256;
		}
		
	    }
	    list.add(tab[1]);
	    list.add(tab[2]);
	}
	return list;
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
	while(taille>=255){
	    fe.write(255);
	    taille=taille-255;
	}
	fe.write(taille);
	ecrireArbre2(a,fe);
	CodeLettre[] c = new CodeLettre[256];
	ArrayList<Integer> ar = new ArrayList<Integer>();
	a.racine.dfs(ar,c);
	int cont=0;
	int c2=0;
	while((l=fl.read())!=-1 ){
	    ecrireLettre(c,fe,l);
	    }
	    fe.writeLastBit();
	fe.close();
	fl.close();
    }
    
    public  void decompression(LireBit fr, EcrireBit fw,long le)throws IOException{
	ArrayList<Integer> list = listDec(fr);
	int placement = 0;
	int taille =  list.size()/(255*3);
	for(long i=0;i<le-list.size()-2-taille;i++){
	    int b=0;
	    fr.lire();
	    for(int j=0;j<8;j++){
		if(list.get(placement+1)!=255 || list.get(placement+2)!=255){			
		    placement=list.get(placement+1+fr.octet[j]);
		}
		else{
		    fw.write(list.get(placement));
			j=j-1;
			placement = 0;
		}
	    }
	}
	    fr.lire();
	    int d;
	    for(d=0;d<8;d++){
		if(fr.octet[7-d]==1)
		    break;
	    }
	    for(int j=0;j<8-d;j++){
		if(list.get(placement)==255)
		    placement=list.get(placement+1+fr.octet[j]);
		else{
		    fw.write(list.get(placement));
		    placement=0;
		    j=j-1;
		}
	    }
    }
}



