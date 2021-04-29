package project2021;
import java.io.Serializable;
//This class is used to get player's data in each match, if he/she score goal(s)
public class playerInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int goals;
	private int playerNum;
	private String playerName;
	public int getPlayerNum() {
		return playerNum;
	}
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public playerInfo(int playerNum, String playerName, int goals) {
		this.playerNum = playerNum;
		this.playerName = playerName;
		this.goals = goals;
	}
	public int getGoalsPerGame() {
		return goals;
	}
	@Override
	public String toString() {
		return playerNum + " - " + playerName + " - Total of goals: " + goals;
	}
}
