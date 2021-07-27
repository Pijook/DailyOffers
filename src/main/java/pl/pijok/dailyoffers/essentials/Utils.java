package pl.pijok.dailyoffers.essentials;

public class Utils {

    public static boolean isDouble(String a){
        try{
            Double.parseDouble(a);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isInteger(String a){
        try{
            Integer.parseInt(a);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}
