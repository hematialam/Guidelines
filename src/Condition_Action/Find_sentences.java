package Condition_Action;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Find_sentences {
public static void main(String args[]) {
//reading file line by line in Java using BufferedReader
FileInputStream fis = null;
BufferedReader reader = null;
BufferedWriter out=null;
Matcher matcher;
try {
fis = new FileInputStream("JNC_H/JNC_Trees");
reader = new BufferedReader(new InputStreamReader(fis));
out = new BufferedWriter(new FileWriter("JNC_H/JNC_possible"));
//System.out.println("Reading File line by line using BufferedReader");
String line = reader.readLine();
String storage = "";
String condition = "";
String action = "";

Pattern pattern = Pattern.compile("\\((\\S+) (\\S+)\\)");
Pattern pattern1 = Pattern.compile("\\)");
Pattern patternIN = Pattern.compile("\\(IN ");
Pattern patternto = Pattern.compile("\\(TO ");
Pattern patternWH = Pattern.compile("\\(WHADVP ");

Pattern patternp = Pattern.compile("\\)+");

while(line != null){

	matcher = patternIN.matcher(line);
if(matcher.find()){
	
	matcher = patternp.matcher(line);
	line=matcher.replaceAll(")");
	matcher = pattern.matcher(line);
	while (matcher.find()) {
		out.write(matcher.group(2)+" ");

}
	out.newLine();
}

else {
	matcher = patternto.matcher(line);
	if(matcher.find()){
		
		matcher = patternp.matcher(line);
		line=matcher.replaceAll(")");
		matcher = pattern.matcher(condition);
		while (matcher.find()) {
			out.write(matcher.group(2)+" ");

	}
		out.newLine();
	}
	else 
	{
		matcher = patternWH.matcher(line);
		if(matcher.find()){
			
			matcher = patternp.matcher(line);
			line=matcher.replaceAll(")");
			matcher = pattern.matcher(condition);
			while (matcher.find()) {
				out.write(matcher.group(2)+" ");

		}
			out.newLine();
		}
		
	}
}

line = reader.readLine();
}
out.close();
System.out.println("Done ");
} catch (FileNotFoundException ex) {
Logger.getLogger(BufferedReader.class.getName()).log(Level.SEVERE, null, ex);
} catch (IOException ex) {
Logger.getLogger(BufferedReader.class.getName()).log(Level.SEVERE, null, ex);
} finally {
try {
reader.close();
fis.close();
} catch (IOException ex) {
Logger.getLogger(BufferedReader.class.getName()).log(Level.SEVERE, null, ex);
}
}
}
}
