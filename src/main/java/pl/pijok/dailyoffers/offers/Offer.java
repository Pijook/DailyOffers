package pl.pijok.dailyoffers.offers;

import org.bukkit.inventory.ItemStack;

public class Offer {

    private String ID;
    private ItemStack itemStack;
    private double cost;

    public Offer(String ID, ItemStack itemStack, double cost){
        this.ID = ID;
        this.itemStack = itemStack;
        this.cost = cost;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getID() {
        return ID;
    }
}
