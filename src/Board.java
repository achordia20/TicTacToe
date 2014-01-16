

public class Board {
	private MoveType[][] board = new MoveType[3][3];
	private int moves = 0;

	public Board () {
		initialize();
	}
	
	private void initialize() {
		int size = board.length;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				board[row][col] = MoveType._;
			}
		}
	}
	
	public int length () {
		return board.length;
	}
	
	public void print() {
		int size = board.length;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				System.out.print(board[row][col]);
				System.out.print(" | ");
			}
			System.out.println();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < size; i++) {
				sb.append("--  ");
			}
			System.out.println(sb.toString());
		}
	}
	
	public boolean insert(TTTMove move) {
		if (move == null || move.pos > (board.length * board.length))
			return false;
		
		int pos = move.pos - 1;
		MoveType mt = move.type;
		
		int row = pos / board.length;
		int col = pos % board.length;
		
		if (mt == MoveType.X || mt == MoveType.O) {
			if (board[row][col] == MoveType._) {
				board[row][col] = move.type;
				moves++;
				return true;
			}
		}
		return false;
	}
	
	public void clear(int pos) {		
		pos = pos - 1;
		
		int row = pos / board.length;
		int col = pos % board.length;
		
		moves--;
		
		board[row][col] = MoveType._;
	}
	
	public boolean gameOver() {
		return moves == (board.length * board.length);
	}
	
	public boolean hasWinner() {
		if (atPos(1) != MoveType._) {
			if (atPos(2) == atPos(3) && (atPos(1) == atPos(2)))
				return true;
			if (atPos(4) == atPos(7) && (atPos(1) == atPos(4)))
				return true;
			if (atPos(5) == atPos(9) && (atPos(1) == atPos(5)))
				return true;
		}
		if (atPos(2) != MoveType._) {
			if (atPos(5) == atPos(8) && (atPos(2) == atPos(5)))
				return true;
		}
		
		if (atPos(3) != MoveType._) {
			if (atPos(6) == atPos(9) && (atPos(3) == atPos(6)))
				return true;
			if (atPos(5) == atPos(7) && (atPos(3) == atPos(5)))
				return true;
		}
		
		if (atPos(4) != MoveType._) {
			if (atPos(5) == atPos(6) && (atPos(4) == atPos(5)))
				return true;
		}
		
		if (atPos(7) != MoveType._) {
			if (atPos(8) == atPos(9) && (atPos(7) == atPos(8)))
				return true;
		}
		
		return false;
	}
	
	public MoveType atPos(int pos) {
		pos = pos - 1;
		int row = pos / board.length;
		int col = pos % board.length;
		return atPos(row, col);
	}
	
	public MoveType atPos(int row, int col) {
		return board[row][col];
	}
}
