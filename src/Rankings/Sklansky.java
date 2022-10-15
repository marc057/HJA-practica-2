package Rankings;


public class Sklansky {
	private static final int NumLabels = 13*13;
	double[][] matrix = new double[13][13];	
	
	public Sklansky() {
		initSlansky();
	}
	private void initSlansky() {
		matrix[0][0] = 999;
		
	}
	
	public double getNum(int i, int j) {
		return matrix[i][j];
	}
}
