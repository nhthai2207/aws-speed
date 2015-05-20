import java.io.FileReader;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonParser extends Parser {

	public GsonParser(String data) {
		super(data);
	}

	@Override
	public void process() {
		try {
			JsonParser parser = new JsonParser();
			FileReader fileReader = new FileReader(this.fullFileDir);
			JsonElement jsonElement = parser.parse(fileReader);
			JsonObject asJsonObject = jsonElement.getAsJsonObject();
			Set<Entry<String, JsonElement>> entrySet = asJsonObject.entrySet();
			for (Entry<String, JsonElement> entry : entrySet) {
				JsonElement value = entry.getValue();
			}
			fileReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
