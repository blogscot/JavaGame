package com.diamond.iain.javagame.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIOManager {

	public static int readHighScoreFromFile(String path) {

		// Uses try-with-resources to close Buffered Reader
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return Integer.parseInt(br.readLine());
		} catch (IOException e) {
			System.out.println("Unable to read High Score from file");
			return 0;
		}
	}

	public static void writeHighScoreToFile(String path, Integer score) {
		
		// Uses try-with-resources to close Buffered Writer
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			bw.write(score.toString());
		} catch (IOException e) {
			System.out.println("Unable to write High Score to File");
		}
	}


}
