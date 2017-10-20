package generator.Android;

import java.io.BufferedWriter;
import java.io.IOException;

import utilities.Tool;
import model.sequence.Fragment;
import model.sequence.Operand;
import generator.GeneratorStrategy;

public class OperandAndroid implements GeneratorStrategy{

	private Operand operand;
	
	private FragmentAndroid generatorFragment;
	private GuardAndroid generatorGuard;
	@SuppressWarnings("unused")
	private Fragment fragment;
	private String tabInd;
	
	public OperandAndroid(Operand operand){
		this.operand = operand;
	}
	
	@Override
	public void codeGenerator(BufferedWriter out, int tab) throws IOException {
		//R: 02/19/2015
		
		for(Fragment fragment : operand.getFragments()){		
			generatorFragment = new FragmentAndroid(fragment);
			generatorFragment.codeGenerator(out, tab + 1);					
		}
		operand.done = true;		
	}	
	
	public void genCodeOpt(BufferedWriter out, int tab) throws IOException {
		
		tabInd = Tool.indentation(tab);
		if( !(operand.getGuard().getSpecification().getBody().equals("else")) ){
			out.write("\n" + tabInd + "if(");
			generatorGuard = new GuardAndroid(operand.getGuard());
			generatorGuard.codeGenerator(out, tab);	
			out.write(")");
		} 
		out.write("{");
		codeGenerator(out, tab);
		out.write("\n" + tabInd + "}");		
	}
	
	
	public void genCodeCaseSwitch(BufferedWriter out, int tab) throws IOException {
		out.write("case ");
		generatorGuard = new GuardAndroid(operand.getGuard());
		generatorGuard.genCodeValue(out);
		out.write(":\n");
		for(Fragment fragment : operand.getFragments()){
			generatorFragment = new FragmentAndroid(fragment);
			generatorFragment.codeGenerator(out, tab + 1);
		}
		tabInd = Tool.indentation(tab);
		out.write("\t" + tabInd +"break;\n");
	}

}
