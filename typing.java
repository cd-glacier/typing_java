package typing;

import java.io.*;
import java.util.*;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.User;
import twitter4j.ResponseList;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
	

class typing{
	public static void main(String[] args){
			  
		//Words words = new Words();
		TweetWords words = new TweetWords();
		Game game = new Game(words);
		
		//System.out.println(words.getWord());

		
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
		

	}
}

////////////////////////////////////////

//tweetでtypingしない場合のデフォルトのwords
class Words{
	
	//words_arrayとarrayを対応付けてる
	String words_array[] = new String[10];
	boolean[] array = new boolean[10];

	//コンストラクタ:デフォルトの単語をset
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

		makeAllTrue();
	}

	//アクセスメソッド:フィールドの単語を一個ずつ表示、使った単語をfalseに
	String getWord(){
		while(true){
			int rand = (int)(Math.random() *  10);
			if(array[rand] == true){
				array[rand] = false;
				return words_array[rand];
			}
		}
	}

	//arrayの中をTrueにするだけ
	void makeAllTrue(){
		for(int i = 0; i < array.length; i++){
			array[i] = true;
		}
	}
	
	//typeミスした単語をtrueに戻すメソッド、なんか汚い
	void makeArrayTrue(String word){
		for(int i = 0; i < words_array.length; i++){
			if(word.equals(words_array[i])){
				array[i] = true;
			}
		}	
	}

}

//tweetを取得、黒歴史度の高いtweetをset
class TweetWords extends Words{
	List<Status> statuses;

	//コンストラクタ、本当は公開しちゃいけない
	TweetWords(){
		super();
		try{
			Twitter twitter = TwitterFactory.getSingleton();
			twitter.setOAuthConsumer("1t6XCLxeRICJUl692UaP7CaNV","pmzXyUdaj2MYtzorJ1RHWHibqUwdZi44yB2P9Q7GF3sXM7vRus");
			AccessToken accessToken = new AccessToken("2406573272-MJo4QovPMpdiFpO0U3jCLSWOv5KGV9vR507zfZI","u5im8xJhDvIm20boY0HcWgFH2cQdZMUcTUHOGznGv7kkZ");		 
			twitter.setOAuthAccessToken(accessToken);
			User user = twitter.verifyCredentials();
			statuses = twitter.getUserTimeline();
			
			
			int i = 0;
			for(Status status : statuses){
				words_array[i] = status.getText();
				i++;
				if(i == 9){
					break;
				}
			}
			
			makeAllTrue();
			
			
		}catch(TwitterException e){
			System.out.println("twitter error");
		}
	}
	
	//黒歴史度の高いtweetを10個取得
	@Override
	String getWord(){
		while(true){
			int rand = (int)(Math.random() *  10);
			if(array[rand] == true){
				array[rand] = false;
				return words_array[rand];
			}
			return "hoge";
			
		}
		
	}
	

}

//ゲーム関係のクラス
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

//プレイヤーのtimeとかを保存したいコンストラクタ
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


