package model.structure;

import java.io.BufferedReader;
import java.io.IOException;

import model.Model;
import utilities.Tool;

public class Literal extends DataStructure {
	
	/*Constructor*/
	public Literal(String name) {
		super(name);		
	}		
	
	public void printProp() {
		System.out.println("\tLiteral: " + this.name);
	}

	public void parser(BufferedReader bf, String line) throws IOException {
		@SuppressWarnings("unused")
		String value = null, key;
		@SuppressWarnings("unused")
		boolean needImport = false;
				
		if(line.contains("name=")){			
			name = Tool.manipulate(line, "name=");			
		}
		
	}
	
	public static void load(BufferedReader bf, String line) {
		String key, value;
		key = Tool.manipulate(line, "xmi:id");		
		value = Tool.manipulate(line, "name=");		
		Literal literal = new Literal(value);
		Model.putTrieLiteral(key,  literal);	
	}

}
