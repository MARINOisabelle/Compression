import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;


class LempelZiv{

	Arbre a;
	int nextPoids; // poids pour le prochain Node a ajouter
	int nbBit; //nb de Node dans l'arbre possible pour la puissance 
	int puissance; // puissance de 2 utile por coder les poids des node
	ArrayList<Integer[]> list;
	long taille;

	public LempelZiv(){
		this.a =new Arbre(new Node(0));
		this.nextPoids=1;
		this.nbBit=1;
		this.puissance=0;
		this.list = new ArrayList<Integer[]>();
		Integer[] tab = {-1,-1};
		list.add(0,tab);
	}


					/* Fonctions pour la compression */ 

	public int[] int2tab(int o){
		int mask=0x80;
		if(o == -1)
			return null;
		int octet [] = new int[8];
		for(int i=0;i<8;i++){
			if((o & mask)==0){
			octet[i]=0;
			mask = mask>>1;
			}
			else{
			octet[i]=1;
			mask = 	mask>>1;
			}
		}
		return octet;
    }


	public void ecrireComp(EcrireBit e, int poids, int bit) throws IOException{
		int[] octet= int2tab(poids);
		if(this.nextPoids>this.nbBit){
			this.puissance++;
			this.nbBit=this.nbBit*2;
		}
		if(8-puissance>=0){
			for(int i=8-this.puissance; i<8; i++){
				e.writeBit(octet[i] );	//System.out.println(octet[i]);
			}
		}
		if(bit==1 || bit==0){
			e.writeBit(bit); //System.out.println(bit + "\n"); 
		}
	
	}

	public  Node analyseOctetComp(LireBit l,EcrireBit e, Node n, int i)throws IOException{
		if(i<8){				//System.out.println(nextPoids + "next");
			if(l.octet[i]==0 && n.left==null){
				n.addNode(l.octet[i], this.nextPoids);
				//System.out.println("ajout 0 " + (n.poids));
				this.ecrireComp(e,n.poids, 0);	
				this.nextPoids ++;
				return analyseOctetComp(l,e, this.a.racine, i+1);

			}
			else if(l.octet[i]==0 && n.left!=null){
					return analyseOctetComp(l,e, (Node)n.left, i+1);

			}
			else if(l.octet[i]==1 && n.right==null){
				n.addNode(l.octet[i], this.nextPoids);	
				//System.out.println("ajout 1 " + (n.poids) );	
				this.ecrireComp(e,n.poids, 1);
				this.nextPoids ++;
				return analyseOctetComp(l,e, this.a.racine, i+1);
			}
			else if(l.octet[i]==1 && n.right!=null){
				return analyseOctetComp(l,e, (Node)n.right, i+1);
			}
		}
		return n;

	}

	public  void lireFichierComp(LireBit l, EcrireBit eb)throws IOException{
			Node n = (Node)this.a.racine;
			this.puissance=0;
			try{
				while(l.lire()!=false){	
					n=analyseOctetComp(l,eb, n, 0);
					/*for(int i=0;i<8;i++){
						System.out.println(l.octet[i]);
					}*/
					

				}
				//System.out.println(nextPoids + "next");
				this.ecrireComp(eb,n.poids, l.octet[7]);
				eb.writeLastBit();
				l.close();
			}
			catch(IOException e){
			System.out.println("faux");
			e.printStackTrace();
			}
   	}


	public static void compression(LireBit fr, EcrireBit fw) throws IOException{
		LempelZiv lz= new LempelZiv();
		lz.lireFichierComp(fr, fw);
	}

	/*Fonctions pour la décompression*/

	public static void ecrireList(ArrayList<Integer> a,EcrireBit e)throws IOException{
		for(int i=a.size(); i>0; i--){
			e.writeBit(a.get(i-1)); 
		}

	}


	public static int tab2int(int []tab){
		int res=0;
		for(int i=0; i<8; i++){
			if(tab[i]==1){		
				int j=i+1;
				while(j<8){
					tab[i]=tab[i]*2;
					j++;
				}
				res=res+tab[i];	
			}
		}
		return res;
	}

	public  ArrayList<Integer[]> ecrireDecomp(int pere, int bit,EcrireBit e)throws IOException{
			//System.out.println("dg " + bit + " pere " + pere + " " +list.size() );
			if(pere>=0 && (bit==0 || bit==1)){
				Integer t[]= new Integer[2];
				t[0]=pere; 
				t[1]=bit; 
				list.add(list.size(),t);
				Integer i=pere;
				ArrayList<Integer> ar = new ArrayList<Integer>();
				while(list.get(i)[0]!=-1){
					ar.add(this.list.get(i)[1]); 
					//System.out.println(" i " + i);
					i= list.get(i)[0];
					

				}
				ecrireList(ar, e);
				e.writeBit(bit);

			}
			
		return this.list;
	}


	public  int[] analyseOctetDecomp(LireBit l,EcrireBit e, int cur_tab , int cur_l, int[] tab)throws IOException{
			if(this.nextPoids>this.nbBit){
				this.puissance++;
				System.out.println( "puissance " +puissance);
				this.nbBit=this.nbBit*2;
			}

			 if(this.puissance<9){
				int k=0;
				for(k=cur_tab; k<puissance; k++){
						if(k+cur_l>=8 ){ break;}
						else{
							tab[k +8-this.puissance]=l.octet[cur_l+k];
							
						}
						
				} 
				cur_tab=k;
				cur_l=cur_l+k;			
				if(k==puissance) {
					/*for(int j=8-cur_tab; j<8; j++){
						System.out.println(tab[j]);
					}*/
					int node = tab2int(tab);

					if(cur_l<8){

						this.ecrireDecomp(node, l.octet[cur_l], e);
					}
					else{
						if(l.lire()!=false){
							cur_l=0;
							this.ecrireDecomp(node, l.octet[0], e);
						}
					}
					tab= new int[8];
					this.nextPoids++;
					cur_l++; 
				}
				if(cur_l<8){			// faire un l.lire().next pour connaitre le dernier octet
					return analyseOctetDecomp(l, e, 0, cur_l, tab);	
				}
				if(cur_tab<puissance){ //System.out.println("tab " +cur_tab);
					if(l.lire()!=false){	
						this.taille --;
						return analyseOctetDecomp(l, e, cur_tab,-cur_tab, tab);	
					}
				}
			}	
		return tab;
	}

	public  void lireFichierDecomp(LireBit l, EcrireBit eb) throws IOException{
			int tab[]=new int[8];
			this.puissance=0;
			try{
				while(l.lire()!=false){	
					taille --;
					tab=this.analyseOctetDecomp(l,eb, 0,0, tab);
				}
			}
			catch(IOException e){
				System.out.println("faux");
				e.printStackTrace();
			}
   	}



	public static void decompression(LireBit fr, EcrireBit fw)throws IOException{
		LempelZiv lz= new LempelZiv();
		lz.lireFichierDecomp(fr, fw);
		System.out.println("Decompression : Ziv");
	}

}
