package project2021;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
/*
 	* This class is used to get the data of a match, each match is represented as an object (MatchData)
 	* All MatchData objects is stored in an ArrayList named playerList.
*/
public class MatchData implements Serializable{
	private static final long serialVersionUID = 1L;
	private Date matchDate;
	private String opponent;
	private int ourScore;
	private int oppScore;
	private int totalGoals;			//this is a counter variable, which limit the total of goals typing from the user.
	public Date getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}
	public String getOpponent() {
		return opponent;
	}
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	public int getOurScore() {
		return ourScore;
	}
	public void setOurScore(int ourScore) {
		this.ourScore = ourScore;
	}
	public int getOppScore() {
		return oppScore;
	}
	public void setOppScore(int oppScore) {
		this.oppScore = oppScore;
	}
	public int getTotalGoals() {
		return totalGoals;
	}
	public void setTotalGoals(int totalGoals) {
		this.totalGoals = totalGoals;
	}
	public MatchData(Date dateOccur,String opponent,int ourScore, int oppScore,int totalGoals) {
		this.matchDate = dateOccur;
		this.ourScore = ourScore;
		this.oppScore = oppScore;
		this.totalGoals = totalGoals;
		//Create a new Exception Handling
		if(opponent.isBlank() || opponent.isEmpty()) {
			throw new NullPointerException("Some data is missing!");
		}
		else {
			this.opponent = opponent;
		}
	}
	private ArrayList<playerInfo> playerList = new ArrayList<>();
	public ArrayList<playerInfo> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(ArrayList<playerInfo> playerList) {
		this.playerList = playerList;
	}
	public void addPlayerScoreGoal(playerInfo player1) {
		playerList.add(player1);
	}
	//when we call this method, the number of goals which inputed by the user will be added here
	public int calculateTotalGoals(int g) {
		this.totalGoals += g;
		return this.totalGoals;
	}
	@Override
	public String toString() {
		StringBuilder strbuild = new StringBuilder();
		strbuild.append("Date: " + matchDate + "\nMatch: PSG - " + opponent + "\nResult: " + ourScore + "-" + oppScore);
		if(playerList.size() != 0) {
			strbuild.append("\nPlayer made a/some goals:\n");
			playerList.stream()
				.sorted(Comparator.comparing(playerInfo::getGoalsPerGame))
				.forEach(li -> strbuild.append(li.toString() + "\n"));
		}
		return strbuild.toString();
	}
}
