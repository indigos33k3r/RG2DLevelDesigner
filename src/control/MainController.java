package control;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.event.ListSelectionEvent;

import model.LBCodeGen;
import model.LBDatabase;
import view.LayoutWindow;
import view.LevelWindow;
import view.SpriteWindow;

public class MainController {
	
	public LayoutWindow lwin;
	public SpriteWindow swin;
	public LevelWindow vwin;
	public LBDatabase db;

	public MainController() {

		this.vwin = new LevelWindow(this);
		this.swin = new SpriteWindow(this);
		this.lwin = new LayoutWindow(this);
		
		db = LBDatabase.getInstance();
		
		// Test stuff
		db.GamePath = "/Users/ben/Documents/workspace/FiretruckGame2D/";
		db.LoadSpriteDirectory();
		
		int il = db.CreateNewLevel("Level_01", 1);
		
		lwin.level = db.LevelArray.get(il);
		
		swin.DisplaySpriteList();
		vwin.DisplayLevelList();
		
	}
	
	public void keyTypedHandler(KeyEvent e){
		System.out.println("MC:Key Typed " + e.getKeyChar());

		if(e.getKeyChar() == 's'){
			
			System.out.println("Saving Data.");
			db.saveDatabase(db.GamePath + "Data.bin");

		} else if (e.getKeyChar() == 'o') {
			
			db.loadDatabase(db.GamePath + "Data.bin");

			//Draw first level by default
			// TODO get level selected my level window or, set level window selection to 0.
			lwin.level = db.LevelArray.get(0);
			
			//UPdate views
			lwin.DrawLevel();
			swin.DisplaySpriteList();
			vwin.DisplayLevelList();
			
		} else {
			System.out.println("Writing Code.");
			try {
				LBCodeGen.GenCode();
			} catch (IOException e1) {
				System.out.println("Failed to generate brightscript code!");
				e1.printStackTrace();
			}
		}
	}
	public void levelSelectionChangeHandler(ListSelectionEvent e) {

		System.out.println("MC: Level selection changed");
		
		//Update level
		if(vwin.activeLevel > 0){
			lwin.level = db.LevelArray.get(vwin.activeLevel);
			lwin.DrawLevel();
		}
		
	}
	
	
	/**
	 * Checks the sprite window and returns true if a sprite is selected. 
	 * 
	 * @return
	 */
	public boolean validSpriteSelected() {
		
		int idx = swin.spriteList.getSelectedIndex();
		
		if(idx < 0){
			return false;
		}
		return true;
		
	}
	/**
	 * Checks the sprite window and gets the index of the currently selected sprite.
	 * 
	 * @return
	 */
	public int getSelectedSpriteIndex(){

		return swin.spriteList.getSelectedIndex();
	}


	
	

}
