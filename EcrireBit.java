import java.io.*;

public class EcrireBit extends FilterOutputStream{
    int octet;
    int nbBit;
    
    public EcrireBit(OutputStream out){
	super(out);
	this.nbBit=0;
	this.octet=0x00;
    }
	
    public void writeBit(int bit) throws IOException{
	int mask = 0x80;
	mask = mask>>nbBit;
	if(bit==1){
	    octet = octet|mask;
	}
	nbBit++;
	if(nbBit==8){
	    write(octet);
	    nbBit=0;
	    this.octet=0x00;
	}
    }
	
	//écriture de l'octet de fin de compression
	public void writeLastBit()throws IOException{
		//écriture de 1 
		writeBit(1);
		// on remplit le reste de l'octet par des 0
		if(nbBit<8 && nbBit!=0){
			int i=0;
			for(i=nbBit; i<8;i++){
				writeBit(0);
			}
		}
			
    }
}
