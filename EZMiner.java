package scripts;
import java.awt.Color;

import org.tribot.api.rs3.Player;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

//This script is a basic iron ore power miner for Old School Runescape
@ScriptManifest(authors = { "Bradley" }, category = "Mining", name = "EZMiner")
public class EZMiner extends Script implements Painting{
	//Initializes the timer between 
	Timer timer = new Timer(3000);
	//ID of the ore we want to mine (iron ore)
	private const final int ORE_ID = 2093;
	//ID of items from our inventory that we don't wanna drop (all types of pickaxes)
	//Bronze, iron, steel, black, mithril, adamant, rune, dragon, and infernal pickaxe IDs
	private const final int [] PICKAXES = { 1265, 1267, 1269, 1271, 1273, 1275, 11920, 13243 };
	
	//Starts the script
	private boolean onStart() {
		println("EZMiner has started");
		return true;
	}
	
	//Runs the script
	public void run() {
		if(onStart()) {
			while(true) {
				sleep(loop());
			}
		}
	}
	
	//Helper function that finds the nearest unmined ore
	public RSObject findNearest(int distance, int itemID) {
		RSObject[] ores = Objects.find(distance, itemID);
		for (RSObject ore : ores) {
			if (ore != null) {
				return ore;
			}
		}
		return;
	}
	
	//Sets delay in milliseconds between each execution of loop 
	private int loop() {
		//Drop all ores if inventory full
		if (Inventory.isFull()) {
			Inventory.dropAllExcept(PICKAXES)
		}
		//Find nearest ore to mine if idle
		else if (Player.getAnimation() == -1) {
			RSObject oreToMine = findNearest(5, ORE_ID);
			//Checks if the script has found a possible ore to mine
			if (oreToMine != null) {
				if (oreToMine.isOnScreen()) {
					oreToMine.click("Mine");
					timer.reset();
					while (timer.isRunning() && Player.getRSPlayer().getAnimation == -1) {
						sleep(40);)
					}
				}
			}
		}
		return 100;
	}
	
	//Displays "Script running: EZMiner" on the botting client in the lower right corner
	public void onPaint(Graphics graphics) {
		graphics.setColor(Color.GREEN);
		graphics.drawString("Script running: EZMiner", 380, 330);
	}
	
}
