import java.io.FileReader;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FangiongParser extends Parser {

	public FangiongParser(String data) {
		super(data);

	}

	@Override
	public void process() {
		try {
			JSONParser parser = new JSONParser();
			FileReader fileReader = new FileReader(this.fullFileDir);
			Object obj = parser.parse(fileReader);
			JSONObject json = (JSONObject) obj;
			Set<String> keySet = json.keySet();
			for(String key : keySet){
				Object object = json.get(key);						
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
