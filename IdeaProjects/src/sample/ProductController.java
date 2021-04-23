package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    int selectedIndex;

    @FXML private Button Save;

    @FXML private Button Cancel;

    @FXML private TextField id;

    @FXML private TextField name;

    @FXML private TextField stock;

    @FXML private TextField price;

    @FXML private TextField min;

    @FXML private TextField max;

    @FXML private TableView<Part>allPartsView;

    @FXML private TableColumn<Part, Integer> partIdColumn;

    @FXML private TableColumn<Part, String> partNameColumn;

    @FXML private TableColumn<Part, Integer> partStockColumn;

    @FXML private TableColumn<Part, Double> partPriceColumn;

    @FXML private TableView<Part> associatedPartsView;

    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;

    @FXML private TableColumn<Part, String> associatedPartNameColumn;

    @FXML private TableColumn<Part, Integer> associatedPartStockColumn;

    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;

    @FXML private Label FormTitleLabel;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // Used to create a new product
    Product newProduct;
    Part associatedPart;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Sets up partTable columns
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        // Sets the table views with allparts
        allPartsView.setItems(Inventory.getAllParts());

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        associatedPartsView.setItems(associatedParts);

        // Sets the Product ID textfields text

        String sizeOfProductsList= String.valueOf(Inventory.getAllProducts().size() + 1);
        this.id.setText(sizeOfProductsList);

    }

    /**
     *
     * @param selectedProduct
     */
    public void setSelectedProduct(Product selectedProduct) {

        associatedParts = selectedProduct.getAllAssociatedParts();

        this.name.setText(selectedProduct.getName());
        this.id.setText(Integer.toString(selectedProduct.getId()));
        this.stock.setText(Integer.toString(selectedProduct.getStock()));
        this.price.setText(Double.toString(selectedProduct.getPrice()));
        this.min.setText(Integer.toString(selectedProduct.getMin()));
        this.max.setText(Integer.toString(selectedProduct.getMax()));

        // This is what needs to be changed to get the correct associatedParts to appear
        associatedPartsView.setItems(associatedParts);

    }


    /**
     *
     * @param text
     */
    public void setFormTitleLabel(String text)
    {
        this.FormTitleLabel.setText(text);
    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void AddAssociatedPart(ActionEvent event)throws Exception
    {
        // get selected part from allPartsView
        Part associatedPart = allPartsView.getSelectionModel().getSelectedItem();

        if(associatedPart == null)
        {
            getAlert(1);
        }
        else {
            // add associatedPart to associatedParts Array
            associatedParts.add(associatedPart);

            // add a way to display the recently added part to the associatedparts tableview.
            associatedPartsView.setItems(associatedParts);
        }
    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void RemoveAssociatedPart(ActionEvent event)throws Exception
    {
        Part selectedPart = associatedPartsView.getSelectionModel().getSelectedItem();

        if(selectedPart == null)
        {
            getAlert(1);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete this? ");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartsView.setItems(associatedParts);
            }
        }
    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void Save(ActionEvent event) throws Exception
    {
        try {
            if (this.FormTitleLabel.getText().equals("Add Product")) {
                if(name.getText().isEmpty())
                {
                    getAlert(4);
                }
                else {
                    newProduct = new Product(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));

                    // Compares the min, max, and stock textfields.
                    if (Integer.parseInt(max.getText()) < Integer.parseInt(min.getText())) {
                        JOptionPane.showMessageDialog(null, "The minimum value is greater than the max value.");
                    } else if (Integer.parseInt(max.getText()) < Integer.parseInt(stock.getText()) || Integer.parseInt(min.getText()) > Integer.parseInt(stock.getText())) {
                        JOptionPane.showMessageDialog(null, "The Inventory value exceeds the max or the minimum value.");
                    }

                    for (Part associatedPart : associatedParts) {
                        newProduct.addAssociatedPart(associatedPart);
                    }

                    Inventory.addProduct(newProduct);
                }
            }

            else
            {
                newProduct = new Product(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));

                Inventory.updateProduct(selectedIndex, newProduct);
            }
            // Get FXML for the partForm
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene partFormScene = new Scene(root);
            // Set scene
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(partFormScene);

            // show scene
            window.show();
        }
        catch (Exception e)
        {
            getAlert(4);
        }
    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void Cancel(ActionEvent event) throws Exception
    {
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
     *
     * @param alertTypeNum
     */
    private void getAlert(int alertTypeNum)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertTypeNum)
        {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Please select something first.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("The minimum value is greater than the max value.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("The Inventory value needs to be equal to or between the Max and Min values.");
                alert.showAndWait();

                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Error adding product");
                alert.setContentText("Form contains blank fields.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Information");
                alert.setHeaderText("Item was not found.");
                alert.showAndWait();
                break;

        }
    }
}
