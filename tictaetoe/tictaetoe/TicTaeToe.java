package tictaetoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TicTaeToe extends JFrame {
	Timer timer;

	JPanel[][] cells = new JPanel[9][9];
	JPanel[] miniboards = new JPanel[9];
	Color[] isMiniboard = { Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
			Color.WHITE, Color.WHITE };
	JPanel empty;
	JButton reset;
	Color BLUE = Color.BLUE;
	Color RED = Color.RED;
	Color YELLOW = Color.YELLOW;
	Color WHITE = Color.WHITE;
	int setMiniBoard[];

	public TicTaeToe() {
		super("UltimateTicTaeToe");
		
		empty = new JPanel();
		reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						cells[i][j].setBackground(YELLOW);
						isMiniboard[i] = Color.WHITE;
					}
				}
			}
		});
		Dimension d = new Dimension(700, 700);
		for (int miniboard = 0; miniboard < 9; miniboard++) {
			for (int cell = 0; cell < 9; cell++) {
				int Miniboard = miniboard;
				int Cell = cell;
				cells[miniboard][cell] = new JPanel();
		
				cells[miniboard][cell].setBackground(YELLOW);
				cells[miniboard][cell].addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseClicked(MouseEvent e) {

						if (cells[Miniboard][Cell].getBackground() == YELLOW) {
							cells[Miniboard][Cell].setBackground(RED);
							setMiniBoard(Miniboard, Cell);
							checkMiniBoard(Miniboard, RED);
							randomMiniBoard();
							int Cell = 0;
							for (int i = 0; i < 9; i++) {
								if (isMiniboard[i] == YELLOW) {
									Cell = i;
								}
							}
							temp t = new temp();
							t.makeTemp(Cell, BLUE);

							// miniMax(Miniboard, Cell, BLUE);

							// checkMiniBoard_RED(num);
						}
					}
				});
				// 파란색 턴으로 바꾼다.
				cells[miniboard][cell].setBackground(YELLOW);
			}
		}
		shape();
		setResizable(true);
		setMinimumSize(d);
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void shape() {
		JPanel cont = new JPanel(new BorderLayout());
		JPanel east = new JPanel(new GridLayout(2, 1, 7, 7));// 2rows 1colomns
		JPanel centre = new JPanel(new GridLayout(3, 3, 7, 7));// 3rows 3colomns
		// setting JPanel arrays as 3x3 GridLayout.
		for (int miniboard = 0; miniboard < 9; miniboard++) {
			miniboards[miniboard] = new JPanel();
			miniboards[miniboard].setLayout(new GridLayout(3, 3, 3, 3));
		}
		// implementing all cells into mini-boards. maybe use doulbe for-loop to
		// do this.
		for (int miniboard = 0; miniboard < 9; miniboard++) {
			for (int cell = 0; cell < 9; cell++) {
				miniboards[miniboard].add(cells[miniboard][cell]);
			}
		}
		for (int miniboard = 0; miniboard < 9; miniboard++) {
			centre.add(miniboards[miniboard]);
		}
		// Reset Button and the Screen.
		east.add(empty);
		east.add(reset);
		cont.add(east, BorderLayout.EAST);
		cont.add(centre, BorderLayout.CENTER);
		setContentPane(cont);
	}

	public void setMiniBoard(int miniboard, int cell) {
	
		isMiniboard[miniboard] = Color.WHITE;
		for (int Miniboard = 0; Miniboard < 9; Miniboard++) {
			for (int Cell = 0; Cell < 9; Cell++) {
				if (cells[Miniboard][Cell].getBackground() != RED && cells[Miniboard][Cell].getBackground() != BLUE) {
					cells[Miniboard][Cell].setBackground(WHITE);
				}
			}
		}
		for (int Miniboard = 0; Miniboard < 9; Miniboard++) {
			if ((cell == Miniboard) && isMiniboard[Miniboard] != RED && isMiniboard[Miniboard] != BLUE) {
				for (int Cell = 0; Cell < 9; Cell++) {
					if (cells[Miniboard][Cell].getBackground() != RED
							&& cells[Miniboard][Cell].getBackground() != BLUE) {
						cells[Miniboard][Cell].setBackground(YELLOW);
					}
				}
				isMiniboard[Miniboard] = YELLOW;
			}
		}
		
	
	}

	public Color checkMiniBoard(int miniboard, Color RED_BLUE) {
		// for (int Miniboard = 0; Miniboard < 9; Miniboard++) {
		for (int i = 0; i < 7; i += 3) {
			if ((cells[miniboard][0 + i].getBackground() == RED_BLUE)
					&& (cells[miniboard][1 + i].getBackground() == RED_BLUE)
					&& (cells[miniboard][2 + i].getBackground() == RED_BLUE)) {
				for (int Cell = 0; Cell < 9; Cell++) {
					cells[miniboard][Cell].setBackground(RED_BLUE);
				}
				isMiniboard[miniboard] = RED_BLUE;
				return RED_BLUE;
			}
		}
		for (int i = 0; i < 3; i++) {
			if ((cells[miniboard][0 + i].getBackground() == RED_BLUE)
					&& (cells[miniboard][3 + i].getBackground() == RED_BLUE)
					&& (cells[miniboard][6 + i].getBackground() == RED_BLUE)) {
				for (int Cell = 0; Cell < 9; Cell++) {
					cells[miniboard][Cell].setBackground(RED_BLUE);
				}
				isMiniboard[miniboard] = RED_BLUE;
				return RED_BLUE;
			}
		}
		if ((cells[miniboard][0].getBackground() == RED_BLUE) && (cells[miniboard][4].getBackground() == RED_BLUE)
				&& (cells[miniboard][8].getBackground() == RED_BLUE)) {
			for (int Cell = 0; Cell < 9; Cell++) {
				cells[miniboard][Cell].setBackground(RED_BLUE);
			}
			isMiniboard[miniboard] = RED_BLUE;
			System.out.println("너 색 뭐냐 : " + isMiniboard[miniboard]);
			return RED_BLUE;
		}
		if ((cells[miniboard][2].getBackground() == RED_BLUE) && (cells[miniboard][4].getBackground() == RED_BLUE)
				&& (cells[miniboard][6].getBackground() == RED_BLUE)) {
			for (int Cell = 0; Cell < 9; Cell++) {
				cells[miniboard][Cell].setBackground(RED_BLUE);
			}
			isMiniboard[miniboard] = RED_BLUE;
			return RED_BLUE;
		}
		return WHITE;
	}

	public void randomMiniBoard() {
		try {
			int notFillMiniBoardCount = 0;
			for (int MiniBoardTest = 0; MiniBoardTest < 9; MiniBoardTest++) {
			}

			ArrayList<Integer> notFillMiniBoard = new ArrayList<Integer>();
			for (int Miniboard = 0; Miniboard < 9; Miniboard++) {
				if (isMiniboard[Miniboard] != YELLOW) {
					notFillMiniBoardCount++;
				}
			}
			if (notFillMiniBoardCount == 9) {
				for (int Miniboard2 = 0; Miniboard2 < 9; Miniboard2++) {
					if (isMiniboard[Miniboard2] == WHITE) {
						notFillMiniBoard.add(Miniboard2);

					}
				}
				int random = (int) (Math.random() * notFillMiniBoard.size());
				System.out.println(random);
				for (int Cell = 0; Cell < 9; Cell++) {
					if (cells[notFillMiniBoard.get(random)][Cell].getBackground() == WHITE) {
						cells[notFillMiniBoard.get(random)][Cell].setBackground(YELLOW);

					}
				}
				isMiniboard[notFillMiniBoard.get(random)] = YELLOW;
			}
		} catch (Exception e) {
			int blue = 0;
			int red = 0;
			for (int i = 0; i < 9; i++) {
				if (isMiniboard[i] == BLUE) {
					blue++;
				}
				if (isMiniboard[i] == RED) {
					red++;
				}
			}
			if (red > blue) {
				System.out.println("RED WIN");
				JOptionPane.showMessageDialog(null, "RED WIN");
			} else {
				System.out.println("BLUE WIN");
				JOptionPane.showMessageDialog(null, "BLUE WIN");
			}
		}
	}

	Color[][] tempColor = new Color[9][9];

	class temp {
		int count = 0;
		boolean warningMiniboard[] = { false, false, false, false, false, false, false, false, false };
		ArrayList<Integer> warningIndex = new ArrayList<Integer>();

		public void warningBoard() {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 3; j++) {
					if ((cells[i][0 + j * 3].getBackground() == RED && cells[i][1 + j * 3].getBackground() == RED)
							|| (cells[i][0 + j * 3].getBackground() == RED
									&& cells[i][2 + j * 3].getBackground() == RED)
							|| (cells[i][1 + j * 3].getBackground() == RED
									&& cells[i][2 + j * 3].getBackground() == RED)) {
						warningMiniboard[i] = true;
					}
				}
				for (int j = 0; j < 3; j++) {
					if ((cells[i][0 + j].getBackground() == RED && cells[i][4 + j].getBackground() == RED)
							|| (cells[i][0 + j].getBackground() == RED && cells[i][6 + j].getBackground() == RED)
							|| (cells[i][4 + j].getBackground() == RED && cells[i][6 + j].getBackground() == RED)) {
						warningMiniboard[i] = true;
					}
				}
				if ((cells[i][0].getBackground() == RED && cells[i][4].getBackground() == RED)
						|| (cells[i][0].getBackground() == RED && cells[i][8].getBackground() == RED)
						|| (cells[i][4].getBackground() == RED && cells[i][8].getBackground() == RED)) {
					warningMiniboard[i] = true;
				}
				if ((cells[i][2].getBackground() == RED && cells[i][4].getBackground() == RED)
						|| (cells[i][2].getBackground() == RED && cells[i][6].getBackground() == RED)
						|| (cells[i][4].getBackground() == RED && cells[i][6].getBackground() == RED)) {
					warningMiniboard[i] = true;
				}
			}
			for (int i = 0; i < 9; i++) {
				if (warningMiniboard[i] == true) {
					warningIndex.add(i);
				}
			}
			System.out.println("경고 미니보드" + warningIndex);
		}

		public void makeTemp(int miniboard, Color RED_BLUE) {
			
			warningBoard();
			
			for (int i = 0; i < 9; i++) {
				if (cells[miniboard][i].getBackground() == YELLOW) {
					count++;
				}
			}
			
			if (count == 9) {
				int random = (int) (Math.random() * 9);
				cells[miniboard][random].setBackground(BLUE);
				setMiniBoard(miniboard, random);
				checkMiniBoard(miniboard, BLUE);
				randomMiniBoard();
			} else {
				Minimax MM = new Minimax();
				int xy[] = new int[2];
				xy = MM.minimax(miniboard, cells, BLUE);
				if (xy[0] == 0 && xy[1] == 0) {
					cells[miniboard][0].setBackground(BLUE);
					setMiniBoard(miniboard, 0);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
				if (xy[0] == 0 && xy[1] == 1) {
					cells[miniboard][1].setBackground(BLUE);
					setMiniBoard(miniboard, 1);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
				if (xy[0] == 0 && xy[1] == 2) {
					cells[miniboard][2].setBackground(BLUE);
					setMiniBoard(miniboard, 2);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
				if (xy[0] == 1 && xy[1] == 0) {
					cells[miniboard][3].setBackground(BLUE);
					setMiniBoard(miniboard, 3);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
				if (xy[0] == 1 && xy[1] == 1) {
					cells[miniboard][4].setBackground(BLUE);
					setMiniBoard(miniboard, 4);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
				if (xy[0] == 1 && xy[1] == 2) {
					cells[miniboard][5].setBackground(BLUE);
					setMiniBoard(miniboard, 5);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
				if (xy[0] == 2 && xy[1] == 0) {
					cells[miniboard][6].setBackground(BLUE);
					setMiniBoard(miniboard, 6);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
				if (xy[0] == 2 && xy[1] == 1) {
					cells[miniboard][7].setBackground(BLUE);
					setMiniBoard(miniboard, 7);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
				if (xy[0] == 2 && xy[1] == 2) {
					cells[miniboard][8].setBackground(BLUE);
					setMiniBoard(miniboard, 8);
					checkMiniBoard(miniboard, BLUE);
					randomMiniBoard();
				}
			}
		}
	}

	public int change(int red_blue) {
		if (red_blue == 1) {
			return 2;
		} else if (red_blue == 2)
			return 1;

		return red_blue;
	}

	class Minimax {

		int score = 10;

		int[][] tempCell = new int[3][3];

		public int[] minimax(int miniboard, JPanel[][] cells, Color RED_BLUE) {
			int yellowminiboard = 0;
			for (int i = 0; i < 9; i++) {
				if (isMiniboard[i] == YELLOW) {
					yellowminiboard = i;
				}
			}
			JPanel[][] j = new JPanel[3][3];
			j[0][0] = cells[yellowminiboard][0];
			j[0][1] = cells[yellowminiboard][1];
			j[0][2] = cells[yellowminiboard][2];
			j[1][0] = cells[yellowminiboard][3];
			j[1][1] = cells[yellowminiboard][4];
			j[1][2] = cells[yellowminiboard][5];
			j[2][0] = cells[yellowminiboard][6];
			j[2][1] = cells[yellowminiboard][7];
			j[2][2] = cells[yellowminiboard][8];

			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 3; k++) {
					if (j[i][k].getBackground() == BLUE) {
						tempCell[i][k] = 1;
					}
					if (j[i][k].getBackground() == RED) {
						tempCell[i][k] = 2;
					}
					if (j[i][k].getBackground() == YELLOW) {
						tempCell[i][k] = 0;
					}
				}
			}

			/***********************************************************/
			Board b = new Board(tempCell);

			long starttime=0;
			long endtime=0;
			b.displayBoard();
			try {
				starttime=System.currentTimeMillis();
				b.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1);
				
			} catch (Exception e) {
				int blue = 0;
				int red = 0;
				for (int i = 0; i < 9; i++) {
					if (isMiniboard[i] == BLUE) {
						blue++;
					}
					if (isMiniboard[i] == RED) {
						red++;
					}
				}
				if (red > blue) {
					JOptionPane.showMessageDialog(null, "RED WIN");
				} else {
					JOptionPane.showMessageDialog(null, "BLUE WIN");
				}

			}
			for (PointsAndScores pas : b.rootsChildrenScore)
				System.out.println("Point: " + pas.point + " Score: " + pas.score);
			b.placeAMove(b.returnBestMove(), 1);
			b.displayBoard();
			int x[] = { b.returnBestMove().x, b.returnBestMove().y };
			endtime=System.currentTimeMillis();
			System.out.println("소요시간 :"+(endtime-starttime));
			return x;
		}

	}
}

/*********************************************************************************************/
class Point {

	int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}

class PointsAndScores {

	int score;
	Point point;

	PointsAndScores(int score, Point point) {
		this.score = score;
		this.point = point;
	}
}

class Board {

	List<Point> availablePoints;
	int[][] board = new int[3][3];

	List<PointsAndScores> rootsChildrenScore = new ArrayList<>();

	public Board(int cells[][]) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = cells[i][j];
			}
		}
	}

	public int evaluateBoard() {
		int score = 0;

		// Check all rows
		for (int i = 0; i < 3; ++i) {
			int blank = 0;
			int X = 0;
			int O = 0;
			for (int j = 0; j < 3; ++j) {
				if (board[i][j] == 0) {
					blank++;
				} else if (board[i][j] == 1) {
					X++;
				} else {
					O++;
				}
			}
			if (X == 3) {
			}
			// System.out.println("X : "+X +"O : "+O+"blank :"+blank);
			score += changeInScore(X, O);
		}

		// Check all columns
		for (int j = 0; j < 3; ++j) {
			int blank = 0;
			int X = 0;
			int O = 0;
			for (int i = 0; i < 3; ++i) {
				if (board[i][j] == 0) {
					blank++;
				} else if (board[i][j] == 1) {
					X++;
				} else {
					O++;
				}
			}
			if (X == 3) {
			}
			// System.out.println("X : "+X +"O : "+O+"blank :"+blank);
			score += changeInScore(X, O);
		}

		int blank = 0;
		int X = 0;
		int O = 0;

		// Check diagonal (first)
		for (int i = 0, j = 0; i < 3; ++i, ++j) {
			if (board[i][j] == 1) {
				X++;
			} else if (board[i][j] == 2) {
				O++;
			} else {
				blank++;
			}
		}
		if (X == 3) {
		}

		score += changeInScore(X, O);
		// System.out.println("X : "+X +"O : "+O+"blank :"+blank);

		blank = 0;
		X = 0;
		O = 0;

		// Check Diagonal (Second)
		for (int i = 2, j = 0; i > -1; --i, ++j) {
			if (board[i][j] == 1) {
				X++;
			} else if (board[i][j] == 2) {
				O++;
			} else {
				blank++;
			}
		}
		if (X == 3) {
		}
		// System.out.println("X : "+X +"O : "+O+"blank :"+blank);
		score += changeInScore(X, O);

		return score;
	}

	private int changeInScore(int X, int O) {
		int change;
		if (X == 3) {
			change = 100;
		} else if (X == 2 && O == 0) {
			change = 10;
		} else if (X == 1 && O == 0) {
			change = 1;
		} else if (O == 3) {
			change = -100;
		} else if (O == 2 && X == 0) {
			change = -10;
		} else if (O == 1 && X == 0) {
			change = -1;
		} else {
			change = 0;
		}
		return change;
	}

	// Set this to some value if you want to have some specified depth limit for
	// search

	int uptoDepth = -1;

	public boolean isGameOver() {
		// Game is over is someone has won, or board is full (draw)
		return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
	}

	public boolean hasXWon() {
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1)
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
			// System.out.println("X Diagonal Win");
			return true;
		}
		for (int i = 0; i < 3; ++i) {
			if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
					|| (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
				// System.out.println("X Row or Column win");
				return true;
			}
		}
		return false;
	}

	public boolean hasOWon() {
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2)
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
			// System.out.println("O Diagonal Win");
			return true;
		}
		for (int i = 0; i < 3; ++i) {
			if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
					|| (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
				// System.out.println("O Row or Column win");
				return true;
			}
		}
		return false;
	}

	public int alphaBetaMinimax(int alpha, int beta, int depth, int turn) throws Exception {
//		if (beta <= alpha) {
//			System.out.println("Pruning at depth = " + depth);
//			if (turn == 1)
//				return Integer.MAX_VALUE;
//			else
//				return Integer.MIN_VALUE;
//		

		if (depth == uptoDepth || isGameOver()) {
			//System.out.println("너 나옴");
			int sc=0;
			if (evaluateBoard() < 0) {
				return evaluateBoard() + depth * 12;
			}
			if (evaluateBoard() > 0) {
				return evaluateBoard() - depth * 12;
			}
		}
		List<Point> pointsAvailable = getAvailableStates();

		if (pointsAvailable.isEmpty())
			return 0;

		if (depth == 0)
			rootsChildrenScore.clear();

		int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;

		for (int i = 0; i < pointsAvailable.size(); ++i) {
			Point point = pointsAvailable.get(i);

			int currentScore = 0;

			if (turn == 1) {
				placeAMove(point, 1);
				currentScore = alphaBetaMinimax(alpha, beta, depth + 1, 2);
				currentScore += depth * 6;
				maxValue = Math.max(maxValue, currentScore);

				// Set alpha
				alpha = Math.max(currentScore, alpha);

				if (depth == 0)
					rootsChildrenScore.add(new PointsAndScores(currentScore, point));
			} else if (turn == 2) {
				placeAMove(point, 2);
				currentScore = alphaBetaMinimax(alpha, beta, depth + 1, 1);
				currentScore -= depth * 6;
				

				minValue = Math.min(minValue, currentScore);

				// Set beta
				beta = Math.min(currentScore, beta);
				if (depth == 0)
					rootsChildrenScore.add(new PointsAndScores(currentScore, point));
			}
			// reset board
			board[point.x][point.y] = 0;

			// If a pruning has been done, don't evaluate the rest of the
			// sibling states
			if (currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE)
				break;
		}
		return turn == 1 ? maxValue : minValue;
	}

	public List<Point> getAvailableStates() {
		availablePoints = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (board[i][j] == 0) {
					availablePoints.add(new Point(i, j));
				}
			}
		}
		return availablePoints;
	}

	public void placeAMove(Point point, int player) {
		board[point.x][point.y] = player; // player = 1 for X, 2 for O
	}

	public Point returnBestMove() {
		int MAX = -100000;
		int best = -1;
		System.out.println("rootsChildrenScore 크기 "+rootsChildrenScore.size());
		for (int i = 0; i < rootsChildrenScore.size(); ++i) {
			if (MAX < rootsChildrenScore.get(i).score) {
				MAX = rootsChildrenScore.get(i).score;
				best = i;
			}
		}
		return rootsChildrenScore.get(best).point;
	}

	public void displayBoard() {
		System.out.println();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
}
