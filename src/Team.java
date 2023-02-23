import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable{
    private String teamID;
    private String teamName;
    private double salaryCap;
    private ArrayList<Player> roster;

    public Team(String teamID, String teamName, double salaryCap, ArrayList<Player> roster){
        this.teamName = teamName;
        this.teamID = teamID;
        this.salaryCap = salaryCap;
        this.roster = roster;
    }    

    public String getTeamID(){
        return this.teamID;
    }

    public void setTeamID(String teamID){
        this.teamID = teamID;
    }

    public String getTeamName(){
        return this.teamName;
    }

    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

    public double getSalaryCap(){
        return this.salaryCap;
    }

    public void setSalaryCap(double salaryCap){
        this.salaryCap = salaryCap;
    }

    public ArrayList<Player> getRoster(){
        return this.roster;
    }

    public void setRoster(ArrayList<Player> roster){
        this.roster = roster;
    }
    
}
