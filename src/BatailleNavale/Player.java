package BatailleNavale;

import java.util.Random;
import java.util.Scanner;

public class Player {
	private String name;
	private Grid shipPosition;
	private Grid shotGrid;
//	private Coordinate coord;
	private int sink; 
	

	public Player(String n){
		//Constructeur des grids par joueurs 
		name = n;
		shipPosition = new Grid();
		shotGrid = new Grid();
		sink = 0;
	}

	public boolean addNewShip(int column, int line, int s, int d) {
		//Affiche les 1 dans le grid
		return shipPosition.addNewShip(column, line, s, d);
	}


	public boolean recordShot(int column, int line, Player p) {
		//enregistre le tir d'un joueur
			if (shotGrid.getValue(column, line) == 2 || shotGrid.getValue(column, line) == 4) {
				return false;
			}
			else {
				if (p.hasShip(column, line) == true)  {
					shotGrid.addShot(column, line, true);
					int sizeShipAttack = shotGrid.sizeShip(column, line);
					int sizeShipDefense = p.shipPosition.sizeShip(column, line);
					if(sizeShipAttack == sizeShipDefense){ //Compare la grille d'attaque � la grille de d�fense du joueur
						sink++; //augmente le compteur des bateaux coul�s
						System.out.println("Vous avez coul� un bateau.\n");
					}
				}else {
					shotGrid.addShot(column, line, false);
				}
				return p.hasShip(column, line);
			}
		}


	private boolean hasShip(int c, int l) {
		//V�rification si y'a un bateau au coordonn�es donn�es
		if(shipPosition.getValue(c, l) == 1 ) {
			return true;
		}else {
			return false; 
		}
	}
	public void initGridRandom() {

		shipPosition.randomInit(); //Initialise la grid random
	}
	public void displayGrid() {
		//affichage les bateaux du joueur 
		System.out.println(shipPosition);
	}

	public void displayShotGrid() {
		//affichage des tires tent� 
		System.out.println(shotGrid);

	}

	public boolean hasWin() { //Si le compteur atteint 7 alors le jeu se termine 
		if(sink == 7) {
			Game.hideGame();
			System.out.println("Vous avez coul� le dernier bateau et vous avez gagn�\n Toutes mes f�licitations matelot vous avez �t� promu  !!");
			return true;
		}
		return false;
	}

	public String getName() {
		//retour le nom du joueur
		return name;

	}


	public boolean getHasShip(int c, int l) {
		return hasShip(c , l); //Coordonn�e du bateau 
	}
}

