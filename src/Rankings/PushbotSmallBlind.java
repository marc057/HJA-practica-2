package Rankings;

public class PushbotSmallBlind extends Ranking {

	@Override
	protected void initMatrix() {
		/*Ax*/ matrix[0] = new double[] { 50,  50,  50,  50,  50,  50,  50,  50,  50,  50,  50,  50,  48};
		/*Kx*/ matrix[1] = new double[] { 50,  50,  50,  50,  50,  50,  50,  49,  36,  32,  26,  20,  19};
		/*Qx*/ matrix[2] = new double[] { 50,  50,  50,  50,  50,  50,  50,  20,  29,  24,  16,  14,  13};
		/*Jx*/ matrix[3] = new double[] { 50,  50,  50,  50,  50,  50,  50,  32,  19,  16,  14,  11, 8.8};
		/*Tx*/ matrix[4] = new double[] { 50,  50,  45,  46,  50,  50,  50,  36,  25,  12,  11, 7.7, 6.5};
		/*9x*/ matrix[5] = new double[] { 45,  24,  24,  29,  32,  50,  50,  36,  27,  14, 6.9, 4.9, 3.7};
		/*8x*/ matrix[6] = new double[] { 43,  19,  13,  14,  18,  21,  50,  43,  31,  19,  10, 2.7, 2.5};
		/*7x*/ matrix[7] = new double[] { 41,  16,  10, 8.5, 9.9,  11,  16,  50,  36,  24,  14, 2.5, 2.1};
		/*6x*/ matrix[8] = new double[] { 35,  15, 9.8, 6.5, 5.7, 5.2, 7.1,  11,  50,  29,  16, 7.1,   2};
		/*5x*/ matrix[9]  = new double[]{ 37,  14, 8.9,   6, 4.1, 3.5,   3, 2.6, 2.4,  50,  24,  13,   2};
		/*4x*/ matrix[10] = new double[]{ 35,  13, 8.3, 5.4, 3.8, 2.7, 2.3, 2.1,   2, 2.1,  50,  10, 1.8};
		/*3x*/ matrix[11] = new double[]{ 32,  13, 7.5,   5, 3.4, 2.5, 1.9, 1.8, 1.7, 1.8, 1.6,  50, 1.7};
		/*2x*/ matrix[12] = new double[]{ 29,  12,   7, 4.6,   3, 2.2, 1.8, 1.6, 1.5, 1.5, 1.4, 1.4,  50};
	}
}
