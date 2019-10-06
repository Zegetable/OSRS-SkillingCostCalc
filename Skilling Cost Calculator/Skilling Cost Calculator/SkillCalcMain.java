
import java.net.*;
import java.util.Scanner;

import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import org.json.*;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.stage.Stage;

public class SkillCalcMain extends Application {

	static int bloodrPrice = 0, natrPrice = 0, soulrPrice = 0,
			// heads
			bvHeadPrice = 0, dmnHeadPrice = 0, drgnHeadPrice = 0,
			// bones
			bigBonesPrice = 0, dragonBonesPrice = 0, drakeBonesPrice = 0, wyrmBonesPrice = 0, wyvernBonesPrice = 0;

	// set up xp level brackets (separated for easier readbility)
	static final int xpForEachLevel[] = { 0, 83, 174, 276, 388, 512, 650, 801, 969, // 1-9

			1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, // 10-19
			4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, // 20-29

			13363, 14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, // 30-39
			37224, 41171, 45529, 50339, 55649, 61512, 67983, 75128, 83014, 91721,

			101333, 11945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, // 50-59
			273742, 302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 688051,

			737627, 814445, 899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200, 1798808, // 70-79
			1986068, 2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294, 4385776, 4842295,

			5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629, 11805606, 13034431, // 90-99

	};

	public static void main(String[] args) throws IOException {

		launch(args);

	}// end of main

	// begin javafx
	public void start(Stage primaryStage) throws IOException {

		int startingXP = 0, goalXP, numActions, totalCost, xpDifference, tempStartLevel, tempEndLevel,
				assignedActionPrice = 0;
		double actionXP, xpmod = 1;
		String skillName, methodName, possibleBoost, startPoint, endPoint, generalMethod;
		Scanner userInput = new Scanner(System.in);

		// rune urls
		// blood rune url
		URL bloodrurl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=565");
		// nature rune url
		URL natrurl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=561");
		// soul runeurl
		URL soulrurl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=566");

		// ensouled head urls
		// bloodveld head url
		URL bvhurl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=13496");
		// demon head url
		URL dmnhurl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=13502");
		// dragon head url
		URL drgnhurl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=13511");

		// bones urls
		// big bones url
		URL bigburl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=532");
		// dragon bones url
		URL drgnburl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=536");
		// drake bones url
		URL drkburl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=22783");
		// wyrm bones url
		URL wyrmburl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=22780");
		// wyvern bones url
		URL wyvburl = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=6812");

		Text initialLoadingText = new Text("Data is being retrieved from osrs API, this may take a moment...");
		primaryStage.setTitle("LOADING API DATA...");
		primaryStage.setWidth(400);
		primaryStage.setHeight(100);
		primaryStage.show();

		
		  // now using function to fetch "current" prices from osrs ge api
		  bloodrPrice = GetPrice(bloodrurl);
		  natrPrice = GetPrice(natrurl);
		  soulrPrice =  GetPrice(soulrurl);
		  // heads
		  bvHeadPrice = GetPrice(bvhurl);
		  dmnHeadPrice =GetPrice(dmnhurl);
		  drgnHeadPrice = GetPrice(drgnhurl);
		  // bones
		  bigBonesPrice = GetPrice(bigburl); dragonBonesPrice = GetPrice(drgnburl);
		  drakeBonesPrice =GetPrice(drkburl);
		  wyrmBonesPrice = GetPrice(wyrmburl);
		  wyvernBonesPrice = GetPrice(wyvburl);
		 

		GridPane loadBack = new GridPane();

		Button herbloreSkillButton = new Button("Herblore");
		Button prayerSkillButton = new Button("Prayer");

		primaryStage.setWidth(400);
		primaryStage.setHeight(400);

		loadBack.setHgap(3);
		loadBack.setVgap(3);
		loadBack.getChildren().addAll(/*herbloreSkillButton, */prayerSkillButton);
		loadBack.setConstraints(prayerSkillButton, 0, 0);
		//loadBack.setConstraints(herbloreSkillButton, 1, 0);
		loadBack.setPadding(new Insets(4, 4, 4, 4));
		primaryStage.setScene(new Scene(loadBack, 500, 300));

		prayerSkillButton.setOnAction(e -> PrayerMain.displayPrayer());

		primaryStage.setTitle("OSRS Skill Calc");
		initialLoadingText.setText("Done loading!");
		// primaryStage.onc

	}// end of javafx start

