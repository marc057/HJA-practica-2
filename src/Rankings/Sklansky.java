package Rankings;

import java.util.ArrayList;
import java.util.List;

public class Sklansky {
	private static final int NumLabels = 13*13;
	static double[][] matrix = new double[13][13];	
	
	public Sklansky() {
		initSlansky();
	}
	private void initSlansky() {
		//                                xA   xK   xQ   xJ   xT   x9   x8   x7   x6   x5   x4   x3   x2
		/*Ax*/ matrix[0] = new double[] {999, 277, 137,  92,  70,  52,  45,  40,  35,  36,  33,  31,  29};
		/*Kx*/ matrix[1] = new double[] {166, 477,  43,  36,  31,  24,  20,  19,  17,  16,  15,  14,  13};
		/*Qx*/ matrix[2] = new double[] { 96,  29, 239,  25,  22,  16,  13,  11,  11,  10, 9.5, 8.9, 8.3};
		/*Jx*/ matrix[3] = new double[] { 68,  25,  16, 160,  18,  13,  10, 8.6, 7.4,   7, 6.5,   6, 5.6};
		/*Tx*/ matrix[4] = new double[] { 53,  23,  15,  12, 120,  11, 8.7, 7.1,   6,   5, 4.6, 4.2, 3.8};
		/*9x*/ matrix[5] = new double[] { 41,  18,  12, 8.9, 7.4,  96, 7.6, 6.1,   5, 4.1, 3.3,   3, 2.7};
		/*8x*/ matrix[6] = new double[] { 36,  15, 9.9, 7.4, 6.1, 5.1,  80, 5.6, 4.5, 3.6, 2.8, 2.2, 2.1};
		/*7x*/ matrix[7] = new double[] { 31,  14, 8.5, 6.3, 5.1, 4.3, 3.8,  67, 4.2, 3.3, 2.6,   2, 1.6};
		/*6x*/ matrix[8] = new double[] { 28,  13, 8.1, 5.4, 4.3, 3.5,   3, 2.7,  58, 3.1, 2.4, 1.9, 1.5};
		/*5x*/ matrix[9] = new double[] { 28,  12, 7.5,   5, 3.5, 2.8, 2.4, 2.1,   2,  49, 2.4, 1.9, 1.6};
		/*4x*/ matrix[10] = new double[] { 26, 11, 6.8, 4.5, 3.1, 2.2, 1.9, 1.7, 1.6, 1.6,  41, 1.7, 1.4};
		/*3x*/ matrix[11] = new double[] { 24, 11, 6.3,   4, 2.7,   2, 1.5, 1.4, 1.3, 1.3, 1.2,  33, 1.3};
		/*2x*/ matrix[12] = new double[] { 23, 10, 5.7, 3.4, 2.4, 1.8, 1.4, 1.1, 1.1, 1.1,   1, 0.9,  24};
	}
	public static List<Double> matrixToList(){
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
