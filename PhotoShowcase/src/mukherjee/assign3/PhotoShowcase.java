package mukherjee.assign3;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.event.*;
import java.io.File;

/**
 * 
 * @author Ankita Mukherjee
 *
 */
public class PhotoShowcase extends JFrame {

	private JPanel cpanel, leftPanel, rightPanel, newRight, panel;
	private JButton imageChooser, submit, clear, byDate, displayPhoto;
	private JButton remove, save;
	private JLabel imageLabel, titleLabel, categoryLabel, photographerLabel,
			locationLabel, dateLabel;
	private JLabel picLabel, newTitleLabel, newCategoryLabel,
			newPhotographerLabel, newLocationLabel, newDateLabel;
	private JTextField titleField, categoryField, photographerField,
			locationField;
	private JFormattedTextField dateTextField;
	private JTextField newTitleField, newCategoryField, newPhotographerField,
			newLocationField;
	private JFormattedTextField newDateTextField;
	private JFileChooser fc;
	private JSplitPane splitPane;
	private JComboBox getCategoryCbox;
	private Picture1 pic;
	private Picture1 pic1;
	private CardLayout cardLayout;
	private File currentFile;
	private String photoPath, photoName, keyOfPic1;
	private Map<String, Picture1> map;
	private Map<String, JButton> map1;
	private String categoryDetail;

