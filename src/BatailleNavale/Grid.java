package BatailleNavale;

import java.util.Random;

public class Grid {

	private int field[][];
	Random r = new Random();
	public Grid(){ //Constructeur 
		field = new int [10][10];
		initMat();
	}


	public int getValue(int column, int line) {
		//Renvoit la valeur de la matrice à la coordonnée en paramètre 
		return field[column][line];
	}


	private void initMat() {
		//initialisation de la taille 10/10 = 0
		int column, line;

		for (column = 0; column < field.length; column++) {
			for (line = 0; line < field.length; line++) {
				field[column][line] = 0;
			}
		}
	}
	public void randomInit() {
		
		//initialisation aléatoire de la grille 
		int i, column, line, dir;
		int ships[] = {5, 4, 3, 2, 2, 1, 1};
		for(i = 0; i < ships.length; i++) {
			do {
				column = r.nextInt(10);
				line = r.nextInt(10);
				dir = r.nextInt(2);
			}while (addNewShip(column, line, ships[i], dir) == false);
		}

	}


	public void addShot(int column, int line, boolean success) {
		//Mise à jour de la grille avec success true or false

		if(success == true) {
			field[column][line] = 2;
		}else 
			field[column][line] = 4; 
	} 


	private boolean isValidShip(int c, int l, int size, int dir) {
		//Vérifie la validité de l'emplacement du bateau
		int i;

		if((c >= 0 && c < 10) && (l >=0 && l < 10) && ((dir == 0 && (l + size - 1) < field.length)||(dir == 1 && (c + size - 1) < field.length && field[c][l] == 0))) {

			for (i = 0; i < size ; i++) {
				if((c == 0 || c == 9) && (l == 0 || l == 9)){ //Dans le cas où le bateau est dans un coin de la matrice
					if (c == 0 && l == 0 && field[c+1][l] == 0 && field[c][l+1] == 0 && field[c+1][l+1] == 0) { // coin supérieur gauche

					}
					else if (c == 9 && l == 0 && field[c-1][l] == 0 && field[c][l+1] == 0 && field[c-1][l+1] == 0) { // coin supérieur droit

					}
					else if (c == 0 && l == 9 && field[c+1][l] == 0 && field[c][l-1] == 0 && field[c+1][l-1] == 0) { // coin inférieur gauche

					}
					else if (c == 9 && l == 9 && field[c-1][l] == 0 && field[c][l-1] == 0 && field[c-1][l-1] == 0) { // coin inférieur droit

					}
					else {
						return false;
					}
				}
				else if(c == 0 || c == 9 || l == 0 || l == 9) { //Dans le cas où le bateau est sur une bordure de la matrice
					if (c == 0  && field[c][l-1] == 0 && field[c+1][l-1] == 0 && field[c+1][l+1] == 0 && field[c+1][l] == 0 && field[c][l+1] == 0) { // Bordure gauche

					}
					else if (c == 9  && field[c][l-1] == 0 && field[c-1][l-1] == 0 && field[c-1][l] == 0 && field[c-1][l+1] == 0 && field[c][l+1] == 0) { // Bordure droite

					}
					else if (l == 0  && field[c-1][l] == 0 && field[c-1][l+1] == 0 && field[c][l+1] == 0 && field[c+1][l+1] == 0 && field[c+1][l] == 0) { // Bordure supérieur

					}
					else if (l == 9  && field[c-1][l] == 0 && field[c-1][l-1] == 0 && field[c][l-1] == 0 && field[c+1][l-1] == 0 && field[c+1][l] == 0){ // Bordure inférieur

					}
					else {
						return false;
					}
				}
				else if (c != 0 && c != 9 && l != 0 && l != 9  && field[c][l-1] == 0 && field[c+1][l-1] == 0 && field[c+1][l] == 0  
						&& field[c+1][l+1] == 0 && field[c][l+1] == 0  && field[c-1][l+1] == 0  && field[c-1][l] == 0 && field[c-1][l-1] == 0){ //Dans le cas où le bateau n'est pas sur les bords de la matrice
					return true;
				}
				else {
					return false;
				}

				if(dir == 0) {
					l ++;
				}
				else {
					c ++;
				}
			}
			return true;
		}
		else {

			return false;
		}

	}





	public boolean addNewShip(int column, int line, int s, int d) {
		//Appel isValidShip est vrai alors pose le bateau sinon pas possible
		if(isValidShip(column, line, s, d) == true){
			placeShip(column, line, s, d);
			return true;
		}

		else {
			return false;
		}
	}


	private void placeShip (int c, int l, int size, int dir) {
		//modifie la matrice d’entiers pour mettre la valeur 1 aux cases contenant le nouveau bateau

		int i;
		for(i = 0; i < size; i++){
			if(dir == 0){

				field[c][l+i] = 1;
			}

			else if (dir == 1){
				field[c + i][l] = 1;
			}
		}
	}


	public String toString() {
		//affiche les grids 
		int l=0;
		String str="\t    Bataille Navale \n\n";
		str+="     A  B  C  D  E  F  G  H  I  J \n";
		str+="   -------------------------------\n";
		for(int i=0; i<field.length; i++) {
			str+=i + " |  ";
			for(int c=0; c<field.length; c++) {
				str+=field[c][l] + "  ";
			}
			str+="|\n";
			l++;
		}
		return str+="   -------------------------------";
	}

	public int sizeShip(int c, int l) {
		int size = 1, i = 1;
		if ((c < 9 && field[c + 1][l] != 0 && field[c + 1][l] != 4) || (c > 0 && field[c - 1][l] != 0 && field[c - 1][l] != 4)) { // Verif a droite et à gauche
			// verifier a gauche et a droite pour incrementer size
			while((c - i >= 0) && field[c - i][l] != 0 && field[c - i][l] != 4) { // regarde a gauche
				size ++;
				i ++;
			}
			i = 1;
			while((c + i < 10) && field[c + i][l] != 0 && field[c + i][l] != 4) { // regarde a droite
				size++;
				i++;
			}
			return size;
		}
		else if ((l > 0 && field[c][l - 1] != 0 && field[c][l - 1] != 4) || (l < 9 && field[c][l + 1] != 0 && field[c][l + 1] != 4)) {
			// verifier en haut et en bas pour incrementer size
			while((l - i >= 0) && field[c][l - i] != 0 && field[c][l - i] != 4) { // regarde en haut
				size ++;
				i ++;
			}
			i = 1;
			while((l + i < 10) && field[c][l + i] != 0 && field[c][l + i] != 4) { // regarde en bas
				size++;
				i++;
			}
			return size;
		}
		else {
			return size = 1;
		}
	}

}


