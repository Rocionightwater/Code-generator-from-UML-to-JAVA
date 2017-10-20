package model.structure;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Model;
import utilities.Tool;

public class Enumeration extends DataStructure {

	/*Attributes*/
	public Pacote pacote;
	private String general;
	
	private ArrayList<Literal> listLiterals;
	public boolean needImport;
	public boolean needGetSet;

	/*Constructor*/
	public Enumeration(String name) {
		super(name);
		general = "";		
		this.listLiterals = new ArrayList<Literal>();
	}
	
	/*Add*/
	public void addLiteral(Literal literal) {
		this.listLiterals.add(literal);
	}
	
	
	public ArrayList<Literal> getLiterals(){
		return this.listLiterals;
	}
	
	
	public void printProp() {

		if (this.pacote != null) {
			System.out.println("Pacote: " + this.pacote.getName());
			this.pacote.printProp();
		}
		System.out.println("ClasseEnum: " + this.name);
		System.out.println("\tVisibilidade: " + this.visibility);
		if (this.isAbstract) {
			System.out.println("\tClasse Abstrata");
		}
		
		if (this.general != null) {
			System.out.printf("\tSuper Classe %s \n", this.general);
		}

	}



	public void parser(BufferedReader bf, String line) throws IOException {
		@SuppressWarnings("unused")
		String key, value;


		/*
		 * Literal
		 */
		if (line.contains("/>")) {
			line = "</packagedElement";
		} else {
			for (line = bf.readLine(); !((line.contains("</packagedElement"))); line = bf.readLine()) {
				if (line.contains("<ownedLiteral")) {
					key = Tool.manipulate(line, "xmi:id=");
					Literal literal = Model.getTrieLiteral(key);
					listLiterals.add(literal);
//					literal.parser(bf, line);
				}

				
			}
		}


			
	}// end for PackagedElement

	
}// end class parserClass




