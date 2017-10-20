package generator.Android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Model;
import model.structure.Classe;
import model.structure.Enumeration;
import model.structure.Interface;
import generator.GeneratorStrategy;

public class ModelAndroid implements GeneratorStrategy{

	private Model model;
	private File dir;
	
	private GeneratorStrategy generatorClasse;
	private GeneratorStrategy generatorInterface;
	private GeneratorStrategy generatorEnum;
	
	public ModelAndroid(Model model){
		this.model = model;
	}
	
	@Override
	public void codeGenerator(BufferedWriter out, int tab) throws IOException {
		System.out.println("strategy model");

		Logger.getGlobal().log(Level.INFO, "Starting to write classes, interfaces and enums");
		
		dir = new File("out/" + model.getName());
		dir.mkdirs();
		
		for(Classe classe :  model.getListClasse()){
			generatorClasse = new ClasseAndroid(classe);
			generatorClasse.codeGenerator(out, tab);
		}
		
		for(Interface inter : model.getListInterface()){
			generatorInterface = new InterfaceAndroid(inter);
			generatorInterface.codeGenerator(out, tab);
		}	
		
		for(Enumeration enumeration : model.getListEnumeration()){
			generatorEnum = new EnumerationAndroid(enumeration);
			generatorEnum.codeGenerator(out, tab);
		}
	}

}
