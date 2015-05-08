import java.util.Calendar;

public abstract class Parser {
	protected String fullFileDir;

	public Parser(String fullFileDir) {
		this.fullFileDir = fullFileDir;
	}

	public abstract void process();

	public void run(int numberOfRunTime) {
		for (int i = 0; i < numberOfRunTime; i++) {
			long startTime = Calendar.getInstance().getTimeInMillis();
			this.process();
			long endTime = Calendar.getInstance().getTimeInMillis();
			System.out.println(String.format("%s,%s,%s", this.getClass().getName(), i, (endTime - startTime)));
		}
	}
}
