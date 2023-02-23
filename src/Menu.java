import java.util.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;


public class Menu {

    public void MenuPage(ArrayList<Team> t, Menu m, ViewTeamsPlayers view, TradePlayers trade, SimulateGames sim, Home h, String saveName){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton ViewTeamsPlayersButton = new JButton("View Teams/Players");
        JButton TradePlayersButton = new JButton("Trade Players");
        JButton SimulateHead2HeadButton = new JButton("Simulate Head to Head Games");
        JButton Save = new JButton("Save and Exit");
        JButton Home = new JButton("Home");


        JLabel label = new JLabel("Menu");

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
        panel.add(ViewTeamsPlayersButton);
        panel.add(TradePlayersButton);
        panel.add(SimulateHead2HeadButton);
        panel.add(Save);
        panel.add(Home);

        ViewTeamsPlayersButton.addActionListener(e -> view.viewTeamsPlayersPage(t, m, view, trade, sim, h, saveName));
        ViewTeamsPlayersButton.addActionListener(close -> frame.dispose());

        TradePlayersButton.addActionListener(e -> trade.tradePlayersPage(t, m, view, trade, sim, h, saveName));
        TradePlayersButton.addActionListener(close -> frame.dispose());

        SimulateHead2HeadButton.addActionListener(e -> sim.SimulateGamesPage(t, m, view, trade, sim, h, saveName));
        SimulateHead2HeadButton.addActionListener(close -> frame.dispose());

        Home.addActionListener(e -> h.HomePage(t, m, view, trade, sim, h));
        Home.addActionListener(close -> frame.dispose());

        Save.addActionListener(e -> save(t, saveName));
        Save.addActionListener(e -> frame.dispose());


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Menu");
        frame.pack();
        frame.setVisible(true);
    }

    public void save(ArrayList<Team> t, String saveName){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveName + ".ser")); 
            out.writeObject(t); 
            out.close();
            System.out.println("League saved");
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("An error occured"); 
        }
    }
    
}
