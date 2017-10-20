package utilities;

import generator.GeneratorStrategy;
import generator.Android.ModelAndroid;
import java.io.IOException;

import model.structure.DataStructure;

public class Main {

	public static DataStructure da;
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String inputFileName = "simulationModel.uml";
		inputFileName = "in/" + inputFileName;

		GeneratorStrategy generator;		

		Parser.loadXMI(inputFileName);		
		Parser.runParser(inputFileName);

		Parser.getModel().printProp();
		generator = new ModelAndroid(Parser.getModel());
		generator.codeGenerator(null, 0);

	}	
	

}// end class