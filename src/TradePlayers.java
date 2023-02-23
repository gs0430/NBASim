import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.*;

public class TradePlayers{
    
    public void tradePlayersPage(ArrayList<Team> t, Menu m, ViewTeamsPlayers view, TradePlayers trade, SimulateGames sim, Home h, String saveName){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton menu = new JButton("Menu");

        menu.setBounds(120, 10, 100, 20);

        panel.add(menu);

        menu.addActionListener(e -> m.MenuPage(t, m, view, trade, sim, h, saveName));
        menu.addActionListener(e -> frame.dispose());

        frame.getContentPane();

        JLabel label = new JLabel("Select a Team:");

        Dimension size = label.getPreferredSize();

        label.setBounds(10, 10, size.width, size.height);

        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label);
        

        String[] teams = new String[t.size()];

        for(int i = 0; i < t.size(); i++){
            teams[i] = t.get(i).getTeamName();
        }

        final JComboBox<String> teamsDropBox = new JComboBox<String>(teams);


        teamsDropBox.setBounds(10, 30, 100, 50);

        panel.add(teamsDropBox);

        JButton submit = new JButton("Submit");

        submit.setBounds(120, 45, 100, 20);

        panel.add(submit);

        JLabel teamName = new JLabel();

        JLabel teamSalaryCap = new JLabel();

        teamName.setBounds(700, 10, 300, 100);

        teamSalaryCap.setBounds(1000, 10, 300, 100);

        panel.add(teamName);

        panel.add(teamSalaryCap);

        JLabel selectPlayer = new JLabel("Select Player(s) you wish to trade:");

        selectPlayer.setBounds(10, 480, 300, 100);;

        panel.add(selectPlayer);

        JButton submitPlayer = new JButton("Submit");

        submitPlayer.setBounds(220, 545, 100, 20);

        final JComboBox<String> playerDropBox = new JComboBox<String>();

        playerDropBox.setBounds(10, 530, 200, 50);

        panel.add(playerDropBox);

        panel.add(submitPlayer);

        JLabel tradeLabel = new JLabel("Player(s) you are trading:");

        tradeLabel.setBounds(350, 480, 300, 100);

        panel.add(tradeLabel);

        JTextArea playersToTradeText = new JTextArea();

        playersToTradeText.setBounds(350, 540, 200, 300);

        panel.add(playersToTradeText);

        ArrayList<String> playersToTrade = new ArrayList<String>();

        String playersString = "";

        JLabel teamSalaryAvailable = new JLabel();

        teamSalaryAvailable.setBounds(1000, 30, 300, 100);

        panel.add(teamSalaryAvailable);

        JLabel selectPlayer2 = new JLabel("Select Player(s) you wish to trade for:");

        selectPlayer2.setBounds(10, 580, 300, 100);;

        panel.add(selectPlayer2);

        final JComboBox<String> playerDropBox2 = new JComboBox<String>();

        playerDropBox2.setBounds(10, 630, 200, 50);

        panel.add(playerDropBox2);

        JButton submitPlayer2 = new JButton("Submit");

        submitPlayer2.setBounds(220, 645, 100, 20);

        panel.add(submitPlayer2);

        JLabel tradeLabel2 = new JLabel("Player(s) you are trading FOR:");

        tradeLabel2.setBounds(600, 480, 300, 100);

        panel.add(tradeLabel2);

        JTextArea playersToTradeForText = new JTextArea();

        playersToTradeForText.setBounds(600, 540, 200, 300);

        panel.add(playersToTradeForText);

        ArrayList<String> playersToTradeFor = new ArrayList<String>();

        JButton reset = new JButton("Reset Trade");

        reset.setBounds(220, 595, 100, 20);

        panel.add(reset);

        JButton submitTrade = new JButton("Submit Trade");

        submitTrade.setBounds(220, 700, 100, 20);

        panel.add(submitTrade);

        reset.addActionListener(e -> resetTrade(playersToTrade, playersToTradeFor, playersToTradeText, playersToTradeForText));

        submitPlayer2.addActionListener(e -> getPlayerToTradeFor(t, playerDropBox2, playersToTradeForText, playersString, playersToTradeFor));

        submitPlayer.addActionListener(e -> getPlayerToTrade(t, playerDropBox, playersToTradeText, playersString, playersToTrade ));

        submit.addActionListener(e -> showTable(t, teamsDropBox, panel, frame, submit, teamName, teamSalaryCap, playerDropBox, teamSalaryAvailable, playerDropBox2));

