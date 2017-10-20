package model.structure;

import utilities.Tool;

public abstract class DataStructure {

	/*Attributes*/
	protected String name;
	protected String type;
	protected String visibility;
	
	protected char upperValue;
	protected char lowerValue;
	
	protected boolean isPrivate;
	protected boolean isStatic;
	protected boolean isAbstract;
	
	protected String paco;
	
	/*Constructor*/
	public DataStructure(String name) {
		this.name = name;
		this.visibility = "public";
		this.upperValue = '-';
		this.lowerValue = '-';
		this.type = "void";
			
	}

	/*Set*/
	public void setType(String type) {
		this.type = type;
	}
	
	public void setUpperValue(char upperValue) {
		this.upperValue = upperValue;
	}

	public void setLowerValue(char lowerValue) {
		this.lowerValue = lowerValue;
	}

	public void setVisibility(String visibility) {
		if((visibility != null)){
			if( (visibility.equals("protected")) || (visibility.equals("private")) ){
				this.isPrivate = true;
			}
			this.visibility = visibility;
		}
	}
	
	public void isAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}
	
	public void setPaco(String pacote){
		this.paco = pacote;
		
	}
	
	
	/*Get*/
	public String getName() {
		return this.name;
	}

	public String getVisibility() {
		return this.visibility;
	}
	
	public String getType() {
		return this.type;
	}
	
	public char getUpperValue() {
		return this.upperValue;
	}

	public char getLowerValue() {
		return this.lowerValue;
	}

	public boolean isAbstract() {
		return this.isAbstract;
	}
	
	public boolean isStatic(){
		return this.isStatic;
	}
	
	public boolean isPrivate(){
		return this.isPrivate;
	}
	
	public String getPaco(){
		return this.paco;
	}
	
	/**
	 * Função auxiliar: parserAtribute, parserParameter, parserReturn
	 * @param str
	 * @param line
	 * @return
	 */
	protected static String parserType(String str, String line){		
		str = Tool.manipulate(line, "pathmap:", "#", "\"");
		if(line.contains("EcorePrimitiveTypes")){
			str = str.substring(1);
		}
		if(line.contains("PrimitiveType") ){
			if(str.contains("Integer")){
				str = "int";
			}
			if(str.contains("Double")){
				str = "double";
			}
			if(!(str.equals("String"))){
				str = str.toLowerCase();
			}
		}
		return str;
	}

	
}
