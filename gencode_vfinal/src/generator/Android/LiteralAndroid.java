package generator.Android;

import java.io.BufferedWriter;
import java.io.IOException;

import utilities.Tool;
import model.structure.Attribute;
import model.structure.Literal;
import generator.GeneratorStrategy;

public class LiteralAndroid implements GeneratorStrategy {

	@SuppressWarnings("unused")
	private Attribute attribute;
	private String tabInd;
	private Literal literal;

	public LiteralAndroid(Literal literal) {
		this.literal = literal;
	}

	@Override
	public void codeGenerator(BufferedWriter out, int tab) throws IOException {		
		tabInd = Tool.indentation(tab);
		out.write("\n" + tabInd + literal.getName().toUpperCase());
		out.write(",");
	}
}
