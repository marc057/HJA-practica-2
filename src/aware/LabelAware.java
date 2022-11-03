package aware;

public interface LabelAware {
	public default void addObserver(LabelAware observer) {
		LabelAwareInfo.getInstance().addObserver(observer);
	}
	public default void addObservable(LabelAware observable) {
		LabelAwareInfo.getInstance().addObservable(observable);
	}
	public void sendSelectionChanged();
	public void receiveSelectionChanged();
}                                      
