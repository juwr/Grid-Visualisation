package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.History;
import core.Image;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class GUI extends JFrame{
	
	private JPanel[][] field;
	public int width;
	public int height;
	public int increment;
	public volatile boolean paused;
	public int historyIndex;
	public History history;
	public boolean historic;
	public boolean backwards;
	
	private JButton pause;
	private JButton unPause;
	private JButton skipLeft;
	private JButton skipRight;
	private JButton btnBackwards;
	private JLabel lblSkipAmount;
	private JSpinner spinner;
	private int skipAmount;
	
	public GUI(int width, int height) {
		this.width = width;
		this.height = height;
		this.increment = (int)Math.pow(10, 15.0/10.0);
		this.field = new JPanel[height][width];
		this.paused = true;
		this.historyIndex = 0;
		this.history = null;
		this.skipAmount = 1;
		this.historic = false;
		this.backwards = false;
		
		initialize(width, height);
	}
	
	private void initialize(int width, int height) {
		this.setSize(640, 480);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		panel.add(slider);
		
		this.skipLeft = new JButton();
		panel.add(skipLeft);
		
		this.btnBackwards = new JButton();
		panel.add(btnBackwards);
		
		this.pause = new JButton();
		panel.add(pause);
		
		this.unPause = new JButton();
		panel.add(unPause);
		
		this.skipRight = new JButton();
		panel.add(skipRight);
		
		try {	
			pause.setIcon(new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResource("Pause.png"))));
			unPause.setIcon(new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResource("Unpause.png"))));
			skipRight.setIcon(new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResource("SkipRight.png"))));
			skipLeft.setIcon(new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResource("SkipLeft.png"))));
			btnBackwards.setIcon(new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResource("Backwards.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 
		
		
		lblSkipAmount = new JLabel("Skip Amount:");
		panel.add(lblSkipAmount);
		
		
		SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
		spinner = new JSpinner(model);
		spinner.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				skipAmount = (int)spinner.getValue();
			  }
		});
		panel.add(spinner);
		
		if(this.paused) {
			pause.setEnabled(false);
		} else {
			unPause.setEnabled(false);
			skipRight.setEnabled(false);
			skipLeft.setEnabled(false);
		}
		
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!paused) {
					setPaused(true);
				}
				
				
				
			}
		});
		
		btnBackwards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(paused) {
					
					
					if(historyIndex > 0) {
						backwards = true;
						Thread worker = new Thread() {
						    public void run() {
						        history.cycleBackwards();
						    }
						};
						worker.start();
						setPaused(false);
					}
					
					
				}
				
				
				
			}
		});
		
		unPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(paused) {
					backwards = false;
					
					if(historyIndex < history.history.size()-1 && historic == false) {
						historic = true;
						Thread worker = new Thread() {
						    public void run() {
						        history.cycleForward();
						    }
						};
						worker.start();
					}
					
					setPaused(false);
				}
				
				
				
			}
		});
		
		skipRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (paused) {
					displayNextImage(skipAmount);
				}
				
				
			}
		});
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			    
				JSlider source = (JSlider)e.getSource();
				increment = (int)Math.pow(10, source.getValue()/10.0);
				
			}
		});
		
		skipLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (paused) {
					displayPreviousImage(skipAmount);
				}
				
				
			}
		});
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(height, width, 0, 0));
		
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				
				JPanel cell = new JPanel();
				cell.setEnabled(false);
				cell.setBackground(Color.WHITE);
				panel_1.add(cell);
				this.field[i][j] = cell;
				
			}
		}
		this.setVisible(true);
	}
	
	
	
	public void displayImage(Image image) {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				
				if(!field[i][j].getBackground().equals(image.getColor(j, i))) {
					field[i][j].setBackground(image.getColor(j, i));
				}
				
				
				
			}
		}
		
		if(this.history.history.contains(image)) {
			this.historyIndex = this.history.history.indexOf(image);
		}
	}
	
	private void displayPreviousImage(int index) {
		if(this.historyIndex - index <= 0) {
			this.historyIndex = 0;
			this.displayImage(this.history.history.get(0));
		} else {
			this.historyIndex = this.historyIndex - index;
			this.displayImage(this.history.history.get(this.historyIndex));
		}
	}
	
	private void displayNextImage(int index) {
		if(this.historyIndex + index >= this.history.history.size()) {
			this.historyIndex = this.history.history.size() - 1;
			this.displayImage(this.history.history.get(this.history.history.size() - 1));
		} else {
			this.historyIndex = this.historyIndex + index;
			this.displayImage(this.history.history.get(this.historyIndex));
		}
	}
	
	public void setPaused(boolean bool) {
		if(this.paused && !bool) {
			this.pause.setEnabled(true);
			this.unPause.setEnabled(false);
			this.btnBackwards.setEnabled(false);
			this.skipRight.setEnabled(false);
			this.skipLeft.setEnabled(false);
			this.paused = false;
		} else if (!this.paused && bool) {
			this.pause.setEnabled(false);
			this.unPause.setEnabled(true);
			this.btnBackwards.setEnabled(true);
			this.skipRight.setEnabled(true);
			this.skipLeft.setEnabled(true);
			this.paused = true;
		}
	}

}
