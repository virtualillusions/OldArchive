package com.tps1.character;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.controls.GameControl;
import com.jme.input.controls.GameControlManager;
import com.jme.input.controls.binding.KeyboardBinding;
import com.jmex.game.StandardGame;
import com.jmex.game.state.GameStateManager;
import com.tps1.GameState.DefineGameState;
import com.tps1.GameState.gameSingleton;
import com.tps1.lvlLoader.CopyOfCopyOflevelTester;
import com.tps1.scene.SkyBoxManager.SkyBoxGameState;

public class controlManager {

	private GameControl jump;


	public controlManager(Animationator animator, InputHandler input) {
		 // Create our Controls
	    GameControlManager manager = new GameControlManager();
	    //Move Forward
	    GameControl forward = manager.addControl("Forward");
	    forward.addBinding(new KeyboardBinding(KeyInput.KEY_I));
	    //Move Backward
	    GameControl backward = manager.addControl("Backward");
	    backward.addBinding(new KeyboardBinding(KeyInput.KEY_K));
	    //Strafe Left
	    GameControl strafeLeft = manager.addControl("Strafe Left");
	    strafeLeft.addBinding(new KeyboardBinding(KeyInput.KEY_J));
	    //Strafe Right
	    GameControl strafeRight = manager.addControl("Strafe Right");
	    strafeRight.addBinding(new KeyboardBinding(KeyInput.KEY_L));
	    //Jump
	    jump = manager.addControl("Jump");
	    jump.addBinding(new KeyboardBinding(KeyInput.KEY_SPACE));
	    
	}

	public void update(float time) {

	}
	
	
	 public static void main(String[] args) throws InterruptedException{
		 System.setProperty("jme.stats", "set");
		 StandardGame standardGame = new StandardGame("GameControl", StandardGame.GameType.GRAPHICAL, null);
	     standardGame.getSettings().setVerticalSync(false);
		 standardGame.start();     

	        try {
				SkyBoxGameState.Manager().setActive(true);
				
				 CopyOfCopyOflevelTester nex = new CopyOfCopyOflevelTester(0,0);
			 	   GameStateManager.getInstance().attachChild(nex);
			 	    nex.setActive(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			gameSingleton.get().stop=true;

			 Charactertype PLAYER1 = new Charactertype("robot");		
			 PLAYER1.setActive(true);	
						 
			gameSingleton.get().stop=false;

				     
		     final DefineGameState base = new DefineGameState(); 
		     base.setActive(true);	

		   }

}
