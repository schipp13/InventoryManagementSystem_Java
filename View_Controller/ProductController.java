package View_Controller;

import Models.Inventory;
import Models.Part;
import Models.Product;
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
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 */
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

    @FXML private TextField lookupPart;

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
     * Initializes the controller and sets the top TableView to display all parts that have been added to the application.
     * If the product is being modified it will also display the selected product's associated parts in the bottom TableView.
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
     * Sets the TextFields to display the selected Product's data.
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
     * Changes the form's title label
     * @param text
     */
    public void setFormTitleLabel(String text)
    {
        this.FormTitleLabel.setText(text);
    }

    /**
     * Adds a copy of the selected Part from the Top TableView to the Bottom TableView.
     * If nothing is selected display a message.
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
     * Removes the selected associated part from the bottom of the list.
     * If nothing is selected than display a message.
     * Get conformation when removing an associated part(s).
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
     * Searchs for a Part ID or Name.
     * Displays the found ID or Name in the TableView
     * If nothing is typed into the search TextField it will refresh the TableView to show all Parts.
     *
     */
    public void searchForPart() {
        String str = lookupPart.getText();

        ObservableList<Part> parts = Inventory.lookupPart(str);

        if(parts.size() == 0)
        {
            try {
                int id = Integer.parseInt(str);

                Part part = Inventory.lookupPart(id);
                if (part != null) {
                    parts.add(part);
                }
                else{
                    getAlert(5);
                }
            }
            catch(NumberFormatException e)
            {
                // ignore
            }
        }

        allPartsView.setItems(parts);
        lookupPart.setText("");
    }

    /**
     * adds the new product/modified product to the ObservableArrayList in the Inventory Class.
     * If the Name TextField or if the Min Value is greater than the Inventory or Max Value display a message.
     * If the Max Value is less than the Inventory Values display a message.
     * @param event
     * @throws Exception
     */
    public void Save(ActionEvent event) throws Exception
    {
        boolean noError = true;

            if (this.FormTitleLabel.getText().equals("Add Product")) {
                if(name.getText().isEmpty())
                {
                    getAlert(4);
                }
                else {
                    newProduct = new Product(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));
                    noError = checkValues();
                    if(noError == true) {
                        for (Part associatedPart : associatedParts) {
                            newProduct.addAssociatedPart(associatedPart);
                        }
                        Inventory.addProduct(newProduct);
                    }
                }
            }
            else
            {
                newProduct = new Product(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));
                noError = checkValues();
                if(noError == true) {
                    Inventory.updateProduct(selectedIndex, newProduct);
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
     * Goes back to the main form without doing anything
     * @param event
     * @throws Exception
     */
    public void Cancel(ActionEvent event) throws Exception
    {
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

    /**
     * Displays a message based on what issue has occurred.
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
