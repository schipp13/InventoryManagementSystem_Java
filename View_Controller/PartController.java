package View_Controller;

import Models.InHouse;
import Models.Inventory;
import Models.Outsourced;
import Models.Part;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates the part form.
 */
public class PartController implements Initializable {
    @FXML
    private Label FormTitleLabel;
    @FXML
    private Label CategoryLabel;
    @FXML
    private Button Save;
    @FXML
    private Button Cancel;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField stock;
    @FXML
    private TextField price;
    @FXML
    private TextField min;
    @FXML
    private TextField max;
    @FXML
    private TextField CategoryValue;
    @FXML
    private RadioButton InHouseOption;
    @FXML
    private RadioButton OutsourcedOption;
    @FXML ToggleGroup CategoryToggleGroup;

    Part oldPart;
    int selectedIndex;

    /**
     * Initializes the controller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    /**
     * Statically initializes the Controller
     */
    public void init()
    {
        // Sets the Product ID textfields text
        String sizeOfPartsList =  String.valueOf(Inventory.getAllParts().size() + 1);
        this.id.setText(sizeOfPartsList);
    }

    /**
     * Changes the Forms Title
     * @param formTitleLabel
     */
    public void setFormTitleLabel(String formTitleLabel) {
        this.FormTitleLabel.setText(formTitleLabel);
    }

    /**
     * Sets the textfields to the selected parts data.
     * @param selectedPart
     * @param Index
     */
    public void setSelectedPart(Part selectedPart, int Index) {
        this.oldPart = selectedPart;
        selectedIndex = Index;

        // Sets the TextField text from a selected part
        if(oldPart instanceof InHouse)
        {
            System.out.println("Passing items to textfields");
            InHouse updatedPart = (InHouse) oldPart;
            InHouseOption.setSelected(true);
            CategoryLabel.setText("Machine ID");
            this.name.setText(updatedPart.getName());
            this.id.setText(Integer.toString(updatedPart.getId()));
            this.stock.setText(Integer.toString(updatedPart.getStock()));
            this.price.setText(Double.toString(updatedPart.getPrice()));
            this.min.setText(Integer.toString(updatedPart.getMin()));
            this.max.setText(Integer.toString(updatedPart.getMax()));
            this.CategoryValue.setText(Integer.toString(updatedPart.getMachineId()));
        }
        else if(oldPart instanceof Outsourced)
        {
            System.out.println("Passing items to textfields");

            Outsourced updatedPart = (Outsourced) oldPart;
            OutsourcedOption.setSelected(true);
            CategoryLabel.setText("Company Name");
            this.name.setText(updatedPart.getName());
            this.id.setText(Integer.toString(updatedPart.getId()));
            this.stock.setText(Integer.toString(updatedPart.getStock()));
            this.price.setText(Double.toString(updatedPart.getPrice()));
            this.min.setText(Integer.toString(updatedPart.getMin()));
            this.max.setText(Integer.toString(updatedPart.getMax()));
            this.CategoryValue.setText(updatedPart.getCompanyName());
        }
    }

    /**
     * Changes the Text based on radiobutton selection
     */
    public void setCategoryLabel()
    {
        if(InHouseOption.isSelected())
        {
            this.CategoryLabel.setText("Machine ID");
        }
        else
        {
            this.CategoryLabel.setText("Company Name");
        }
    }

    /**
     * Adds a new part or modified part into the ObservableArrayList in the Inventory class.
     *
     * @param event
     * @throws Exception
     */
    public void Save(ActionEvent event) throws Exception
    {
        boolean noError = true;

            //Creates a new instance of a part
            if (FormTitleLabel.getText().equals("Add Part")) {
                if (this.CategoryToggleGroup.getSelectedToggle().equals(this.InHouseOption)) {
                    InHouse inhouse = new InHouse(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), Integer.parseInt(CategoryValue.getText()));
                    noError = checkValues();
                    if(noError == true) {
                        Inventory.addPart(inhouse);
                    }

                } else {
                    Outsourced outsourced = new Outsourced(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), CategoryValue.getText());
                    noError = checkValues();
                    if(noError == true) {
                        Inventory.addPart(outsourced);
                    }
                }
            }
            // Makes changes to a selected part
            else {
                if (this.CategoryToggleGroup.getSelectedToggle().equals(this.InHouseOption)) {
                    InHouse updatedPart = new InHouse(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), Integer.parseInt(CategoryValue.getText()));
                    noError = checkValues();
                    if(noError == true) {
                        Inventory.updatePart(selectedIndex, updatedPart);
                    }
                } else {
                    Outsourced updatedPart = new Outsourced(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), CategoryValue.getText());
                    noError = checkValues();
                    if(noError == true) {
                        Inventory.updatePart(selectedIndex, updatedPart);
                    }
                }
            }

        if(noError == true) {

            // Get FXML for the partForm
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
            Scene partFormScene = new Scene(root);

            // Set scene
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(partFormScene);

            // show scene
            window.show();
        }
    }

    /**
     * Checks the Min, Max, and Inventory Values
     */
    private boolean checkValues()
    {
        boolean issueNotFound = true;

        // Compares the min, max, and stock textfields.
        // If error is thrown get alert and set noError to false
        if (Integer.parseInt(max.getText()) < Integer.parseInt(min.getText())) {
            getAlert(1);
            issueNotFound = false;
        } else if (Integer.parseInt(max.getText()) < Integer.parseInt(stock.getText()) || Integer.parseInt(min.getText()) > Integer.parseInt(stock.getText())) {
            getAlert(2);
            issueNotFound = false;
        }
    return issueNotFound;
    }

    /**
     * Displays a message based on what issue has occurred.
     * @param alertTypeNum
     */
    private void getAlert(int alertTypeNum) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        switch (alertTypeNum) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("The minimum value is greater than the max value.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("The Inventory value needs to be equal to or between the Max and Min values.");
                alert.showAndWait();

                break;
        }
    }

    /**
     * Does not make and changes and goes back to the main form.
     * @param event
     * @throws Exception
     */
    public void Cancel(ActionEvent event) throws  Exception{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to go back to the main form? ");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            // Get FXML for the partForm
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
            Scene partFormScene = new Scene(root);
            // Set scene
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(partFormScene);

            // show scene
            window.show();
        }
    }
}
