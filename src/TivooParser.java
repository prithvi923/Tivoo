import java.io.*;

import java.util.*;
import java.lang.Object;

import org.jdom.*;
import org.jdom.input.*;
import org.joda.time.*;
import org.joda.time.format.*;

import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Html;


public class TivooParser {
	public static void main(String[] args) {
        // Assume filename argument
        String filename = "DukeBasketball.xml";
        PrintStream out = System.out;
        try {
            // Build the document with SAX and Xerces, no validation
            SAXBuilder builder = new SAXBuilder();
            // Create the document
            Document doc = builder.build(new File(filename));
            Element root = doc.getRootElement();
            List calendars = root.getChildren("Calendar");
            out.println("Duke has "+ calendars.size() +" scheduled games");
            List remainingGames = returnRemainingGames(calendars);
            
            out.println("Duke has "+ remainingGames.size() +" remaining games");
            
            writeToHtml(remainingGames, out);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private static void writeToHtml(List remainingGames, PrintStream out) {
		
		Html html = new Html();
		
		for (Object game : remainingGames) {
			
			Element thisGame = (Element) game;
			Div div = new Div();
			div.setId("Game").setCSSClass("myclass");
			A link = new A();
			link.setHref(thisGame.getChild("Description").getText()).setTarget("_blank").appendText(thisGame.getChild("Subject").getText());
			                
			div.appendChild(link);
			//out.println(div.write());
			html.appendChild(div);
		}
		//out.println(html.write());
		try {
            
            //Construct the BufferedWriter object
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.html"));
            
            //Start writing to the output stream
            bufferedWriter.write(html.write());
            bufferedWriter.close();
        
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
	}

	public static List returnRemainingGames(List games) {
		List<Object> remainingGames = new ArrayList<Object>();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("M/d/yyyy");
		
		for (Object game : games) {
        	Element checkGame = (Element) game;
        	String str = checkGame.getChild("StartDate").getText();
        	DateTime dt = fmt.parseDateTime(str);
        	if (dt.isAfterNow()) {
        		remainingGames.add(game);
        	}
        }
		
		return remainingGames;
		
	}
	
	
}
