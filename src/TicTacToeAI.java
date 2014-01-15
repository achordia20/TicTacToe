
import java.util.ArrayList;


public class TicTacToeAI {
	Board board;
	MoveType moveType;
	MoveType theirMoveType;
	
	Boolean placeInCorner = false;
	
	public TicTacToeAI(Board b, MoveType mT) {
		this.board = b;
		this.moveType = mT;
		this.theirMoveType = mT.other();
	}
	
	public TTTMove move() {
		MinMaxData data = minmax(2, moveType);
		return new TTTMove(moveType, data.pos);
	}
	
	private ArrayList<Integer> getOpenMoves() {
		ArrayList<Integer> open = new ArrayList<Integer>();
		
		if (!board.hasWinner()) {
			for (int i = 1; i < 10; i++) {
				if (board.atPos(i) == MoveType._)
					open.add(i);
			}
		}
		return open;
	}
	
	private MinMaxData minmax(int depth, MoveType type) {
		ArrayList<Integer> openMoves = getOpenMoves();
		
		MinMaxData best = new MinMaxData((type == moveType) ? Integer.MIN_VALUE : Integer.MAX_VALUE);
		
		if (openMoves.isEmpty() || depth == 0) {
			best.score = getBoardScore();
		} else {
			int currentScore;
			for (Integer move : openMoves) {
				board.insert(new TTTMove(type, move));
				
				if (type == moveType) {
					currentScore = minmax(depth - 1, moveType.other()).score;
					if (currentScore > best.score) {
						best.score = currentScore;
						best.pos = move;
					}
				} else {
					currentScore = minmax(depth - 1, moveType).score;
					if (currentScore < best.score) {
						best.score = currentScore;
						best.pos = move;
					}
				}
				
				board.clear(move);
			}
		}
		return best;
	}
	
	private int getBoardScore() {
		int score = 0;
		score += calculateScore(1, 2, 3);
		score += calculateScore(4, 5, 6);
		score += calculateScore(7, 8, 9);
		score += calculateScore(1, 4, 7);
		score += calculateScore(2, 5, 8);
		score += calculateScore(3, 6, 9);
		score += calculateScore(1, 5, 9);
		score += calculateScore(3, 5, 7);
		return score;
	}
	
	private int calculateScore(int pos1, int pos2, int pos3) {
		MoveType m1 = board.atPos(pos1);
		MoveType m2 = board.atPos(pos2);
		MoveType m3 = board.atPos(pos3);
		
		if (m1 == moveType) {
			if (m2 == theirMoveType || m3 == theirMoveType)
				return 0;
			
			if (m2 == moveType && m3 == moveType)
				return 100;
			else if (m2 == moveType || m3 == moveType)
				return 10;
			else
				return 1;
		} else if (m1 == theirMoveType) {
			if (m2 == moveType || m3 == moveType)
				return 0;
			
			if (m2 == theirMoveType && m3 == theirMoveType)
				return -100;
			else if (m2 == theirMoveType || m3 == theirMoveType)
				return -10;
			else
				return -1;
		} else {
			if (m2 == moveType) {
				if (m3 == theirMoveType)
					return 0;
				
				if (m3 == moveType)
					return 10;
				return 1;
			} else if (m2 == theirMoveType) {
				if (m3 == moveType)
					return 0;
				
				if (m3 == theirMoveType)
					return -10;
				return -1;
			} else {
				if (m3 == moveType)
					return 1;
				else if (m3 == theirMoveType)
					return -1;
				return 0;
			}
		}
	}
}
