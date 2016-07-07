package conga;

import java.util.Arrays;
import java.util.Random;

public class Conga {

	public static void main(String[] args) {
		int[][] matrix = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[i][j] = 0;
			}
		}
		boolean player1 = true;

		matrix[0][0] = -10;
		matrix[3][3] = 10;
		
		double hn = heuristic(matrix);
		
		while(hn!= Double.POSITIVE_INFINITY && hn!=Double.NEGATIVE_INFINITY){
			
			matrix = randomMove(matrix);
			
			Integer[] sol = recursive(matrix,3,true);
			
			matrix = move(matrix, sol[2],sol[0],sol[1], true);
			
			System.out.println(Arrays.deepToString(matrix));
			
		}
		
		
	}

	public static Integer[] recursive(int[][] matrix, int depth, boolean whiteMove) {

		
		Integer[] max_sol = new Integer[4];
		Integer[] min_sol = new Integer[4];
		
		int hn = heuristic(matrix);

		if (depth == 0) {
			max_sol[3] = hn;
			min_sol[3] = hn;
			return max_sol;
		}

		if (hn == 999|| hn == -999) {
			max_sol[3] = hn;
			min_sol[3] = hn;
			return max_sol;		
		}
		
		int max = -999;
		int min = 999;
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {

				if (whiteMove && matrix[i][j] > 0) {

					// LEFT
					if (j >= 1 && matrix[i][j - 1] >= 0) {
						// Left possible
						int[][] tmp = deepCopy(matrix);
						tmp = move(tmp, 1, i, j , whiteMove);
						Integer h[] = recursive(tmp, depth--, !whiteMove);
						if(h[3]>max_sol[3]){
							
							max_sol[0] =  i;
							max_sol[1] = j;
							max_sol[2] = 1;
							max_sol[3] = h[3];
						}
					}

					// DOWN
					if (i <= 2 && matrix[i + 1][j] >= 0) {
						// Down possible
						int[][] tmp = deepCopy(matrix);
						tmp = move(tmp, 4, i , j, whiteMove);
						Integer h[] = recursive(tmp, depth--, !whiteMove);
						if(h[3]>max_sol[3]){
							
							max_sol[0] =  i;
							max_sol[1] =  j;
							max_sol[2] =  4;
							max_sol[3] = h[3];
						}			
					}

					// RIGHT
					if (j <= 2 && matrix[i][j + 1] >= 0) {
						// Right possible
						int[][] tmp = deepCopy(matrix);
						tmp = move(tmp, 2, i, j , whiteMove);
						Integer h[] = recursive(tmp, depth--, !whiteMove);
						if(h[3]>max_sol[3]){
							
							max_sol[0] =  i;
							max_sol[1] =  j;
							max_sol[2] =  2;
							max_sol[3] = h[3];
						}				
					}

					// UP
					if (i >= 1 && matrix[i - 1][j] >= 0) {
						// Up possible
						int[][] tmp = deepCopy(matrix);
						tmp = move(tmp, 3, i , j, whiteMove);
						Integer h[] = recursive(tmp, depth--, !whiteMove);
						if(h[3]>max_sol[3]){
							
							max_sol[0] =  i;
							max_sol[1] =  j;
							max_sol[2] =  3;
							max_sol[3] = h[3];
						}
					}

				} else if (!whiteMove && matrix[i][j] < 0) {

					// LEFT
					if (j >= 1 && matrix[i][j - 1] <= 0) {
						// Left possible
						int[][] tmp = deepCopy(matrix);
						tmp = move(tmp, 1, i, j , whiteMove);
						Integer[] h = recursive(tmp, depth--, !whiteMove);
						if(h[3]< min_sol[3]){
							
							min_sol[0] =  i;
							min_sol[1] =  j;
							min_sol[2] =  1;
							min_sol[3] = h[3];
						}
					}

					// DOWN
					if (i <= 2 && matrix[i + 1][j] <= 0) {
						// Down possible
						int[][] tmp = deepCopy(matrix);
						tmp = move(tmp, 4, i , j, whiteMove);
						Integer[] h = recursive(tmp, depth--, !whiteMove);
						if(h[3]< min_sol[3]){
							
							min_sol[0] = i;
							min_sol[1] =  j;
							min_sol[2] =  3;
							min_sol[3] = h[3];
						}					
					}

					// RIGHT
					if (j <= 2 && matrix[i][j + 1] <= 0) {
						// Right possible
						int[][] tmp = deepCopy(matrix);
						tmp = move(tmp, 2, i, j , whiteMove);
						Integer[] h = recursive(tmp, depth--, !whiteMove);
						if(h[3]< min_sol[3]){
							
							min_sol[0] =  i;
							min_sol[1] =  j;
							min_sol[2] =  2;
							min_sol[3] = h[3];
						}				
					}

					// UP
					if (i >= 1 && matrix[i - 1][j] <= 0) {
						// Up possible
						int[][] tmp = deepCopy(matrix);
						tmp = move(tmp, 3, i , j, whiteMove);
						Integer[] h = recursive(tmp, depth--, !whiteMove);
						if(h[3]< min_sol[3]){
							
							min_sol[0] = i;
							min_sol[1] =  j;
							min_sol[2] =  3;
							min_sol[3] = h[3];
						}
					}

				}

			}
		}

		if(whiteMove){
			return max_sol;
		}else{		
			return min_sol;
		}
		
	}

	public static int[][] deepCopy(int[][] matrix) {
		int[][] tmp = new int[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tmp[i][j] = matrix[i][j];
			}
		}
		return tmp;
	}

	public static int[][] move(int[][] matrix, int direction, int i, int j, boolean whiteMove) {

		// DIRECTION = 1: LEFT
		// DIRECTION = 2: Right
		// Direction = 3: Up
		// Direction = 4: Down

		int num_moves = 0;

		if (whiteMove) {
			// LEFT
			if (direction == 1) {
				for (int k = 1; (j - k) >= 0; k--) {
					if (matrix[i][j - k] >= 0) {
						num_moves++;
					}
				}
				if (num_moves == 1) {
					matrix[i][j - 1] = matrix[i][j - 1] + matrix[i][j];
					matrix[i][j] = 0;
				} else if (num_moves == 2) {
					matrix[i][j - 1] = matrix[i][j - 1] + 1;
					matrix[i][j - 2] = matrix[i][j - 2] + matrix[i][j] - 1;
					matrix[i][j] = 0;
				} else if (num_moves == 3) {
					matrix[i][j - 1] = matrix[i][j - 1] + 1;
					matrix[i][j - 2] = matrix[i][j - 2] + 2;
					matrix[i][j - 3] = matrix[i][j - 3] + matrix[i][j] - 3;
					matrix[i][j] = 0;
				}
			}

			//Check RIGHT
			if (direction == 2) {
				for (int k = 1; (j + k) <= 3; k++) {
					if (matrix[i][j + k] >= 0) {
						num_moves++;
					}
				}

				if (num_moves == 1) {
					matrix[i][j + 1] = matrix[i][j + 1] + matrix[i][j];
					matrix[i][j] = 0;
				} else if (num_moves == 2) {
					matrix[i][j + 1] = matrix[i][j + 1] + 1;
					matrix[i][j + 2] = matrix[i][j + 2] + matrix[i][j] - 1;
					matrix[i][j] = 0;
				} else if (num_moves == 3) {
					matrix[i][j + 1] = matrix[i][j + 1] + 1;
					matrix[i][j + 2] = matrix[i][j + 2] + 2;
					matrix[i][j + 3] = matrix[i][j + 3] + matrix[i][j] - 3;
					matrix[i][j] = 0;
				}
			}
			
			//Check UP
			if (direction == 3) {
				for (int k = 1; (i - k) >= 0; k--) {
					if (matrix[i - k][j] >= 0) {
						num_moves++;
					}
				}
				
				if (num_moves == 1) {
					matrix[i - 1][j] = matrix[i - 1][j] + matrix[i][j];
					matrix[i][j] = 0;
				} else if (num_moves == 2) {
					matrix[i - 1][j] = matrix[i - 1][j] + 1;
					matrix[i - 2][j] = matrix[i - 2][j] + matrix[i][j] - 1;
					matrix[i][j] = 0;
				} else if (num_moves == 3) {
					matrix[i - 1][j] = matrix[i - 1][j] + 1;
					matrix[i- 2][j] = matrix[i - 2][j] + 2;
					matrix[i - 3][j] = matrix[i - 3][j] + matrix[i][j] - 3;
					matrix[i][j] = 0;
				}
			}
			
			//Check DOWN
			if (direction == 4) {
				for (int k = 1; (i + k) <= 3; k++) {
					if (matrix[i + k][j] >= 0) {
						num_moves++;
					}
				}

				if (num_moves == 1) {
					matrix[i + 1][j] = matrix[i + 1][j] + matrix[i][j];
					matrix[i][j] = 0;
				} else if (num_moves == 2) {
					matrix[i + 1][j] = matrix[i + 1][j] + 1;
					matrix[i + 2][j] = matrix[i + 2][j] + matrix[i][j] - 1;
					matrix[i][j] = 0;
				} else if (num_moves == 3) {
					matrix[i + 1][j] = matrix[i + 1][j] + 1;
					matrix[i + 2][j] = matrix[i + 2][j] + 2;
					matrix[i + 3][j] = matrix[i + 3][j] + matrix[i][j] - 3;
					matrix[i][j] = 0;
				}
			}

		} else {
			//IF IT NOT WHITES MOVE
			
			// LEFT
			if (direction == 1) {
				for (int k = 1; (j - k) >= 0; k--) {
					if (matrix[i][j - k] < 0) {
						num_moves++;
					}
				}
				if (num_moves == 1) {
					matrix[i][j - 1] = matrix[i][j - 1] + matrix[i][j];
					matrix[i][j] = 0;
				} else if (num_moves == 2) {
					matrix[i][j - 1] = matrix[i][j - 1] - 1;
					matrix[i][j - 2] = matrix[i][j - 2] + matrix[i][j] + 1;
					matrix[i][j] = 0;
				} else if (num_moves == 3) {
					matrix[i][j - 1] = matrix[i][j - 1] - 1;
					matrix[i][j - 2] = matrix[i][j - 2] - 2;
					matrix[i][j - 3] = matrix[i][j - 3] + matrix[i][j] + 3;
					matrix[i][j] = 0;
				}
			}

			//Check RIGHT
			if (direction == 2) {
				for (int k = 1; (j + k) <= 3; k++) {
					if (matrix[i][j + k] < 0) {
						num_moves++;
					}
				}

				if (num_moves == 1) {
					matrix[i][j + 1] = matrix[i][j + 1] + matrix[i][j];
					matrix[i][j] = 0;
				} else if (num_moves == 2) {
					matrix[i][j + 1] = matrix[i][j + 1] - 1;
					matrix[i][j + 2] = matrix[i][j + 2] + matrix[i][j] + 1;
					matrix[i][j] = 0;
				} else if (num_moves == 3) {
					matrix[i][j + 1] = matrix[i][j + 1] - 1;
					matrix[i][j + 2] = matrix[i][j + 2] - 2;
					matrix[i][j + 3] = matrix[i][j + 3] + matrix[i][j] + 3;
					matrix[i][j] = 0;
				}
			}
			
			//Check UP
			if (direction == 3) {
				for (int k = 1; (i - k) >= 0; k--) {
					if (matrix[i - k][j] < 0) {
						num_moves++;
					}
				}
				
				if (num_moves == 1) {
					matrix[i - 1][j] = matrix[i - 1][j] + matrix[i][j];
					matrix[i][j] = 0;
				} else if (num_moves == 2) {
					matrix[i - 1][j] = matrix[i - 1][j] - 1;
					matrix[i - 2][j] = matrix[i - 2][j] + matrix[i][j] + 1;
					matrix[i][j] = 0;
				} else if (num_moves == 3) {
					matrix[i - 1][j] = matrix[i - 1][j] - 1;
					matrix[i- 2][j] = matrix[i - 2][j] - 2;
					matrix[i - 3][j] = matrix[i - 3][j] + matrix[i][j] + 3;
					matrix[i][j] = 0;
				}
			}
			
			//Check DOWN
			if (direction == 4) {
				for (int k = 1; (i + k) <= 3; k++) {
					if (matrix[i + k][j] < 0) {
						num_moves++;
					}
				}

				if (num_moves == 1) {
					matrix[i + 1][j] = matrix[i + 1][j] + matrix[i][j];
					matrix[i][j] = 0;
				} else if (num_moves == 2) {
					matrix[i + 1][j] = matrix[i + 1][j] - 1;
					matrix[i + 2][j] = matrix[i + 2][j] + matrix[i][j] + 1;
					matrix[i][j] = 0;
				} else if (num_moves == 3) {
					matrix[i + 1][j] = matrix[i + 1][j] - 1;
					matrix[i + 2][j] = matrix[i + 2][j] - 2;
					matrix[i + 3][j] = matrix[i + 3][j] + matrix[i][j] + 3;
					matrix[i][j] = 0;
				}
			}

		}

	return matrix;
	}

	public static int heuristic(int[][] matrix) {
		int heuristic = 0;
		int positive_h = 0;
		int negative_h = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] > 0) {

					// LEFT
					if (j >= 1 && matrix[i][j - 1] >= 0) {
						// Left possible
						positive_h++;
					}

					// DOWN
					if (i <= 2 && matrix[i + 1][j] >= 0) {
						// Down possible
						positive_h++;

					}

					// RIGHT
					if (j <= 2 && matrix[i][j + 1] >= 0) {
						// Right possible
						positive_h++;

					}

					// UP
					if (i >= 1 && matrix[i - 1][j] >= 0) {
						// Up possible
						positive_h++;

					}
				} else if (matrix[i][j] < 0) {

					// LEFT
					if (j >= 1 && matrix[i][j - 1] <= 0) {
						// Left possible
						negative_h++;
					}

					// DOWN
					if (i <= 2 && matrix[i + 1][j] <= 0) {
						// Down possible
						negative_h++;

					}

					// RIGHT
					if (j <= 2 && matrix[i][j + 1] <= 0) {
						// Right possible
						negative_h++;

					}

					// UP
					if (i >= 1 && matrix[i - 1][j] <= 0) {
						// Up possible
						negative_h++;

					}

				}
			}
		}

		if (positive_h == 0) {
			heuristic = -999;
		} else if (negative_h == 0) {
			heuristic = 999;
		} else {
			heuristic = positive_h - negative_h;
		}

		return heuristic;
	}
	
	
	
	public static int[][] randomMove(int[][] matrix){
		
		Random x = new Random();
		
		int[][] tmp = deepCopy(matrix);
		
		boolean flag = true;
		
		while(flag){
			
			int i = x.nextInt(3);
			int j = x.nextInt(3);
			int direction = x.nextInt(3)+1;
			
			
			if(matrix[i][j]<0){
				


				// LEFT
				if (j >= 1 && matrix[i][j - 1] <= 0 && direction ==1) {
					// Left possible
					tmp = move(matrix, 1, i, j , false);
					flag = false;

				}

				// DOWN
				if (i <= 2 && matrix[i + 1][j] <= 0 && direction ==4) {
					// Down possible
					tmp = move(matrix, 4, i , j, false);
					flag = false;
			
				}

				// RIGHT
				if (j <= 2 && matrix[i][j + 1] <= 0 && direction == 2) {
					// Right possible
					tmp = move(matrix, 2, i, j , false);
					flag = false;
			
				}

				// UP
				if (i >= 1 && matrix[i - 1][j] <= 0 && direction == 3) {
					// Up possible
					tmp = move(matrix, 3, i , j, false);
					flag = false;

				}
				
			}
			
			
		}
		
		return tmp;
		
		
	}
		
		
	

}
