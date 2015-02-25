import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeSolver {

	public String[][] maze;
	public String[] end;
	public int rows;
	public int columns;

	public static void main(String[] args) throws FileNotFoundException {


		try{
			Scanner in = new Scanner(System.in);
			System.out.println("Type the filename of the maze you wish to be solved");
			String input = in.nextLine();
			File file = new File("resources/" + input + ".txt");

			MazeSolver mS = new MazeSolver();

			mS.readfile(file);
		}
		catch (FileNotFoundException fe){
			System.out.println("A file under that name could not be found, please try again. You do not need to type the extension.");
		}
	}

	public void readfile(File filename) throws FileNotFoundException {
		Scanner s = new Scanner(filename);

		String firstLine = s.nextLine();
		// System.out.println(firstLine);
		String[] tokens = firstLine.split(" ");
		rows = Integer.parseInt(tokens[0]);
		columns = Integer.parseInt(tokens[1]);

		// create the maze as a 2d array
		maze = new String[Integer.parseInt(tokens[0])][Integer
		                                               .parseInt(tokens[1])];

		// get the start point
		String secondLine = s.nextLine();
		String[] start = secondLine.split(" ");
		// System.out.println(start[0]+" "+start[1]);

		// set the end goal
		String thirdLine = s.nextLine();
		end = thirdLine.split(" ");
		// System.out.println(end[0]+" "+end[1]);

		int y = 0;

		while (s.hasNextLine()) {
			String nextLine = s.nextLine();
			String[] mazePlot = nextLine.split(" ");

			for (int x = 0; x < mazePlot.length; x++) {
				maze[y][x] = mazePlot[x];
			}
			y++;
		}

		escapeMaze(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
	}

	public void escapeMaze(int x, int y) {

		if (move(x, y)) {
			maze[x][y] = "S";

			for (int i = 0; i < rows; i++) {
				System.out.println();
				for (int j = 0; j < columns; j++) {

					if (maze[i][j].equals("1")){
						System.out.print("#");
					}
					else if (maze[i][j].equals("0")){
						System.out.print(" ");
					}
					else
						System.out.print(maze[i][j]);

				}

			}
		}
	}


	private boolean move(int x, int y) {

		maze[Integer.parseInt(end[0])][Integer.parseInt(end[1])] = "E";

		// check if we've reached the exit
		if (maze[x][y].equals("E")) {
			return true;
		}

		// either hit a wall or hit ourselves
		if (maze[x][y].equals("1") || maze[x][y].equals("X")) {
			return false;
		}

		// Mark this location as part of our path
		maze[x][y] = "X";
		boolean result;

		// check right
		result = move(x, y + 1);
		if (result) {
			return true;
		}

		// check up
		result = move(x - 1, y);
		if (result) {
			return true;
		}

		// check left
		result = move(x, y - 1);
		if (result) {
			return true;
		}

		// check right
		result = move(x + 1, y);
		if (result) {
			return true;
		}

		// been here before, unmark it
		maze[x][y] = "0";

		// Go back
		return false;
	}
}
