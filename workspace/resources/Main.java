package resources;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		Blackjack game = new Blackjack();
		GUI gui = new GUI(game);
    }

	}
