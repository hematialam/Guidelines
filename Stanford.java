import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class tagging {
    public static void main(String[] args) throws IOException,
        ClassNotFoundException {
        MaxentTagger tagger = new MaxentTagger(
            "taggers/bidirectional-distsim-wsj-0-18.tagger");
        //reading file line by line in Java using BufferedReader
        FileInputStream fis = null;
        BufferedReader reader = null;
        BufferedWriter out=null;
        fis = new FileInputStream("A1C-result.txt");
        reader = new BufferedReader(new InputStreamReader(fis));
        out = new BufferedWriter(new FileWriter("Tagged.txt"));
        System.out.println("Reading File line by line using BufferedReader");
        String line = reader.readLine();
        while(line != null){
            String tagged = tagger.tagString(line);
            out.write(tagged);
            out.newLine();
            
            line = reader.readLine();
        }
        out.close();
        System.out.println("Done");
    }
}
