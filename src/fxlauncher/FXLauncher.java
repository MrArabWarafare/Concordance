package fxlauncher;

import java.util.Collection;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import spellChecker.SpellChecker;

public class FXLauncher extends Application {
	
	public static final double SCENE_WIDTH = 900.0, SCENE_HEIGHT = 550.0, SCROLL_PANE_WIDTH = 1000.0;

	private SpellChecker spellcheck;
	private VBox misspelledVBox, concordanceVBox;
	private SplitPane splitPane;
	private WebView resultsWebView;
	private HBox hboxContents;
	private Scene scene;
	private VBox sceneGraphRoot;

	@Override
	public void start(Stage primaryStage) throws Exception {
		spellcheck = new SpellChecker();
		spellcheck.loadMisspelledWords();
		spellcheck.loadConcordance();
		resultsWebView = new WebView();
		splitPane = new SplitPane();
		splitPane.getItems().addAll(new ScrollPane(buildMisspelledVBox()), new ScrollPane(buildConcordanceVBox()), resultsWebView);
		
		hboxContents = new HBox(splitPane);
		sceneGraphRoot = new VBox();
		sceneGraphRoot.getChildren().addAll(buildMenuBar(primaryStage), hboxContents);
		primaryStage.setTitle("Concordance v0.6");
		scene = new Scene(sceneGraphRoot, SCENE_WIDTH, SCENE_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}// end start()

	private VBox buildConcordanceVBox() {
		concordanceVBox = new VBox();
		for (String word : spellcheck.getConcordanceMap().keySet()) { //for every word of the concordance keyset
			final Text text = new Text(word);
			text.setFont(new Font("Arial", Math.sqrt(spellcheck.getConcordanceMap().get(word).size() + 190.0))); //create a new text for the word
			concordanceVBox.getChildren().add(text);//add the word to the vbox
            // String sRegExSearch = "\\b(?i:" + word + ")\\b";
			String sRegExSearch = "\\W(?i:" + word + ")\\W";
			text.setOnMouseEntered(hoverEvent -> {
				Collection<String> paragraphs = spellcheck.getConcordanceMap().get(text.getText()); //create ref to the value of the key 
				final String HTMLBoundingBlueBoxCSSstyle = "<style>.highlight{color:red;}.border{border-style:solid;border-color:#287EC7;}</style>"; //create css string for blue box
				StringBuilder stringBuilder = new StringBuilder(HTMLBoundingBlueBoxCSSstyle);
				for (String paragraph : paragraphs) {
					String highlightedString = paragraph.toString().replaceAll(sRegExSearch, "<span class=\"border\">$0</span>");
					stringBuilder.append(highlightedString).append("<br><br>");
					resultsWebView.getEngine().loadContent(stringBuilder.toString());
				}
			}); //end setOnMouseEntered()
		}
		return concordanceVBox;
	}

	private VBox buildMisspelledVBox() {
		misspelledVBox = new VBox();
		for (String word : spellcheck.getMisspelledMap().keySet()) { //for every word in the Misspelled map
			final Text text = new Text(word);
			text.setFont(new Font("Arial", Math.sqrt(spellcheck.getConcordanceMap().get(word).size() + 190.0)));//set font and font size, final: copy of the value is stuffed inside the method for later use.
			misspelledVBox.getChildren().add(text);
			String sRegExSearch = "\\W(?i:" + word + ")\\W";
			text.setOnMouseEntered(hoverEvent -> {
				Collection<String> paragraphs = spellcheck.getMisspelledMap().get(text.getText()); //create ref to the value of the word 
				final String HTMLBoundingBlueBoxCSSstyle = "<style>.highlight{color:red;}.border{border-style:solid;border-color:#287EC7;}</style>";
				StringBuilder stringBuilder = new StringBuilder(HTMLBoundingBlueBoxCSSstyle);
				for (String paragraph : paragraphs) { //for each paragraph in the LinkedHashSet
					String highlightedString = paragraph.toString().replaceAll(sRegExSearch, "<span class=\"border\">$0</span>");
					stringBuilder.append(highlightedString).append("<br><br>");
					resultsWebView.getEngine().loadContent(stringBuilder.toString());
				}
			}); //end setOnMouseEntered()
		}
		return misspelledVBox;
	}

	/** returns the menu bar for the scene */
	private MenuBar buildMenuBar(Stage primaryStage) {
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		Menu fileMenu = buildFileMenu(primaryStage, menuBar);
		menuBar.getMenus().addAll(fileMenu);
		return menuBar;
	}// end createMenu()

	/** builds the File menu for the menu bar */
	private Menu buildFileMenu(Stage primaryStage, MenuBar menuBar) {
		Menu fileMenu = new Menu("File");
		/** build exitItem for menu */
		MenuItem exitItem = new MenuItem("Exit", null);
		exitItem.setMnemonicParsing(true);
		exitItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
		exitItem.setOnAction(event -> Platform.exit());
		fileMenu.getItems().addAll(exitItem);
		return fileMenu;
	} //end buildFileMenu()

	public static void main(String[] args) {
		launch(args);
	}
}// end FXLauncher class
