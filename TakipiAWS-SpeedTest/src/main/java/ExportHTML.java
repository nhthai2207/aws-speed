import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class ExportHTML {
	public static String dataFile = "data.txt";
	public static String indexFile = "Template_index.html";
	public static String newsLetterFile = "Template_newsletter.html";

	public static Properties prop = new Properties();
	public static Set<String> keySet = new HashSet<String>();
	//public static String moreDetail = "<td align=\"center\" style=\"font-size: 14px; line-height: 20px; font-family: Helvetica, Arial, sans-serif;\"><a href=\"$url$\" style=\"color: #18B2C3; text-decoration: none;\">More details</a></td>";
	public static String moreDetail = "<a href=\"$url$\" style=\"color: #18B2C3; text-decoration: none;\">More details</a>";
	public static String contact = "<p><strong>Contact : </strong>$contact$ </p>";
	public static String contact_news = "<tr> <td style=\"font-size: 12px; line-height: 26px; font-family: Helvetica, Arial, sans-serif;\"><strong>Contact :</strong> $contact$</td> </tr>"; 
			

	public static void main(String[] args) {
		try {
			initData();
			run(indexFile);
			run(newsLetterFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initData() throws Exception {

		InputStream input = null;

		try {
			input = new FileInputStream(dataFile);
			prop.load(input);
			Set<Object> keySet2 = prop.keySet();
			for (Object obj : keySet2) {
				keySet.add((String) obj);
			}			

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void run(String srcFile) throws IOException {
		File file = new File(srcFile);
		Reader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		File tempFile = new File("Output_" + srcFile);
		FileWriter fw = new FileWriter(tempFile);

		while (br.ready()) {
			String test = br.readLine();
			String ret = processForString(test,srcFile.contains("news"));
			fw.write(ret + "\n");
		}

		fw.close();
		br.close();
		fr.close();

	}

	public static String processForString(String data, boolean isNew) {
		data = data.trim();
		String ret = data;
		for (String s : keySet) {
			if (data.contains(s)) {
				if (s.contains("$moredetail")) {
					String property = prop.getProperty(s);
					if (property != null && property.length() != 0) {
						String tmp = moreDetail.replace("$url$", property);
						ret = ret.replace(s, tmp);
					} else {
						ret = ret.replace(s, "");
					}
				} else if (s.contains("$contact")) {
					String base = contact;
					if(isNew){
						base = contact_news;
					}
					String property = prop.getProperty(s);
					if (property != null && property.length() != 0) {
						String tmp = base.replace("$contact$", property);
						ret = ret.replace(s, tmp);
					} else {
						ret = ret.replace(s, "");
					}
				} else {
					ret = ret.replace(s, prop.getProperty(s));
				}
			}
		}
		return ret;
	}
}
