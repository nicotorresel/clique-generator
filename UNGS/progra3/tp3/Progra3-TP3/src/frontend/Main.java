package frontend;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import backend.Graph;
import backend.Node;
import backend.Solution;
import backend.Solver;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JScrollPane;

public class Main {

	private JFrame frame;
	private JTextField path;
	private JButton openPath;
	private JFileChooser chooser;
	private JButton confirmar;
	private JButton salir;
	private Graph graph;
	private Solver solver;
	private Solution solution;
	private JTextArea result;
	private Long start;
	private Long end;
	private double time;
	private JScrollPane scrollPane;
	private ImageIcon image;
	private JLabel imageTitle;
	private JLabel band;
	private JButton reset;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		image = new ImageIcon(getClass().getResource("graph.png"));

		
		
	    graph = new Graph();
	    
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		openPath = new JButton("Choose File");
		openPath.setFont(new Font("Dialog", Font.PLAIN, 13));
	    openPath.setBackground(new Color(230,230,250));
	    openPath.setForeground(new Color(88, 0, 116));
	    openPath.setOpaque(true);
		openPath.setBorder(new LineBorder(new Color(88, 0, 116), 1, true));
		openPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				openPath.setBorder(new LineBorder(new Color(255,97,126)));
				openPath.setToolTipText("Upload a File in JSON format with the Graph");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				openPath.setBorder(new LineBorder(new Color(88,0,116)));
			}
		});
		openPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select JSON file");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON FILES", "JSON", "json");
				chooser.setFileFilter(filter);

				try {
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						String input = chooser.getSelectedFile().getAbsolutePath();
						path.setText(input);
					}
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		openPath.setBounds(500, 219, 123, 21);
		frame.getContentPane().add(openPath);
		
		path = new JTextField();
		path.setBounds(181, 215, 319, 29);
		frame.getContentPane().add(path);
		path.setColumns(10);
		
		confirmar = new JButton("Generate Clique");
		confirmar.setBorderPainted(false);
		confirmar.setForeground(new Color(255, 255, 255));
		confirmar.setBackground(new Color(88,0,116));
		confirmar.setOpaque(true);
		confirmar.setBorder(new LineBorder(new Color(88, 0, 116), 1, true));
		confirmar.setFont(new Font("Dialog", Font.PLAIN, 15));
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!path.getText().equals("") && Graph.readJSON(path.getText())!=null) {
					path.setEditable(false);
					path.setEnabled(false);
					openPath.setEnabled(false);	
	
					graph = Graph.readJSON(path.getText());		

					//make the solution with processing time for the clique
					start = System.currentTimeMillis();
					solver = new Solver(graph);
					solution = solver.solution();
					end = System.currentTimeMillis();
					time = (end-start)/1000.0;
					
					result.setText(Integer.toString(graph.getNodes().size())+" Nodes analized in: "+Double.toString(time)+ " Seconds.\n\n");
					result.setText(result.getText()+"The max weight Clique of the Graph is: \n");
					for (Node n : solution.getNodes()) {
						result.setText(result.getText()+"\n"+n.toString());
					}
					result.setText(result.getText()+"\n");
					result.setText(result.getText()+"\nThe total weight of the Clique is: "+solution.getWeight());
					confirmar.setEnabled(false);
					
					
				}
				else {
					JOptionPane.showMessageDialog(frame, "To generate a Max Weight Clique you must input a valid graph/Path ");
				}
			}
		});
		confirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				confirmar.setToolTipText("When you press Generate Clique you will not be able to change the Path/Graph");
				confirmar.setBackground(new Color(255,97,126));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				confirmar.setBackground(new Color(88,0,116));
			}
		});
		confirmar.setBounds(283, 256, 202, 42);
		frame.getContentPane().add(confirmar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(240, 248, 255));
		scrollPane.setBorder(null);
		scrollPane.setBounds(273, 347, 309, 170);
		frame.getContentPane().add(scrollPane);

		result = new JTextArea();
		result.setBackground(new Color(0, 192, 145));
		scrollPane.setViewportView(result);
		result.setBorder(null);
		result.setEditable(false);

		//boton para salir
		salir = new JButton("Exit");
		salir.setBorderPainted(false);
		salir.setContentAreaFilled(false);
		salir.setForeground(new Color(255, 255, 255));
		salir.setBackground(new Color(88,0,116));
		salir.setOpaque(true);
		salir.setBorder(new LineBorder(new Color(88, 0, 116), 1, true));
		salir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				salir.setBackground(new Color(255,97,126));
				salir.setToolTipText("Exit");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				salir.setBackground(new Color(88,0,116));
			}
		});
		salir.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		salir.setBounds(730, 551, 64, 21);
		frame.getContentPane().add(salir);
		
		reset = new JButton("Reset");
		reset.setBorderPainted(false);
		reset.setContentAreaFilled(false);
		reset.setForeground(new Color(255, 255, 255));
		reset.setBackground(new Color(88,0,116));
		reset.setOpaque(true);
		reset.setBorder(new LineBorder(new Color(88, 0, 116), 1, true));
		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				reset.setBackground(new Color(255,97,126));
				reset.setToolTipText("Reset all Fields to Load a new Graph");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				reset.setBackground(new Color(88,0,116));
			}
		});
		reset.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				path.setEnabled(true);
				path.setEditable(true);
				path.setText("");
				openPath.setEnabled(true);
				confirmar.setEnabled(true);
				
				result.setText("");
				
			}
		});
		reset.setBounds(660, 551, 64, 21);
		frame.getContentPane().add(reset);
		
		imageTitle = new JLabel("New label");
		imageTitle.setFocusable(false);
		imageTitle.setBounds(242, 6, 281, 201);
		Image img = image.getImage();
		Image img1 = img.getScaledInstance(imageTitle.getWidth(), imageTitle.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon i = new ImageIcon(img1);
		imageTitle.setIcon(i);
		frame.getContentPane().add(imageTitle);
		
		band = new JLabel("");
		band.setBackground(new Color(0,192,145));
		band.setOpaque(true);
		band.setBounds(0, 321, 800, 218);
		frame.getContentPane().add(band);
		
		JLabel lblPath = new JLabel("Path: ");
		lblPath.setBounds(146, 221, 35, 16);
		frame.getContentPane().add(lblPath);
		
	}
}
