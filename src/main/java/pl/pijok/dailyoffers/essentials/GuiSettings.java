package pl.pijok.dailyoffers.essentials;

import java.util.ArrayList;
import java.util.List;

public class GuiSettings {

    private String title;
    private int rows;
    private List<Integer> slots;

    public GuiSettings(){
        this.slots = new ArrayList<>();
    }

    public GuiSettings(String title, int rows, List<Integer> slots){
        this.title = title;
        this.rows = rows;
        this.slots = slots;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }
}
