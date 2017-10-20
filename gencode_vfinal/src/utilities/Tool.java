package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Model;

public final class Tool {


	private Tool() {
		throw new AssertionError();
	}
	
		
	/**
	 * loadXMI: First step, created trie need to forward references
	 * @param inputFileName
	 */
	public static void loadXMI(String inputFileName) throws IOException {
		String line, key, value;
		@SuppressWarnings("resource")
		BufferedReader bf = new BufferedReader(new FileReader(inputFileName));
		while (bf.ready()) {
			line = bf.readLine();
			key = Tool.manipulate(line, "xmi:id");
		
			if (key != null) {
				value = Tool.manipulate(line, "name=");
			
				Model.putTrieID(key, value);
		
			}
		}
	}
	
	/**
	 * manipulate: Return a substring of key from String str.
	 * 			   Delimited for String first and last
	 * @param str
	 * @param key
	 * @param first
	 * @param last
	 * @return String
	 */
	public static String manipulate(String str, String key, String first,
			String last) {
		int x, x1;
		int n = str.indexOf(key);
		if (n == -1) {
			return "";
		}
		x = str.indexOf(first, n);
		if(str.contains(last)){
			x1 = str.indexOf(last, x + 1);
		} else{
			x1 = str.length();
		}
		return str.substring(x + 1, x1);
	}
	
	/*
	 * manipulate: Return a substring of key from String str.
	 */
	public static String manipulate(String str, String key) {
		int n = str.indexOf(key);
		if (n == -1) {
			return "";
		}
		int x = str.indexOf("\"", n);
		int x1 = str.indexOf("\"", x + 1);
		return str.substring(x + 1, x1);
	}

	/*
	 * indentation: Return a string with \t * tab to indentation file
	 */
	public static String indentation(int tab){
		if(tab == 0) return "";
		StringBuilder tabInd = new StringBuilder("\t");
		for(int i=0; i < tab - 1; i++){
			tabInd.append("\t");
		}
		return tabInd.toString();
	}
	
	

}
