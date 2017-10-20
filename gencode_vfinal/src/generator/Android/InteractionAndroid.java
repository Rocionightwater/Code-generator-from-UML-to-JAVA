package generator.Android;

import java.io.BufferedWriter;
import java.io.IOException;

import utilities.Tool;
import model.sequence.Fragment;
import model.sequence.Interaction;
import generator.GeneratorStrategy;

public class InteractionAndroid implements GeneratorStrategy{

	private Interaction interaction;
	@SuppressWarnings("unused")
	private LifelineAndroid generatorLifeline;
	private FragmentAndroid generatorFragmentAndroid;

	
	public InteractionAndroid(Interaction interaction){
		this.interaction = interaction;

	}
	
	@Override
	public void codeGenerator(BufferedWriter out, int tab) throws IOException {
		String tabInd = Tool.indentation(tab);
		out.write("\n" + tabInd + "/** Specified from Sequence Diagram " + interaction.getName() + " */");
		if(!(interaction.getLifelines().isEmpty())){	
	for(Fragment frag : interaction.getFragments()){
		 for(int i=0; i< interaction.getLifelines().size();i++){
		
				for(Fragment lifrag : interaction.getLifelines().get(i).getOrder()){
					if(frag.equals(lifrag)){
			generatorLifeline = new LifelineAndroid(interaction.getLifelines().get(i));
//			generatorLifeline.codeGenerator(out, tab);
			generatorFragmentAndroid = new FragmentAndroid(frag);
			generatorFragmentAndroid.codeGenerator(out, tab);
			
			 }	  
		   	 }
			}
		}
	
		}	
			
			
	}

}
