
import javafx.application.*;
import javafx.collections.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.stage.Stage;

//today add basic look
//add new scene from main
//tomorrow add xp back and forth func
//next day add calc a retrieve stats

public class PrayerMain {

	// each skill in calc will have its own xp/level input boxes
	// and its own drop down menu for selecting methods
	// and its own area to display results
	static int assignedActionPrice = 0;
	static double basexp=0;
	static double xpmod = 1;
	static String genericMethod ="";

	public static void displayPrayer() {

		Stage primaryStage = new Stage();

		// stage
		// scene
		// layout
		// content or other layout

		// stuff to make the scene
		Label newXPLabel = new Label("Goal XP");
		Label oldXPLabel = new Label("Current XP");
		Label newLevelLabel = new Label("Goal Level");
		Label oldLevelLabel = new Label("Current Level");

		TextField newXPField = new TextField();
		TextField oldXPField = new TextField();
		TextField newLevelField = new TextField();
		TextField oldLevelField = new TextField();

		// RadioButtonGroup
		ToggleGroup prayerLocation = new ToggleGroup();
		Label altarSelectionLabel = new Label("Where are you training prayer:");
		RadioButton buryButton = new RadioButton("bury bones");
		RadioButton gildedButton = new RadioButton("gilded altar");
		RadioButton ectoButton = new RadioButton("ectofunctus");
		RadioButton chaosButton = new RadioButton("chaos altar");
		
		buryButton.setSelected(true);

		buryButton.setToggleGroup(prayerLocation);
		gildedButton.setToggleGroup(prayerLocation);
		ectoButton.setToggleGroup(prayerLocation);
		chaosButton.setToggleGroup(prayerLocation);

		Label methodPrompt = new Label("What method will you use: ");
		ComboBox<String> methodBox = new ComboBox<>();
		methodBox.getItems().addAll("bloodveld heads", "demon heads",
						"dragon heads", "big bones", "dragon bones", "drake bones", "wyrm bones", "wyvern bones");
		methodBox.setPromptText("choose method");

		Button calculateButton = new Button("Calculate!");
		calculateButton.setOnAction(e -> newXPField.setText("" + SkillCalcMain.bloodrPrice));

		Label totalGP = new Label("Approx gp required: ?");// + give the amt in label
		Label totalActions = new Label("Actions required: ?");// + give the amt in label
		Label XPNeeded = new Label("XP to your goal: ?");// + give the amt in label

		GridPane prayerGrid = new GridPane();
		prayerGrid.getChildren().addAll(newXPLabel, oldXPLabel, newLevelLabel, oldLevelLabel, newXPField, oldXPField,
				newLevelField, oldLevelField, altarSelectionLabel, buryButton, gildedButton, ectoButton, chaosButton, methodPrompt,
				methodBox, calculateButton, totalGP, totalActions, XPNeeded);

		prayerGrid.setVgap(3);
		prayerGrid.setHgap(3);
		prayerGrid.setPadding(new Insets(5, 5, 5, 5));

		prayerGrid.setConstraints(newXPLabel, 1, 2);
		prayerGrid.setConstraints(oldXPLabel, 1, 0);
		prayerGrid.setConstraints(newLevelLabel, 0, 2);
		prayerGrid.setConstraints(oldLevelLabel, 0, 0);
		prayerGrid.setConstraints(newXPField, 1, 3);
		prayerGrid.setConstraints(oldXPField, 1, 1);
		prayerGrid.setConstraints(newLevelField, 0, 3);
		prayerGrid.setConstraints(oldLevelField, 0, 1);

		prayerGrid.setConstraints(altarSelectionLabel, 0, 4);
		prayerGrid.setConstraints(buryButton, 0, 5);
		prayerGrid.setConstraints(gildedButton, 0, 6);
		prayerGrid.setConstraints(ectoButton, 0, 7);
		prayerGrid.setConstraints(chaosButton, 0, 8);

		prayerGrid.setConstraints(methodPrompt, 0, 9);
		prayerGrid.setConstraints(methodBox, 0, 10);

		prayerGrid.setConstraints(calculateButton, 0, 11);

		prayerGrid.setConstraints(totalGP, 0, 12);
		prayerGrid.setConstraints(totalActions, 0, 13);
		prayerGrid.setConstraints(XPNeeded, 0, 14);

		Scene prayerScene = new Scene(prayerGrid);

		primaryStage.setTitle("Prayer");
		primaryStage.setScene(prayerScene);
		primaryStage.show();

		// check for append
		// check for select
		// loop for correct level/ w.e
		// compare
		// do something

		// on press take previous string value
		// try to parse new string value to number
		// if doesn't parse then paste old string & throw out new string

		
		
		//update combobox to also set price/xp
		//hook up prayer loc functionality
		//hook up calc button to calc
		//make output correct
		
		

		// checks for pressing spacebar in specific field
		oldXPField.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				// now loop for correct level to assign to oldLevelField (if contains int)
				try {
					int tempInt = Integer.parseInt(oldXPField.getText());
					int tempLevel=0;
					while(tempInt >= SkillCalcMain.xpForEachLevel[tempLevel]) {
						tempLevel++;
					}
					//tempLevel++;
					oldLevelField.setText(tempLevel+"");

				} catch (NumberFormatException E) {
					if(!(oldXPField.getText().equals("")))
					oldXPField.setText("Integers only");

				}

			}
		});

		newXPField.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				// now loop for correct level to assign to oldLevelField (if contains int)
				try {
					int tempInt = Integer.parseInt(newXPField.getText());
					int tempLevel=0;
					while(tempInt >= SkillCalcMain.xpForEachLevel[tempLevel]) {
						tempLevel++;
					}
					//tempLevel++;
					newLevelField.setText(tempLevel+"");

				} catch (NumberFormatException E) {
					if(!(newXPField.getText().equals("")))
					newXPField.setText("Integers only");

				}

			}
		});

		oldLevelField.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				// now loop for correct level to assign to oldLevelField (if contains int)
				try {
					int tempInt = Integer.parseInt(oldLevelField.getText());

					if(tempInt >99)
						tempInt =99;
					
					oldLevelField.setText(tempInt+"");
					oldXPField.setText(SkillCalcMain.xpForEachLevel[tempInt-1]+"");
					
					
				} catch (NumberFormatException E) {
					if(!(oldLevelField.getText().equals("")))
					oldLevelField.setText("Integers only");

				}

			}
		});

		newLevelField.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				// now loop for correct level to assign to oldLevelField (if contains int)
				try {
					int tempInt = Integer.parseInt(newLevelField.getText());
					
					if(tempInt >99)
						tempInt =99;
					
					newLevelField.setText(tempInt+"");
					newXPField.setText(SkillCalcMain.xpForEachLevel[tempInt-1]+"");

				} catch (NumberFormatException E) {
					if(!(newLevelField.getText().equals("")))
					newLevelField.setText("Integers only");

				}

			}
		});
		
		buryButton.setOnAction(e -> xpmod =1.0);
		gildedButton.setOnAction(e -> xpmod =3.5);
		ectoButton.setOnAction(e -> xpmod =4.0);
		chaosButton.setOnAction(e -> xpmod =7.0);
		
		
		methodBox.setOnAction(e -> {
			//set base xp
			//set base price
			basexp = SkillCalcMain.AssignBaseXP(methodBox.getValue());
			assignedActionPrice = SkillCalcMain.AssignMethodActionPrice(methodBox.getValue());
			
			if(methodBox.getValue().contains("bones")) {
				genericMethod = "bones";
			} else {
				genericMethod ="heads";
			}
		});
		
		calculateButton.setOnAction(e ->{
			double actionXP=0;
			int startingxp, goalxp, reqActions, totalCost, xpDif;
			//check for base xp >0
			//check for price >0
			
			//System.out.println("before check xp/price");
			if(basexp >0 && assignedActionPrice > 0) {
				
				try {
				startingxp =Integer.parseInt(oldXPField.getText());
				goalxp =Integer.parseInt(newXPField.getText());
				
				if(genericMethod.equals("bones"))
					actionXP = basexp*xpmod;
				else
					actionXP = basexp;
				
				//System.out.println("before calc actions");
				reqActions =SkillCalcMain.calcActions(startingxp, goalxp, actionXP);
				if(reqActions != -1) {
					//System.out.println("req actions != -1");
					totalCost = reqActions* assignedActionPrice;
					xpDif = goalxp-startingxp;
					
					totalGP.setText("Approx gp required: "+totalCost);// + give the amt in label
					totalActions.setText("Actions required: "+reqActions);// + give the amt in label
					XPNeeded.setText("XP to your goal: "+xpDif);//
					
				} else {
					totalGP.setText("Approx gp required: ?");
					totalActions.setText("Actions required: ?");
					XPNeeded.setText("XP to your goal: ?");
				}
				
				} catch(NumberFormatException e1){
					System.out.println("couldn't get valid ints");
				}
				
			}
			
			//use mult and base for actual xp
				//based on gen method/mult
			//use actual xp with xp box amounts and calc actions needed
			//print xp dif, actions needed, approx gp needed
			
			
			
			
		});
		

	}//end of main display function
	
	

}






