import org.jdom.Element;
import org.joda.time.DateTime;


public class Node {

	DateTime startDt;
	String title;
	DateTime endDt;
	Element element;
	
	public Node(Element e) {
		
		element = e;
		
	}
	
}
