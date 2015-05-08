import java.io.FileReader;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonParser extends Parser {

	public JacksonParser(String data) {
		super(data);

	}

	@Override
	public void process() {
		try {
			FileReader fileReader = new FileReader(this.fullFileDir);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(fileReader);
			Iterator<JsonNode> ite = rootNode.iterator();				 
			while (ite.hasNext()) {
				JsonNode temp = ite.next();
				//System.out.println(temp); 
			}
 
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
