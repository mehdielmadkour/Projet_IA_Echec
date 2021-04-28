package chess;

import java.util.Random;

import chess.ia.Genome;
import chess.move.Validator;
import chess.player.AgentPlayer;
import chess.player.HumanPlayer;
import chess.player.Player;

public class Game {

	protected Board board;
	private UI ui;
	
	public Game() {

		ui = new UI();
		board = new Board(ui);
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void start() {
		Player human = new HumanPlayer(Player.WHITE, board, ui);
		Player agent = new AgentPlayer(Player.BLACK, board, new Genome());

		while (true){
			if (!human.play()) break;
			if (!agent.play()) break;				
		}
	}
	
	public void learn() {
		
		int maxGenerationNumber = 100;
		int n = 5;
		Genome[] genomes = new Genome[2*n];

		for (int i = 0; i < genomes.length; i++) {
			genomes[i] = new Genome();
		}
		
		int generationNumber = 0;
		while(generationNumber < maxGenerationNumber) {
			
			System.out.println("generation " + generationNumber);
			for (int i = 0; i < genomes.length; i++) {
				System.out.println(genomes[i].toString());
			}
			generationNumber++;
			genomes = getNextGeneration(genomes);
		}
		
	}
	
	private Genome[] getNextGeneration(Genome[] genomes) {
		
		Genome[] winners = new Genome[genomes.length /2];
		Genome[] loosers = new Genome[genomes.length /2];
		
		for (int i = 0; i < genomes.length /2; i++) {
			
			Player whitePlayer = new AgentPlayer(Player.WHITE, board, genomes[2*i]);
			Player blackPlayer = new AgentPlayer(Player.BLACK, board, genomes[2*i+1]);
			
			while (true){
				if (Validator.getPlayerPositions(Player.WHITE, board.getGrid()).size() == 1
						&& Validator.getPlayerPositions(Player.BLACK, board.getGrid()).size() == 1) {
					System.out.println("nulle");
					Random rand = new Random();
					int result = rand.nextInt(1);
					if (result == 0) {
						winners[i] = genomes[2*i+1];
						loosers[i] = genomes[2*i];
						break;
					}
					else {
						winners[i] = genomes[2*i];
						loosers[i] = genomes[2*i+1];
						break;
					}
				}
				if (!whitePlayer.play()) {
					System.out.println("black win");
					winners[i] = genomes[2*i+1];
					loosers[i] = genomes[2*i];
					break;
				}
				if (!blackPlayer.play()) {
					System.out.println("white win");
					winners[i] = genomes[2*i];
					loosers[i] = genomes[2*i+1];
					break;				
				}
			}
			
			reset();
		}
		
		for (int i = 0; i < winners.length; i++) {
			genomes[i] = winners[i];
		}
		
		for (int i = winners.length; i < genomes.length; i++) {
			genomes[i] = mutate(winners);
		}
		
		return genomes;
	}
	
	private Genome mutate(Genome[] genomes) {
		
		Random rand = new Random();
		int x = 0;
		int y = 0;
		
		while(x == y) {
			x = rand.nextInt(genomes.length-1);
			y = rand.nextInt(genomes.length-1);
		}
		
		return new Genome(genomes[x], genomes[y]);
	}
	
	private void reset() {
		ui = new UI();
		board = new Board(ui);
	}
}
