import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;


class LempelZiv{

	Arbre a;
	int nextPoids;

	public LempelZiv(){
		this.a =new Arbre(new Node(0));
		this.nextPoids=1;
	}

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


	public void ecrire(EcrireBit e, int poids, int bit,FileOutputStream fw) throws IOException{
		int[] octet= int2tab(poids);
		for(int i=0; i<8; i++){
			e.writeBit(octet[i]);
		}
		e.writeBit(bit);
	
	}

	public  Node analyseOctet(LireBit l,EcrireBit e, Node n, int i, FileOutputStream fw)throws IOException{
		if(i<8){
			//System.out.println("octet " + l.octet[i]);
			if(l.octet[i]==0 && n.left==null){
				this.a.addNode(l.octet[i], this.nextPoids, n);
				System.out.println("ajout 0");
				ecrire(e,n.poids, 0, fw);	
				this.nextPoids ++;
				return analyseOctet(l,e, (Node)this.a.racine, i+1, fw);

			}
			else if(l.octet[i]==0 && n.left!=null){
					return analyseOctet(l,e, (Node)n.left, i+1, fw);

			}
			else if(l.octet[i]==1 && n.right==null){
				this.a.addNode(l.octet[i], this.nextPoids, n);	
				ecrire(e,n.poids, 1, fw);
				System.out.println("ajout 1");	
				this.nextPoids ++;
				return analyseOctet(l,e, (Node)this.a.racine, i+1, fw);
			}
			else if(l.octet[i]==1 && n.right!=null){
				return analyseOctet(l,e, (Node)n.right, i+1, fw);
			}
		}
		return n;

	}

	public  void lireFichier(FileInputStream fr, FileOutputStream fw)throws IOException{
			LireBit l = new LireBit(fr);
			EcrireBit eb = new EcrireBit(fw);
			Node n = (Node)this.a.racine;
			try{
				while(l.lire()!=false){	
					n=analyseOctet(l,eb, n, 0, fw);
					/*for(int i=0;i<8;i++){
						System.out.println(l.octet[i]);
					}*/
					

				}
				l.close();
			}
			catch(IOException e){
			System.out.println("faux");
			e.printStackTrace();
			}
    }


	public static FileOutputStream compression(FileInputStream fr, FileOutputStream fw)throws IOException{
		LempelZiv lz= new LempelZiv();
		lz.lireFichier(fr, fw);
		System.out.println("Ziv");
	
		return fw;
	}

}
