import java.util.Random;

public class Main {

	public static int population[][] = new int[10][8];
	static Random rand = new Random();
	static int adapt[] = new int[10];
	static int descend[][] = new int[25][8];
	static int adaptDes[] = new int[25];
	static int index = -1;

	static void geraPop() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				population[i][j] = rand.nextInt(2);
			}
		}
	}

	static void printPop() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.println(population[i][j]);
			}
		}
	}

	static void printDesc() {
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.println(descend[i][j]);
			}
		}
	}

	static void printAdaptDesc() {
		for (int i = 0; i < 25; i++) {
				System.out.println(adaptDes[i]);
		}
	}

	static void adaptation(int[][] matrix, int[] adaptt) {
		for (int i = 0; i < 10; i++) {
			adaptt[i]=0;
			for (int j = 0; j < 7; j++) {
				if (matrix[i][j] == 0 && matrix[i][j + 1] == 1) {
					adaptt[i]++;
				}
			}
		}
	}

	static void printAdaptPop() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Adapt: " + adapt[i]);
			for (int j = 0; j < 8; j++) {
				System.out.println(population[i][j]);
			}
		}
	}

	static void order(int[][] matrix, int[] adaptt, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (adaptt[i] < adaptt[j]) {
					int[] aux = matrix[i];
					matrix[i] = matrix[j];
					matrix[j] = aux;
					int aux2 = adaptt[i];
					adaptt[i] = adaptt[j];
					adaptt[j] = aux2;
				}
			}
		}
	}

	static void crossPoint() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (i != j) {
					int r = rand.nextInt(101);
					if (r >= 60) {
						int cutPoint = rand.nextInt(9);
						int k, k2;
						index++;
						for (k = 0; k < cutPoint; k++) {
							descend[index%25][k] = population[i][k];
						}
						for (k2 = cutPoint + 1; k2 < 8; k2++) {
							descend[index%25][k2] = population[j][k2];
						}
						for (k = 0; k < cutPoint; k++) {
							descend[index%25][k] = population[j][k];
						}
						for (k2 = cutPoint + 1; k2 < 8; k2++) {
							descend[index%25][k2] = population[i][k2];
						}
					}
				}
			}
		}
	}

	static void mutComplement() {
		for (int i = 0; i < 5; i++) {
			int r = rand.nextInt(101);
			if (r >= 90 ) {
				index++;
				for (int j = 0; j < 8; j++) {
					int rr = rand.nextInt(2);
					if (rr == 1) {
						if (population[i][j] == 0) {
							descend[index%25][j] = 1;
						} else {
							descend[index%25][j] = 0;
						}
					} else {
						descend[index%25][j] = population[i][j];
					}
				}
			}
		}
	}

	static void subst() {
		//printAdaptDesc();
		//System.out.println();
		adaptation(descend,adaptDes);
		order(descend,adaptDes,25);
		//printAdaptDesc();
		for(int i=20; i>15; i--) {
			descend[i]=population[20-i];
			adaptDes[i]=adapt[20-i];
		}
		adaptation(descend,adaptDes);
		order(descend,adaptDes,25);
		for(int i=0;i<10;i++) {
			population[i]=descend[i];
			adapt[i] = adaptDes[i];
		}
	}
	

	public static void main(String[] args) {
		geraPop();
		adaptation(population,adapt);
		order(population,adapt,10);
		int i = 0;
		while(adapt[i]!=4) {
			crossPoint();
			mutComplement();
			//adaptation(descend,adaptDes);
			subst();
			i++;
			if(i>=10) {
				break;
			}
		}
		printAdaptPop();

	}

}
