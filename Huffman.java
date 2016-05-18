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
	    for(int i=0;i<tab.length;i++){
		if(tab[i]==1){
		    System.out.println(i+"c'est celui la");
		}
	    }
	  //  System.out.println(tab[255]);
	  
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
	//Test.afficheList(list);
	System.out.println(list.size()+"size");
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
    public int[] tabDec(LireBit l) throws IOException{
	int taille  = l.read();
	int tab[] = new int[taille*3];
	for(int i=0;i<tab.length;i++){
	    tab[i]=l.read();
	    if(i%3==0)
		System.out.println(" ");
	       
	}
	//	System.out.println("fin du tableau de décompression");
	return tab;
    }
    public ArrayList<Integer> listDec(LireBit l) throws IOException{
	int taille =l.read();
	while(taille%255==0){
	    taille=taille+l.read();
	}
	System.out.println("taille2 "+taille);
	ArrayList<Integer> list = new ArrayList<Integer>();
	for(int i=0;i<taille*3;i++){
	    int r =l.read();
	    if(i%3!=0){
		while(r<i){
		    r=r+256;
		    
		}
		list.add(r);
	    }
	    else{
		list.add(r);
		
	    }
	    if(i%3==0){
		System.out.println("("+i+")");
	    }
	}
	return list;
    }
    
	/*  fonction appeler dans les main de compresion et decompression */
    public  void compression(String fr, String fw)throws Exception{
	LireBit fl = new LireBit(new FileInputStream(new File(fr)));
	
	Arbre a = arbreCompr(fl);
	//a.racine.affiche();
	fl.close();
	fl =  new LireBit(new FileInputStream(new File(fr)));
	int l ;
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File(fw)));
	fe.write(1);
	int taille=a.taille();
	System.out.println("taille "+taille );
	while(taille>=255){
	    fe.write(255);
	    taille=taille-255;
	}
	fe.write(taille);
	
	//ecrireArbre(a,fe,taille*3);
	ecrireArbre2(a,fe);
	
	//	System.out.println(taille*3+"ceci est ma taille");
	//System.out.println("arbre ecrit");
	CodeLettre[] c = new CodeLettre[256];
	ArrayList<Integer> ar = new ArrayList<Integer>();
	a.racine.dfs(ar,c);
	/*	for(int i=0;i<c.length;i++){
	    System.out.println(new Character((char)i)+ " "+c[i].code.size());
	    Test.afficheList(c[i].code);
	    }*/
	int cont=0;
		while((l=fl.read())!=-1  ){
		    // System.out.println(l+" lettre");
		    ecrireLettre(c,fe,l);
		    //System.out.println(new Character((char)l));
		    // cont++;
	    // ecrireLettre2(fe,a.racine,new Character((char)l));
	    }
		
		//System.out.println("c'est la fin");
	    fe.writeLastBit();
	fe.close();
	fl.close();
    }
    
    public  void decompression(LireBit fr, EcrireBit fw,long le)throws IOException{
	//int [] tab = tabDec(fr);
	    // System.out.println("on est la");
	ArrayList<Integer> list = listDec(fr);
	//Test.afficheList(list);
	    int placement = 0;
	    int taille =  list.size()/(255*3);
	    System.out.println("taille en plus"+taille);
	    for(long i=0;i<le-list.size()-2-taille;i++){
		int b=0;
		fr.lire();
		for(int j=0;j<8;j++){
		    //System.out.println(tab[placement]);
		    //System.out.println("le placement est :"+placement);
		    if(list.get(placement)==255){
			//	System.out.println("l'octet lu est :"+fr.octet[j]);
			//placement=tab[placement+1+fr.octet[j]];
			
			placement=list.get(placement+1+fr.octet[j]);
			
			
			//System.out.println(placement);
			
		    }
		    else{
			//	fw.write(tab[placement]);
			fw.write(list.get(placement));
			//	System.out.println(new Character((char)((int)list.get(placement)))+"voila pourquoi 12");
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
	     System.out.println(d);
	    for(int j=0;j<8-d;j++){
		if(list.get(placement)==255)
		     placement=list.get(placement+1+fr.octet[j]);
		
		    //placement=tab[placement+1+fr.octet[j]];
		else{
		    // fw.write(tab[placement]);
		    fw.write(list.get(placement));
		    System.out.println(list.get(placement));
		    placement=0;
		    j=j-1;
		}
	    }
    }
		
    
}



