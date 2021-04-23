package sample;


public class InHouse extends Part{

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineID(machineId);
    }
    public void setMachineID(int machineId)
    {
        this.machineId = machineId;
    }
    public int getMachineId()
    {
        return machineId;
    }
}
