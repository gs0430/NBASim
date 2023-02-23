import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SimulateGames {
    
    public void SimulateGamesPage(ArrayList<Team> t, Menu m, ViewTeamsPlayers view, TradePlayers trade, SimulateGames sim, Home h, String saveName){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton menu = new JButton("Menu");

        menu.setBounds(10, 500, 100, 20);

        panel.add(menu);

        menu.addActionListener(e -> m.MenuPage(t, m, view, trade, sim, h, saveName));
        menu.addActionListener(e -> frame.dispose());

        JLabel label = new JLabel("Select 2 Teams's to Matchup:");

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

        final JComboBox<String> teamsDropBox2 = new JComboBox<String>(teams);

        teamsDropBox.setBounds(10, 30, 100, 50);

        panel.add(teamsDropBox);

        teamsDropBox2.setBounds(120, 30, 100, 50);

        panel.add(teamsDropBox2);

        JButton submit = new JButton("Submit");

        submit.setBounds(10, 100, 100, 20);

        panel.add(submit);

        JTextArea finalScore = new JTextArea();

        finalScore.setBounds(10, 150, 200, 200);

        panel.add(finalScore);

        submit.addActionListener(e -> simulateGame(t, teamsDropBox, teamsDropBox2, panel, submit, finalScore));

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Simulate Games");
        frame.setSize(2000, 1000);
        frame.setVisible(true);

    }

    public void simulateGame(ArrayList<Team> t, JComboBox<String> teamsDropBox, JComboBox<String> teamsDropBox2, JPanel panel, JButton submit, JTextArea finalScore){

        String selectedTeam = (String)teamsDropBox.getSelectedItem();

        String[][] players = new String[0][0];

        Random rand = new Random();

        DefaultTableModel model = new DefaultTableModel();

        double team1Total = 0.0;

        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Points");
        model.addColumn("Rebounds");
        model.addColumn("Assists");
        model.addColumn("Blocks");
        model.addColumn("Steals");
        model.addColumn("FG %");
        model.addColumn("3PM");
        model.addColumn("Turnovers");

        for(Team team: t){
            if(team.getTeamName().equals(selectedTeam)){
                for(int i = 0; i < team.getRoster().size(); i++){

                    players = new String[team.getRoster().size()][10];

                    double ppg = team.getRoster().get(i).getPPG();
                    ppg = Math.round(rand.nextGaussian() * 1 + ppg);
                    if(ppg < 0){
                        ppg = 0;
                    }

                    team1Total = team1Total + ppg;
                
                    double apg = team.getRoster().get(i).getAPG();
                    apg = Math.round(rand.nextGaussian() * 2 + apg);
                    if(apg < 0){
                        apg = 0;
                    }

                    double rpg = team.getRoster().get(i).getRPG();
                    rpg = Math.round(rand.nextGaussian() * 2 + rpg);
                    if(rpg < 0){
                        rpg = 0;
                    }

                    double bpg = team.getRoster().get(i).getBPG();
                    bpg = Math.round(rand.nextGaussian() * 1 + bpg);
                    if(bpg < 0){
                        bpg = 0;
                    }

                    double spg = team.getRoster().get(i).getSPG();
                    spg = Math.round(rand.nextGaussian() * 1 + spg);
                    if(spg < 0){
                        spg = 0;
                    }

                    double fgPercent = team.getRoster().get(i).getFGPercent();
                    fgPercent = rand.nextGaussian() * 0.05 + fgPercent;
                    if(fgPercent< 0){
                        fgPercent = 0;
                        ppg = 0;
                    }

                    double threePointersMade = team.getRoster().get(i).get3PM();
                    threePointersMade = Math.round(rand.nextGaussian() * 1 + threePointersMade);
                    if(threePointersMade < 0){
                        threePointersMade = 0;
                    }

                    double tpg = team.getRoster().get(i).getTPG();
                    tpg = Math.round(rand.nextGaussian() * 0.5 + tpg);
                    if(tpg < 0){
                        tpg = 0;
                    }


                    players[i][0] = team.getRoster().get(i).getName();
                    players[i][1] = (team.getRoster().get(i).getPosition());
                    players[i][2] = String.valueOf(ppg);
                    players[i][3] = String.valueOf(rpg);
                    players[i][4] = String.valueOf(apg);
                    players[i][5] = String.valueOf(bpg);
                    players[i][6] = String.valueOf(spg);
                    players[i][7] = String.format("%.2f%%", fgPercent*100);
                    players[i][8] = String.valueOf(threePointersMade);
                    players[i][9] = String.valueOf(tpg);

                    model.addRow(players[i]);
    
                }

            }
        }


        JTable scoreBoard = new JTable(model);


        JScrollPane scrollBox = new JScrollPane(scoreBoard);   

        scrollBox.setBounds(300, 10, 1000, 300);
        
        panel.add(scrollBox, BorderLayout.CENTER);

        String selectedTeam2 = (String)teamsDropBox2.getSelectedItem();

        double team2Total = 0.0;

        String[][] players2 = new String[0][0];

        DefaultTableModel model2 = new DefaultTableModel();

        model2.addColumn("Name");
        model2.addColumn("Position");
        model2.addColumn("Points");
        model2.addColumn("Rebounds");
        model2.addColumn("Assists");
        model2.addColumn("Blocks");
        model2.addColumn("Steals");
        model2.addColumn("FG %");
        model2.addColumn("3PM");
        model2.addColumn("Turnovers");

        for(Team team: t){
            if(team.getTeamName().equals(selectedTeam2)){
                for(int i = 0; i < team.getRoster().size(); i++){

                    players2 = new String[team.getRoster().size()][10];

                    double ppg = team.getRoster().get(i).getPPG();
                    ppg = Math.round(rand.nextGaussian() * 1 + ppg);
                    if(ppg < 0){
                        ppg = 0;
                    }

                    team2Total = team2Total + ppg;
                
                    double apg = team.getRoster().get(i).getAPG();
                    apg = Math.round(rand.nextGaussian() * 2 + apg);
                    if(apg < 0){
                        apg = 0;
                    }

                    double rpg = team.getRoster().get(i).getRPG();
                    rpg = Math.round(rand.nextGaussian() * 2 + rpg);
                    if(rpg < 0){
                        rpg = 0;
                    }

                    double bpg = team.getRoster().get(i).getBPG();
                    bpg = Math.round(rand.nextGaussian() * 1 + bpg);
                    if(bpg < 0){
                        bpg = 0;
                    }

                    double spg = team.getRoster().get(i).getSPG();
                    spg = Math.round(rand.nextGaussian() * 1 + spg);
                    if(spg < 0){
                        spg = 0;
                    }

                    double fgPercent = team.getRoster().get(i).getFGPercent();
                    fgPercent = rand.nextGaussian() * 0.05 + fgPercent;
                    if(fgPercent< 0){
                        fgPercent = 0;
                        ppg = 0;
                    }

                    double threePointersMade = team.getRoster().get(i).get3PM();
                    threePointersMade = Math.round(rand.nextGaussian() * 1 + threePointersMade);
                    if(threePointersMade < 0){
                        threePointersMade = 0;
                    }

                    double tpg = team.getRoster().get(i).getTPG();
                    tpg = Math.round(rand.nextGaussian() * 0.5 + tpg);
                    if(tpg < 0){
                        tpg = 0;
                    }


                    players2[i][0] = team.getRoster().get(i).getName();
                    players2[i][1] = (team.getRoster().get(i).getPosition());
                    players2[i][2] = String.valueOf(ppg);
                    players2[i][3] = String.valueOf(rpg);
                    players2[i][4] = String.valueOf(apg);
                    players2[i][5] = String.valueOf(bpg);
                    players2[i][6] = String.valueOf(spg);
                    players2[i][7] = String.format("%.2f%%", fgPercent*100);
                    players2[i][8] = String.valueOf(threePointersMade);
                    players2[i][9] = String.valueOf(tpg);

                    model2.addRow(players2[i]);
    
                }

            }
        }


        JTable scoreBoard2 = new JTable(model2);


        JScrollPane scrollBox2 = new JScrollPane(scoreBoard2);  
        
        scrollBox2.setBounds(300, 400, 1000, 300);
 
        
        panel.add(scrollBox2, BorderLayout.CENTER);

        finalScore.setText("Final Score:\n" + selectedTeam + ": " + team1Total + "\n" + selectedTeam2 + ": " + team2Total);

        submit.addActionListener(e -> deleteTable(t, teamsDropBox, teamsDropBox2, panel, submit, scrollBox, scoreBoard, scrollBox2, scoreBoard2, finalScore));


    }

    public void deleteTable(ArrayList<Team> t, JComboBox<String> teamsDropBox, JComboBox<String> teamsDropBox2, JPanel panel, JButton submit, JScrollPane scroll, JTable scoreTable, JScrollPane scroll2, JTable scoreTable2, JTextArea finalScore){
        panel.remove(scroll);
        panel.remove(scroll2);
        scroll.removeAll();
        scoreTable.removeAll();
        scroll = null;
        scroll2 = null;
        scoreTable = null;
        submit.addActionListener(e -> simulateGame(t, teamsDropBox, teamsDropBox2, panel, submit, finalScore));
    }


}
