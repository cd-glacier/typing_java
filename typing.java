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
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class typing{
	public static void main(String[] args){
			  
		//Words words = new Words();
		TweetWords words = new TweetWords();
		Rank rank = new Rank();
		Game game = new Game(words);
		
		
		long startTime = System.nanoTime();
		game.start();
		long endTime = System.nanoTime();
		long time = endTime - startTime;
		rank.setRank(time);

		

	}
}

////////////////////////////////////////

//tweetでtypingしない場合のデフォルトのwords
class Words{
	int num = 5;
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
			int rand = (int)(Math.random() *  num);
			if(array[rand] == true){
				array[rand] = false;
				return words_array[rand];
			}
		}
	}

	//arrayの中をTrueにするだけ
	void makeAllTrue(){
		for(int i = 0; i < num; i++){
			array[i] = true;
		}
	}
	
	//typeミスした単語をtrueに戻すメソッド、なんか汚い
	void makeArrayTrue(String word){
		for(int i = 0; i < num; i++){
			if(word.equals(words_array[i])){
				array[i] = true;
			}
		}	
	}

}

//tweetを取得、黒歴史度の高いtweetをset
class TweetWords extends Words{
	int num = 5;
	List<Status> statuses;
	//words_arrayとarrayを対応付けてる
	//String words_array[] = new String[10];
	//boolean[] array = new boolean[10];

	//コンストラクタ、本当は公開しちゃいけない(捨て垢作った)
	TweetWords(){
		super();
		try{
			Twitter twitter = TwitterFactory.getSingleton();
			twitter.setOAuthConsumer("qCwq7EbWWGileUmQXBAtpWZV6","9GEtTKe202apuu9q4yQ1zn3gNTu9dMphjItGSXJwwAt8jJyVUz");
			AccessToken accessToken = new AccessToken("4653308594-jEvwFBMEP9Cr1oTzUFspSN1qxRn7dkXjwfRNafL","FoYAdnROch4ZscWrHbnhu9stZFyBbsTIZ2psawuGivybb");		 
			twitter.setOAuthAccessToken(accessToken);
			User user = twitter.verifyCredentials();
			statuses = twitter.getUserTimeline();
			
			
			int i = 0;
			for(Status status : statuses){
				String str = status.getText();
				// ハッシュタグとURLの削除	
				StringTokenizer sta = new StringTokenizer(str, " ");
				//トークンの出力
				while(sta.hasMoreTokens()) {
					String wk = sta.nextToken();
					if(wk.indexOf("#") == -1 && wk.indexOf("http") == -1
							&& wk.indexOf("RT") == -1 && wk.indexOf("@") == -1 
							&& wk.indexOf("\n") == -1 && wk.indexOf("(") == -1
							&& wk.indexOf(")") == -1 ){
						words_array[i] = wk;
					}
				}
				
				i++;
				if(i == (num - 1)){
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
			int rand = (int)(Math.random() *  num);
			if(array[rand] == true){
				array[rand] = false;
				return words_array[rand];
			}
			
		}
		
	}
	

}

//ゲーム関係のクラス
class Game{
	int num = 5;
	Words word;

	Game(Words word){
		this.word = word;
	}

	void start(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try{
			System.out.println("type "+ String.valueOf(num) + " words!");
			System.out.println("when you input \'start\', game start!");
			
			if(reader.readLine().equals("start")){
				int i = 0;
				while(i < num){
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

			}else{
				
				
				
			}
		}catch(IOException e){
			System.out.println("error");
		}

	}
}

//プレイヤーのnameとtimeとひとまとめにしたいだけ
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
	
	Rank(){	
		
	}

	//MySQLに接続して、name,timeをinsertする.
	void setRank(long time){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try{
			MySQL db = new MySQL();
			System.out.println("input your name");
			String name = reader.readLine();
			Player player = new Player(name, time);
			
			db.insertDB(name, time);
	
			//System.out.println("time: " + player.getTime());
	
		}catch(IOException e){
			System.out.println("error");
		}
	}

}


class MySQL{
	Connection con = null;
	
	MySQL(){
        try {
            // JDBCドライバのロード
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // MySQLに接続
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/typing", "root", "");
            System.out.println("MySQLに接続できました。");
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("JDBCドライバのロードに失敗しました。");
        } catch (SQLException e) {
            System.out.println("MySQLに接続できませんでした。");
        }
        /*finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("MySQLのクローズに失敗しました。");
                }
            }
        }
        */
		
	}
	
	void insertDB(String name,long time){
		try{
			Statement stm = con.createStatement();	
	        String sql = "insert into players values(" + name + ',' + String.valueOf(time) + ')';
	        int result = stm.executeUpdate(sql);	//errorrrrrrrrrrrrrrr
	        System.out.println("更新件数は" + result + "です。");
		}catch(SQLException e){
			System.out.println("MySQLに接続できませんでした。");
		}
	}
	
	
}

