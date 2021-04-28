package chess.ia;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import chess.Board;
import chess.UI;
import chess.move.Validator;
import chess.player.AgentPlayer;
import chess.player.Player;

public class Genetic {
	
	private int AGENT_PER_GENERATION;
	private int MAX_GENERATION;
	
	private ArrayList<Genome> genomes = new ArrayList<>();
	private ArrayList<Genome> winners = new ArrayList<>();
	
	private int generationNumber = 0;
	
	
	public Genetic(int GAME_PER_GENERATION, int MAX_GENERATION) {
		
		this.AGENT_PER_GENERATION = 2*GAME_PER_GENERATION;
		this.MAX_GENERATION = MAX_GENERATION;
		
		generateFirstGenomes();
		evaluateGeneration();
		
	}
	
	private void generateFirstGenomes() {
		
		for (int i = 0; i < AGENT_PER_GENERATION; i++) {
			genomes.add(new Genome());
		}
	}
	
	private void evaluateGeneration() {
		
		
		System.out.println("generation " + generationNumber);
		for (int i = 0; i < AGENT_PER_GENERATION; i++) {
			System.out.println(genomes.get(i).toString());
		}

		if (generationNumber > MAX_GENERATION) return;
		generationNumber++;
		
		for (int i = 0; i < AGENT_PER_GENERATION; i = i+2) {
			
			new Thread(new Runnable() {
				
				private int i;
				
				public Runnable init(int i) {
					this.i = i;
					return this;
				}
				
				@Override
				public void run() {
					Board board = new Board(null);
					
					Player whitePlayer = new AgentPlayer(Player.WHITE, board, genomes.get(i));
					Player blackPlayer = new AgentPlayer(Player.BLACK, board, genomes.get(i+1));
					
					int counter = 0;
					while (true){
						if (Validator.getPlayerPositions(Player.WHITE, board.getGrid()).size() == 1
								&& Validator.getPlayerPositions(Player.BLACK, board.getGrid()).size() == 1
								|| counter > 100) {
							Random rand = new Random();
							int result = rand.nextInt(1);
							if (result == 0) {
								addWinner(genomes.get(i+1));
								System.out.println(i + " vs " + (i+1) + " : " + (i+1) + " win");
								break;
							}
							else {
								addWinner(genomes.get(i));
								System.out.println(i + " vs " + (i+1) + " : " + i + " win");
								break;
							}
						}
						if (!whitePlayer.play()) {
							System.out.println(i + " vs " + (i+1) + " : " + (i+1) + " win");
							addWinner(genomes.get(i+1));
							break;
						}
						if (!blackPlayer.play()) {
							System.out.println(i + " vs " + (i+1) + " : " + i + " win");
							addWinner(genomes.get(i));
							break;				
						}
						counter++;
					}
					board.close();
				}
			}.init(i)).start();
		}
	}
	
	public void addWinner(Genome winner) {
		winners.add(winner);
		if (winners.size() == AGENT_PER_GENERATION / 2) {
			logWinners();
			generateMutationsFromWinners();
		}
	}
	
	private void logWinners() {
		FileWriter logWriter;
		try {
			logWriter = new FileWriter("winners.txt");
			
			for (Genome genome : winners) {
				logWriter.write(genome.toString() + "\n");
			}
			
			logWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void generateMutationsFromWinners() {
		
		genomes.clear();
		
		for(Genome genome : winners) {
			
			genomes.add(genome);
			
			Random rand = new Random();
			int x = rand.nextInt(winners.size()-1);
			int y = rand.nextInt(winners.size()-1);
			
			genomes.add(new Genome(winners.get(x), winners.get(y)));
		}
		
		winners.clear();
		evaluateGeneration();
	}
}
