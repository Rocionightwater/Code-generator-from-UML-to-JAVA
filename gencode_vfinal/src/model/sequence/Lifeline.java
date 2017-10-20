package model.sequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Model;
import model.structure.Attribute;
import utilities.Parser;
import utilities.Tool;

public class Lifeline extends DataSequence{

	/*Attributes*/
	private Attribute represents;
	private String parameter;
	
	private ArrayList<Fragment> coveredBy;
	private ArrayList<Fragment> order;
	private boolean isRepeated;
	private boolean isClassInstance;
	private int numRep;

	/*Constructor*/
	public Lifeline(String name){
		super(name);
		this.coveredBy = new ArrayList<Fragment>();
		this.order = new ArrayList<Fragment>();
		this.isRepeated = false;
		this.numRep =0;
		
	}
	
	
	/*Add*/
	public void addFragment( Fragment coveredBy ){
		this.coveredBy.add(coveredBy);
	}


	/*Set*/
	public void setRepresents( Attribute represents ){
		 this.represents = represents;
	}
	

	/*Get*/
	public Attribute getRepresents(){
		return this.represents;
	}

	public Fragment getLastFragment(){
		return this.coveredBy.get(this.coveredBy.size() - 1);
	}
	
	public Fragment getIndexOfFragment(int index){
		return this.coveredBy.get(index);
	}	
	
	public ArrayList<Fragment> getOrder(){
		return this.order;
	}
	
	public ArrayList<Fragment> getCoveredBy(){
		return this.coveredBy;
	}
	
	public String getParameter(){
		return this.parameter;
	}
	
		



	public void parser(BufferedReader bf, String line) throws IOException{
		String value, key;
		if(line.contains("represents=")){//se tiver atributo associado a lifeline
			key = Tool.manipulate(line, "represents=");
			represents = Model.getTrieAtributte(key);
			Parser.getModel();
			
			

			if( represents == null) {
				parameter = Model.getTrieID(key);
				
			}
		}
		value = Tool.manipulate(line, "coveredBy=");
		for(int i = 0; i < value.length() ; i+=24){
			key = value.substring(i, i + 23);
			if(Model.containsTrieXMIfragment(key)){
				order.add(Model.getTrieFragment(key));
				
			}			
		}
		
		
	}

	
	
	public void printProp() {
		System.out.println("Lifeline: " + this.name);
		if(represents != null){
			System.out.println("represents " + represents.getName());
		}
		for(int i = 0; i < order.size(); i++){
			order.get(i).printProp();
		}
		
	}


	public boolean isRepeated() {
		return isRepeated;
	}


	public void setRepeated(boolean isRepeated) {
		this.isRepeated = isRepeated;
	}


	public boolean isClassInstance() {
		return isClassInstance;
	}


	public void setClassInstance(boolean isClassInstance) {
		this.isClassInstance = isClassInstance;
	}


	public int getNumRep() {
		return numRep;
	}


	public void setNumRep(int numRep) {
		this.numRep = numRep;
	}



}