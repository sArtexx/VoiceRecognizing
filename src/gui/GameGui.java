/*
 * Лабораторные работы №2-3 по дисциплине "Интеллектуальные системы".
 *
 * Использование существующих библиотек для решения задачи распознавание речи.
 * Генерация лабиринта методом поиска в глубину (depth-first search).
 * 
 * Выполнил студент 443 группы Симоненко Игнат Константинович.
 */

package gui;

import javax.swing.*;

import build.BFS;
import build.BFS.Point;
import build.DFS;

import logic.TheArchitect;

import java.awt.*;
import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

@SuppressWarnings("serial")
public class GameGui extends JFrame {
	private Container cp;
	private JLabel[][] labelMatrix;
	private JPanel newPanel;
	private TheArchitect theArc = new TheArchitect();
	private int height = 25;
	private int width = 25;
	private DFS maze = new DFS(height, width);
	private String[][] scrapMatrix;
	
	public static void main(String[] args) throws IOException {
		new GameGui();
	}
	
	public GameGui() throws IOException {
		super("Игра 'Лабиринт'. Симоненко И.К., 443 группа.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp = getContentPane();
		newPanel = new JPanel();
		pack();
		
		loadMatrixGui("newLoad");
         
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("voice/8874.dic");
        configuration.setLanguageModelPath("voice/8874.lm");
        LiveSpeechRecognizer recognize = new LiveSpeechRecognizer(configuration);
        recognize.startRecognition(true);
        
        while (true) {
        	SpeechResult result = recognize.getResult();
        	String[] commands = result.getHypothesis().split(" ");
        	for (String command : commands) {
        		System.out.println(command);
        		switch (command) {
        			case "UP":
        				theArc.playerMove(-1, 0, scrapMatrix);
        				loadMatrixGui("updateLoad");
        			break;
        			case "DOWN":
        				theArc.playerMove(1, 0, scrapMatrix);
        				loadMatrixGui("updateLoad");
        			break;
        			case "LEFT":
        				theArc.playerMove(0, -1, scrapMatrix);
        				loadMatrixGui("updateLoad");
        			break;
        			case "RIGHT":
        				theArc.playerMove(0, 1, scrapMatrix);
        				loadMatrixGui("updateLoad");
        			break;
        			case "RECREATE":
        				maze = new DFS(height, width);
        				loadMatrixGui("newLoad");
        			break;
        			case "WAY":
        				Point source = null;
                		for (int i = 0; i < height; ++i) {
                			for (int j = 0; j < width; ++j) {
                				if (scrapMatrix[i][j].equals("P")) {
                					source = new Point(i, j, null);
                				}
                			}
                		}
        			
                		theArc.printWay(BFS.getPath(source, scrapMatrix), scrapMatrix);
        				loadMatrixGui("updateLoad");
        			break;
        			case "CLEAR":
        				theArc.clearWay(scrapMatrix);
        				loadMatrixGui("updateLoad");
        			break;
        			case "DEADENDS":
        				theArc.printDeadEnds(scrapMatrix);
        				loadMatrixGui("updateLoad");
        			break;
        		}
        	}
        }
    }
    
    public void loadMatrixGui(String event) {
    	if (event == "newLoad") {
    	   String[][] temp = maze.getMaze();
    	   scrapMatrix = new String[maze.getHeight()][maze.getWidth()];
    	   for (int i = 0; i < scrapMatrix.length; ++i) {
    		   for (int j = 0; j < scrapMatrix[i].length; ++j) {
    			   scrapMatrix[i][j]= temp[i][j];
    		   }
    	   }
    	   newPanel = new JPanel();
    	   newPanel.setLayout(new GridLayout(maze.getHeight(),maze.getWidth()));
    	   labelMatrix = new JLabel[maze.getHeight()][maze.getWidth()];
    	} else if(event =="updateLoad") {
           scrapMatrix = theArc.getUpdatedMatrix();
           newPanel = new JPanel();
           newPanel.setLayout(new GridLayout(maze.getHeight(), maze.getWidth()));
           newPanel.grabFocus();
    	}
    	for (int i = 0; i < labelMatrix.length; ++i) {
    		for (int j = 0; j < labelMatrix[i].length; ++j) {
    			labelMatrix[i][j]= new mazeObject(scrapMatrix[i][j]);
    		}
    	}
    	cp.add(newPanel);
        System.gc();
        pack();
        setVisible (true);
        newPanel.grabFocus();
    }
    
    public class mazeObject extends JLabel {
    	public mazeObject(String fileName) {
    		fileName = "img/" + fileName + ".png";
    		JLabel fancyLabel;
    		fancyLabel = new JLabel("",new ImageIcon(fileName),JLabel.LEFT);
    		newPanel.add(fancyLabel);
    	}
    }
}