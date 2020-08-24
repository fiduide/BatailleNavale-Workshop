package BatailleNavale;
import java.util.Random;
import java.util.Scanner;
public class Game {
	private static Player player1;
	private static Player player2;
	private static Scanner scan = new Scanner(System.in);
	private static Player robot;
	private static Player currentPlayer;
	private static Player opponent;
	private static int  difficulty;
	private static int mode = 1; //1 player, 2 players
	private static void fakeInitPlayerGrid(Player p) {
		p.addNewShip(0,0, 5, 0);
		p.addNewShip(5,3, 4, 1);
		p.addNewShip(9,6, 3, 0);
		p.addNewShip(2,9, 2, 1);
		p.addNewShip(0,0, 2, 0);
		p.addNewShip(8,0, 1, 0);
		p.addNewShip(8,3, 1, 0);
	}
	private static void initPlayerGrid(Player p) {
		int c,l,dir;
		boolean added;
		int ships[] = {5, 4, 3, 2, 2, 1, 1};
		int i = 0;
		String str;
		do {
			p.displayGrid();
			System.out.println("\tPlacer bateau --------> taille " + ships[i] + " : ");
			System.out.println("Entrez la coordonnée : ");
			str = scan.nextLine();
			if(str.length()<2) continue;
			c=str.charAt(0)-65;
			l=Integer.parseInt(str.substring(1, 2));
			System.out.println("Entrez la direction (0: vert, 1:horiz) : ");
			dir=Integer.parseInt(scan.nextLine());
			added = p.addNewShip(c,l, ships[i], dir);
			if(added) {
				i++;
			}else {
				System.out.println("Placement du bateau de taille " + ships[i] + " en " + c + "," + l + " impossible.\n");
			}
		}while(i<ships.length);
		System.out.println("Votre grille est complète.");
		p.displayGrid();
	}
	public static void launch2players() {
		mode = 2;
		player1 = new Player("Joueur1");
		player2 = new Player("Joueur2");
		currentPlayer = player1;
		opponent = player2;
		initGame();
	}
	public static void launch1player() {
		mode = 1;
		player1 = new Player("Joueur1");
		robot = new Player("Robot");
		currentPlayer = player1;
		opponent = robot;
		initGame();
	}
	public static int initGameMode() {
		int m;
		do{
			System.out.println("Choisissez le mode de jeu :\n\n \t [1] 1 Joueur || [2] 2 joueurs");

			m = scan.nextInt();

			if(m == 1) {
				botDifficulty();
				launch1player();
				
			}else {
				launch2players();
			}
		}while(m != 1 && m != 2);
		return m;
	}
	public static int botDifficulty() {
		do {
			System.out.println("Choisissez un mode de difficulter pour le Bot\n\n \t [0] très facile || [1] facile\n\n \t [2] intermédiaire || [3] Difficile");
			difficulty = scan.nextInt();
		}while(difficulty > 4);
		
		return difficulty;
	}
	private static void initGame() {
		System.out.println(player1.getName()+", remplissez votre grille.\n");
//		fakeInitPlayerGrid(player1);
		initPlayerGrid(player1);
		System.out.println("Appuyez sur entrée pour changer de joueur.");
		scan.nextLine();
		hideGame();
		if(mode==2) {
			System.out.println(player2.getName()+", remplissez votre grille.\n");
			fakeInitPlayerGrid(player2);
			//initPlayerGrid(player2);
		}else {
			robot.initGridRandom();
			System.out.println("La grille de " + robot.getName()+" est remplie.\n");
		}
		System.out.println("Appuyez sur entrée pour commencer la partie.");
		scan.nextLine();
		hideGame();
	}
	public static boolean shot() {
		if(currentPlayer == robot && botDifficulty() == 0) {
			Random r = new Random();
			System.out.println(currentPlayer.getName() + ", entrez une coordonnée à attaquer.\n");
			currentPlayer.displayShotGrid();
			int c= r.nextInt(10);
			int l= r.nextInt(10);
			boolean replay = currentPlayer.recordShot(c, l, opponent); 
			return replay;
		}else {
		System.out.println(currentPlayer.getName() + ", entrez une coordonnée à attaquer.\n");
		currentPlayer.displayShotGrid();
		String str = scan.nextLine() ;
		int c=str.charAt(0)-65;
		int l=Integer.parseInt(str.substring(1, 2));
		boolean replay = currentPlayer.recordShot(c, l, opponent); 
		return replay;
		}
		
		}
	public static void changeCurrentPlayer() {
		Player tmp = currentPlayer;
		currentPlayer = opponent;
		opponent = tmp;
		System.out.println("Appuyez sur entrée pour changer de joueur.");
		scan.nextLine();
		hideGame();
	}
	public static void hideGame() {
		int i;
		for(i=0; i<40; i++) {
			System.out.println();
		}
	}
	public static boolean isOver() {
		boolean over = currentPlayer.hasWin() || opponent.hasWin();
		return over; 
	}
}