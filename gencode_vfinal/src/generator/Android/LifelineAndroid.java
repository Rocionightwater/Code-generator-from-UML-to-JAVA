package generator.Android;

import java.io.BufferedWriter;
import java.io.IOException;

import utilities.Parser;
import model.sequence.Fragment;
import model.sequence.Lifeline;
import model.structure.Attribute;
import model.structure.Classe;
import model.structure.Method;
import generator.GeneratorStrategy;




public class LifelineAndroid implements GeneratorStrategy{
	
	private Lifeline lifeline;
	private FragmentAndroid generatorFragment;
	public int counter = 0;
	public int i = 0;
	public int j = 0;
	public int numbrep = 0;
	public String className;
	public String classSenderName;
	

	
	public LifelineAndroid(Lifeline lifeline){
		this.lifeline = lifeline;
	}

	@Override
	public void codeGenerator(BufferedWriter out, int tab) throws IOException {
		for(Fragment order : lifeline.getOrder()){
			generatorFragment = new FragmentAndroid(order);
			generatorFragment.codeGenerator(out, tab);
		}		
	}
	
	
	public String nameClassLifeline(){
		
		for(Classe clas: Parser.getModel().getListClasse()){
			if(lifeline.getRepresents().getName().equalsIgnoreCase(clas.getName())){
				className = clas.getName();
			}
		}
		return className;
	}
	
	//R: when we have more than one instance of the lifeline into the SD
	public boolean repeatedLifeline(){
		boolean isRepeated = false;
		int t=0;
		for(Classe clas: Parser.getModel().getListClasse()){
			for(Method method: clas.getMethods()){
				if(method.getInteraction()!=null){					
					for(int y=t; y<=method.getInteraction().getLifelines().lastIndexOf(lifeline)-1; y++){													
								if(method.getInteraction().getLifelines().get(y).getRepresents().getName().equals(lifeline.getRepresents().getName())){										
									className = this.nameClassLifeline();
									isRepeated = true;								
									lifeline.setRepeated(isRepeated);
									numbrep++;
									lifeline.setName(lifeline.getRepresents().getName()+numbrep);
									lifeline.setNumRep(numbrep);								
							}		
								
								j = j + 1 ;			
						
						i = i + 1 ;
					}					
				 }
				}
		}
		return isRepeated;		
	}
	


	public void genCodeAttribute(BufferedWriter out) throws IOException {
		//R: representand of the lifelines 	
		this.isUpperCase(this.lifeline.getRepresents().getName());
		boolean si = this.repeatedLifeline();
		boolean delete = false; //Instance of the class in the same class (this)		
		
		for(Classe clas: Parser.getModel().getListClasse()){
			String nameClasse = clas.getName();
			for(Method method: clas.getMethods()){
				if(method.getInteraction()!=null){
					for(Lifeline lfline : method.getInteraction().getLifelines()){
						if(lifeline.equals(lfline)&&(nameClasse.equalsIgnoreCase(lifeline.getRepresents().getName()) && (!si))){
							delete = true;
							lifeline.setClassInstance(true);						
							}
						}
				}
			}
		}		
		
		if(lifeline.getParameter() != null){
			out.write(lifeline.getParameter() + ".");
		} else {
				
				if((lifeline.getRepresents() != null)){								
					if(si) {
						out.write(lifeline.getRepresents().getName() +numbrep+ ".");
						lifeline.setRepeated(true);
					}else{
						if(delete){
								out.write("this.");
										}else{
											out.write(lifeline.getRepresents().getName() + ".");
										}
					}				
				}
			}	
		}
	

	public void genCodeAttributeGetSet(BufferedWriter out) throws IOException {
		if(lifeline.getParameter() != null){
			out.write(lifeline.getParameter());
		} else {
			if(lifeline.getRepresents() != null) {
				out.write(lifeline.getRepresents().getName());
			}
		}
	}

	public void genCodeCreate(BufferedWriter out) throws IOException {
		if(lifeline.getName().contains("Intent")){
			
				int n;
				n = lifeline.getName().indexOf(":");
				lifeline.setRepresents(new Attribute(lifeline.getName().substring(0, n - 1)));
				lifeline.getRepresents().setType(lifeline.getName().substring(n + 2));
				out.write(lifeline.getName().substring(n + 2)+ " ");
				out.write(lifeline.getName().substring(0, n - 1) + " = new " );
				out.write(lifeline.getName().substring(n + 2) + "(");
				out.write(lifeline.getOrder().get(0).getMessage().getName());
				out.write(");");

		
			}
		else if(lifeline.getRepresents() != null){
			String classRepresented = nameClassLifeline();
			if(this.repeatedLifeline()){
			out.write(className + " " + 
					  lifeline.getRepresents().getName()+numbrep + " = new " + className + "();");
			}else{
				if(lifeline.getRepresents().getUpperValue() == '*'){
					out.write("ArrayList<"+classRepresented+">" + " " + 
					  lifeline.getRepresents().getName()+ " = new " + "ArrayList<"+classRepresented+">"+ "();");
					
				}else{

					out.write(classRepresented + " " + 
				  lifeline.getRepresents().getName()+ " = new " + lifeline.getRepresents().getType() + "();");
				}				
			}
		}
			
	
	}
	
	//R: - cases eDTE, sCC, mOC, gEO
	public boolean testAllUpperCase(String str){
		for(int i=1; i<str.length(); i++){
			char c = str.charAt(i);
			if(c >= 97 && c <= 122) {
				return false;
			}
		}
			//str.charAt(index)
			return true;
	}
		
	public boolean testSomeUpperCase(String str){
		for(int i=1; i<str.length()-1; i++){
			char c = str.charAt(i);
			char cn = str.charAt(i+1);
			if(!(c >= 97 && c <= 122)&& !(cn >= 97 && cn <= 122)) {
				this.lifeline.getRepresents().setName(this.lifeline.getRepresents().getName().substring(0, i+1)+ this.lifeline.getRepresents().getName().substring(i+1, this.lifeline.getRepresents().getName().length()).toLowerCase());
				return true;
			}
		}
		//str.charAt(index)
		return false;
	}
		
	public void isUpperCase(String str){
		if(testAllUpperCase(str)){
			this.lifeline.getRepresents().setName(this.lifeline.getRepresents().getName().toLowerCase());
		}else {
			
			testSomeUpperCase(str);
		}
	}

}
