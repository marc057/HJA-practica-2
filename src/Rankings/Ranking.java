package Rankings;

import java.util.ArrayList;
import java.util.List;

public abstract class Ranking {
	private static final int NumLabels = 13*13;
	static double[][] matrix = new double[13][13];
	
	protected Ranking() {
		initMatrix();
	}
	
	protected abstract void initMatrix();
	
	public List<Double> matrixToList(){
		List<Double> list = new ArrayList<>();
		
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 13; j++) {
				list.add(matrix[i][j]);
			}
		}
		
		return list;
		
	}
	public double getNum(int i, int j) {
		return matrix[i][j];
	}
}
