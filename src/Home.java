import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import org.jsoup.Jsoup;
import org.apache.commons.lang3.*;



public class Home{

    public void HomePage(ArrayList<Team> t, Menu m, ViewTeamsPlayers view, TradePlayers trade, SimulateGames sim, Home h){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton NewLeagueButton = new JButton("Create New League");
        JButton LoadLeagueButton = new JButton("Load Saved League");
        JLabel label = new JLabel("NBA myLeague");

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
        panel.add(NewLeagueButton);
        panel.add(LoadLeagueButton);
        

        NewLeagueButton.addActionListener(newLeague -> {
            try {
                createNewLeague(t, m, view, trade, sim, h);
            } catch (IOException | InterruptedException | ParseException e) {
                e.printStackTrace();
            }
        }); 

        NewLeagueButton.addActionListener(close -> frame.dispose());
        LoadLeagueButton.addActionListener(loadLeague -> loadLeague(t, m, view, trade, sim, h));
        LoadLeagueButton.addActionListener(close -> frame.dispose());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Home");
        frame.pack();
        frame.setVisible(true);


    }
   @SuppressWarnings("unchecked")
    public void loadLeague(ArrayList<Team> t, Menu m, ViewTeamsPlayers view, TradePlayers trade, SimulateGames sim, Home h){

        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        jfc.setVisible(true);
        File file = jfc.getSelectedFile();
        String fileName = file.getName();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            t = (ArrayList<Team>) in.readObject(); 
            in.close();
            m.MenuPage(t, m, view, trade, sim, h, fileName);
        } 
        catch(IOException io){
            io.printStackTrace();
        } 
        catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("An error occured");}
    }


    public void createNewLeague(ArrayList<Team> t, Menu m, ViewTeamsPlayers view, TradePlayers trade, SimulateGames sim, Home h) throws IOException, InterruptedException, ParseException{
        JFrame frame = new JFrame("Create League");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String result = (String)JOptionPane.showInputDialog(
               frame,
               "Enter a name for your League:", 
               "League Name",            
               JOptionPane.PLAIN_MESSAGE,
               null,         
               null,
               ""
            );

        String saveName = result;


        URL teamsURL = new URL("https://api.sportradar.us/nba/trial/v7/en/league/hierarchy.json?api_key=kajr3xhezwme2uayk3afn44f");

        HttpURLConnection conn = (HttpURLConnection) teamsURL.openConnection();

        conn.setRequestMethod("GET");
        conn.connect();

        StringBuilder teamData = new StringBuilder();
        Scanner scanner = new Scanner(teamsURL.openStream());

        while (scanner.hasNext()) {
            teamData.append(scanner.nextLine());
        }
        
        scanner.close();

        JSONParser parse = new JSONParser();

        JSONObject leagueData  = (JSONObject) parse.parse(String.valueOf(teamData));

        JSONArray conferences = (JSONArray) leagueData.get("conferences");

        for (int i = 0; i < 2; i++) {
            JSONObject divisionsObject = (JSONObject) conferences.get(i);
            JSONArray divisions = (JSONArray) divisionsObject.get("divisions");
        
            for (int x = 0; x < 3; x++) {
                JSONObject teamsObject = (JSONObject) divisions.get(x);
                JSONArray teams = (JSONArray) teamsObject.get("teams");
      
                for (int e = 0; e < 5; e++) {
                    JSONObject team = (JSONObject) teams.get(e);
                    String teamName = String.valueOf(team.get("name"));
                    String teamID = String.valueOf(team.get("id"));
                    
                    t.add(new Team(teamID, teamName, 0, new ArrayList<Player>()));
                }
            }
        }

        Thread.sleep(4000);
        
        System.out.println("Loading NBA Players Please Wait...");

        for(int i = 0; i < t.size(); i++){

            String teamID = t.get(i).getTeamID();

            URL rosterURL = new URL("https", "api.sportradar.us", "/nba/trial/v7/en/teams/" + teamID + "/profile.json?api_key=kajr3xhezwme2uayk3afn44f");

            HttpURLConnection conn2 = (HttpURLConnection) rosterURL.openConnection();

            conn2.setRequestMethod("GET");
            conn2.connect();

            StringBuilder roster = new StringBuilder();
            Scanner scanner2 = new Scanner(rosterURL.openStream());

            while (scanner2.hasNext()) {
                roster.append(scanner2.nextLine());
            }
        
            scanner2.close();

            JSONObject rosterData = (JSONObject) parse.parse(String.valueOf(roster));

            JSONArray players = (JSONArray) rosterData.get("players");

            for (int e = 0; e < players.size(); e++) {
                JSONObject player = (JSONObject) players.get(e);
                String playerName = String.valueOf(player.get("full_name"));

                t.get(i).getRoster().add(new Player(playerName, "", 0, 0, 0, 0, 0, 0, 0, 0, 0));
                
            }

            Thread.sleep(4000);

        }

        System.out.println("All Players Loaded");

        

        System.out.println("Loading Player Stats...");

        for(int i = 0; i < t.size(); i++){

            String teamID = t.get(i).getTeamID();

            URL playerStatsURL = new URL("https", "api.sportradar.us", "/nba/trial/v7/en/seasons/2021/REG/teams/" + teamID + "/statistics.json?api_key=kajr3xhezwme2uayk3afn44f");

            HttpURLConnection conn3 = (HttpURLConnection) playerStatsURL.openConnection();

            conn3.setRequestMethod("GET");
            conn3.connect();

            StringBuilder playerStats = new StringBuilder();
            Scanner scanner3 = new Scanner(playerStatsURL.openStream());

            while (scanner3.hasNext()) {
                playerStats.append(scanner3.nextLine());
            }
        
            scanner3.close();

            JSONObject playerData = (JSONObject) parse.parse(String.valueOf(playerStats));

            JSONArray stats = (JSONArray) playerData.get("players");


            for (int e = 0; e < stats.size(); e++) {
                JSONObject playerAllStats = (JSONObject) stats.get(e);
                String playerNameStats = String.valueOf(playerAllStats.get("full_name"));

                JSONObject playerAverageStats = (JSONObject) playerAllStats.get("average");

                String position = String.valueOf(playerAllStats.get("primary_position"));

                double rpg = Double.parseDouble(String.valueOf(playerAverageStats.get("rebounds")));

                double ppg = Double.parseDouble(String.valueOf(playerAverageStats.get("points")));

                double apg = Double.parseDouble(String.valueOf(playerAverageStats.get("assists")));

                double bpg = Double.parseDouble(String.valueOf(playerAverageStats.get("blocks")));

                double spg = Double.parseDouble(String.valueOf(playerAverageStats.get("steals")));

                double fgPercent = Double.parseDouble(String.valueOf(playerAverageStats.get("field_goals_made"))) / Double.parseDouble(String.valueOf(playerAverageStats.get("field_goals_att")));

                double threePointersMade = Double.parseDouble(String.valueOf(playerAverageStats.get("three_points_made")));

                double tpg = Double.parseDouble(String.valueOf(playerAverageStats.get("turnovers")));

                for(int z = 0; z < t.get(i).getRoster().size(); z++){
                    if(playerNameStats.equals(t.get(i).getRoster().get(z).getName())){
                        t.get(i).getRoster().get(z).setPosition(position);
                        t.get(i).getRoster().get(z).setPPG(ppg);
                        t.get(i).getRoster().get(z).setRPG(rpg);
                        t.get(i).getRoster().get(z).setAPG(apg);
                        t.get(i).getRoster().get(z).setBPG(bpg);
                        t.get(i).getRoster().get(z).setSPG(spg);
                        t.get(i).getRoster().get(z).setFGPercent(fgPercent);
                        t.get(i).getRoster().get(z).set3PM(threePointersMade);
                        t.get(i).getRoster().get(z).setTPG(tpg);
                    }
                }
            }

            Thread.sleep(4000);
        
        }

        System.out.println("All Player Stats Loaded.");

        System.out.println("Loading Salaries");


        org.jsoup.nodes.Document sal = Jsoup.connect("https://www.basketball-reference.com/contracts/players.html").get();
        org.jsoup.select.Elements rows = sal.select("tr");
        for(org.jsoup.nodes.Element row :rows){
            String player = "";
            String newName = "";
            String playerSalary = "";
            org.jsoup.select.Elements columns = row.select("td");
      
            for (org.jsoup.nodes.Element column:columns){
                
                String dataStat = (column.attr("data-stat"));
    
                if(dataStat.equals("player")){
                    org.jsoup.select.Elements as = column.select("a");
                    for (org.jsoup.nodes.Element a:as){
                        player = a.text();
                        if(player.isEmpty()){}
                        else if (player.isEmpty() == false){
                            newName = player;
                            newName = StringUtils.stripAccents(newName);
                        }
                    }

                }   
            
                if(dataStat.equals("y1")){
                    playerSalary = column.attr("csk");
                }
                    
                
                if(newName.isEmpty() == false && playerSalary.isEmpty() == false){
                    double playerSalaryDouble = Double.parseDouble(playerSalary);
                    for(int z = 0; z < t.size(); z++){
                        for(int c = 0; c < t.get(z).getRoster().size(); c++){
                            String name = t.get(z).getRoster().get(c).getName();
                            if(newName.equals(name)){
                                t.get(z).getRoster().get(c).setPlayerSalary(playerSalaryDouble);
                            }
                            if(name.equals("Goran Dragic")){
                                t.get(z).getRoster().get(c).setPlayerSalary(7000000);
                            }
                            if(name.equals("Blake Griffin")){
                                t.get(z).getRoster().get(c).setPlayerSalary(7000000);
                            }
                            if(name.equals("Nikola Vucevic")){
                                t.get(z).getRoster().get(c).setPlayerSalary(15000000);
                            } 
                            if(name.equals("Tristan Thompson")){
                                t.get(z).getRoster().get(c).setPlayerSalary(6000000);
                            } 
                            if(name.equals("Bojan Bogdanovic")){
                                t.get(z).getRoster().get(c).setPlayerSalary(15000000);
                            } 
                            if(name.equals("Kemba Walker")){
                                t.get(z).getRoster().get(c).setPlayerSalary(10000000);
                            }
                            if(name.equals("Robert Williams III")){
                                t.get(z).getRoster().get(c).setPlayerSalary(6000000);
                            }  
                            if(name.equals("Kevin Know II")){
                                t.get(z).getRoster().get(c).setPlayerSalary(5000000);
                            }  
                            if(name.equals("KJ Martin Jr.")){
                                t.get(z).getRoster().get(c).setPlayerSalary(6000000);
                            } 
                            if(name.equals("Marcus Morris Sr.")){
                                t.get(z).getRoster().get(c).setPlayerSalary(6000000);
                            }

                        }
                    }     
    
                }
            
            }   
    
        }

        org.jsoup.nodes.Document doc = Jsoup.connect("https://www.spotrac.com/nba/cap/").get();
        org.jsoup.select.Elements rows1 = doc.select("tr");
        for(org.jsoup.nodes.Element row1 :rows1){
            org.jsoup.select.Elements columns = row1.select("td");
            String teamName = "";
            String newTeamName = "";
            for (org.jsoup.nodes.Element column:columns){
                org.jsoup.select.Elements as = row1.select("a");
                for (org.jsoup.nodes.Element a: as ){
                    teamName = (a.getElementsByClass("xs-hide").text());
                }
                String teamSalary = (column.getElementsByClass(" center").text());
                if(teamName.isEmpty()){}
                else if (teamName.isEmpty() == false){
                    newTeamName = teamName.substring(teamName.lastIndexOf(" ")+1);}
                if(newTeamName.isEmpty() == false && teamSalary.isEmpty() == false){
                    teamSalary = teamSalary.replace("$", "");
                    teamSalary = teamSalary.replace(",", "");
                    double teamSalaryDouble = Double.parseDouble(teamSalary);
                    for(int z = 0; z < t.size(); z++){
                        String name = t.get(z).getTeamName();
                        if(newTeamName.equals(name)){
                            t.get(z).setSalaryCap(1500000 + teamSalaryDouble);
                        }
                        if(name.equals("Trail Blazers")){
                            t.get(z).setSalaryCap(131015888);
                        }
                                
                    }
                }     
    
            }
            
        }
        
        



        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveName + ".ser")); 
            out.writeObject(t); 
            out.close();
            System.out.println("League saved");
            m.MenuPage(t, m, view, trade, sim, h, saveName);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("An error occured"); 
        }

    }


}