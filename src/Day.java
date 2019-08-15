public class Day {
    private static Day date;
    
    public Day (Day date){
        this.date = date;
    }
    
    public static Day getDate(){
        return date;
    }
    
    public void setDate(Day date){
        this.date = date;
    }
}