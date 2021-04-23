package sample;

import com.sun.javafx.image.IntPixelGetter;
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
import java.util.ResourceBundle;

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
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    init();
    }

    /**
     *
     */
    public void init()
    {
        // Sets the Product ID textfields text
        String sizeOfPartsList =  String.valueOf(Inventory.getAllParts().size() + 1);
        this.id.setText(sizeOfPartsList);
    }

    /**
     * 4
     * @param formTitleLabel
     */
    public void setFormTitleLabel(String formTitleLabel) {
        this.FormTitleLabel.setText(formTitleLabel);
    }

    /**
     *
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
     *
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
     *
     * @param event
     * @throws Exception
     */
    public void Save(ActionEvent event) throws Exception
    {
        //Creates a new instance of a part
        if(FormTitleLabel.getText().equals("Add Part"))
        {
            if(this.CategoryToggleGroup.getSelectedToggle().equals(this.InHouseOption))
            {
                InHouse inhouse = new InHouse(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), Integer.parseInt(CategoryValue.getText()));
                Inventory.addPart(inhouse);
            }
            else
            {
                Outsourced outsourced = new Outsourced(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), CategoryValue.getText());
                Inventory.addPart(outsourced);
            }

        }
        // Makes changes to a selected part
        else
        {
            if(this.CategoryToggleGroup.getSelectedToggle().equals(this.InHouseOption))
            {
                InHouse updatedPart = new InHouse(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), Integer.parseInt(CategoryValue.getText()));
                Inventory.updatePart(selectedIndex, updatedPart);
                System.out.println("Creating new InHouse");
            }
            else
            {
                Outsourced updatedPart = new Outsourced(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), CategoryValue.getText());
                Inventory.updatePart(selectedIndex, updatedPart);
                System.out.println("Creating new Outsourced");
            }
        }


        // Compares the min, max, and stock textfields.
        if(Integer.parseInt(max.getText()) < Integer.parseInt(min.getText()))
        {
            JOptionPane.showMessageDialog(null, "The minimum value is greater than the max value.");
        }
        else if(Integer.parseInt(max.getText()) < Integer.parseInt(stock.getText()) || Integer.parseInt(min.getText()) > Integer.parseInt(stock.getText()))
        {
            JOptionPane.showMessageDialog(null, "The Inventory value exceeds the max or the minimum value.");
        }


        // Get FXML for the partForm
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene partFormScene = new Scene(root);
        // Set scene
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(partFormScene);

        // show scene
        window.show();
    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void Cancel(ActionEvent event) throws  Exception{
        // Get FXML for the partForm
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene partFormScene = new Scene(root);
        // Set scene
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(partFormScene);

        // show scene
        window.show();
    }


}
