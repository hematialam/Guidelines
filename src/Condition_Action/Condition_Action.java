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
public class Condition_Action {
public static void main(String args[]) {
//reading file line by line in Java using BufferedReader
FileInputStream fis = null;
BufferedReader reader = null;
BufferedWriter out=null;
Matcher matcher;
int if1c=0;
int if2c=0;
int when1c=0;
int when2c=0;
int ind=0;
int start=0;
int end = 0;
try {
fis = new FileInputStream("Asthma/Asthma_Trees");
reader = new BufferedReader(new InputStreamReader(fis));
out = new BufferedWriter(new FileWriter("Asthma_result"));
//System.out.println("Reading File line by line using BufferedReader");
String line = reader.readLine();
String storage = "";
String condition = "";
String action = "";

Pattern pattern = Pattern.compile("\\((\\S+) (\\S+)\\)");
Pattern pattern1 = Pattern.compile("\\)");
Pattern patternif = Pattern.compile("(.*?)\\(SBAR \\(IN [Ii]f\\)");
Pattern patternif1 = Pattern.compile("(.*?)\\(SBAR \\(IN [Ii]f\\) (.*?) \\(, ,\\) (.*?)\\.");
Pattern patternif2 = Pattern.compile("(.*?)\\(SBAR \\(IN [Ii]f\\) (.*?)\\. ");
Pattern patternwhen = Pattern.compile("(.*?)\\(SBAR \\(WHADVP \\(WRB [Ww]hen\\)");
Pattern patternwhen1 = Pattern.compile("(.*?)\\(SBAR \\(WHADVP \\(WRB [Ww]hen\\) (.*?) \\(, ,\\) (.*?)\\.");
Pattern patternwhen2 = Pattern.compile("(.*?)\\(SBAR \\(WHADVP \\(WRB [Ww]hen\\) (.*?)\\. ");
Pattern patternp = Pattern.compile("\\)+");

while(line != null){
//if (line.indexOf("if ")!=-1 || line.indexOf("If ")!=-1 || line.indexOf("should")!=-1 || line.indexOf("Should")!=-1)
//{matcher = pattern4.matcher(storage);
	matcher = patternif.matcher(line);
if(matcher.find()){
	ind=line.indexOf("(SBAR (IN");
	start =1;
	end=0;
	int i;;
	for(i=ind+1;start!=end && i<line.length();i++)
	{
		if (line.charAt(i)=='(')
			start++;
		else if(line.charAt(i)==')')
			end++;
	}
	condition=line.substring(ind, i);
	System.out.println(condition);
	while(i<line.length()-1 && line.charAt(i)!=','&& line.charAt(i)!='.')
		i++;
	if (line.charAt(i)==',')
		action=line.substring(i+5, line.length());
	else
		action=line.substring(0,ind-1);
	System.out.println(action);
	matcher = patternp.matcher(condition);
	condition=matcher.replaceAll(")");
	matcher = patternp.matcher(action);
	action=matcher.replaceAll(")");
	matcher = pattern.matcher(condition);
	out.write(" Condition: ");
	while (matcher.find()) {
		out.write(matcher.group(2)+" ");

}
	matcher = pattern.matcher(action);
	out.write(" Action: ");
	while (matcher.find()) {
		out.write(matcher.group(2)+" ");

}

	
	if1c++;
	out.newLine();
	//matcher = patternif1.matcher(line);
//if(matcher.find()){
//	condition=matcher.group(2);
//	action=matcher.group(3);
//	matcher = pattern.matcher(condition);
//	out.write(" Condition if 1: ");
//	while (matcher.find()) {
//		out.write(matcher.group(2)+" ");
//
//}
//	matcher = pattern.matcher(action);
//	out.write(" Action: ");
//	while (matcher.find()) {
//		out.write(matcher.group(2)+" ");
//
//}
//	if1c++;
//	out.newLine();
//}
//else if(patternif2.matcher(line).find())
//{
//	matcher = patternif2.matcher(line);
//	if(matcher.find()){
//	condition=matcher.group(2);
//	action=matcher.group(1);
//	matcher = pattern.matcher(condition);
//	out.write(" Condition if 2: ");
//	while (matcher.find()) {
//		out.write(matcher.group(2)+" ");
////storage=matcher.replaceAll("$2");
//
//}
//	matcher = pattern.matcher(action);
//	out.write(" Action: ");
//	while (matcher.find()) {
//		out.write(matcher.group(2)+" ");
////storage=matcher.replaceAll("$2");
//
//}
//	
//	if2c++;
//	out.newLine();	
//
//	}
//	}
}

else {
		matcher = patternwhen.matcher(line);
		if(matcher.find()){
	ind=line.indexOf("(SBAR (WHADVP (WRB");
	start =1;
	end=0;
	int i;;
	for(i=ind+1;i<line.length()-1 &&start!=end;i++)
	{
		if (line.charAt(i)=='(')
			start++;
		else if(line.charAt(i)==')')
			end++;
	}
	condition=line.substring(ind, i);
	while(i<line.length()-1 &&line.charAt(i)!=','&& line.charAt(i)!='.')
		i++;
	if (line.charAt(i)==',')
		action=line.substring(i+5, line.length());
	else
		action=line.substring(0,ind-1);
	
	matcher = patternp.matcher(condition);
	condition=matcher.replaceAll(")");
	matcher = patternp.matcher(action);
	action=matcher.replaceAll(")");
	matcher = pattern.matcher(condition);
	out.write(" Condition: ");
	while (matcher.find()) {
		out.write(matcher.group(2)+" ");

}
	matcher = pattern.matcher(action);
	out.write(" Action: ");
	while (matcher.find()) {
		out.write(matcher.group(2)+" ");

}
System.out.println(condition);
	System.out.println(action);
	when1c++;
	out.newLine();
	
}}
//else if(patternwhen1.matcher(line).find()){
//	
//	
//	
//	
//	
//	
//	
//	
//matcher = patternwhen1.matcher(line);
//if(matcher.find()){
//condition=matcher.group(2);
//action=matcher.group(3);
//matcher = pattern.matcher(condition);
//out.write(" Condition when 1: ");
//while (matcher.find()) {
//	
//	out.write(matcher.group(2)+" ");
////storage=matcher.replaceAll("$2");
//
//}
//matcher = pattern.matcher(action);
//out.write(" Action: ");
//while (matcher.find()) {
//	out.write(matcher.group(2)+" ");
////storage=matcher.replaceAll("$2");
//
//}
//when1c++;
//out.newLine();
//}
//}
//else if(patternwhen2.matcher(line).find())
//{
//matcher = patternwhen2.matcher(line);
//if(matcher.find()){
//condition=matcher.group(2);
//action=matcher.group(1);
//matcher = pattern.matcher(condition);
//out.write(" Condition when 2: ");
//while (matcher.find()) {
//	out.write(matcher.group(2)+" ");
////storage=matcher.replaceAll("$2");
//
//}
//matcher = pattern.matcher(action);
//out.write(" Action: ");
//while (matcher.find()) {
//	out.write(matcher.group(2)+" ");
////storage=matcher.replaceAll("$2");
//
//}
//
//when2c++;
//out.newLine();	
//}
//
//}
condition="";
action="";

line = reader.readLine();
}
out.close();
System.out.println("Done "+if1c+";"+if2c+";"+when1c+";"+when2c);
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
