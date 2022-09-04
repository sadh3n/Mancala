public class score {
	private String name;
	private int score;
	
	public score (String name, int score) {
		setName(name);
		setScore(score);
	}
	public void setName(String n) {
		name = n;
	}
	public void setScore(int s) {
		score = s;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
}
