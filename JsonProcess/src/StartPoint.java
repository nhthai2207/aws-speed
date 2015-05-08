public class StartPoint {
	enum ParserLib {
		JSONP, FANGIONG, GSON, JACKSON
	};

	public static void main(String[] args) {
		// Will comment this line when actual run
		args = new String[] { "JSONP", "jsondata/01.json", "100" };
		if (args.length != 3) {
			System.out
					.println("You must enter the valid 3 arguments: Type of Json Object (1: Jsonp, 2: Fangiong, 3: Gson, 4: Jackson), FullFileLocation, NumberOfRun");
			return;
		}		
		ParserLib parserLib = ParserLib.valueOf(args[0].toUpperCase());
		String fullFileLocation = args[1];
		int numberOfRun = Integer.valueOf(args[2]);
		Parser parser = null;
		switch (parserLib) {
		case JSONP:
			parser = new JsonpParser(fullFileLocation);
			break;
		case FANGIONG:
			parser = new FangiongParser(fullFileLocation);
			break;
		case GSON:
			parser = new GsonParser(fullFileLocation);
			break;
		case JACKSON:
			parser = new JacksonParser(fullFileLocation);
			break;
		}
		parser.run(numberOfRun);

	}
}
