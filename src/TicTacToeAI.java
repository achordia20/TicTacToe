
import java.util.ArrayList;


public class TicTacToeAI {
	Board board;
	MoveType myMoveType;
	MoveType theirMoveType;
	
	Boolean placeInCorner = false;
	
	public TicTacToeAI(Board b, MoveType type) {
		this.board = b;
		this.myMoveType = type;
		this.theirMoveType = type.other();
	}
	
	public TTTMove move() {
		MinMaxData data = minmax(2, myMoveType);
		return new TTTMove(myMoveType, data.pos);
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
		
		MinMaxData best = new MinMaxData((type == myMoveType) ? Integer.MIN_VALUE : Integer.MAX_VALUE);
		
		if (openMoves.isEmpty() || depth == 0) {
			best.score = getBoardScore();
		} else {
			int currentScore;
			for (Integer move : openMoves) {
				board.insert(new TTTMove(type, move));
				
				if (type == myMoveType) {
					currentScore = minmax(depth - 1, myMoveType.other()).score;
					if (currentScore > best.score) {
						best.score = currentScore;
						best.pos = move;
					}
				} else {
					currentScore = minmax(depth - 1, myMoveType).score;
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
		
		if (m1 == myMoveType) {
			return getScoreForRow(myMoveType, m1, m2, m3);
		} else if (m1 == theirMoveType) {
			return getScoreForRow(theirMoveType, m1, m2, m3);
		} else {
			return calculateFromEmptyMoveType(m1, m2, m3);
		}
	}
	
	private int getScoreForRow(MoveType base, MoveType m1, MoveType m2, MoveType m3) {
		if (base.other() == m2 || base.other() == m3)
			return 0;
		
		if (m2 == base && m3 == base)
			return (base == myMoveType) ? 100 : -100;
		else if (m2 == base || m3 == base)
			return (base == myMoveType) ? 10 : -10;
		else
			return (base == myMoveType) ? 1 : -1;
	}
	
	private int calculateFromEmptyMoveType(MoveType m1, MoveType m2, MoveType m3) {
		if (m2 == myMoveType) {
			if (m3 == theirMoveType)	// both moveTypes there
				return 0;
			
			if (m3 == myMoveType)	// 2/ 3 same as AI
				return 10;
			return 1;	// only one same as AI
		} else if (m2 == theirMoveType) {
			if (m3 == myMoveType)			// both moveTypes there
				return 0;
			
			if (m3 == theirMoveType)	// 2/3 same as user
				return -10;
			return -1;		//only one same as user
		} else {
			if (m3 == myMoveType)	// only one same as AI
				return 1;
			else if (m3 == theirMoveType)	// only one same as user
				return -1;
			return 0;	// all blank
		}
	}
}
