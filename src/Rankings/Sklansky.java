package Rankings;


public class Sklansky {
	private static final int NumLabels = 13*13;
	double[][] matrix = new double[13][13];	
	
	public Sklansky() {
		initSlansky();
	}
	private void initSlansky() {
		//Fila 1
		matrix[0][0] = 999;
		matrix[0][1] = 277;
		matrix[0][2] = 137;
		matrix[0][3] = 92;
		matrix[0][4] = 70;
		matrix[0][5] = 52;
		matrix[0][6] = 45;
		matrix[0][7] = 40;
		matrix[0][8] = 35;
		matrix[0][9] = 36;
		matrix[0][10] = 33;
		matrix[0][11] = 31;
		matrix[0][12] = 29;
		//Fila 2 FALTA PONER LOS VALORES DE TODAS LAS FILAS
		matrix[1][0] = 999;
		matrix[1][1] = 277;
		matrix[1][2] = 137;
		matrix[1][3] = 92;
		matrix[1][4] = 70;
		matrix[1][5] = 52;
		matrix[1][6] = 45;
		matrix[1][7] = 40;
		matrix[1][8] = 35;
		matrix[1][9] = 36;
		matrix[1][10] = 33;
		matrix[1][11] = 31;
		matrix[1][12] = 29;
		
	}
	
	public double getNum(int i, int j) {
		return matrix[i][j];
	}
}
