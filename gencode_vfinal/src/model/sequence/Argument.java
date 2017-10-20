package model.sequence;

import java.io.BufferedReader;
import java.io.IOException;

import model.Model;
import utilities.Tool;

public class Argument extends DataSequence {
	
	private boolean isEnum;
	private int numRep;

		
	public Argument(String name){
		super(name);
	}
		
	public void parser(BufferedReader bf, String line) throws IOException{
		//R: set value equal to name if value is empty
		type = Tool.manipulate(line, " type=");
		value = line.contains("symbol=") ? Tool.manipulate(line, "symbol=") : Tool.manipulate(line, "value=");
		if(value.equals("")) {
			value = name;
		}		
		//R: - we can choose: we show the generic var in the 
		//messages or the instance specified in Papyrus.
		
		if(line.contains("instance=")){
			String instance= Tool.manipulate(line, "instance=");
			if(Model.getTrieLiteral(instance)!=null) this.setEnum(true);
			
//			System.out.println(instance);
			String name = Model.getTrieID(instance);
//			System.out.println(elnombre);
			this.setInstance(name);
			
//		}else{
//			this.setInstance(null);
		}
		
		
	}
	
	public void printProp() {
		System.out.println("\t" + type);
		System.out.println("\t" + value);
	}

	public boolean isEnum() {
		return isEnum;
	}

	public void setEnum(boolean isEnum) {
		this.isEnum = isEnum;
	}

	public int getNumRep() {
		return numRep;
	}

	public void setNumRep(int numRep) {
		this.numRep = numRep;
	}

}