	/**
	 * Default Constructor to build frame and panels
	 */
	public PhotoShowcase() {
		super("My_Photo_ShowCase");
		cardLayout = new CardLayout();
		cpanel = new JPanel();
		newRight = new JPanel();
		buildRight();
		cpanel.setLayout(cardLayout);
		cpanel.add(rightPanel, "open");
		cpanel.add(newRight, "show");
		newRight.setLayout(new FlowLayout());
		cardLayout.show(cpanel, "open");
		buildLeftPanel();
		map1 = new ConcurrentHashMap<String, JButton>();
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		leftPanel.setPreferredSize(new Dimension(700, 500));
		rightPanel.setPreferredSize(new Dimension(500, 400));
		leftPanel.setBackground(Color.cyan);
		rightPanel.setBackground(Color.cyan);
		newRight.setBorder(BorderFactory.createLineBorder(Color.black));
		newRight.setBackground(Color.YELLOW);

		try {
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			getContentPane().add(splitPane);
			splitPane.setLeftComponent(leftPanel);
			splitPane.setRightComponent(cpanel);
			splitPane.setVisible(true);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "exception occured" + ex);
		}
		map = new HashMap<String, Picture1>();
	}

	/**
	 * Method to build left panel with all components
	 */
	public void buildLeftPanel() {

		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		imageChooser = new JButton("Add Photo to Collection");
		leftPanel.add(imageChooser);
		fc = new JFileChooser();
		imageLabel = new JLabel();
		imageChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setCurrentDirectory(new File("."));
				int result = fc.showOpenDialog(imageChooser);

				if (result == JFileChooser.APPROVE_OPTION) {

					currentFile = fc.getSelectedFile();
					imageLabel.setIcon(new ImageIcon(currentFile.toString()));
					leftPanel.add(imageLabel);
					leftPanel.validate();
					photoName = fc.getSelectedFile().getName();
					photoPath = currentFile.getAbsolutePath();
				}
			}
		});

		// Adding Text field for leftPanel
		titleLabel = new JLabel(" Enter Photo Title", JLabel.RIGHT);
		titleField = new JTextField();
		titleField.setMaximumSize(new Dimension(180, 30));
		titleField.setEditable(true);

		leftPanel.add(titleField);
		leftPanel.add(titleLabel);

		categoryLabel = new JLabel(" Enter Category", JLabel.RIGHT);
		categoryField = new JTextField();
		categoryField.setMaximumSize(new Dimension(180, 30));
		categoryField.setEditable(true);

		leftPanel.add(categoryField);
		leftPanel.add(categoryLabel);

		photographerLabel = new JLabel(" Enter Photographer", JLabel.RIGHT);
		photographerField = new JTextField();
		photographerField.setMaximumSize(new Dimension(180, 30));
		photographerField.setEditable(true);

		leftPanel.add(photographerField);
		leftPanel.add(photographerLabel);

		locationLabel = new JLabel(" Enter Location and Comments", JLabel.RIGHT);
		locationField = new JTextField();
		locationField.setMaximumSize(new Dimension(180, 30));
		locationField.setEditable(true);

		leftPanel.add(locationField);
		leftPanel.add(locationLabel);

		dateLabel = new JLabel(" Enter Date", JLabel.RIGHT);
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		JFormattedTextField dateTextField = new JFormattedTextField(format);
		dateTextField.setMaximumSize(new Dimension(180, 30));
		dateTextField.setEditable(true);
		dateTextField.setValue(new java.util.Date());

		String str = dateTextField.getText();
		dateTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				try {
					Date date1 = format.parse(str);
				} catch (ParseException eb) {
					System.out.println("Wrong Date");
				}
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SLASH))) {
					JOptionPane.showMessageDialog(null,
							"Please Enter Valid Date");
					e.consume();
				}
			}
		});

		leftPanel.add(dateTextField);
		leftPanel.add(dateLabel);

		// Add event handler for Save button

		submit = new JButton("Save");
		leftPanel.add(submit);
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					if (!photoPath.isEmpty()) {
						if ((titleField.getText()).isEmpty()) {
							JOptionPane.showMessageDialog(null,
									"Please enter Title");
							return;
						}
						if ((categoryField.getText()).isEmpty()) {
							JOptionPane.showMessageDialog(null,
									"Please enter Category");
							return;
						}

						else {
							categoryDetail = categoryField.getText();
							createShowComboBox(categoryDetail);
							pic = new Picture1();
							pic.title = titleField.getText();
							pic.filePath = photoPath;
							pic.fileName = photoName;
							pic.category = categoryField.getText();
							pic.photographer = photographerField.getText();
							pic.location = locationField.getText();
							map.put(photoName, pic);
							JOptionPane.showMessageDialog(null, "Photo Added");
						}

					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Photo must be added to collection" + ex);
				}

			}
		});

		clear = new JButton("Clear");
		leftPanel.add(clear);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				titleField.setText("");
				categoryField.setText("");
				photographerField.setText("");
				locationField.setText("");
				dateTextField.setText("");
				imageLabel.setIcon(null);
			}
		});

		leftPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

	/**
	 * Method to build right panel with all components
	 */
	public void buildRight() {
		rightPanel = new JPanel(new FlowLayout());
		panel = new JPanel(new FlowLayout());
		rightPanel.add(panel);
		byDate = new JButton("SortByDate");
		panel.add(byDate);
		getCategoryCbox = new JComboBox<String>();
		getCategoryCbox.addItem("Select a category");
		getCategoryCbox.setEditable(false);
		panel.add(getCategoryCbox);
		panel.setBackground(Color.cyan);
		panel.setPreferredSize(new Dimension(700, 30));

	}

	/**
	 * Method to handle events of combobox
	 */
	public void handleActionListeners() {
		getCategoryCbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				String category = "";
				if (state == ItemEvent.SELECTED) {
					ItemSelectable is = e.getItemSelectable();
					Object selected[] = is.getSelectedObjects();
					if (selected.length != 0) {
						category = (String) selected[0];
						System.out.println("Category In itemchanged:"
								+ category);
					}
					showCollections(pic);
				}
			}
		});
	}

	/**
	 * Method to show combox details
	 * 
	 * @param categoryDetail
	 */
	public void createShowComboBox(String categoryDetail) {
		Boolean check = false;
		for (String key : map.keySet()) {
			Picture1 detail = map.get(key);
			if ((detail.category).equals(categoryDetail)) {
				check = true;
				break;
			}
		}
		if (check == false) {
			getCategoryCbox.addItem(categoryDetail);
		}
		check = false;

		getCategoryCbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (String value : map1.keySet()) {
					JButton deleteValue = map1.get(value);
					rightPanel.remove(deleteValue);
					map1.remove(value);
				}
				String selectedSortCategory = (String) getCategoryCbox
						.getSelectedItem();
				for (String key : map.keySet()) {
					Picture1 detail = map.get(key);
					if (selectedSortCategory.equals(detail.category)) {
						showCollections(detail);
					}
				}
				rightPanel.updateUI();
			}
		});
	}

	/**
	 * Method to show collections
	 * 
	 * @param detail
	 */
	public void showCollections(Picture1 detail) {
		displayPhoto = new JButton(new ImageIcon(detail.getfilePath()));
		displayPhoto.setText(detail.getfileName());
		rightPanel.add(displayPhoto);
		map1.put(detail.getfileName(), displayPhoto);
		rightPanel.validate();
		displayPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cpanel, "show");
				keyOfPic1 = e.getActionCommand();
				pic1 = map.get(keyOfPic1);
				showDetailsOfPicture();
			}
		});
		return;
	}

	/**
	 * Method to show details of each picture
	 */
	public void showDetailsOfPicture() {
		newRight.setLayout(new BoxLayout(newRight, BoxLayout.Y_AXIS));
		newRight.setPreferredSize(new Dimension(100, 200));
		newRight.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		picLabel = new JLabel(new ImageIcon(pic1.getfilePath()));
		newRight.add(picLabel);

		// Adding Text field for leftPanel
		newTitleLabel = new JLabel(" Enter Photo Title", JLabel.RIGHT);
		newTitleField = new JTextField(pic1.getTitle());
		newTitleField.setMaximumSize(new Dimension(180, 30));
		newTitleField.setEditable(true);

		newRight.add(newTitleField);
		newRight.add(newTitleLabel);

		newCategoryLabel = new JLabel(" Enter Category", JLabel.RIGHT);
		newCategoryField = new JTextField(pic1.getCategory());
		newCategoryField.setMaximumSize(new Dimension(180, 30));
		newCategoryField.setEditable(false);

		newRight.add(newCategoryField);
		newRight.add(newCategoryLabel);

		newPhotographerLabel = new JLabel(" Enter Photographer", JLabel.RIGHT);
		newPhotographerField = new JTextField(pic1.getPhotographer());
		newPhotographerField.setMaximumSize(new Dimension(180, 30));
		newPhotographerField.setEditable(true);

		newRight.add(newPhotographerField);
		newRight.add(newPhotographerLabel);

		newLocationLabel = new JLabel(" Enter Location and Comments",
				JLabel.RIGHT);
		newLocationField = new JTextField(pic1.getLocation());
		newLocationField.setMaximumSize(new Dimension(180, 30));
		newLocationField.setEditable(true);

		newRight.add(newLocationField);
		newRight.add(newLocationLabel);

		save = new JButton("Save Details");
		newRight.add(save);
		remove = new JButton("Remove Photo From Collection");
		newRight.add(remove);
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cpanel, "open");
				JButton removeValue = map1.get(keyOfPic1);
				rightPanel.remove(removeValue);
				map.remove(keyOfPic1);
				newRight.removeAll();
				newRight.updateUI();
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cpanel, "open");
				pic1.setTitle(newTitleField.getText());
				pic1.setCategory(newCategoryField.getText());
				pic1.setPhotographer(newPhotographerField.getText());
				pic1.setLocation(newLocationField.getText());
				newRight.removeAll();
				newRight.updateUI();
			}
		});

	}

	/**
	 * Method to display GUI
	 */
	public static void createAndShowGUI() {
		PhotoShowcase window = new PhotoShowcase();
		window.setSize(1500, 1000);
		window.setLocationRelativeTo(null);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
