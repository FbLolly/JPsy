package utilsPkg;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import mainPkg.Defines;

//implements an easier way to handle keys
public class KeyHandler {
	public HashMap<String, Integer> KeyMap;
	
	public KeyHandler() {
		KeyMap = new HashMap<String, Integer>();

		for (char i = 'A'; i <= 'Z'; i++){
			KeyMap.put(i + "", 0);
		}

		for (char i = '0'; i <= '9'; i++){
			KeyMap.put(i + "", 0);
		}

		KeyMap.put("BACK_SPACE", 0);
		KeyMap.put("ENTER", 0);
		KeyMap.put("ESCAPE", 0);
		KeyMap.put("SHIFT", 0);
		KeyMap.put("SPACE", 0);
	}
	
	private void buildKeyMap(JPanel panel){
		for (char i = 'A'; i <= 'Z'; i++){
			panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke(i + ""), i + "");
			panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("released " + i + ""), "released " + i + "");
		}
		for (char i = '0'; i <= '9'; i++){
			panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke(i + ""), i + "");
			panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("released " + i + ""), "released " + i + "");
		}
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("BACK_SPACE"), "BACK_SPACE");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("released BACK_SPACE"), "released BACK_SPACE");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("ENTER"), "ENTER");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("released ENTER"), "released ENTER");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("released ESCAPE"), "released ESCAPE");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("SHIFT"), "SHIFT");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("released SHIFT"), "released SHIFT");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("SPACE"), "SPACE");
		panel.getInputMap(Defines.IFW).put(KeyStroke.getKeyStroke("released SPACE"), "released SPACE");


		for (char i = 'A'; i <= 'Z'; i++){
			panel.getActionMap().put(i + "", new KeyPress(i + ""));
			panel.getActionMap().put("released " + i + "", new KeyRelease(i + ""));
		}
		for (char i = '0'; i <= '9'; i++){
			panel.getActionMap().put(i + "", new KeyPress(i + ""));
			panel.getActionMap().put("released " + i + "", new KeyRelease(i + ""));
		}
		panel.getActionMap().put("BACK_SPACE", new KeyPress("BACK_SPACE"));
		panel.getActionMap().put("released BACK_SPACE", new KeyRelease("BACK_SPACE"));
		panel.getActionMap().put("ENTER", new KeyPress("ENTER"));
		panel.getActionMap().put("released ENTER", new KeyRelease("ENTER"));
		panel.getActionMap().put("ESCAPE", new KeyPress("ESCAPE"));
		panel.getActionMap().put("released ESCAPE", new KeyRelease("ESCAPE"));
		panel.getActionMap().put("SHIFT", new KeyPress("SHIFT"));
		panel.getActionMap().put("released SHIFT", new KeyRelease("SHIFT"));
		panel.getActionMap().put("SPACE", new KeyPress("SPACE"));
		panel.getActionMap().put("released SPACE", new KeyRelease("SPACE"));
	}

	public void handleKeys(JPanel panel){
		buildKeyMap(panel);
	}
	

	public class KeyPress extends AbstractAction{
		String key;
		
		public KeyPress(String key){
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			KeyMap.replace(key, 1);
		}
	}

	public class KeyRelease extends AbstractAction{
		String key;
		
		public KeyRelease(String key){
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			KeyMap.replace(key, 0);
		}
	}


	@Override
	public String toString(){
		String ret = "";

		for (String key : this.KeyMap.keySet()){
			ret += "key: " + key + "; value: " + this.KeyMap.get(key) + ";\n";
		}

		return ret;
	}
}

















