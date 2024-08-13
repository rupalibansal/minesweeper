import java.util.*; // Import necessary classes for Random and Scanner

public class MineSweeper1 {

    // Define 10x10 grids for visible and hidden fields
    private static int[][] fieldVisible = new int[10][10];
    private int[][] fieldHidden = new int[10][10];

    // Main method to start the game
    public static void main(String[] args) {
        MineSweeper1 M = new MineSweeper1(); // Create a new instance of MineSweeper
        M.startGame(); // Call the startGame method to begin the game

    }

    // Method to initialize and run the game loop
    public void startGame() {
        System.out.println("\n\n================Welcome to Minesweeper ! ================\n");
        setupField(); // Setup the field with mines

        boolean flag = true; // Game loop flag
        while (flag) {
            displayVisible(); // Display the current state of the visible field
            flag = playMove(); // Process the player's move and update the flag
            if (checkWin()) { // Check if the player has won
                displayHidden(); // Reveal the hidden field
                System.out.println("\n================You WON!!!================"); // Display win message
                break; // Exit the loop if the player has won
            }
        }
    }

    // Method to place mines randomly on the field
    public void setupField() {
        int var = 0; // Counter for the number of placed mines
        while (var != 10) { // Place 10 mines
            Random random = new Random(); // Create a new random object
            int i = random.nextInt(10); // Generate a random row index
            int j = random.nextInt(10); // Generate a random column index
            fieldHidden[i][j] = 100; // Place a mine at the generated index
            var++; // Increment the counter
        }
        buildHidden(); // Calculate the number of adjacent mines for each cell
    }

    // Method to calculate the number of adjacent mines for each cell
    public void buildHidden() {
        for (int i = 0; i < 10; i++) { // Iterate over all rows
            for (int j = 0; j < 10; j++) { // Iterate over all columns
                int cnt = 0; // Counter for adjacent mines
                if (fieldHidden[i][j] != 100) { // Check if the current cell is not a mine

                    // Check all 8 possible neighboring cells
                    for (int di = -1; di <= 1; di++) { // Row offset
                        for (int dj = -1; dj <= 1; dj++) { // Column offset
                            if (di == 0 && dj == 0)
                                continue; // Skip the current cell

                            int ni = i + di; // Neighbor row index
                            int nj = j + dj; // Neighbor column index

                            // Check bounds and if the neighbor cell contains a mine
                            if (ni >= 0 && ni < 10 && nj >= 0 && nj < 10 && fieldHidden[ni][nj] == 100) {
                                cnt++; // Increment the mine counter
                            }
                        }
                    }

                    fieldHidden[i][j] = cnt; // Set the number of adjacent mines for the current cell
                }
            }
        }
    }

    // Method to display the visible field
    public void displayVisible() {
        System.out.print("\t "); // Print top header
        for (int i = 0; i < 10; i++) { // Print column indices
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < 10; i++) { // Iterate over all rows
            System.out.print(i + "\t| "); // Print row index and left border
            for (int j = 0; j < 10; j++) { // Iterate over all columns
                // Display cell content based on its state
                if (fieldVisible[i][j] == 0) {
                    System.out.print("?"); // Unknown cell
                } else if (fieldVisible[i][j] == 50) {
                    System.out.print(" "); // Empty cell
                } else {
                    System.out.print(fieldVisible[i][j]); // Number of adjacent mines
                }
                System.out.print(" | "); // Right border
            }
            System.out.print("\n"); // Newline after each row
        }
    }

    // Method to process a player's move
    public boolean playMove() {
        Scanner sc = new Scanner(System.in); // Create a scanner object for input
        System.out.print("\nEnter Row Number: "); // Prompt for row index
        int i = sc.nextInt(); // Read row index
        System.out.print("Enter Column Number: "); // Prompt for column index
        int j = sc.nextInt(); // Read column index

        // Check for invalid input or already revealed cells
        if (i < 0 || i > 9 || j < 0 || j > 9 || fieldVisible[i][j] != 0) {
            System.out.print("\nIncorrect Input!!"); // Print error message
            return true; // Continue the game loop
        }

        // Check if the selected cell contains a mine
        if (fieldHidden[i][j] == 100) {
            displayHidden(); // Reveal the hidden field
            System.out.print("Oops! You stepped on a mine!\n============GAME OVER============"); // Print game over
                                                                                                 // message
            return false; // End the game
        } else if (fieldHidden[i][j] == 0) {
            fixVisible(i, j); // Reveal adjacent cells if the cell is empty
        } else {
            fixNeighbours(i, j); // Reveal neighbors if the cell is not empty
        }

        return true; // Continue the game loop
    }

    // Method to reveal adjacent cells if the selected cell is empty
    public void fixVisible(int i, int j) {
        fieldVisible[i][j] = 50; // Mark the cell as revealed
        // Check and reveal all 8 neighboring cells
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0)
                    continue; // Skip the cell itself

                int ni = i + di; // Neighbor row index
                int nj = j + dj; // Neighbor column index

