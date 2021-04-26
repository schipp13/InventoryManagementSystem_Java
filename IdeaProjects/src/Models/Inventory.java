package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);

    }

    /**
     *
     * @param partId to be searched
     * @return part if found
     */
    public static Part lookupPart(int partId)
    {
        for(int i = 0; i < allParts.size(); i++)
        {
            Part part = allParts.get(i);
            if(part.getId() == partId)
            {
                return part;
            }
        }
        return null;
    }

    /**
     *
     * @param productId to be searched
     * @return product if found
     */
    public static Product lookupProduct(int productId)
    {

        for(int i = 0; i < allProducts.size(); i++)
        {
            Product product = allProducts.get(i);
            if(product.getId() == productId)
            {
                return product;
            }
        }
        return null;
    }

    /**
     *
     * @param partName to be searched
     * @return searchedPartName
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchPartName = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                searchPartName.add(part);
            }
        }

        return searchPartName;
    }

    /**
     *
     * @param productName to be searched
     * @return searchProductName
     */
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> searchProductName = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                searchProductName.add(product);
            }
        }

        return searchProductName;
    }

    /**
     *
     * @param index to modify selectedPart
     * @param selectedPart to be modified
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }

    /**
     *
     * @param index to modify selectedProduct
     * @param selectedProduct to be modified
     */
    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.remove(index);
        allProducts.add(index, selectedProduct);
    }

    /**
     *
     * @param selectedPart to remove from allParts
     * @return true
     */
    public static boolean deletePart(Part selectedPart)
    {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     *
     * @param selectedProduct to remove from allProducts
     * @return true
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
