package BatailleNavale;

public class Battleship {
	public static void main(String[] args) {
		boolean replay;
		Game.initGameMode();
		do {
			replay = Game.shot();
			if(!replay) {
				System.out.println("Rat� !");
				Game.changeCurrentPlayer();
			}else {
				System.out.println("Touch� ! Vous pouvez rejouer.");
			}
		}while(!Game.isOver());
	}
}