                // Check bounds and reveal the neighbor cell
                if (ni >= 0 && ni < 10 && nj >= 0 && nj < 10) {
                    fieldVisible[ni][nj] = fieldHidden[ni][nj];
                    // Reveal empty cells
                    if (fieldVisible[ni][nj] == 0) {
                        fieldVisible[ni][nj] = 50;
                    }
                }
            }
        }
    }

    // Method to reveal a random neighboring cell if the selected cell is not empty
    public void fixNeighbours(int i, int j) {
        Random random = new Random(); // Create a random object
        int x = random.nextInt() % 4; // Randomly select a neighbor direction
        
        fieldVisible[i][j] = fieldHidden[i][j]; // Reveal the selected cell

        // Check and reveal a random set of neighboring cells based on x
        if (x == 0) {
            if (i != 0) {
                if (fieldHidden[i - 1][j] != 100) {
                    fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
                    if (fieldVisible[i - 1][j] == 0)
                        fieldVisible[i - 1][j] = 50;
                }
            }
            if (j != 0) {
                if (fieldHidden[i][j - 1] != 100) {
                    fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
                    if (fieldVisible[i][j - 1] == 0)
                        fieldVisible[i][j - 1] = 50;
                }
            }
            if (i != 0 && j != 0) {
                if (fieldHidden[i - 1][j - 1] != 100) {
                    fieldVisible[i - 1][j - 1] = fieldHidden[i - 1][j - 1];
                    if (fieldVisible[i - 1][j - 1] == 0)
                        fieldVisible[i - 1][j - 1] = 50;
                }
            }
        } else if (x == 1) {
            if (i != 0) {
                if (fieldHidden[i - 1][j] != 100) {
                    fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
                    if (fieldVisible[i - 1][j] == 0)
                        fieldVisible[i - 1][j] = 50;
                }
            }
            if (j != 9) {
                if (fieldHidden[i][j + 1] != 100) {
                    fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
                    if (fieldVisible[i][j + 1] == 0)
                        fieldVisible[i][j + 1] = 50;
                }
            }
            if (i != 0 && j != 9) {
                if (fieldHidden[i - 1][j + 1] != 100) {
                    fieldVisible[i - 1][j + 1] = fieldHidden[i - 1][j + 1];
                    if (fieldVisible[i - 1][j + 1] == 0)
                        fieldVisible[i - 1][j + 1] = 50;
                }
            }
        } else if (x == 2) {
            if (i != 9) {
                if (fieldHidden[i + 1][j] != 100) {
                    fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
                    if (fieldVisible[i + 1][j] == 0)
                        fieldVisible[i + 1][j] = 50;
                }
            }
            if (j != 9) {
                if (fieldHidden[i][j + 1] != 100) {
                    fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
                    if (fieldVisible[i][j + 1] == 0)
                        fieldVisible[i][j + 1] = 50;
                }
            }
            if (i != 9 && j != 9) {
                if (fieldHidden[i + 1][j + 1] != 100) {
                    fieldVisible[i + 1][j + 1] = fieldHidden[i + 1][j + 1];
                    if (fieldVisible[i + 1][j + 1] == 0)
                        fieldVisible[i + 1][j + 1] = 50;
                }
            }
        } else {
            if (i != 9) {
                if (fieldHidden[i + 1][j] != 100) {
                    fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
                    if (fieldVisible[i + 1][j] == 0)
                        fieldVisible[i + 1][j] = 50;
                }
            }
            if (j != 0) {
                if (fieldHidden[i][j - 1] != 100) {
                    fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
                    if (fieldVisible[i][j - 1] == 0)
                        fieldVisible[i][j - 1] = 50;
                }
            }
            if (i != 9 && j != 0) {
                if (fieldHidden[i + 1][j - 1] != 100) {
                    fieldVisible[i + 1][j - 1] = fieldHidden[i + 1][j - 1];
                    if (fieldVisible[i + 1][j - 1] == 0)
                        fieldVisible[i + 1][j - 1] = 50;
                }
            }
        }
    }

    // Method to check if the player has won
    public boolean checkWin() {
        for (int i = 0; i < 10; i++) { // Iterate over all rows
            for (int j = 0; j < 10; j++) { // Iterate over all columns
                // If a cell is still hidden and is not a mine, the game is not won
                if (fieldVisible[i][j] == 0) {
                    if (fieldHidden[i][j] != 100) {
                        return false; // Player has not won
                    }
                }
            }
        }
        return true; // All non-mine cells are revealed, player has won
    }

    // Method to display the hidden field (for debugging or after game over)
    public void displayHidden() {
        System.out.print("\t "); // Print top header
        for (int i = 0; i < 10; i++) { // Print column indices
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < 10; i++) { // Iterate over all rows
            System.out.print(i + "\t| "); // Print row index and left border
            for (int j = 0; j < 10; j++) { // Iterate over all columns
                // Display cell content based on its state
                if (fieldHidden[i][j] == 0) {
                    System.out.print(" "); // Empty cell
                } else if (fieldHidden[i][j] == 100) {
                    System.out.print("X"); // Mine
                } else {
                    System.out.print(fieldHidden[i][j]); // Number of adjacent mines
                }
                System.out.print(" | "); // Right border
            }
            System.out.print("\n"); // Newline after each row
        }
    }
}
