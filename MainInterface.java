package project2021;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.event.*;
import javafx.geometry.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainInterface extends Application {
	private final ComboBox<String> playerNameCmb = new ComboBox<>(); // Display player's name
	private final HashMap<String, Integer> player = new HashMap<>(); // Store player's name and number
	private final TextField dateField = new TextField();
	private final TextField opponent = new TextField();
	private final TextField result1 = new TextField();
	private final TextField result2 = new TextField();
	private final TextField numOfGoals = new TextField();
	private final TextArea displayArea = new TextArea();
	private final FileChooser fileChooser = new FileChooser();
	private final FileChooser fileChooser1 = new FileChooser();
	private final ComboBox<String> gameListCmb = new ComboBox<>(); // Display a selected game
	private MatchData newMatch;
	private final TextArea result = new TextArea(); // A small TextArea, which will appear in a small pop-up
	// All games will be stored in this ArrayList
	private final ArrayList<MatchData> matchData = new ArrayList<>();
	// All games which will be taken from file be stored in this ArrayList
	private ArrayList<MatchData> matchDataFromFile = new ArrayList<>();
	// All player's data (name, number, total of goals) will be stored here
	private final ArrayList<playerInfo> playerTotalGoals = new ArrayList<>();
	//File is used for saving player's name and number 
	private final String playerBasicFile = "project2021/playerNumber.txt";
	
	//Updated
	private final ComboBox<String> competition = new ComboBox<>(); //Display new league name
	private final ComboBox<String> season = new ComboBox<>();
	private final TextField assistNum = new TextField();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			readPlayerFromFile();
		} catch (Exception ex1) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Error");
			alert1.setHeaderText("Error on reading player file!");
			alert1.showAndWait();
			return;
		}
		//Save method will save file as .dat file
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("DAT Files", "*.dat"));
		//Save method will save file as .txt file
		fileChooser1.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

		// Start creating interface
		BorderPane mainPane = new BorderPane();
		// Create a vertical box which displays the menubar and the FlowPane vertically
		VBox grid1 = new VBox();
		grid1.setAlignment(Pos.CENTER);

		MenuBar menu = new MenuBar();
		Menu manageFile = new Menu("File");
		menu.getMenus().add(manageFile);
		Menu statistic = new Menu("Other");
		menu.getMenus().add(statistic);

		MenuItem newFile = new MenuItem("New");
		MenuItem openFile = new MenuItem("Open");
		MenuItem saveFile = new MenuItem("Save this game");
		MenuItem allFile = new MenuItem("Save all games");
		MenuItem sort = new MenuItem("Total shots");
		manageFile.getItems().addAll(newFile, openFile, saveFile, allFile);
		statistic.getItems().addAll(sort);
		grid1.getChildren().add(menu);

		//Updated
		FlowPane updateGrid1 = new FlowPane();
		updateGrid1.setHgap(10);
		updateGrid1.setPadding(new Insets(20,50,10,50));
		Label updateLabel1 = new Label("League:");
		Label updateLabel2 = new Label("Season");
		updateGrid1.getChildren().add(updateLabel1);
		updateGrid1.getChildren().add(competition);
		updateGrid1.getChildren().add(updateLabel2);
		updateGrid1.getChildren().add(season);
		Button newSeason = new Button("Add");
		updateGrid1.getChildren().add(newSeason);
		
		// A FlowPane with text controls for adding new game
		FlowPane grid1_1 = new FlowPane();
		grid1_1.setHgap(10);
		grid1_1.setPadding(new Insets(10, 50, 10, 50));
		Text title1 = new Text("Add a new game");
		grid1_1.setAlignment(Pos.BASELINE_LEFT);
		grid1_1.getChildren().add(title1);

		FlowPane grid1_2 = new FlowPane();
		grid1_2.setHgap(10);
		grid1_2.setPadding(new Insets(0, 50, 10, 50));
		Label label1 = new Label("Date:");
		Label label2 = new Label("Opponent:");
		Label label3 = new Label("Result:");
		Label label4 = new Label("-");
		result1.setPrefWidth(40);
		result2.setPrefWidth(40);
		grid1_2.getChildren().add(label1);
		grid1_2.getChildren().add(dateField);
		grid1_2.getChildren().add(label2);
		grid1_2.getChildren().add(opponent);
		grid1_2.getChildren().add(label3);
		grid1_2.getChildren().add(result1);
		grid1_2.getChildren().add(label4);
		grid1_2.getChildren().add(result2);

		FlowPane grid1_3 = new FlowPane();
		grid1_3.setPadding(new Insets(0, 50, 10, 50));
		grid1_3.setAlignment(Pos.BASELINE_RIGHT);
		Button addGameBtn = new Button("Add match");
		grid1_3.getChildren().add(addGameBtn);

		// Another FlowPane with text controls for adding player who score goals
		FlowPane grid2_1 = new FlowPane();
		Text title2 = new Text("Add the player who made goal(s):");
		grid2_1.setPadding(new Insets(0, 50, 10, 50));
		grid2_1.setAlignment(Pos.BASELINE_LEFT);
		grid2_1.getChildren().add(title2);

		FlowPane grid2_2 = new FlowPane();
		grid2_2.setPadding(new Insets(0, 50, 10, 50));
		grid2_2.setHgap(10);
		Label label5 = new Label("Player");
		Label label6 = new Label("Number of goals:");
		numOfGoals.setPrefWidth(40);
		grid2_2.getChildren().add(label5);
		grid2_2.getChildren().add(playerNameCmb);
		grid2_2.getChildren().add(label6);
		grid2_2.getChildren().add(numOfGoals);

		FlowPane grid2_3 = new FlowPane();
		grid2_3.setPadding(new Insets(0, 50, 10, 50));
		grid2_3.setHgap(10);
		grid2_3.setAlignment(Pos.BASELINE_RIGHT);
		Button addPlayerGoals = new Button("Add players");
		grid2_3.getChildren().add(addPlayerGoals);

		// Another FlowPane with text controls for viewing a selected game
		FlowPane grid2_4 = new FlowPane();
		grid2_4.setPadding(new Insets(0, 50, 10, 50));
		grid2_4.setAlignment(Pos.BASELINE_LEFT);
		grid2_4.setHgap(10);
		Label label7 = new Label("Choose a game to view: ");
		grid2_4.getChildren().add(label7);
		grid2_4.getChildren().add(gameListCmb);

		FlowPane grid2_5 = new FlowPane();
		grid2_5.setPadding(new Insets(0, 50, 20, 50));
		grid2_5.setAlignment(Pos.BASELINE_RIGHT);
		grid2_5.setHgap(10);
		Button view = new Button("View");
		Button clearBtn = new Button("Clear all");
		grid2_5.getChildren().add(view);
		grid2_5.getChildren().add(clearBtn);
		
		grid1.getChildren().add(updateGrid1);
		grid1.getChildren().add(grid1_1);
		grid1.getChildren().add(grid1_2);
		grid1.getChildren().add(grid1_3);
		grid1.getChildren().add(grid2_1);
		grid1.getChildren().add(grid2_2);
		grid1.getChildren().add(grid2_3);
		grid1.getChildren().add(grid2_4);
		grid1.getChildren().add(grid2_5);
		
		mainPane.setTop(grid1);
		displayArea.setEditable(false);
		mainPane.setCenter(displayArea);

		Scene scene = new Scene(mainPane, 900, 600);
		primaryStage.setTitle("");
		primaryStage.setScene(scene);
		primaryStage.show();

		// Event handlers
		newSeason.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				addNewSeasonToGame(arg0);
			}
		});
		
		addGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addNewGame(e);
			}
		});

		addPlayerGoals.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addPlayerGoals(e);
			}
		});

		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearData();
			}
		});

		newFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				displayArea.setText(null);
			}
		});

		openFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File f1 = fileChooser.showOpenDialog(primaryStage);
				if (f1 != null) {
					openFile(f1);
				}
			}
		});

		saveFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File f1 = fileChooser1.showSaveDialog(primaryStage);
				if (f1 != null) {
					saveDataIntoFile(f1);
				}
			}
		});
		
		allFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File f1 = fileChooser.showSaveDialog(primaryStage);
				if (f1 != null) {
					saveAllGameIntoFile(f1);
				}
			}
		});

		sort.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				sortPopUp(e);
			}
		});

		view.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				viewGame(e);
			}
		});
	}

	public void readSeasonFromFile() throws Exception {
		
	}
	
	private void addNewSeasonToGame(ActionEvent e) {
		
	}
	
	// Read player's name and number from text file (It is playerNumber.txt in this case)
	public void readPlayerFromFile() throws Exception {
		ClassLoader cl = this.getClass().getClassLoader();
		URL linkUrl = cl.getResource(playerBasicFile);
		try (InputStream instr = linkUrl.openStream();
				BufferedReader readChar = new BufferedReader(new InputStreamReader(instr))) {
			String line;
			while ((line = readChar.readLine()) != null) {
				int playerNum = new Integer(readChar.readLine());
				player.put(line, playerNum);
				playerInfo newPlayer = new playerInfo(playerNum, line, 0);
				playerTotalGoals.add(newPlayer);
			}
			List<String> playerNames = player.keySet().stream().sorted().collect(Collectors.toList());
			playerNameCmb.setItems(FXCollections.observableArrayList(playerNames));
		}
	}

	// Create a new game and add that game to ComboBox and an ArrayList
	private void addNewGame(ActionEvent e) {
		int ourPoint, oppPoint;
		String date, opposite;
		try {
			date = dateField.getText();
			opposite = opponent.getText();
			ourPoint = Integer.parseInt(result1.getText());
			oppPoint = Integer.parseInt(result2.getText());
			Date newDate = new Date(date);
			int totalGoals = 0;
			newMatch = new MatchData(newDate, opposite, ourPoint, oppPoint, totalGoals);
			if (!newDate.toString().equals("Unknown")) {
				gameListCmb.getItems().addAll(newDate.toString() + " - " + opposite);
				displayArea.setText(newMatch.toString());
				matchData.add(newMatch);
			}
		} catch (NullPointerException ex1) {
			Alert alert1 = new Alert(AlertType.ERROR, "Please fill all of the inputs!");
			alert1.showAndWait();
		} catch (NumberFormatException ex2) {
			Alert alert2 = new Alert(AlertType.ERROR, "Make sure you type right format of the inputs.");
			alert2.showAndWait();
		}
	}

	// Add player who score goals into newMatch (which is a MatchData object) and
	// also change the value
	// in the ArrayList which stores player's data.
	private void addPlayerGoals(ActionEvent e) {
		// Make sure that total goals per game (totalPerGame variable) must not be
		// greater than our score in that game!
		// That is why I use a counter variable in MatchData class
		if (newMatch.getTotalGoals() <= newMatch.getOurScore()) {
			try {
				String chosenGame = gameListCmb.getValue();
				// This helps us verify which match that I want to change data,
				// otherwise the data will be automatically added to the recent MatchData
				// object.
				if (chosenGame != null) {
					for (MatchData li : matchData) {
						if (chosenGame.equals(li.getMatchDate().toString() + " - " + li.getOpponent())) {
							newMatch = li;
						}
					}
				} 
				String playerScoreGoalName = playerNameCmb.getValue();
				int goals = Integer.parseInt(numOfGoals.getText());
				int playerNo = player.get(playerScoreGoalName);
				// User must input a positive integer for number of goals
				if (goals > 0) {
					playerInfo playerScoreGoal = new playerInfo(playerNo, playerScoreGoalName, goals);
					newMatch.calculateTotalGoals(playerScoreGoal.getGoalsPerGame());
					// Check if user input number of goals greater than the team score or
					// the total of goals inputed is bigger than the team score
					if (playerScoreGoal.getGoalsPerGame() <= newMatch.getOurScore()
							&& newMatch.getTotalGoals() <= newMatch.getOurScore()) {
						newMatch.addPlayerScoreGoal(playerScoreGoal);
						int newTotalPerPerson = 0;
						for (playerInfo li : playerTotalGoals) {
							if (li.getPlayerNum() == playerScoreGoal.getPlayerNum()) {
								newTotalPerPerson += playerScoreGoal.getGoalsPerGame();
								playerTotalGoals.set(playerTotalGoals.indexOf(li),
										new playerInfo(playerNo, playerScoreGoalName, newTotalPerPerson));
							}
						}
					}
				}
				displayArea.setText(newMatch.toString());
			} catch (NumberFormatException ex1) {
				Alert alert3 = new Alert(AlertType.ERROR, "Make sure you type right format of the inputs.");
				alert3.showAndWait();
			}
		}
	}

	// Display a selected game
	private void viewGame(ActionEvent e) {
		try {
			String selectedItem = gameListCmb.getValue();
			for (MatchData li : matchData) {
				if (selectedItem.equals(li.getMatchDate().toString() + " - " + li.getOpponent())) {
					displayArea.setText(null);
					displayArea.setText(li.toString());
				}
			}
		} catch (NullPointerException ex1) {
			Alert alert4 = new Alert(AlertType.ERROR, "Please select a match to view");
			alert4.showAndWait();
		}
	}

	// Save the displayed match into a file
	private void saveDataIntoFile(File file) {
		try (PrintWriter outFile = new PrintWriter(new FileOutputStream(file),true)) {
//			outFile.writeObject(newMatch);
			outFile.print(displayArea.getText());
			Alert alert7 = new Alert(AlertType.INFORMATION, "File " + file + " saved sucessfully!");
			alert7.showAndWait();
		} catch (Exception ex) {
			Alert alert8 = new Alert(AlertType.ERROR, "Error in saving data to file " + file);
			alert8.showAndWait();
			ex.printStackTrace();
		}
	}
	// Save all games in stock into a file
	private void saveAllGameIntoFile(File file) {
		try (ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream(file))) {
			outFile.writeObject(matchData);
			Alert alert7 = new Alert(AlertType.INFORMATION, "File " + file + " saved sucessfully!");
			alert7.showAndWait();
		} catch (Exception ex) {
			Alert alert8 = new Alert(AlertType.ERROR, "Error in saving data to file " + file);
			alert8.showAndWait();
			ex.printStackTrace();
		}
	}

	// Open a file
	@SuppressWarnings("unchecked")
	private void openFile(File file) {
		try (ObjectInputStream inputFile = new ObjectInputStream(new FileInputStream(file))) {
			matchDataFromFile = (ArrayList<MatchData>)inputFile.readObject();
			//Check if the system has contains some/all data of the matchDataFromFile arrayList
			for (MatchData li : matchDataFromFile) {
				if(!matchData.toString().contains(li.toString())) {
					gameListCmb.getItems().add(li.getMatchDate() + " - " + li.getOpponent());
					matchData.add(li);
					for(int i=0;i<li.getPlayerList().size();i++) {
						int playerNo = li.getPlayerList().get(i).getPlayerNum();
						String playerName = li.getPlayerList().get(i).getPlayerName();
						int numOfGoals = li.getPlayerList().get(i).getGoalsPerGame();
						for(int j=0;j<playerTotalGoals.size();j++) {
							if(playerTotalGoals.get(j).getPlayerNum() == playerNo) {
								int oldGoals = playerTotalGoals.get(j).getGoalsPerGame();
								playerTotalGoals.set(j,new playerInfo(playerNo, playerName, numOfGoals + oldGoals));
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			Alert alert6 = new Alert(AlertType.ERROR, "Opening file " + file + " failed!");
			alert6.showAndWait();
			ex.printStackTrace();
		}
	}
	// Sorting each player's total of goals in descending order and display them in
	// a small pop-up
	private void sortPopUp(ActionEvent e) {
		StackPane sortPlayer = new StackPane();
		result.setEditable(false);
		StringBuilder strbuild = new StringBuilder();
		playerTotalGoals.sort(new Comparator<playerInfo>(){
			@Override
			public int compare(playerInfo p1, playerInfo p2) {
				return Integer.compare(p2.getGoalsPerGame(), p1.getGoalsPerGame());
			}		
		});
		for (playerInfo li : playerTotalGoals) {
			strbuild.append(li.toString());
			strbuild.append("\n");
		}
		result.setText(strbuild.toString());
		sortPlayer.getChildren().add(result);

		Scene newWindow2 = new Scene(sortPlayer, 600, 600);
		Stage newStage2 = new Stage();
		newStage2.setTitle("Sort Player data");
		newStage2.setScene(newWindow2);

		newStage2.setX(newWindow2.getX() + 50);
		newStage2.setY(newWindow2.getY() + 50);
		newStage2.show();
	}

	// Clear all data
	private void clearData() {
		displayArea.setText(null);
		gameListCmb.getSelectionModel().clearSelection();
		playerNameCmb.getSelectionModel().clearSelection();
		dateField.setText(null);
		opponent.setText(null);
		result1.setText(null);
		result2.setText(null);
		numOfGoals.setText(null);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
