package markov.valueIteration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ValueIteration {

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		// TODO Auto-generated method stub
		int numberOfRows = 0;
		int numberOfColumns = 0;
		int myCount = 0;
		/******************************************** enter states *************************************/
		System.out
				.println("Enter the number of states in the form of rows and columns");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			numberOfRows = Integer.parseInt(br.readLine());
			numberOfColumns = Integer.parseInt(br.readLine());
			double[][] matrix = new double[numberOfRows][numberOfColumns];
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					matrix[i][j] = 0;
				}
			}

			/******************************************** enter rewards for states **************************/
			System.out.println("Enter the rewards for each state");
			double[][] rewards = new double[numberOfRows][numberOfColumns];
			for (int rewardRowCount = 0; rewardRowCount < numberOfRows; rewardRowCount++) {
				for (int rewardColumnCount = 0; rewardColumnCount < numberOfColumns; rewardColumnCount++) {
					rewards[rewardRowCount][rewardColumnCount] = Double
							.parseDouble(br.readLine());
				}
			}
			/******************************************** enter discount ************************************/
			System.out.println("Enter discount");
			double discount = Double.parseDouble(br.readLine());
			/******************************************** Intended probability *********************************/
			System.out
					.println("Enter probability of going into intended direction");
			double probability = Double.parseDouble(br.readLine());
			/***************************************** Enter the number of halt states and their utilities *****/
			System.out.println("Enter the 2 final states utilities");
			int tempColumn = numberOfColumns - 1;
			/******************************************** Creating new matrix ****************************/
			double[][] newMatrix = new double[numberOfRows][numberOfColumns];
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					matrix[i][j] = 0;
					newMatrix[i][j] = 0;
				}
			}
			matrix[0][tempColumn] = Double.parseDouble(br.readLine());
			matrix[1][tempColumn] = Double.parseDouble(br.readLine());
			newMatrix[0][tempColumn] = matrix[0][tempColumn];
			newMatrix[1][tempColumn] = matrix[1][tempColumn];
			// For testing
			/*
			 * for(int i=0;i<numberOfRows;i++){ for(int
			 * j=0;j<numberOfColumns;j++){ System.out.print(rewards[i][j]); }
			 * System.out.println(); }
			 */
			/******************************************** enter max error allowed *****************************/
			System.out.println("Enter maximum error allowed");
			double maxErrorAllowed = Double.parseDouble(br.readLine());
			/*
			 * Initializing a variable for measuring maximum change in the
			 * utility of any state in an iteration
			 */
			double maxChange = 0;
			do {
				maxChange = 0;
				/*******
				 * Assuming that 1-probability will give us the prob in right
				 * angles to given direction
				 ************/
				/*
				 * Actions 1. Up 2. Down 3. Left 4. Right
				 */
				for (int i = 0; i < numberOfRows; i++) {
					for (int j = 0; j < numberOfColumns; j++) {
						double up = 0;
						double down = 0;
						double left = 0;
						double right = 0;
						double probAtRightAngles = (1 - probability) / 2;
						if ((i == 0 && j == numberOfColumns - 1)
								|| (i == 1 && j == numberOfColumns - 1)) {
							continue;
						}
						// Executing Actions
						else {
							if (i == 0) {
								up = up + (probability * matrix[i][j]);
								left = left + probAtRightAngles * matrix[i][j];
								right = right + probAtRightAngles
										* matrix[i][j];
							} else {
								up = up + (probability * matrix[i - 1][j]);
								left += probAtRightAngles * matrix[i - 1][j];
								right += probAtRightAngles * matrix[i - 1][j];
							}
							if (j == 0) {
								up = up + (probAtRightAngles * matrix[i][j]);
								down = down
										+ (probAtRightAngles * matrix[i][j]);
								left += probability * matrix[i][j];
							} else {
								up = up
										+ (probAtRightAngles * matrix[i][j - 1]);
								down += probAtRightAngles * matrix[i][j - 1];
								left += probability * matrix[i][j - 1];
							}
							if (j == numberOfColumns - 1) {
								up = up + (probAtRightAngles * matrix[i][j]);
								down += probAtRightAngles * matrix[i][j];
								right += probability * matrix[i][j];
							} else {
								up = up
										+ (probAtRightAngles * matrix[i][j + 1]);
								down += probAtRightAngles * matrix[i][j + 1];
								right += probability * matrix[i][j + 1];
							}
							if (i == numberOfRows - 1) {
								down += probability * matrix[i][j];
								left += probAtRightAngles * matrix[i][j];
								right += probAtRightAngles * matrix[i][j];
							} else {
								down += probability * matrix[i + 1][j];
								left += probAtRightAngles * matrix[i + 1][j];
								right += probAtRightAngles * matrix[i + 1][j];
							}
							// Max of the 4 states
							double[] arr = new double[4];
							arr[0] = up;
							arr[1] = down;
							arr[2] = left;
							arr[3] = right;
							double max = 0;
							max = arr[0];
							up = down = left = right = 0;
							for (int k = 1; k < arr.length; k++) {
								if (max < arr[k]) {
									max = arr[k];
								}// end of if for finding max
							}// end of for for finding max

							newMatrix[i][j] = rewards[i][j] + (max * discount);
						}// end of else
					}// end of j
					/*
					 * if(myCount<10){ System.out.println("My count"+myCount);
					 * myCount++; for(int h=0;h<numberOfRows;h++){ for(int
					 * t=0;t<numberOfColumns;t++){
					 * System.out.print(newMatrix[h][t]+"          "); }
					 * System.out.println("max val"); } } System.out.println();
					 */}// end of i
						// calculating the maxChange b/w two arrays
				for (int l = 0; l < numberOfRows; l++) {
					for (int j = 0; j < numberOfColumns; j++) {
						if (newMatrix[l][j] - matrix[l][j] > maxChange) {
							maxChange = Math
									.abs(newMatrix[l][j] - matrix[l][j]);
						} else {
							maxChange = Math
									.abs(matrix[l][j] - newMatrix[l][j]);
							// System.out.println("maxhange val"+maxChange);
						}
					}
				}// System.out.println("Max change"+maxChange);
					// Assigning new Matrix to matrix for next iteration

				for (int i = 0; i < numberOfRows; i++) {
					for (int j = 0; j < numberOfColumns; j++) {
						matrix[i][j] = newMatrix[i][j];
					}
				}
			} while (maxChange > (maxErrorAllowed * ((1 - discount) / discount)));// end of while
			System.out.println("The final utilities of states");
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					System.out.print(newMatrix[i][j] + "  ");
				}
				System.out.println();
			}
		} catch (NumberFormatException e) {
			System.out.println("The number should be entered as integrals");

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}