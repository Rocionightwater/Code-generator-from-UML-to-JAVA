package model.structure;

import java.io.BufferedReader;
import java.io.IOException;

import model.Model;
import utilities.Tool;

public class RealizationInterface {
	
	/*Attributes*/
	private String name;
	private Interface supplier;
	@SuppressWarnings("unused")
	private Interface contract;
	
	/*Constructor*/
	public RealizationInterface(String name) {
		this.name = name;
	}
	
	/*Get*/
	public String getName(){
		return this.name;
	}
	
	public String getNameSupplier(){
		return supplier.getName();
	}

	public Interface getSupplier(){
		return this.supplier;
	}
	
	public void parser(BufferedReader bf, String line) throws IOException{
		String key;
		name = Tool.manipulate(line, "name=");
		key = Tool.manipulate(line, "supplier=");
		supplier = Model.getTrieInterface(key);
		key = Tool.manipulate(line, "contract=");
		contract = Model.getTrieInterface(key);
	}
}
