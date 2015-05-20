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
			FileReader fileReader = new FileReader(this.fullFileDir);
			JsonReader reader = Json.createReader(fileReader);
			JsonObject readObject = reader.readObject();
			Set<String> keySet = readObject.keySet();
			for(String key : keySet){
				JsonValue jsonValue = readObject.get(key);
			}			
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
