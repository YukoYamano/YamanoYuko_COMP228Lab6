package exercise1;

public class Game {
	private String gameID,gameTitle;
	String record[] = new String[2]; //holds the data for the current record
	private GameData gameData = null;
	
	//no-argument constructor
		public Game() throws Exception{
			gameData = new GameData();
			// load the current record
			record=gameData.loadCurrentRecord("Select * from game");
			//populate the instance variables
			gameID = record[0];
			gameTitle=record[1];
			
		}
		
		public Game(String gameID, String gameTitle)
		{
			 this.gameID=gameID;
			 this.gameTitle=gameTitle;
		}
		
		public String getGameID()
		{
			return gameID;
		}
		public String getGameTitle()
		{
			return gameTitle;
		}
		
		public void setGameTitle(String gameTitle)
		{
			this.gameTitle=gameTitle;
		}
		
		public String[] moveToNext()
		{
			 record=gameData.moveNext();
			 refreshGameData(record);
			 return record;
		}
		public String[] moveToLast()
		{
			 record=gameData.moveLast();
			 //refresh customer's data with current record's data
			 refreshGameData(record);
			 return record;
		}
		public String[] moveToPrevious()
		{
			 record=gameData.movePrevious();
			 refreshGameData(record);
			 return record;
		}
		public String[] moveToFirst()
		{
			 record=gameData.moveFirst();
			 refreshGameData(record);
			 return record;
		}
		// - database manipulation methods
		public void saveStudent(String[] row)
		{
			refreshGameData(row);
			 gameData.saveRow(row);
		}
		public void updateGame(String[] row)
		{
			refreshGameData(row);
			 gameData.updateRow(row);
		}
		public void deleteGame()
		{
			gameData.deleteRow();
		}
		public void refreshGameData(String data[])
		{
			 this.gameID=data[0];
			 this.gameTitle=data[1];
			
		}
		//Add display function
		public void display() 
		{
			//*************************************
			System.out.println("Game's DisplayFunction was called");
			
		}
		
}//end of Game class
