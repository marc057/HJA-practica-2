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
	
	public void addObserver(LabelAware observer) {
		observers.add(observer);
	}
	


	LabelAwareInfo.getInstance().addObserver(observer);
}
public default void addObservable(LabelAware observable) {
	LabelAwareInfo.getInstance().addObservable(observable);
}
