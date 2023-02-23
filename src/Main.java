import java.io.*;
import java.net.*;
import java.util.*;

import org.json.simple.parser.*;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, URISyntaxException, InterruptedException {

        ArrayList<Team> t = new ArrayList<Team>();

        Home h = new Home();

        Menu m = new Menu();

        ViewTeamsPlayers view = new ViewTeamsPlayers();

        TradePlayers trade = new TradePlayers();

        SimulateGames sim = new SimulateGames();

        h.HomePage(t, m, view, trade, sim, h);

    }
}