	static int GetPrice(URL itemUrl) throws IOException {
		int price = 0;
		String tempString1 = "", tempString2 = "";

		HttpURLConnection con = (HttpURLConnection) itemUrl.openConnection();
		con.setRequestMethod("GET");

		if (con.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

		String inline = "";
		Scanner sc = new Scanner(itemUrl.openStream());
		while (sc.hasNext()) {
			inline += sc.nextLine();
		}
		// System.out.println(inline);

		JSONObject jobj = new JSONObject(inline);

		Object objPrice = jobj.getJSONObject("item").getJSONObject("current").get("price");

		tempString1 = objPrice.toString();

		for (int i = 0; i < tempString1.length(); i++) {
			if (tempString1.charAt(i) != ',')
				tempString2 += tempString1.charAt(i);
		}

		price = Integer.parseInt(tempString2);

		con.disconnect();
		return price;
	}

	static int calcActions(double startxp, double finishxp, double xpPerAction) {
		// xpmod is xpmodifier for stuff like chaos/gilded altar etc

		if ((startxp >= finishxp) || finishxp <= 0) {
			System.out.println("finish xp should be above 0... exitting...");
			System.exit(-1);
		}

		double xpNeeded = finishxp - startxp;
		int neededActions = 0;
		double currentxp = 0;

		//xpPerAction = xpPerAction * xpMod;

		while (currentxp < xpNeeded) {
			neededActions++;
			currentxp = currentxp + xpPerAction;
		}

		return neededActions;
	}

	static double AssignBaseXP(String givenMethod) {

		double xpvalue = 0;

		switch (givenMethod) {
		case "big bones":
			xpvalue = 15;
			break;
		case "dragon bones":
			xpvalue = 72;
			break;
		case "drake bones":
			xpvalue = 80;
			break;
		case "wyrm bones":
			xpvalue = 50;
			break;
		case "wyvern bones":
			xpvalue = 72;
			break;
		case "bloodveld heads":
			xpvalue = 1040;
			break;
		case "demon heads":
			xpvalue = 1170;
			break;
		case "dragon heads":
			xpvalue = 1560;
			break;
		default:
			System.out.print("no matches with given method name... exitting...");
			System.exit(-1);
			break;

		}

		return xpvalue;
	}
	
	static int AssignMethodActionPrice(String givenMethod) {
		int actionPrice=0;
		switch (givenMethod) {
		case "big bones":
			actionPrice = bigBonesPrice;
			break;
		case "dragon bones":
			actionPrice = dragonBonesPrice;
			break;
		case "drake bones":
			actionPrice = drakeBonesPrice;
			break;
		case "wyrm bones":
			actionPrice = wyrmBonesPrice;
			break;
		case "wyvern bones":
			actionPrice = wyvernBonesPrice;
			break;
		case "bloodveld heads":
			actionPrice = bvHeadPrice+ (2*soulrPrice)+(2*natrPrice)+(1*bloodrPrice);
			break;
		case "demon heads":
			actionPrice = dmnHeadPrice+ (2*soulrPrice)+(4*natrPrice)+(1*bloodrPrice);
			break;
		case "dragon heads":
			actionPrice = drgnHeadPrice+ (4*soulrPrice)+(4*natrPrice)+(2*bloodrPrice);
			break;
		default:
			System.out.print("no matches with given method name... exitting...");
			System.exit(-1);
			break;

		}
		return actionPrice;
	}

}// end of classs
