import java.util.*;

public class MineSweeper {

    // Define 10x10 grids for visible and hidden fields
    private int[][] fieldVisible = new int[10][10];
    private int[][] fieldHidden = new int[10][10];

    public void startGame() {
        System.out.println("\n\n================Welcome to Minesweeper ! ================\n");
        setupField(); // Setup the field with mines

        boolean flag = true;
        while (flag) {
            displayVisible(); // Display the current state of the visible field
            flag = playMove(); // Process the player's move and update the flag
            if (checkWin()) {
                displayHidden(); // Reveal the hidden field
                System.out.println("\n================You WON!!!================");
                break;
            }
        }
    }

    public void setupField() {
        int var = 0;
        while (var != 10) {
            Random random = new Random();
            int i = random.nextInt(10);
            int j = random.nextInt(10);
            fieldHidden[i][j] = 100;
            var++;
        }
        buildHidden(); // Calculate the number of adjacent mines for each cell
    }

    public void buildHidden() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int cnt = 0;
                if (fieldHidden[i][j] != 100) {

                    if (i != 0) {
                        if (fieldHidden[i - 1][j] == 100)
                            cnt++;
                        if (j != 0) {
                            if (fieldHidden[i - 1][j - 1] == 100)
                                cnt++;
                        }

                    }
                    if (i != 9) {
                        if (fieldHidden[i + 1][j] == 100)
                            cnt++;
                        if (j != 9) {
                            if (fieldHidden[i + 1][j + 1] == 100)
                                cnt++;
                        }
                    }
                    if (j != 0) {
                        if (fieldHidden[i][j - 1] == 100)
                            cnt++;
                        if (i != 9) {
                            if (fieldHidden[i + 1][j - 1] == 100)
                                cnt++;
                        }
                    }
                    if (j != 9) {
                        if (fieldHidden[i][j + 1] == 100)
                            cnt++;
                        if (i != 0) {
                            if (fieldHidden[i - 1][j + 1] == 100)
                                cnt++;
                        }
                    }

                    fieldHidden[i][j] = cnt;
                }
            }
        }

    }
    // Method to display the visible field
    public void displayVisible() {
        System.out.print("\t ");
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < 10; j++) {
                if (fieldVisible[i][j] == 0) {
                    System.out.print("?");
                } else if (fieldVisible[i][j] == 50) {
                    System.out.print(" ");
                } else {
                    System.out.print(fieldVisible[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

    // Method to process a player's move
    public boolean playMove() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Row Number: ");
        int i = sc.nextInt();
        System.out.print("Enter Column Number: ");
        int j = sc.nextInt();

        if (i < 0 || i > 9 || j < 0 || j > 9 || fieldVisible[i][j] != 0) {
            System.out.print("\nIncorrect Input!!");
            return true;
        }
        // Check if the selected cell contains a mine
        if (fieldHidden[i][j] == 100) {
            displayHidden();
            System.out.print("Oops! You stepped on a mine!\n============GAME OVER============");
            return false;
        } else if (fieldHidden[i][j] == 0) {
            fixVisible(i, j);// Reveal adjacent cells if the cell is empty
        } else {
            fixNeighbours(i, j); // Reveal neighbors if the cell is not empty
        }

        return true;
    }

    public void fixVisible(int i, int j) {
        fieldVisible[i][j] = 50; // Mark the cell as revealed
        if (i != 0) {
            fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
            if (fieldVisible[i - 1][j] == 0)
                fieldVisible[i - 1][j] = 50;
            if (j != 0) {
                fieldVisible[i - 1][j - 1] = fieldHidden[i - 1][j - 1];
                if (fieldVisible[i - 1][j - 1] == 0)
                    fieldVisible[i - 1][j - 1] = 50;

            }
        }
        if (i != 9) {
            fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
            if (fieldVisible[i + 1][j] == 0)
                fieldVisible[i + 1][j] = 50;
            if (j != 9) {
                fieldVisible[i + 1][j + 1] = fieldHidden[i + 1][j + 1];
                if (fieldVisible[i + 1][j + 1] == 0)
                    fieldVisible[i + 1][j + 1] = 50;
            }
        }
        if (j != 0) {
            fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
            if (fieldVisible[i][j - 1] == 0)
                fieldVisible[i][j - 1] = 50;
            if (i != 9) {
                fieldVisible[i + 1][j - 1] = fieldHidden[i + 1][j - 1];
                if (fieldVisible[i + 1][j - 1] == 0)
                    fieldVisible[i + 1][j - 1] = 50;
            }
        }
        if (j != 9) {
            fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
            if (fieldVisible[i][j + 1] == 0)
                fieldVisible[i][j + 1] = 50;
            if (i != 0) {
                fieldVisible[i - 1][j + 1] = fieldHidden[i - 1][j + 1];
                if (fieldVisible[i - 1][j + 1] == 0)
                    fieldVisible[i - 1][j + 1] = 50;
            }
        }
    }

    // Method to reveal a random neighboring cell if the selected cell is not empty
    public void fixNeighbours(int i, int j) {
        Random random = new Random();
        int x = random.nextInt() % 4;

        fieldVisible[i][j] = fieldHidden[i][j];

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

    public boolean checkWin() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (fieldVisible[i][j] == 0) {
                    if (fieldHidden[i][j] != 100) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void displayHidden() {
        System.out.print("\t ");
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < 10; j++) {
                if (fieldHidden[i][j] == 0) {
                    System.out.print(" ");
                } else if (fieldHidden[i][j] == 100) {
                    System.out.print("X");
                } else {
                    System.out.print(fieldHidden[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }
}