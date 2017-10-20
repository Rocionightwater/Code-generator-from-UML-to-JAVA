package generator;

import java.io.BufferedWriter;
import java.io.IOException;

public interface GeneratorStrategy {
	
	public void codeGenerator(BufferedWriter out, int tab) throws IOException;

	

}
