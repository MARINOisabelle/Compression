import java.io.File;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.BufferedInputStream;
import java.nio.channels.*;
import java.io.IOException;

public class Comp{
    public static void main(String[]args){
	try{
	    File f = new File("Memoire.pdf.comp.decomp");
	    LireBit fl = new LireBit(new FileInputStream(new File("Memoire.pdf")));
	    System.out.println((int)'\n');
	    LireBit fl2 = new LireBit(new FileInputStream(f));int b=0;
	    for(int i=0;i<f.length();i++){
		int ligne=0;
		
		int a = fl.read();
		int b2 = fl2.read();
		if(a==10)
		    ligne++;
		if(a!=b2){
		    System.out.println(i+"sa div");
		    System.out.println(a+" "+b2+" ligne"+ligne);
		    b++;
		}
		if(b==30){
		    break;
		}
	    }
	    fl.close();
	    fl2.close();
	    
	    
	}
	catch(Exception e){
	    }
    }
}
