import java.util.*;
import java.awt.*;
import java.text.DecimalFormat;

import javax.swing.*;

public class ViewTeamsPlayers {

    public void viewTeamsPlayersPage(ArrayList<Team> t, Menu m, ViewTeamsPlayers view, TradePlayers trade, SimulateGames sim, Home h, String saveName){

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton menu = new JButton("Menu");

        menu.setBounds(120,10, 100, 20);

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

        submit.addActionListener(e -> showTeam(t, teamsDropBox, panel, frame, submit, teamName, teamSalaryCap));

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("View Teams and Players");
        frame.setSize(2000, 1000);
        frame.setVisible(true);
        
    }

    public void deleteTable(ArrayList<Team> t, JComboBox<String> teamsDropBox, JPanel panel, JFrame frame, JButton submit, JScrollPane scroll, JLabel teamName, JLabel teamSalaryCap){
        panel.remove(scroll);
        submit.addActionListener(e -> showTeam(t, teamsDropBox, panel, frame, submit, teamName, teamSalaryCap));
    }

    public void showTeam(ArrayList<Team> t, JComboBox<String> teamsDropBox, JPanel panel, JFrame frame, JButton submit, JLabel teamName, JLabel teamSalaryCap){

        String selectedTeam = (String)teamsDropBox.getSelectedItem();

        teamName.setText(selectedTeam);

        String[][] players = new String[0][0];

        DecimalFormat df = new DecimalFormat("#,###");


        for(int i = 0; i < t.size(); i++){
            if(selectedTeam.equals(t.get(i).getTeamName())){
                players = new String [t.get(i).getRoster().size()][11];
                teamSalaryCap.setText("Salary Cap: " + "$" + String.format(df.format(t.get(i).getSalaryCap())));
                for(int x = 0; x < t.get(i).getRoster().size(); x++){
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

        scroll.setBounds(225, 200, 1000, 500);
        
        panel.add(scroll, BorderLayout.CENTER);

        submit.addActionListener(e -> deleteTable(t, teamsDropBox, panel, frame, submit, scroll, teamName, teamSalaryCap));


    }
    
}
