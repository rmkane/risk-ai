import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class TestPopup extends JFrame {
	public TestPopup() {
		JLabel l = new JLabel();
		this.add(l);
		
		int sourceArmies = 15;		
		Object[] vals = new Object[sourceArmies-1];
		for (int i = 0; i < vals.length; i++) vals[i] = i+1;
		String val = (String)JOptionPane.showInputDialog(l,
		                    "Transfer Amount", "Fortify",
		                    JOptionPane.QUESTION_MESSAGE,
		                    null, vals, "1");
		if (val != null && Integer.parseInt(val) > 0) {
		    System.out.println(val);
		}
	}
	
	public static void main(String[] args) {
		new TestPopup();
	}
}
