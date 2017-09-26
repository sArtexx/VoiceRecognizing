package build;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	public static class Point {
		public int x, y;
		public Point parent;

		public Point (int x, int y, Point parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
		}
	}

    public static Point getPath(Point start, String[][] maze) {
    	String[][] tempMaze = new String[maze.length][maze[0].length];
    	for (int i = 0; i < maze.length; ++i) {
    		for (int j = 0; j < maze[i].length; ++j) {
    			System.arraycopy(maze[i], 0, tempMaze[i], 0, maze[i].length);
    		}
    	}
    	
    	Queue<Point> q = new LinkedList<Point>();
        q.add(start);

        while(!q.isEmpty()) {
            Point p = q.remove();

            if (tempMaze[p.x][p.y].equals("E")) {
                return p;
            }

            if(isFree(p.x + 1, p.y, tempMaze)) {
            	tempMaze[p.x][p.y] = "W";
                Point nextP = new Point(p.x + 1, p.y, p);
                q.add(nextP);
            }

            if(isFree(p.x - 1, p.y, tempMaze)) {
            	tempMaze[p.x][p.y] = "W";
                Point nextP = new Point(p.x - 1, p.y, p);
                q.add(nextP);
            }

            if(isFree(p.x, p.y + 1, tempMaze)) {
            	tempMaze[p.x][p.y] = "W";
                Point nextP = new Point(p.x, p.y + 1, p);
                q.add(nextP);
            }

             if(isFree(p.x, p.y - 1, tempMaze)) {
            	tempMaze[p.x][p.y] = "W";
                Point nextP = new Point(p.x, p.y - 1, p);
                q.add(nextP);
            }
        }
        return null;
    }


    public static boolean isFree(int x, int y, String[][] maze) {
        return (x >= 0 && x < maze.length) && (y >= 0 && y < maze[x].length) && !(maze[x][y].equals("W"));
    }
}
