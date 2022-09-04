import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class leaderboard {
	private ArrayList<score> leaderboard;
	
	public leaderboard(){
		leaderboard = new ArrayList();
		Scanner read;
		try {
		read = new Scanner(new File("highscore.txt"));
		while (read.hasNext()) {
			read.nextInt();
			leaderboard.add(new score(read.next(),read.nextInt()));
		}
		if (read != null){
			read.close();
			}
		} 
	catch (FileNotFoundException fe){
		System.out.println("File does not exist.");
		}
	catch (NoSuchElementException ex){
		System.out.println("File improperly formed.");
	}
	}
	
	public void storeIntoLeaderboard(String name, int score) {
		if (score > leaderboard.get(1).getScore() && score > leaderboard.get(0).getScore()){	
			leaderboard.remove(2);
			leaderboard.add(0,(new score(name, score)));
		}
		else if (score > leaderboard.get(1).getScore() && score < leaderboard.get(0).getScore()) {
			leaderboard.remove(2);
			leaderboard.add(1,(new score(name, score)));
		}
		else if (score < leaderboard.get(1).getScore() && score < leaderboard.get(2).getScore()) {
			leaderboard.remove(2);
			leaderboard.add(2,(new score(name, score)));
		}
		else if (score == leaderboard.get(1).getScore()) {
			leaderboard.remove(2);
			leaderboard.add(2,(new score(name, score)));

		 }else if (score == leaderboard.get(0).getScore()){
			leaderboard.remove(1);
			leaderboard.add(1,(new score(name, score)));
		}
	}
	
	
	public void showHighscore() {
		Scanner read;
		try{
			read = new Scanner(new File("highscore.txt"));
			while (read.hasNext()){
				System.out.printf("%-10d%-12s%10d\n", read.nextInt(),
				read.next(), read.nextInt());
				}
			if (read != null){
				read.close();
				}
			} 
		catch (FileNotFoundException fe){
			System.out.println("File does not exist.");
			}
		catch (NoSuchElementException ex){
			System.out.println("File improperly formed.");
			}

	}
	public void StoreHighscore() {
		Formatter output;
		try{
			output = new Formatter("highscore.txt");
			for (int i=0; i<3;i++) {
			output.format("%d %s %d\n", i+1, leaderboard.get(i).getName(), leaderboard.get(i).getScore());
			}
			if (output!= null)
				{
				output.close();
				}
		} 
		catch (SecurityException se)
			{
			System.out.println("You do not have write access");
			System.exit(1);
			} 
		catch (FileNotFoundException fe)
			{
			System.out.println("Error opening/creating file.");
			System.exit(1);
			}

	}
}

