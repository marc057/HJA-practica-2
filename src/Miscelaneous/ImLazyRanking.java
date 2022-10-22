package Miscelaneous;

import java.util.Scanner;


//Created this quickly to not need to bopy by hand all the values in sklansky matrix
public class ImLazyRanking {
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in); 
		String total = "";
		String thisLine;
		int col = 0;
		int row = 0;
		
		while (row < 13 && ((thisLine = sc.nextLine()) != null && !thisLine.equals("a"))) {
			//Beginning of the row text
			if (col == 0) { total += "/*" + Constants.CardNumbers.get(row) + "x*/ matrix[" + row + "] = new double[] {"; } 
			
			
			thisLine = sc.nextLine().strip(); //Ignore the line with the cards
			int thisLineLen = thisLine.length();
			for (int i = 0; i < (3-thisLineLen); i++) { //format to that always use 3 spaces
				thisLine = " " + thisLine;
			}
			
			total += thisLine + (col < 12 ? ", " : "");//Add separation commas
			
			col++;
			
			if (col == 13) { //If end of row, jump to next
				col = 0; row++;
				total += "};\n";
			}
		}
		
		System.out.print(total);
		sc.close();
	}
}
