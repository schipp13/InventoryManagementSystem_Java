package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id, stock, min, max;
    private String name;
    private double price;

    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     *
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     *
     * @param price the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     *
     * @param stock the stock to set
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     *
     * @param min the min to set
     */
    public void setMin(int min)
    {
        this.min = min;
    }
    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param part to be added to the associatedParts array.
     */
    public void addAssociatedPart(Part part)
    {

        associatedParts.add(part);
    }

    /**
     *
     * @param selectedAssociatedPart to be deleted
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     *
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
}