        submitTrade.addActionListener(e -> completeTrade(t, playersToTrade, playersToTradeFor, teamSalaryAvailable));

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Trade Players");
        frame.setSize(2000, 1000);
        frame.setVisible(true);

    }

    public void deleteTable(ArrayList<Team> t, JComboBox<String> teamsDropBox, JPanel panel, JFrame frame, JButton submit, JScrollPane scroll, JLabel teamName, JLabel teamSalaryCap, JComboBox<String> playerDropBox, JLabel teamSalaryAvailable, JTable playerTable, JComboBox<String> playerDropBox2){
        panel.remove(scroll);
        scroll.removeAll();
        playerTable.removeAll();
        scroll = null;
        playerTable = null;
        playerDropBox.removeAllItems();
        playerDropBox2.removeAllItems();
        submit.addActionListener(e -> showTable(t, teamsDropBox, panel, frame, submit, teamName, teamSalaryCap , playerDropBox, teamSalaryAvailable, playerDropBox2));
    }


    public void showTable(ArrayList<Team> t, JComboBox<String> teamsDropBox, JPanel panel, JFrame frame, JButton submit, JLabel teamName, JLabel teamSalaryCap, JComboBox<String> playerDropBox, JLabel teamSalaryAvailable, JComboBox<String> playerDropBox2){

        String selectedTeam = (String)teamsDropBox.getSelectedItem();

        teamName.setText(selectedTeam);

        String[][] players = new String[0][0];

        DecimalFormat df = new DecimalFormat("#,###");

        double teamSalaryAvailableDouble = 0.0;

        for(int i = 0; i < t.size(); i++){
            if(selectedTeam.equals(t.get(i).getTeamName())){
                players = new String[t.get(i).getRoster().size()][11];

                teamSalaryAvailableDouble = t.get(i).getSalaryCap();

                teamSalaryCap.setText("Salary Cap: " + "$" + String.format(df.format(t.get(i).getSalaryCap())));
                for(int x = 0; x < t.get(i).getRoster().size(); x++){
                    playerDropBox.addItem(t.get(i).getRoster().get(x).getName());
                    playerDropBox2.addItem(t.get(i).getRoster().get(x).getName());


                    teamSalaryAvailableDouble = teamSalaryAvailableDouble - t.get(i).getRoster().get(x).getPlayerSalary();

                    players[x][0] = (t.get(i).getRoster().get(x).getName());
                    players[x][1] = (t.get(i).getRoster().get(x).getPosition());
                    players[x][2] = "$" + String.format(df.format((t.get(i).getRoster().get(x).getPlayerSalary())));
                    players[x][3] = String.valueOf((t.get(i).getRoster().get(x).getPPG()));
                    players[x][4] = String.valueOf((t.get(i).getRoster().get(x).getRPG()));
                    players[x][5] = String.valueOf((t.get(i).getRoster().get(x).getAPG()));
                    players[x][6] = String.valueOf((t.get(i).getRoster().get(x).getBPG()));
                    players[x][7] = String.valueOf((t.get(i).getRoster().get(x).getSPG()));
                    players[x][8] = String.format("%.2f%%", (t.get(i).getRoster().get(x).getFGPercent())*100);
                    players[x][9] = String.valueOf((t.get(i).getRoster().get(x).get3PM()));
                    players[x][10] = String.valueOf((t.get(i).getRoster().get(x).getTPG()));
                }
            }
        }

        String categories[] = {"Name", "Position", "Salary", "PPG", "RPG", "APG", "BPG", "SPG", "FG %", "3PM", "TPG"};


        JTable playerTable = new JTable(players, categories);


        JScrollPane scroll = new JScrollPane(playerTable);   

        scroll.setBounds(225, 100, 1000, 400);
        
        panel.add(scroll, BorderLayout.CENTER);

        teamSalaryAvailable.setText("Salary Available: $" + String.format(df.format(teamSalaryAvailableDouble)));

        playerDropBox.setVisible(true);
        playerDropBox2.setVisible(true);

        submit.addActionListener(e -> deleteTable(t, teamsDropBox, panel, frame, submit, scroll, teamName, teamSalaryCap, playerDropBox, teamSalaryAvailable, playerTable, playerDropBox2));

    }

    public void getPlayerToTrade(ArrayList<Team> t, JComboBox<String> playerDropBox, JTextArea playersToTradeText, String playersString, ArrayList<String> playersToTrade){

        String selectedPlayer = (String)playerDropBox.getSelectedItem();

        boolean isThere = false;

        double tradeSalary1 = 0.0;


        DecimalFormat df = new DecimalFormat("#,###");

        for(int i = 0; i < playersToTrade.size(); i++){
            if(playersToTrade.get(i).equals(selectedPlayer) == true){
                isThere = true;     
            }
        }

        if(isThere == false){
            playersToTrade.add(selectedPlayer);
        }

        for(int i = 0; i < playersToTrade.size(); i++){
            for(int x = 0; x < t.size(); x++){
                for(int e = 0; e < t.get(x).getRoster().size(); e++){
                    if(playersToTrade.get(i).equals(t.get(x).getRoster().get(e).getName())){
                        tradeSalary1 = tradeSalary1 + (t.get(x).getRoster().get(e).getPlayerSalary());

                    }
                }
            }
        }

        for(int i = 0; i < playersToTrade.size(); i++){
            playersString = playersString + playersToTrade.get(i) + "\n";
        }

        playersString = playersString + "-------------------\n" + "Total Tradable Salary:\n$" + String.format(df.format(tradeSalary1));



        playersToTradeText.setText(playersString);


    }

    public void getPlayerToTradeFor(ArrayList<Team> t, JComboBox<String> playerDropBox2, JTextArea playersToTradeForText, String playersString, ArrayList<String> playersToTradeFor){
        String selectedPlayer1 = (String)playerDropBox2.getSelectedItem();

        boolean isThere = false;

        double tradeSalary2 = 0.0;

        DecimalFormat df = new DecimalFormat("#,###");

        for(int i = 0; i < playersToTradeFor.size(); i++){
            if(playersToTradeFor.get(i).equals(selectedPlayer1) == true){
                isThere = true;     
            }
        }

        if(isThere == false){
            playersToTradeFor.add(selectedPlayer1);
        }

        for(int i = 0; i < playersToTradeFor.size(); i++){
            for(int x = 0; x < t.size(); x++){
                for(int e = 0; e < t.get(x).getRoster().size(); e++){
                    if(playersToTradeFor.get(i).equals(t.get(x).getRoster().get(e).getName())){
                        tradeSalary2 = tradeSalary2 + (t.get(x).getRoster().get(e).getPlayerSalary());

                    }
                }
            }
        }

        for(int i = 0; i < playersToTradeFor.size(); i++){
            playersString = playersString + playersToTradeFor.get(i) + "\n";
        }

        playersString = playersString + "-------------------\n" + "Total Tradable Salary:\n$" + String.format(df.format(tradeSalary2));



        playersToTradeForText.setText(playersString);

    }

    public void resetTrade(ArrayList<String> playersToTrade, ArrayList<String> playersToTradeFor, JTextArea playersToTradeText, JTextArea playersToTradeForText){
        playersToTrade.removeAll(playersToTrade);
        playersToTradeFor.removeAll(playersToTradeFor);

        playersToTradeForText.setText("");

        playersToTradeText.setText("");

    }

    public void completeTrade(ArrayList<Team> t, ArrayList<String> playersToTrade, ArrayList<String> playersToTradeFor, JLabel teamSalaryAvailable){

        double tradeSalary1 = 0.0;
        
        for(int i = 0; i < playersToTrade.size(); i++){
            for(int x = 0; x < t.size(); x++){
                for(int e = 0; e < t.get(x).getRoster().size(); e++){
                    if(playersToTrade.get(i).equals(t.get(x).getRoster().get(e).getName())){
                        tradeSalary1 = tradeSalary1 + (t.get(x).getRoster().get(e).getPlayerSalary());

                    }
                }
            }
        }

        double tradeSalary2 = 0.0;

        for(int i = 0; i < playersToTradeFor.size(); i++){
            for(int x = 0; x < t.size(); x++){
                for(int e = 0; e < t.get(x).getRoster().size(); e++){
                    if(playersToTradeFor.get(i).equals(t.get(x).getRoster().get(e).getName())){
                        tradeSalary2 = tradeSalary2 + (t.get(x).getRoster().get(e).getPlayerSalary());

                    }
                }
            }
        }

        String salaryAvailable = teamSalaryAvailable.getText();
        salaryAvailable = salaryAvailable.replace("$", "");
        salaryAvailable = salaryAvailable.replace(",", "");
        salaryAvailable = salaryAvailable.replace("Salary Available: ", "");


        double salaryAvailableDouble = Double.parseDouble(salaryAvailable);

        ArrayList<Player> p = new ArrayList<Player>();

        ArrayList<Player> p2 = new ArrayList<Player>();


        if(salaryAvailableDouble + (tradeSalary1 - tradeSalary2) > 0){

            String team1 = "";
            String team2 = "";

            for(int e = 0; e < playersToTrade.size(); e++){
                String playername = playersToTrade.get(e);
                for(Team team:t){
                    for(int b = 0; b < team.getRoster().size(); b++){
                        if(team.getRoster().get(b).getName().equals(playername)){
                            p.add(team.getRoster().get(b));
                            team1 = team.getTeamName();
                        }
                    }
                }
            }



            for(int i = 0; i < playersToTradeFor.size(); i++){
                String playername = playersToTradeFor.get(i);
                for(Team team:t){
                    for(int z = 0; z < team.getRoster().size(); z++){
                        if(team.getRoster().get(z).getName().equals(playername)){
                            p2.add(team.getRoster().get(z));
                            team2 = team.getTeamName();
                        }
            
                    }
                }
            }

            for(Player player: p){
                String player1delete = player.getName();
                for(Team team:t){
                    for(int i = 0; i < team.getRoster().size(); i++){
                        if(team.getRoster().get(i).getName().equals(player1delete)){
                            team.getRoster().remove(i);
                        }
                    }
                }
            }

            for(Player player: p2){
                String player2delete = player.getName();
                for(Team team:t){
                    for(int i = 0; i < team.getRoster().size(); i++){
                        if(team.getRoster().get(i).getName().equals(player2delete)){
                            team.getRoster().remove(i);
                        }
                    }
                }
            }

            for(Player player: p){
                for(Team team: t){
                    if(team.getTeamName().equals(team2)){

                        team.getRoster().add(player);
                    }

                }
            }

            for(Player player: p2){
                for(Team team: t){
                    if(team.getTeamName().equals(team1)){
                        team.getRoster().add(player);
                    }

                }
            }
        }
    }
}
