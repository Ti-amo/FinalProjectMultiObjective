package main;

import java.awt.Color;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

import algorithm.NSGAII;
import graph.GraphDivision;
import gui.GUIRobotics;
import util.Graph;
import util.Path;
import util.Point;

public class Main {
	public static void main(String[] args) throws IOException {
		final long startTime = System.currentTimeMillis();
		String FILE_URL = "/home/primer/Primer/FINAL PROJECT/nsgaii_test.txt";
		File file = new File(FILE_URL);

		// Tao moi truong
		GUIRobotics gui = new GUIRobotics(1000, 100, 10);
		gui.generateEnvironment("obstacles.txt");

		// Doc du lieu dau vao
		Graph graph = new Graph("obstacles.txt");
		LinkedList<Point> startEndPoints = readPointData("input.txt");
		GraphDivision graphDivision = new GraphDivision(graph, startEndPoints);
		NSGAII nsgaii = new NSGAII(graph, startEndPoints.getFirst(), startEndPoints.getLast());
		try {

//			LinkedList<Point> findPathAfterFixed = nsgaii.getPath();
//
//			for (int i = 0; i < findPath.size() - 1; i++) {
//				gui.canvas.drawLine(findPath.get(i), findPath.get(i + 1), Color.BLACK);
//			}
			LinkedList<Point> findPathAfterFixed = nsgaii.getPathAfterFixed();
			for (int i = 0; i < (findPathAfterFixed.size() - 1); i++) {
				gui.canvas.drawLine(findPathAfterFixed.get(i), findPathAfterFixed.get(i + 1), Color.ORANGE);
			}
			gui.canvas.drawLineStartToEnd(startEndPoints);

			System.out.println("Done");
			System.out.println("Path Distance: " + (double) Math.round(nsgaii.pathDistance * 10000) / 10000);
			System.out.println("Path Smooth: " + (double) Math.round(nsgaii.pathSmooth * 10000) / 10000);
			System.out.println("Path Safety: " + (double) Math.round(nsgaii.pathSafety * 10000) / 10000);
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}

		final long endTime = System.currentTimeMillis();
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Path path : nsgaii.NEWPOP) {
				bw.write("Path :" + "  " + (double) Math.round(path.pathDistance() * 10000) / 10000 + " "
						+ (double) Math.round(path.pathSafety(graph) * 10000) / 10000 + "  "
						+ (double) Math.round(path.pathSmooth() * 10000) / 10000 + "\n");
			}
			bw.write("Total execution time: " + (endTime - startTime) + "\n");
			bw.write("----------------------\n");
			bw.close();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}
		System.out.println("Total execution time: " + (endTime - startTime));
		System.out.println("End!");
	}

	public static LinkedList<Point> readPointData(String filename) throws IOException {
		Scanner scan = new Scanner(new File(filename));
		LinkedList<Point> pointsToVisit = new LinkedList<Point>();
		double x = scan.nextDouble();
		while (x != -1) {
			double y = scan.nextDouble();
			pointsToVisit.addLast(new Point(x, y));
			x = scan.nextDouble();
		}
		scan.close();

		return pointsToVisit;
	}
}