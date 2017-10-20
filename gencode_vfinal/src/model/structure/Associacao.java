package model.structure;

import java.io.BufferedReader;
import java.io.IOException;

import model.Model;
import utilities.Tool;

public class Associacao extends DataStructure {

	public Associacao(String name) {
		super(name);
	}
	
		
	/**
	 * loadAssociation: Criação da trieAssoc, informações referentes ao Member(1)
	 * @param bf
	 * @param line
	 * @throws IOException
	 */
	public static void load(BufferedReader bf, String line) throws IOException{
		String key, value;
		
		value = Tool.manipulate(line, "name=");
		
		if (line.contains("/>")) {
			line = "</packagedElement>";
		} else {
			for (line = bf.readLine(); !(line.contains("</packagedElement")); 	line = bf.readLine() ) {
				if (line.contains("<ownedEnd")) {
					key = Tool.manipulate(line, "xmi:id=");
					value = Tool.manipulate(line, "name");
					
					Attribute atributte = new Attribute(value);
					Model.putTrieAtributte(key, atributte);
				}
			}// end else
		}
	}//end loadAssociation

	
	public void printProp() {

		System.out.println( "\tNome da associação: " + this.name );
		System.out.println("\t\tVisibilidade: " + this.visibility);
		
	}
	

}
