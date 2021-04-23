package sample;

import javafx.application.Platform;
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


public class MainController implements Initializable {

    @FXML private Button addPart;

    @FXML private Button modifyPart;

    @FXML private Button deletePart;

    @FXML private Button addProduct;

    @FXML private Button modifyProduct;

    @FXML private Button deleteProduct;

    @FXML private Button exit;

    @FXML private TextField lookupPart;

    @FXML private TextField lookupProduct;

    @FXML private TableView<Part> PartTableView;

    @FXML private TableColumn<Part, Integer> partIdColumn;

    @FXML private TableColumn<Part, String> partNameColumn;

    @FXML private TableColumn<Part, Integer> partStockColumn;

    @FXML private TableColumn<Part, Double> partPriceColumn;

    @FXML private TableView<Product> ProductTableView;

    @FXML private TableColumn<Product, Integer> productIdColumn;

    @FXML private TableColumn<Product, String> productNameColumn;

    @FXML private TableColumn<Product, Integer> productStockColumn;

    @FXML private TableColumn<Product, Double> productPriceColumn;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Sets up partTable columns
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        PartTableView.setItems(Inventory.getAllParts());
        ProductTableView.setItems(Inventory.getAllProducts());
    }

    /**
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
            }
            catch(NumberFormatException e)
            {
                // ignore
            }
        }

        PartTableView.setItems(parts);
        lookupPart.setText("");
    }

    /**
     *
     */
    public void searchForProduct() {
        String str = lookupProduct.getText();

        ObservableList<Product> products = Inventory.lookupProduct(str);

        if(products.size() == 0)
        {
            try {
                int id = Integer.parseInt(str);

                Product product = Inventory.lookupProduct(id);
                if (product != null) {
                    products.add(product);
                }
            }
            catch(NumberFormatException e)
            {
                // ignore
            }
        }

        ProductTableView.setItems(products);
        lookupProduct.setText("");
    }

    /**
     *
     * @param event
     */
    public void addPartListener(ActionEvent event) throws Exception{

            // Get FXML for the Product form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PartForm.fxml"));
            Parent root = loader.load();

            PartController controller = loader.getController();
            controller.setFormTitleLabel("Add Part");

            Scene partFormScene = new Scene(root);

            // Set scene
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(partFormScene);

            window.show();
    }

    /**
     *
     * @param event
     */
    public void addProductListener(ActionEvent event) throws Exception
    {

            // Get FXML for the Product form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductForm.fxml"));
            Parent root = loader.load();

            ProductController controller = loader.getController();
            controller.setFormTitleLabel("Add Product");
            Scene productFormScene = new Scene(root);

            // Set scene
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(productFormScene);
            window.show();

    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void modifyPartListener(ActionEvent event) throws Exception
    {
        Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();
        int index = PartTableView.getSelectionModel().getSelectedIndex();

        if(selectedPart == null) {
            getAlert(1);
        }
        else
        {// Get FXML for the Product form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PartForm.fxml"));
            Parent root = loader.load();

            PartController controller = loader.getController();
            controller.setFormTitleLabel("Modify Part");

            controller.setSelectedPart(selectedPart, index);

            Scene partFormScene = new Scene(root);

            // Set scene
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(partFormScene);

            window.show();
        }
    }

    /**
     *
     * @param event
     */
    public void modifyProductListener(ActionEvent event) throws Exception
    {

        Product selectedProduct = ProductTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            getAlert(1);
        }
        else
        {

            // Get FXML for the Product form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductForm.fxml"));
            Parent root = loader.load();

            ProductController controller = loader.getController();
            controller.setFormTitleLabel("Modify Product");

            controller.setSelectedProduct(selectedProduct);

            Scene productFormScene = new Scene(root);

            // Set scene
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(productFormScene);

            window.show();
        }

    }

    /**
     *
     */
    public void deletePartListener(ActionEvent event)
    {
        Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null)
        {
            getAlert(1);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete " + selectedPart + " ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                Inventory.deletePart(selectedPart);
            }
        }

    }

    /**
     *
     */
    public void deleteProductListener()
    {
        Product selectedProduct = ProductTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null)
        {
            getAlert(1);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete " + selectedProduct + " ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                ObservableList<Part> assocaitedParts = selectedProduct.getAllAssociatedParts();

                if(assocaitedParts.size() >= 1){
                    getAlert(3);
                }
                else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }

    }

    /**
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
                alert.setHeaderText("Please select something first");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Item was not found");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Associated Parts");
                alert.setContentText("All associated parts must be removed before deletion.");
                alert.showAndWait();
                break;

        }
    }

    /**
    *
    */
    public void exitListener(ActionEvent event)
    {
       Platform.exit();
    }

}
