package logic;

import build.BFS;
import build.BFS.Point;

import javax.swing.*;

@SuppressWarnings("serial")
public class TheArchitect extends JFrame {
	private String[][] updatedMatrix;
	
	public void clearWay(String[][] currentMatrix) {
		for (int i = 0; i < currentMatrix.length; ++i) {
			for (int j = 0; j < currentMatrix[i].length; ++j) {
				if (currentMatrix[i][j].equals("M") || currentMatrix[i][j].equals("D")) {
					currentMatrix[i][j] = "N";
				}
			}
	    }
		
		updatedMatrix = currentMatrix;
	}
	
	public void printWay(Point point, String[][] currentMatrix) {
		point = point.parent;
		while(point.parent != null) {
			currentMatrix[point.x][point.y] = "M";
			point = point.parent;
		}
		
		updatedMatrix = currentMatrix;
	}
	
	public void playerMove(int xScale, int yScale, String[][] currentMatrix) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < currentMatrix.length; ++i) {
			for (int j = 0; j < currentMatrix[i]. length; ++j) {
				if(currentMatrix[i][j].equals("P")) {
					x = i;
					y = j;
					break;
				}
			}
		}
		
        if (currentMatrix[x + xScale][y + yScale].equals("E")) {
        	currentMatrix[x][y] = "N";
        	currentMatrix[x + xScale][y + yScale] = "P";
        	JFrame frame = new JFrame("Congratz");
        	JOptionPane.showMessageDialog(frame, "Win");
        } else if (!currentMatrix[x + xScale][y + yScale].equals("W")) {
        	currentMatrix[x][y] = "N";
        	currentMatrix[x + xScale][y + yScale] = "P";
        }
        updatedMatrix = currentMatrix;
	}

	public void printDeadEnds(String[][] currentMatrix) {
		for (int i = 0; i < currentMatrix.length; ++i) {
			for (int j = 0; j < currentMatrix[i].length; ++j) {
				if (getNeighboursCount(i, j, currentMatrix) == 1 && currentMatrix[i][j].equals("N")) {
					currentMatrix[i][j] = "D";
				}
			}
	    }
		updatedMatrix = currentMatrix;
	}
	
	private int getNeighboursCount(int x, int y, String[][] currentMatrix) {
		int count = 0;
		if (BFS.isFree(x + 1, y, currentMatrix)) {
			++count;
		} 
		if (BFS.isFree(x - 1, y, currentMatrix)) {
			++count;
		} 
		if (BFS.isFree(x, y + 1, currentMatrix)) {
			++count;
		} 
		if (BFS.isFree(x, y - 1, currentMatrix)) {
			++count;
		}
		return count;
	}
	
	public String[][] getUpdatedMatrix() {
		return updatedMatrix;
	}
}