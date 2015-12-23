import java.io.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.User;
import twitter4j.ResponseList;
import twitter4j.Paging;

class typing{
	public static void main(String[] args){

		Twitter twitter = new TwitterFactory().getInstance();

		/*
		Words word = new Words();
		Game game = new Game(word);

		long startTime = System.nanoTime();
		game.start();
		long endTime = System.nanoTime();
		long time = endTime - startTime;

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try{
			System.out.println("input your name");
			String name = reader.readLine();
			Player player = new Player(name, time);

			System.out.println("time: " + player.getTime());

		}catch(IOException e){
			System.out.println("error");
		}
		*/

	}
}

////////////////////////////////////////

class Words{
	String words_array[] = new String[10];
	static boolean[] array = new boolean[10];

	Words(){
		words_array[0] = "hello";
		words_array[1] = "world";
		words_array[2] = "static";
		words_array[3] = "public";
		words_array[4] = "extends";
		words_array[5] = "hoge";
		words_array[6] = "class";
		words_array[7] = "array";
		words_array[8] = "int";
		words_array[9] = "String";

		for(int i = 0; i < array.length; i++){
			array[i] = true;
		}
	}

	Words(String word, int i){
		words_array[i] = word;

		array[i] = true;
	}

	String getWord(){
		while(true){
			int rand = (int)(Math.random() *  10);
			if(array[rand] == true){
				array[rand] = false;
				return words_array[rand];
			}
		}
	}

	void makeArrayTrue(String word){
		for(int i = 0; i < words_array.length; i++){
			if(word.equals(words_array[i])){
				array[i] = true;
			}
		}	
	}

}

class TweetWords extends Words{

}


class Game{
	Words word;

	Game(Words word){
		this.word = word;
	}

	void start(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try{
			System.out.println("type 10 words!");

			int i = 0;
			while(i < 10){
				String vocab = word.getWord();

				System.out.println(vocab);

				String input = reader.readLine();

				if(input.equals(vocab)){
					i++;
					System.out.println("ok");
				}else{
					word.makeArrayTrue(vocab);
					System.out.println("miss type");
				}

			}

		}catch(IOException e){
			System.out.println("error");
		}

	}
}


class Player{
	String name;
	long time; 

	Player(String name, long time){
		this.name = name;
		this.time = time;
	}

	String getName(){
		return name;
	} 

	long getTime(){
		return time;
	}

}

class Rank{
	Player[] rank = new Player[10];

	Rank(Player[] rank){
		this.rank = rank;	
	}

	Player[] getRank(){
		return rank;	
	}

}


