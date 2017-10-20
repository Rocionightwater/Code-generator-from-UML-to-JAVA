package generator.Android;

import java.io.BufferedWriter;
import java.io.IOException;

import utilities.Parser;
import utilities.Tool;
import model.Model;
import model.sequence.Argument;
import model.sequence.Message;
import generator.GeneratorStrategy;


public class MessageAndroid implements GeneratorStrategy{
	
	private Message message;
	private FragmentAndroid generatorFragment;
	
	public MessageAndroid(Message message){
		this.message = message;

	}

	@Override
	public void codeGenerator(BufferedWriter out, int tab) throws IOException {
		String tabInd = Tool.indentation(tab);
		out.write("\n" + tabInd);
		if(message.getSort().equals("createMessage")){
			genCodeCreate(out);
		} else{
			genCodeVariable(out);
			genCodeAtributte(out);
		}
		
	}
	

	public void genCodeVariable(BufferedWriter out) throws IOException {
		if(message.getVariable() != null){
			out.write(message.getVariable());
			out.write(" = ");
		}
	} 
	

	/*
	 * Faz chamadas de método da própria classe e de outros objetos
	 */
	public void genCodeAtributte(BufferedWriter out) throws IOException {
		//if(sendEvent.getCovered() != receiveEvent.getCovered()){
		generatorFragment = new FragmentAndroid(message.getReceiveEvent());
		generatorFragment.genCodeAttribute(out);
		//}
	}
	
	public void genCodeAtributteGetSet(BufferedWriter out) throws IOException {
		//if(sendEvent.getCovered() != receiveEvent.getCovered()){
		generatorFragment = new FragmentAndroid(message.getReceiveEvent());
		generatorFragment.genCodeAttributeGetSet(out);
		//}
	}
	
	
	/*
	 * Gera os n argumentos para um chamada de método
	 */
	
	//R: - we can choose: we show the generic var in the 
	//messages or the instance specified in Papyrus.
	
	@SuppressWarnings("static-access")
	public void genCodeArguments(BufferedWriter out) throws IOException {
		
		int i = 0;
		out.write("(");
		
		for(Argument argument : message.getArguments()){
			Parser.getModel();
			if(argument.getInstance()!=null){
				if(argument.isEnum()){
					String type = Model.getTrieID(argument.getType());
					out.write(type + "."+argument.getInstance().toUpperCase());

			//No Enum but instance
				}else{

					//Instance of the class in the same file of the class
					if(message.getSendEvent().getCovered().isClassInstance()){
						out.write(Parser.getModel().getTrieID(argument.getType()).substring(0, 1).toLowerCase()+Parser.getModel().getTrieID(argument.getType()).substring(1));
					}
					
//					out.write(argument.getInstance()); BECAREFUL
					else if(message.getSendEvent().getCovered().isRepeated()){
						
						out.write(message.getSendEvent().getCovered().getName()+"."+"get" + Parser.getModel().getTrieID(argument.getType()).substring(0, 1).toUpperCase()+Parser.getModel().getTrieID(argument.getType()).substring(1)+"()");
					}else					
						out.write(message.getSendEvent().getCovered().getRepresents().getName()+"."+"get" + argument.getName().substring(0, 1).toUpperCase()+argument.getName().substring(1)+"()");

				}
			}else{
				
				if(argument.getNumRep()>0){
					out.write(message.getSendEvent().getCovered().getRepresents().getName()+argument.getNumRep());
				}else{
					out.write(argument.getValue());

				}
			}		
			if(i++ < message.getArguments().size() - 1){
				out.write(",");
			}
		}
		out.write(")");
	}
	


	private void genCodeCreate(BufferedWriter out) throws IOException {		
		generatorFragment = new FragmentAndroid(message.getReceiveEvent());
		generatorFragment.genCodeCreate(out);
	}
	
	
	
}
