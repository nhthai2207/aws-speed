import java.util.Calendar;

public abstract class Parser {
	protected String fullFileDir;

	public Parser(String fullFileDir) {
		this.fullFileDir = fullFileDir;
	}

	public abstract void process();

	public void run(int numberOfRunTime) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < numberOfRunTime; i++) {
			this.process();
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("%s,%s", this.getClass().getName(), (endTime - startTime)));
	}
}
