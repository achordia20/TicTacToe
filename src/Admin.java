

import java.util.InputMismatchException;
import java.util.Scanner;


public class Admin {

	public static void main(String[] args) {
		MoveType userMove = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Tic-Tac-Toe!");
		
		Board b = new Board();
		
		TicTacToeAI ai = null;
		
		String moveType = null;
		while (moveType == null) {
			System.out.print("Please choose your move type. Player X will always go first [X / O]: ");
			try {
				moveType = sc.next();
				moveType = moveType.toUpperCase();
				if (moveType.equals(MoveType.O.toString())) {
					userMove = MoveType.O;
					ai = new TicTacToeAI(b, MoveType.X);
					b.insert(ai.move());
				} else if (moveType.equals(MoveType.X.toString())) {
					userMove = MoveType.X;
					ai = new TicTacToeAI(b, MoveType.O);
				} else {
					System.out.println("Please select a valid move type!");
					moveType = null;
				}
			} catch (InputMismatchException ex) {
				System.out.println("Please select a valid move type!");
			}
		}
		
		b.print();
		
		Integer pos = null;
		while(true) {
			while (pos == null) {
				System.out.print("What's your move? [1-9] : ");
				try {
					pos = sc.nextInt();
					if (! b.insert(new TTTMove(userMove, pos))) {
						System.out.println("Please enter a valid position!");
						pos = null;
					}
				} catch (InputMismatchException ex) {
					System.out.println("Please enter a valid position!");
					sc.next();
				}
			}
			
			if (b.hasWinner()) {
				b.print();
				System.out.println("User has won!");
				break;
			} else if (b.gameOver()) {
				b.print();
				System.out.println("Game Over - Tie");
				break;
			}
				
			
			if (!b.insert(ai.move())) {
				b.print();
				System.out.println("Game is a Tie");
				break;
			}
			if (b.hasWinner()) {
				b.print();
				System.out.println("User has lost!");
				break;
			} else if (b.gameOver()) {
				b.print();
				System.out.println("Game Over - Tie");
				break;
			}
			
			b.print();
			pos = null;
		}
	}
}
