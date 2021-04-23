/**
 * File: csci1302/ch16/PAssign10.java
 * Package: ch16
 * @author Ivan Acha
 * Created on: Apr 12, 2017
 * Last Modified: Apr 21, 2020
 * Description:  
 */
package progAssignment;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PAssign10 extends Application {
	// default values/strings
    private double txtWidth = 125.0;
    private String defaultCalc = String.format("%.2f", 0.00);
    private String defaultEntry = String.format("%.2f", 0.00);
    private String defaultMileage = "Miles";
    private String defaultCapacity = "Gallons";
    private String defaultResult = "MPG";
    private String altMileage = "Kilometers";
    private String altCapacity = "Liters";
    private String altResult = "L/100KM";
    private String customStyle = "-fx-text-fill: linear-gradient(lime, white); -fx-font-weight: bold;";
    private String bgURL = "url(http://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/52cf024e-4cc2-4480-aa9b-192e14e46400/d9gft23-fea5307b-945b-49f2-9212-5b5d0283b27a.gif?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvNTJjZjAyNGUtNGNjMi00NDgwLWFhOWItMTkyZTE0ZTQ2NDAwXC9kOWdmdDIzLWZlYTUzMDdiLTk0NWItNDlmMi05MjEyLTViNWQwMjgzYjI3YS5naWYifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.gTC5cL7yLSAsim01TzDDxttpn1rClgHIQoEJ16C0DOw)";

    
    // create UI components split by type
    private Button btnCalc = new Button("Calculate");
    private Button btnReset = new Button("Reset");
    
    private Label lblDistance = new Label(defaultMileage);
    private Label lblCapacity = new Label(defaultCapacity);
    private Label lblResult = new Label(defaultResult);
    private Label lblEffType = new Label("Efficiency Type");
    
    private TextField tfDistance = new TextField(defaultEntry);
    private TextField tfCapacity = new TextField(defaultEntry);
    private TextField tfResult = new TextField(defaultCalc);
    
    //The ComboBox that replaces the RadioButtons
    private ComboBox<String> cbBox = new ComboBox<>();
	private ObservableList<String> items = FXCollections.observableArrayList(new String[] {defaultResult, altResult}); 

    
    private GridPane mainPane = new GridPane();
    
    public void start(Stage primaryStage) {   	
    	// set list of Strings for the ComboBox
    	cbBox.getItems().addAll(items);
    	
        // set preferences for TextFields and ComboBox
        tfDistance.setMaxWidth(txtWidth);
        tfCapacity.setMaxWidth(txtWidth);
        tfResult.setMaxWidth(txtWidth);
        tfResult.setEditable(false);
    	cbBox.setValue(items.get(0));
    	
    	// set preferences for UI
    	lblDistance.setStyle(customStyle);
    	lblCapacity.setStyle(customStyle);
    	lblResult.setStyle(customStyle);
    	lblEffType.setStyle(customStyle);
        
        // create a main grid pane to hold items
        mainPane.setPadding(new Insets(10.0));
        mainPane.setHgap(txtWidth/2.0);
        mainPane.setVgap(txtWidth/12.0);
        mainPane.setStyle("-fx-background-image: " + bgURL + ";");
        
        // add items to mainPane
        mainPane.add(lblEffType, 0, 0);
        mainPane.add(cbBox, 1, 0);
        mainPane.add(lblDistance, 0, 1);
        mainPane.add(tfDistance, 1, 1);
        mainPane.add(lblCapacity, 0, 2);
        mainPane.add(tfCapacity, 1, 2);
        mainPane.add(lblResult, 0, 3);
        mainPane.add(tfResult, 1, 3);
        mainPane.add(btnReset, 0, 4);
        mainPane.add(btnCalc, 1, 4);
        
        // register action handlers
        btnCalc.setOnAction(e -> calcMileage());
        tfDistance.setOnAction(e -> calcMileage());
        tfCapacity.setOnAction(e -> calcMileage());
        tfResult.setOnAction(e -> calcMileage());
        cbBox.setOnAction(e -> changeLabels());
        btnReset.setOnAction(e -> resetForm());
        
        // create a scene and place it in the stage
        Scene scene = new Scene(mainPane, 300, 200); 
        
        // set and show stage
        primaryStage.setTitle("Mileage Calculator"); 
        primaryStage.setScene(scene); 
        primaryStage.show();      
        primaryStage.setResizable(false);
        
        // stick default focus in first field for usability
        tfDistance.requestFocus();
    }
    
    /**
     * Convert existing figures and recalculate
     * This needs to be separate to avoid converting when
     * the conversion is not necessary
     */
    private void changeLabels() {
    	// distinguish between L/100KM and MPG
    	if (lblCapacity.getText().equals(defaultCapacity)) {
    		// update labels
    		lblCapacity.setText(altCapacity);
    		lblDistance.setText(altMileage);
    		lblResult.setText(altResult);
    		switchFormat();
         } else {
        	 // update labels
        	 lblCapacity.setText(defaultCapacity);
        	 lblDistance.setText(defaultMileage);
        	 lblResult.setText(defaultResult);
     		switchFormat();
        }
    	
    }
    
    /*
     * Convert existing figures to the desired Mileage calculation format
     */
    private void switchFormat() {
    	// set default values
        double distance = 0.0, capacity = 0.0;
        
        // make sure to get numeric values only
        if (tfCapacity.getText() != null && !tfCapacity.getText().isEmpty()
        		&& tfDistance.getText() != null && !tfDistance.getText().isEmpty()) {
        	distance = Double.parseDouble(tfDistance.getText());
            capacity = Double.parseDouble(tfCapacity.getText());
        }

    	// distinguish between L/100KM and MPG
    	if (lblCapacity.getText().equals(defaultCapacity)) {
    		// L/100KM to MPG
    		distance = (distance != 0) ? distance / 1.61 : 0;
    		capacity = (capacity != 0) ? capacity / 3.79 : 0;
        } else {
        	// MPG to L/100KM
        	distance *= 1.61;
        	capacity *= 3.79;
        }
    	
    	// update calculation fields with currency formatting
    	tfDistance.setText(String.format("%.2f", distance));
    	tfCapacity.setText(String.format("%.2f", capacity));
        calcMileage();
    }
    
    /**
     * Calculate expenses based on entered figures
     */
    private void calcMileage() {       
    	// set default values
        double distance = 0.0, capacity = 0.0;
        
        // make sure to get numeric values only
        if (tfCapacity.getText() != null && !tfCapacity.getText().isEmpty()
        		&& tfDistance.getText() != null && !tfDistance.getText().isEmpty()) {
        	distance = Double.parseDouble(tfDistance.getText());
            capacity = Double.parseDouble(tfCapacity.getText());
        }

        // check for type of calculation
        double result = 0.0;
        if (lblCapacity.getText().equals(defaultCapacity)) {
        	// MPG 
        	result = (capacity != 0) ? distance/capacity : 0; 

        } else {
        	// liters / 100KM
        	result = (distance != 0) ? capacity/(distance/100.0) : 0;
        }
    
	    // update calculation fields with currency formatting
        tfResult.setText(String.format("%.2f", result));
    }
    
    /**
     * Reset all values in the application
     */
    private void resetForm() {
        // reset all form fields
    	cbBox.setValue(items.get(0));
        tfDistance.setText(defaultEntry);
        tfCapacity.setText(defaultEntry);
        tfResult.setText(defaultCalc);
        lblCapacity.setText(defaultCapacity);
    	lblDistance.setText(defaultMileage);
    	lblResult.setText(defaultResult);
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}

}

