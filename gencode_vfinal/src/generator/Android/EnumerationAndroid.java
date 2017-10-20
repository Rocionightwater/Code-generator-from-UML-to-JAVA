package generator.Android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import utilities.Parser;
import utilities.Tool;
import generator.GeneratorStrategy;
import model.structure.Enumeration;
import model.structure.Literal;



public class EnumerationAndroid implements GeneratorStrategy{

	public Enumeration enumeration;
	
	private LiteralAndroid generatorLiteral;
	
	public EnumerationAndroid(Enumeration enumeration) {
		this.enumeration = enumeration;

	}

	@Override
	public void codeGenerator(BufferedWriter out, int tab) throws IOException {
		
		System.out.println("strategy classe");
		Logger.getGlobal().log(Level.INFO, "Writing Enum class: " + enumeration.getName());
		try {
			if (Android.General.valueOf(enumeration.getName()) != null) {
				return;
			}
		} catch (java.lang.IllegalArgumentException ex) {
			File cls = new File("out/" + Parser.getModel().getName(),
					enumeration.getName().concat(".java"));
			
			
			out = new BufferedWriter(new FileWriter(cls));
			// Pacote
//			if (enumeration != null) {
//				out.write("package " + enumeration.pacote.getName() + ";\n");
//				if (enumeration.pacote.getAssocPacote() != null) {
//					out.write("import " + enumeration.pacote.getAssocPacote().getSupplier()
//							+ ".*;\n");
//					
//				}
			
			
			// Pacote
			if (enumeration.pacote != null) {
				out.write("package " + enumeration.pacote.getName() + ";\n");
				if (enumeration.pacote.getAssocPacote() != null) {
					out.write("import " + enumeration.pacote.getAssocPacote().getSupplier()
							+ ".*;\n");
					
				}
			}else{
				out.write("package " + Parser.getModel().getName() + ";\n");
			}
			
			
			}
			
			
				this.genInnerClass(enumeration, out, tab);
			
			out.close();
		}
		
//	}
	
	

	/**
	 * @param out
	 * @param tab
	 * @throws IOException
	 */
	public void genInnerClass(Enumeration enume, BufferedWriter out, int tab) throws IOException {
		String tabInd = Tool.indentation(tab);
		// Name Class and General		
		out.write("\n" + tabInd + enume.getVisibility()+" enum "+enume.getName());
		out.write("{");
		if (enume.getLiterals().size() > 0) {
			out.write("\n\n" + tabInd + "\t/**Literals */");
			for (Literal lit : enume.getLiterals()) {
				generatorLiteral = new LiteralAndroid(lit);
				generatorLiteral.codeGenerator(out, tab + 1);
			}
		}

		// Implements
	


		// Atributos
		

		// Atributos Return dos Métodos
//		for (int i = 0; i < listMethod.size(); i++) {
//			if (!((listMethod.get(i).getName().substring(0, 3).equals("get")) || (listMethod
//					.get(i).getName().substring(0, 3).equals("set")))) {
//				if (listMethod.get(i).getListReturn().size() > 0) {
//					out.write("\n" + tabInd + "/**Attribute of Return Method "
//							+ listMethod.get(i).getName() + " */");
//					for (int j = 0; j < listMethod.get(i).getListReturn()
//							.size(); j++) {
//						listMethod.get(i).getListReturn().get(j)
//								.genCodeReturn(out);
//					}
//				}
//			}
//		}
		
		

		
		

		
		out.write("\n" + tabInd + "}");
		

		}
		
}
