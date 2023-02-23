import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String position;
    private double playerSalary;
    private double ppg;
    private double rpg;
    private double apg;
    private double bpg;
    private double spg;
    private double fgPercent;
    private double threePointersMade;
    private double tpg;

    public Player(String name, String position, double playerSalary, double ppg, double rpg, double apg, double bpg, double spg, double fgPercent, double threePointersMade, double tpg){
        this.name = name;
        this.position = position;
        this.playerSalary = playerSalary;
        this.ppg = ppg;
        this.rpg = rpg;
        this.apg = apg;
        this.bpg = bpg;
        this.spg = spg;
        this.fgPercent = fgPercent;
        this.threePointersMade = threePointersMade;
        this.tpg = tpg;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPosition(){
        return this.position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public double getPlayerSalary(){
        return this.playerSalary;
    }

    public void setPlayerSalary(double playerSalary){
        this.playerSalary= playerSalary;
    }

    public double getPPG(){
        return this.ppg;
    }

    public void setPPG(double ppg){
        this.ppg = ppg;
    }

    public double getRPG(){
        return this.rpg;
    }

    public void setRPG(double rpg){
        this.rpg = rpg;
    }
    
    public double getAPG(){
        return this.apg;
    }

    public void setAPG(double apg){
        this.apg = apg;
    }

    public double get3PM(){
        return this.threePointersMade;
    }
     
    public void set3PM(double threePointersMade){
        this.threePointersMade = threePointersMade;
    }

    public double getBPG(){
        return this.bpg;
    }

    public void setBPG(double bpg){
        this.bpg = bpg;
    }

    public double getSPG(){
        return this.spg;
    }

    public void setSPG(double spg){
        this.spg = spg;
    }

    public double getFGPercent(){
        return this.fgPercent;
    }

    public void setFGPercent(double fgPercent){
        this.fgPercent = fgPercent;
    }

    public double getTPG(){
        return this.tpg;
    }

    public void setTPG(double tpg){
        this.tpg = tpg;
    }

}
