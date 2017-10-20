package model.structure;

import java.io.BufferedReader;
import java.io.IOException;

import model.Model;
import utilities.Parser;
import utilities.Tool;

public class Attribute extends DataStructure {

	/*Attributes*/
	@SuppressWarnings("unused")
	private String aggregation;
	private String defautlValue;
	@SuppressWarnings("unused")
	private String defautlValueType;
	
	@SuppressWarnings("unused")
	private boolean objectType;
	private boolean primitiveType;
	private boolean hasGetMethod;
	private boolean hasSetMethod;
	
	//R: attribute is ID, attribute is only readable
	private boolean isID;
	private boolean isReadOnly;
	private boolean needImport;	
	private boolean isFinal;
	

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	/*Constructor*/
	public Attribute(String name) {
		super(name);
		this.aggregation = "simple";
	}
	
	/*Get*/		
	public boolean isPrimitiveType(){
		return this.primitiveType; 
	}
	
	public boolean hasGetMethod(){
		return this.hasGetMethod;
	}
	
	public boolean hasSetMethod(){
		return this.hasSetMethod;
	}
	
	public String getDefaultValue(){
		return this.defautlValue;
	}
	
	public String getDefaultValueType(){
		return this.getDefaultValue();
	}
	
	public boolean getIsID(){
		return this.isID;
	}
	
	public void setIsID(boolean isid){
		this.isID = isid;
	}
	
	public boolean getIsReadOnly(){
		return this.isReadOnly;
	}

	public void setIsReadOnly(boolean isreadonly){
		this.isReadOnly = isreadonly;
	}
	public void setName(String name){
		this.name = name;
	}
	
	
	public void printProp() {
		System.out.println("\tAtributo: " + this.name);
		System.out.println("\t\tTipo: " + this.type);
		System.out.println("\t\tPrimitivo: " + this.primitiveType);
		System.out.println("\t\tObjeto: " + this.primitiveType);
		System.out.println("\t\tVisibilidade: " + this.visibility);
		System.out.printf("\t\tUpper Value: %s\n", this.upperValue == -1 ? "*" : this.upperValue);
		System.out.printf("\t\tLower Value: %s\n", this.lowerValue == -1 ? "*" : this.lowerValue);
		System.out.printf("\t\tDefault Value: %s\n", this.defautlValue);	
		System.out.println("\t\thasGet "  + this.hasGetMethod);
		System.out.println("\t\thasGet "  + this.hasSetMethod);
		System.out.println("\t\tisID "  + this.isID);
	
	}
	
	
	

	


	/**
	 * 
	 * @param bf
	 * @param line
	 * @return needImport (boolean)
	 * @throws IOException
	 */
	public boolean parser(BufferedReader bf, String line) throws IOException {
		@SuppressWarnings("unused")
		String value = null, key;
		
		
		if(Model.getTrieMetodoName("get" + name.substring(0, 1).toUpperCase() + name.substring(1)) != null){
			hasGetMethod = true;
		}
		
		if(Model.getTrieMetodoName("set" + name.substring(0, 1).toUpperCase() + name.substring(1)) != null){
			hasSetMethod = true;
		}
		
		if (line.contains("visibility=")) {
			visibility = Tool.manipulate(line, "visibility=");
			//Rocio modify
					
			if( (visibility != null) && ((visibility.equals("public")) || (visibility.equals("private")) || (visibility.equals("protected"))) ){
				Parser.getModel().getLastClasse().setNeedGetSet(true);
				isPrivate = true;
			}
		//Rocio modify
		}else{
//			System.out.println(line);
			Parser.getModel().getLastClasse().setNeedGetSet(true);
			isPrivate = true;
		}
		
		if(line.contains("isStatic")){
			this.isStatic = true;
		}
		
		if (line.contains("aggregation=")) {
			aggregation = Tool.manipulate(line, "aggregation=");
		}
		if (line.contains(" type=")) {
			
			value = Tool.manipulate(line, " type=");
			if (value.charAt(0) == '_') {
				objectType = true;
				value = Model.getTrieID(value);
			} else{
				primitiveType = true;
			}
			type = value;
		}
		if (line.contains("/>")) {
			line = "</ownedAttribute>";
		} else{
			for (line = bf.readLine(); !((line.contains("</ownedAttribute>")) ||  (line.contains(" </ownedEnd>"))) ; line = bf.readLine() ) {
				if (line.contains("uml:Stereotype")) {
					value = Tool.manipulate(line, "pathmap:", "#", "\"");
					value = Model.getTrieID(value);
					type = value;
					objectType = true;
				}
				if (line.contains("uml:PrimitiveType")) {
					value = parserType(value, line);
					type = value;
					primitiveType = true;
				}
				if ( (line.contains("upperValue"))  && (line.contains("value=")) ) {
					value = Tool.manipulate(line, "value");
					upperValue = value.charAt(0);
					if(value.substring(0, 1).equals("*")){
						needImport = true;
						
					}
					
				}
				if ( (line.contains("lowerValue")) && (line.contains("value=")) ) {
					value = Tool.manipulate(line, "value");
					lowerValue = value.charAt(0);
				}
				if ( (line.contains("defaultValue")) && (line.contains("value=")) ) {
					defautlValueType = Tool.manipulate(line, "type");
					defautlValue = Tool.manipulate(line, "value");
					if(defautlValue.contains("&quot;")){
						defautlValue = defautlValue.replaceAll("&quot;", "\"");
					}
				}  
			}// end for
		}
		return needImport;
	}
	
	public static void load(BufferedReader bf, String line) {
		String key, value;
		key = Tool.manipulate(line, "xmi:id");
		value = Tool.manipulate(line, "name=");
		Attribute atributte = new Attribute(value);
		//Rocio modify
		String isid = Tool.manipulate(line, "isID=");
		if (isid.equals("true")){
			atributte.setIsID(true);
		}else{
			atributte.setIsID(false);
		}
		
		String isreadonly = Tool.manipulate(line, "isReadOnly=");
		if (isreadonly.equals("true")){
			atributte.setIsReadOnly(true);
		}else{
			atributte.setIsReadOnly(false);
		}
		
		Model.putTrieAtributte(key,  atributte);
	}


}
