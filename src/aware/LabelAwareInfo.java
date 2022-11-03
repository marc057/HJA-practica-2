package aware;

import java.util.ArrayList;
import java.util.List;

public class LabelAwareInfo {
	private static LabelAwareInfo instance = null;
	
	public static LabelAwareInfo getInstance() {
		if (instance == null) { instance = new LabelAwareInfo(); }
		return instance;
	}
	
	
	private static List<LabelAware> observers = new ArrayList<>();
}
