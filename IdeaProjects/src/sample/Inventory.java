package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);

    }

    /**
     *
     * @param partId
     * @return
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
     * @param productId
     * @return
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
     * @param partName
     * @return
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
     * @param productName
     * @return
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
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }

    /**
     *
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.remove(index);
        allProducts.add(index, selectedProduct);
    }

    /**
     *
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart)
    {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     *
     * @param selectedProduct
     * @return
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
