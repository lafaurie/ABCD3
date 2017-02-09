package cu.uci.abos.widget.template.util;

public class TabItemRange {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private int tag;

	public TabItemRange(int tag){
		this.tag = tag;
	}

	public int calculateTabItem(){	
		return tag / 100;						
	}    
}
