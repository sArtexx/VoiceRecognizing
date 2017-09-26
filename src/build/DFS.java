package build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DFS {
	private int height;
	private int width;
	private String[][] maze;
	private int current_x = 0;
	private int current_y = 0;
	
	public DFS(int height, int width) {
		this.height = height;
		this.width = width;
		this.maze = generateMaze();
	}
	
	public String[][] getMaze() {
		return maze;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public String[][] generateMaze() {
		maze = new String[height][width];
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				maze[i][j] = "W";
			}
		}
		
		Random rand = new Random();
		
		int r = rand.nextInt(height);
		while (r % 2 == 0) {
			r = rand.nextInt(height);
		}
		
		int c = rand.nextInt(width);
		while (c % 2 == 0) {
			c = rand.nextInt(width);
			}
		
		maze[r][c] = "N";
		
		recursion(r, c);
		
		maze[r][c] = "P";
		maze[current_x][current_y] = "E";
		return maze;
	}
	
	public void recursion(int r, int c) {
		Integer[] randDirs = generateRandomDirections();
		for (int i = 0; i < randDirs.length; ++i) {
			switch(randDirs[i]) {
				case 1:
					if (r - 2 <= 0) {
						continue;
					}
					if (!maze[r - 2][c].equals("N")) {
						maze[r - 2][c] = "N";
						maze[r - 1][c] = "N";
						current_x = r - 2;
						current_y = c;
						recursion(r - 2, c);
					}
				break;
				case 2:
					if (c + 2 >= width - 1) {
						continue;
					}
					if (!maze[r][c + 2].equals("N")) {
						maze[r][c + 2] = "N";
						maze[r][c + 1] = "N";
						current_x = r;
						current_y = c + 2;
						recursion(r, c + 2);
						}
				break;
				case 3:
					if (r + 2 >= height - 1) {
						continue;
					}
					if (!maze[r + 2][c].equals("N")) {
						maze[r + 2][c] = "N";
						maze[r + 1][c] = "N";
						current_x = r + 2;
						current_y = c;
						recursion(r + 2, c);
					}
				break;
				case 4:
					if (c - 2 <= 0) {
						continue;
					}
					if (!maze[r][c - 2].equals("N")) {
						maze[r][c - 2] = "N";
						maze[r][c - 1] = "N";
						current_x = r;
						current_y = c - 2;
						recursion(r, c - 2);
					}
				break;
			}
		}
	}
	
	public Integer[] generateRandomDirections() {
		List<Integer> randoms = new ArrayList<Integer>();
		for (int i = 0; i < 4; ++i) {
			randoms.add(i + 1);
		}
		Collections.shuffle(randoms);
		
		return randoms.toArray(new Integer[4]);
	}
}