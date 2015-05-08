import java.io.FileReader;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class JsonpParser extends Parser {
	
	public JsonpParser(String data) {
		super(data);
	}

	@Override
	public void process() {
		try {
			JsonReader reader = Json.createReader(new FileReader(this.fullFileDir));
			JsonObject readObject = reader.readObject();
			Set<String> keySet = readObject.keySet();
			for(String key : keySet){
				JsonValue jsonValue = readObject.get(key);
				//System.out.println(jsonValue);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
