
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;

import java.io.*;
import javax.net.ssl.HttpsURLConnection;

import org.json.*;

public class JAPIMain {

	// BASE URL:
	// http://services.runescape.com/m=itemdb_oldschool

	public static void main(String[] args) throws IOException {

		int startingXP = 0, goalXP, numActions, totalCost, xpDifference, tempStartLevel, tempEndLevel,
				assignedActionPrice = 0;
		double actionXP, xpmod = 1;
		String skillName, methodName, possibleBoost, startPoint, endPoint, generalMethod;
		Scanner userInput = new Scanner(System.in);

		// it will take a little bit to read in all of the info
		int bloodrPrice, natrPrice, soulrPrice,
				// heads
				bvHeadPrice, dmnHeadPrice, drgnHeadPrice,
				// bones
				bigBonesPrice, dragonBonesPrice, drakeBonesPrice, wyrmBonesPrice, wyvernBonesPrice;

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

		// now using function to fetch "current" prices from osrs ge api

		 bloodrPrice=GetPrice(bloodrurl);
		 natrPrice=GetPrice(natrurl);
		 soulrPrice=GetPrice(soulrurl);
		// heads
		 bvHeadPrice=GetPrice(bvhurl);
		 dmnHeadPrice=GetPrice(dmnhurl);
		 drgnHeadPrice=GetPrice(drgnhurl);
		// bones
		bigBonesPrice = GetPrice(bigburl);
		dragonBonesPrice = GetPrice(drgnburl);
		drakeBonesPrice = GetPrice(drkburl);
		wyrmBonesPrice = GetPrice(wyrmburl);
		wyvernBonesPrice = GetPrice(wyvburl);

		System.out.println("...Just finished reading in all prices");
		System.out.println("");// for formatting
		System.out.println("");// for formatting

		/*
		 * //now printing all results //System.out.println("Printing prices so far: ");
		 * 
		 * //rune prices print//
		 * System.out.println("blood rune price is currently:		"+bloodrPrice+"gp");
		 * System.out.println("nature rune price is currently:		"+natrPrice+"gp");
		 * System.out.println("soul rune price is currently:		"+soulrPrice+"gp");
		 * System.out.println("");//for formatting
		 * 
		 * //head prices print//
		 * System.out.println("bloodveld head price is currently:	"+bvHeadPrice+"gp");
		 * System.out.println("demon head price is currently:		"+dmnHeadPrice+"gp")
		 * ;
		 * System.out.println("dragon head price is currently:		"+drgnHeadPrice+"gp"
		 * ); System.out.println("");//for formatting
		 * 
		 * //bone prices print//
		 * System.out.println("big bones price is currently:		"+bigBonesPrice+"gp"
		 * ); System.out.println("dragon bonens price is currently:	"+dragonBonesPrice+
		 * "gp");
		 * System.out.println("drake bones price is currently:		"+drakeBonesPrice+
		 * "gp");
		 * System.out.println("wyrm bones price is currently:		"+wyrmBonesPrice+
		 * "gp");
		 * System.out.println("wyvern bones price is currently:	"+wyvernBonesPrice+"gp")
		 * ; System.out.println("");//for formatting
		 */

		// set up xp level brackets (separated for easier readbility)
		int xpForEachLevel[] = { 0, 83, 174, 276, 388, 512, 650, 801, 969, // 1-9

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

		/*
		 * 2 ints for xps 3 strings for others
		 */

		System.out.println("enter all answers in all lower case");

		// ask for initial xp
		System.out.println("current xp or current level: ");
		startPoint = userInput.nextLine();

		if (startPoint.equals("xp")) {
			System.out.println("What is your current xp: ");
			startingXP = userInput.nextInt();
		} else {
			System.out.println("what is your current level: ");
			tempStartLevel = userInput.nextInt();
			if (tempStartLevel < 1)
				tempStartLevel = 1;
			startingXP = xpForEachLevel[tempStartLevel - 1];
		}
		userInput.nextLine();

		// get goal xp
		System.out.println("Is your goal a level or xp: ");
		endPoint = userInput.nextLine();
		if (endPoint.equals("xp")) {
			System.out.println("What is your xp goal: ");
			goalXP = userInput.nextInt();
		} else {
			System.out.println("What level do you want: ");
			tempEndLevel = userInput.nextInt();
			if (tempEndLevel < 1)
				tempEndLevel = 1;
			goalXP = xpForEachLevel[tempEndLevel - 1];
		}

		userInput.nextLine();

		// ask for skill
		System.out.println("What skill: ");
		skillName = userInput.nextLine();

		// ask for general method (prayer)
		System.out.println("bones or heads: ");
		generalMethod = userInput.nextLine();
		
		//ask for specific method name
		System.out.println("What specific method are you using: ");
		methodName = userInput.nextLine();
		actionXP = AssignBaseXP(methodName);

		
		// (if prayer and not heads) ask for modifier
		if (!(generalMethod.equals("heads"))) {
			System.out.println("Where are you training: ");
			possibleBoost = userInput.nextLine();

			if (possibleBoost.equals("gilded altar")) {
				xpmod = 3.5;
			} else if (possibleBoost.equals("chaos altar")) {
				xpmod = 7.0;
			} else if (possibleBoost.equals("ectofunctus")) {
				xpmod = 4.0;
			}
		}

		// assign price taken from database to answer for skilling choice
		switch (methodName) {
		case "big bones":
			assignedActionPrice = bigBonesPrice;
			break;
		case "dragon bones":
			assignedActionPrice = dragonBonesPrice;
			break;
		case "drake bones":
			assignedActionPrice = drakeBonesPrice;
			break;
		case "wyrm bones":
			assignedActionPrice = wyrmBonesPrice;
			break;
		case "wyvern bones":
			assignedActionPrice = wyvernBonesPrice;
			break;
		case "bloodveld heads":
			//add 3 rune types to cost
			assignedActionPrice+= (2*soulrPrice);
			assignedActionPrice+= (2*natrPrice);
			assignedActionPrice+= (1*bloodrPrice);
			//add head to cost
			assignedActionPrice+= bvHeadPrice;
			break;
		case "demon heads":
			//add 3 rune types to cost
			assignedActionPrice+= (2*soulrPrice);
			assignedActionPrice+= (4*natrPrice);
			assignedActionPrice+= (1*bloodrPrice);
			//add head to cost
			assignedActionPrice+= dmnHeadPrice;
			break;
		case "dragon heads":
			//add 3 rune types to cost
			assignedActionPrice+= (4*soulrPrice);
			assignedActionPrice+= (4*natrPrice);
			assignedActionPrice+= (2*bloodrPrice);
			//add head to cost
			assignedActionPrice+= drgnHeadPrice;
			break;
		default:
			System.out.print("no matches with given method name... exitting...");
			System.exit(-1);
			break;
		}

		// calc xp/actions
		numActions = calcActions(startingXP, goalXP, actionXP, xpmod);
		totalCost = assignedActionPrice * numActions;
		xpDifference = goalXP - startingXP;
		System.out.println("");
		System.out.println("================================");
		System.out.println("for training with " + methodName + ": ");
		System.out.println("================================");
		// print total actions and gp/xp for method
		System.out.println("total cost:		" + totalCost);
		System.out.println("total actions:		" + numActions);
		System.out.println("xp needed:		" + xpDifference);

	}// end of main

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

	static int calcActions(double startxp, double finishxp, double xpPerAction, double xpMod) {
		// xpmod is xpmodifier for stuff like chaos/gilded altar etc

		if ((startxp >= finishxp) || finishxp == 0) {
			System.out.println("finish xp should be above 0... exitting...");
			System.exit(-1);
		}

		double xpNeeded = finishxp - startxp;
		int neededActions = 0;
		double currentxp = 0;

		xpPerAction = xpPerAction * xpMod;

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

}

/*
 * //response not specific to nature rune, but is general item jsonresponse
 * 
 * { "item":{ "icon":
 * "http://services.runescape.com/m=itemdb_rs/4908_obj_sprite.gif?id=4151",
 * "icon_large":
 * "http://services.runescape.com/m=itemdb_rs/4908_obj_big.gif?id=4151",
 * "id":4151, "type":"Default",
 * "typeIcon":"http://www.runescape.com/img/categories/Default",
 * "name":"Abyssal whip", "description":"A weapon from the abyss.", "current":{
 * "trend":"neutral", "price":"2.3m" }, "today":{ "trend":"neutral", "price":0
 * }, "members":"true", "day30":{ "trend":"positive", "change":"+9.0%" },
 * "day90":{ "trend":"negative", "change":"-7.0%" }, "day180":{
 * "trend":"positive", "change":"+44.0%" } } }
 */

// ***first attempt at getting data***//
// String output;
// StringBuffer response = new StringBuffer();

// System.out.println("Output from Server .... \n");

/*
 * while ((output = br.readLine()) != null) { response.append(output);
 * System.out.println(output); }
 */

// System.out.println(response.toString());
// System.out.print(output);

// JSONParser parse = new JSONParser(output, null, false);
// JSObject jobj = (JSObject)parse.parse(response.toString());
