

public enum MoveType {
	X("X"),
	O("O"),
	_(" ");
	
	private String val;
	
	private MoveType(String s) {
		this.val = s;
	}
	
	public MoveType other() {
		if (this == MoveType.O)
			return MoveType.X;
		if (this == MoveType.X)
			return MoveType.O;
		return null;
	}
}
