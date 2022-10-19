package Miscelaneous;

import java.util.Scanner;


//Created this quickly to not need to bopy by hand all the values in sklansky matrix
public class ImLazyRanking {
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in); 
		String thisLine = sc.nextLine();
		String total = "";
		int count = 0;
		while (!thisLine.equals("a")) {
			thisLine = sc.nextLine(); //Ignore the line with the cards
			thisLine = thisLine.strip();
			total += thisLine + ", ";
			thisLine = sc.nextLine();
			count++;
			if (count == 13) { count = 0; total += "\n"; }
		}
		
		System.out.print(total);
	}
}
