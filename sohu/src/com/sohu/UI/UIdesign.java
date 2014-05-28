package com.sohu.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sohu.SohuNews;
import com.sohu.db.ConnectionManager;

public class UIdesign {

	private static final String MORE = "more";
	private static final String FASHION = "fashion";
	private static final String FINANCE = "finance";
	private static final String TIYU = "tiyu";
	private static final String YULE = "yule";
	private static final String TRAVEL = "travel";
	private static final String MILITARY = "military";
	private static final String TOP = "top";
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	String title = null;

	private ConnectionManager manager = null; // 鏁版嵁搴撹繛鎺ョ鐞嗗櫒銆�
	private PreparedStatement pstmt = null;

	// private PreparedStatement pstmt2 = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIdesign window = new UIdesign();
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
	public UIdesign() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("新闻客户端");
		frame.setSize(331, 530);
		frame.getContentPane().setLayout(null);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 315, 492);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane);

		// 涓荤晫闈�
		final JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(313, 820));
		panel.setLayout(null);

		// 鐐瑰ご鏉′互鍚庣殑鐣岄潰
		final JScrollPane topnewpanels = new JScrollPane();
		topnewpanels.setBounds(0, 0, 315, 492);
		topnewpanels
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		topnewpanels
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(topnewpanels);
		// topnewpanels.setLayout(null);
		topnewpanels.setVisible(false);

		// 所有新闻的正文标签
		final JLabel newContentLabel = new JLabel("New label");
		newContentLabel.setBounds(0, 0, 315, 492);
		frame.getContentPane().add(newContentLabel);
		newContentLabel.setVisible(false);



		final JList topNewList = new JList();
		topNewList.setBounds(0, 0, 313, 820);
		topNewList.setPreferredSize(new Dimension(313, 820));
		topnewpanels.setViewportView(topNewList);
		topNewList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				topnewpanels.setVisible(false);
				newContentLabel.setVisible(true);
				String newsContent = null;
				String sql_top = "SELECT newscontent FROM top where newsid = 108";
				// String picname = "SELECT newspic FROM top";
				manager = new ConnectionManager();

				try {
					pstmt = manager.getConnection().prepareStatement(
							sql_top);

					ResultSet resultSet = pstmt.executeQuery();
					if(resultSet.next()) {
						newsContent = resultSet.getString("newscontent");
					}

					// CustomerUI ui = new CustomerUI();
					// topNewList.setUI(ui);
					// topNewList.setCellRenderer(new listView());
					// for(int i = 0;i<cnt_top;i++)
					// {
					// if (resultSet.next()) {
					// title = resultSet.getString("newstitle");
					// }
					//
					// model.add(i, i+title);
					// }

				} catch (SQLException ex) {
					Logger.getLogger(SohuNews.class.getName()).log(
							Level.SEVERE, null, ex);
				} finally {
					try {
						pstmt.close();
						manager.close();
					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					}

				}
				newContentLabel.setText(newsContent );
				
			}
		});
		//topNewList.setCellRenderer(new MyListView());

		// 鐐瑰嚮璐㈢粡浠ュ悗鐨勯〉闈�
		final JScrollPane fiancenewpanels = new JScrollPane();
		fiancenewpanels.setBounds(0, 0, 315, 492);
		fiancenewpanels
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fiancenewpanels
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(fiancenewpanels);
		fiancenewpanels.setVisible(false);

		final JList financeNewList = new JList();
		financeNewList.setBounds(0, 0, 313, 820);
		financeNewList.setPreferredSize(new Dimension(313, 820));
		fiancenewpanels.setViewportView(financeNewList);

		// 鐐瑰嚮鍐涗簨浠ュ悗鐨勭晫闈�
		// final JScrollPane militaryNewpanels = new JScrollPane();
		// militaryNewpanels.setBounds(0, 0, 315, 492);
		// militaryNewpanels
		// .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// militaryNewpanels
		// .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// frame.getContentPane().add(militaryNewpanels);
		// militaryNewpanels.setVisible(false);
		//
		// final JPanel panel3 = new JPanel();
		// panel3.setPreferredSize(new Dimension(313, 820));
		// militaryNewpanels.setViewportView(panel3);
		// panel3.setLayout(null);
		//
		// final JList militaryNewList = new JList();
		// militaryNewList.setBounds(0, 0, 313, 820);
		// panel3.add(militaryNewList);

		final JScrollPane militaryNewpanels = new JScrollPane();
		militaryNewpanels.setBounds(0, 0, 315, 492);
		militaryNewpanels
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		militaryNewpanels
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(militaryNewpanels);
		militaryNewpanels.setVisible(false);

		final JList militaryNewList = new JList();
		militaryNewList.setBounds(0, 0, 313, 820);
		militaryNewList.setPreferredSize(new Dimension(313, 820));
		militaryNewpanels.setViewportView(militaryNewList);

		// 鐐瑰嚮浣撹偛浠ュ悗鐨勭晫闈�
		final JScrollPane tiyuNewpanels = new JScrollPane();
		tiyuNewpanels.setBounds(0, 0, 315, 492);
		tiyuNewpanels
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tiyuNewpanels
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(tiyuNewpanels);
		tiyuNewpanels.setVisible(false);

		final JPanel panel4 = new JPanel();
		panel4.setPreferredSize(new Dimension(313, 820));
		tiyuNewpanels.setViewportView(panel4);
		panel4.setLayout(null);

		final JList tiyuNewList = new JList();
		tiyuNewList.setBounds(0, 0, 313, 820);
		panel4.add(tiyuNewList);

		// 鐐瑰ū涔愪互鍚庣殑鐣岄潰
		final JScrollPane yuleNewpanels = new JScrollPane();
		yuleNewpanels.setBounds(0, 0, 315, 492);
		yuleNewpanels
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		yuleNewpanels
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(yuleNewpanels);
		// yuleNewpanels.setLayout(null);
		yuleNewpanels.setVisible(false);

		final JPanel panel5 = new JPanel();
		panel5.setPreferredSize(new Dimension(313, 820));
		yuleNewpanels.setViewportView(panel5);
		panel5.setLayout(null);

		final JList yuleNewList = new JList();
		yuleNewList.setBounds(0, 0, 313, 820);
		panel5.add(yuleNewList);

		// 鐐规梾娓镐互鍚庣殑鐣岄潰
		final JScrollPane travelpanels = new JScrollPane();
		travelpanels.setBounds(0, 0, 315, 492);
		travelpanels
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		travelpanels
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(travelpanels);
		// travelpanels.setLayout(null);
		travelpanels.setVisible(false);

		final JPanel panel6 = new JPanel();
		panel6.setPreferredSize(new Dimension(313, 820));
		travelpanels.setViewportView(panel6);
		panel6.setLayout(null);

		final JList travelList = new JList();
		travelList.setBounds(0, 0, 313, 820);
		panel6.add(travelList);

		// 鐐规椂灏氫互鍚庣殑鐣岄潰
		final JScrollPane fashionNewpanels = new JScrollPane();
		fashionNewpanels.setBounds(0, 0, 315, 492);
		fashionNewpanels
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fashionNewpanels
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(fashionNewpanels);
		// fashionNewpanels.setLayout(null);
		fashionNewpanels.setVisible(false);

		final JPanel panel7 = new JPanel();
		panel7.setPreferredSize(new Dimension(313, 820));
		fashionNewpanels.setViewportView(panel7);
		panel7.setLayout(null);

		final JList fashionNewList = new JList();
		fashionNewList.setBounds(0, 0, 313, 820);
		panel7.add(fashionNewList);

		// 鐐规椂灏氫互鍚庣殑鐣岄潰
		final JScrollPane moreNewpanels = new JScrollPane();
		moreNewpanels.setBounds(0, 0, 315, 492);
		moreNewpanels
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		moreNewpanels
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(moreNewpanels);
		// moreNewpanels.setLayout(null);
		moreNewpanels.setVisible(false);

		final JPanel panel8 = new JPanel();
		panel8.setPreferredSize(new Dimension(313, 820));
		moreNewpanels.setViewportView(panel8);
		panel8.setLayout(null);

		final JList moreNewList = new JList();
		moreNewList.setBounds(0, 0, 313, 820);
		panel8.add(moreNewList);

		// 澶存潯鏂伴椈
		final JButton topNewButton = new JButton("");
		topNewButton
				.setIcon(new ImageIcon("D:\\work\\sohu\\resource\\澶存潯.jpg"));
		topNewButton.setBounds(10, 10, 131, 159);
		panel.add(topNewButton);
		topNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == topNewButton) {
					scrollPane.setVisible(false);
					topnewpanels.setVisible(true);
					DefaultListModel model = new DefaultListModel();
					topNewList.setModel(model);

					// String sqlCnt_top = "SELECT COUNT(*) FROM top";
					// int cnt_top = 0;
					// manager = new ConnectionManager();
					//
					// try {
					// pstmt =
					// manager.getConnection().prepareStatement(sqlCnt_top);
					//
					// ResultSet cntResultSet = pstmt.executeQuery();
					// if (cntResultSet.next()) {
					// cnt_top = cntResultSet.getInt(1);
					// }
					//
					// } catch (SQLException ex) {
					// Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE,
					// null, ex);
					// } finally {
					// try {
					// pstmt.close();
					// manager.close();
					// } catch (SQLException ex) {
					// Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE,
					// null, ex);
					// }
					//
					// }
					String title = null;
					String sql_top = "SELECT newstitle FROM top";
					// String picname = "SELECT newspic FROM top";
					manager = new ConnectionManager();

					try {
						pstmt = manager.getConnection().prepareStatement(
								sql_top);

						ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						while (resultSet.next()) {
							title = resultSet.getString("newstitle");
							model.add(i, title);// 娣诲姞鏉＄洰
							i++;
						}
						// CustomerUI ui = new CustomerUI();
						// topNewList.setUI(ui);
						// topNewList.setCellRenderer(new listView());
						// for(int i = 0;i<cnt_top;i++)
						// {
						// if (resultSet.next()) {
						// title = resultSet.getString("newstitle");
						// }
						//
						// model.add(i, i+title);
						// }

					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					} finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException ex) {
							Logger.getLogger(SohuNews.class.getName()).log(
									Level.SEVERE, null, ex);
						}

					}

				}
			}
		});
		textField = new JTextField();
		textField.setBounds(10, 10, 131, 159);
		panel.add(textField);
		textField.setColumns(10);

		topNewButton.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("released");
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				textField.setText(null);
				// /System.out.println("entered");
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField.getDocument().addDocumentListener(new DocumentListener() {
			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱

			public void removeUpdate(DocumentEvent e) {
				// System.out.println("remove");
			}

			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱
			public void insertUpdate(DocumentEvent e) {
				String url = textField.getText();
				if (url != null) {
					SohuNews news = new SohuNews();

					try {
						news.parser(url, TOP);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					title = news.newsTitle;

				}
			}

			// 鐩戝惉鏂囨湰灞炴�х殑鍙樺寲锛�
			public void changedUpdate(DocumentEvent e) {
				// System.out.println("change");
			}
		});

		// 鍐涗簨鏂伴椈
		final JButton militaryNewButton = new JButton("");
		militaryNewButton.setBounds(10, 179, 131, 159);
		panel.add(militaryNewButton);
		militaryNewButton.setIcon(new ImageIcon(
				"D:\\work\\sohu\\resource\\鍐涗簨.jpg"));
		militaryNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == militaryNewButton) {
					scrollPane.setVisible(false);
					militaryNewpanels.setVisible(true);
					DefaultListModel model = new DefaultListModel();
					militaryNewList.setModel(model);
					String title = null;
					String sql_military = "SELECT newstitle FROM military";
					manager = new ConnectionManager();

					try {
						pstmt = manager.getConnection().prepareStatement(
								sql_military);

						ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						while (resultSet.next()) {
							title = resultSet.getString("newstitle");
							model.add(i, title);
							i++;
						}
					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					} finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException ex) {
							Logger.getLogger(SohuNews.class.getName()).log(
									Level.SEVERE, null, ex);
						}

					}

				}
			}
		});
		textField_2 = new JTextField();
		textField_2.setBounds(10, 179, 131, 159);
		panel.add(textField_2);
		textField_2.setColumns(10);
		militaryNewButton.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("released");
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				textField.setText(null);
				// /System.out.println("entered");
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField_2.getDocument().addDocumentListener(new DocumentListener() {
			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱

			public void removeUpdate(DocumentEvent e) {

			}

			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱
			public void insertUpdate(DocumentEvent e) {
				String url = textField_2.getText();
				if (url != null) {
					SohuNews news = new SohuNews();

					try {
						news.parser(url, MILITARY);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					title = news.newsTitle;

				}
			}

			// 鐩戝惉鏂囨湰灞炴�х殑鍙樺寲锛�
			public void changedUpdate(DocumentEvent e) {

			}
		});

		// 鏃呮父鏂伴椈
		final JButton travelButton = new JButton("");
		travelButton.setBounds(10, 348, 131, 159);
		panel.add(travelButton);
		travelButton.setIcon(new ImageIcon(".\\resource\\" + TRAVEL));
		travelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == travelButton) {
					scrollPane.setVisible(false);
					travelpanels.setVisible(true);
					DefaultListModel model = new DefaultListModel();
					travelList.setModel(model);
					String title = null;
					String sql_travel = "SELECT newstitle FROM travel";
					manager = new ConnectionManager();

					try {
						pstmt = manager.getConnection().prepareStatement(
								sql_travel);

						ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						while (resultSet.next()) {
							title = resultSet.getString("newstitle");
							model.add(i, title);
							i++;
						}
					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					} finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException ex) {
							Logger.getLogger(SohuNews.class.getName()).log(
									Level.SEVERE, null, ex);
						}

					}

				}
			}
		});

		textField_4 = new JTextField();
		textField_4.setBounds(10, 348, 131, 159);
		panel.add(textField_4);
		textField_4.setColumns(10);
		travelButton.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("released");
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				textField.setText(null);
				// /System.out.println("entered");
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField_4.getDocument().addDocumentListener(new DocumentListener() {
			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱

			public void removeUpdate(DocumentEvent e) {

			}

			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱
			public void insertUpdate(DocumentEvent e) {
				String url = textField_4.getText();
				if (url != null) {
					SohuNews news = new SohuNews();

					try {
						news.parser(url, TRAVEL);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					title = news.newsTitle;

				}
			}

			// 鐩戝惉鏂囨湰灞炴�х殑鍙樺寲锛�
			public void changedUpdate(DocumentEvent e) {

			}
		});

		// 濞变箰鏂伴椈
		final JButton yuleButton = new JButton("");
		yuleButton.setBounds(151, 179, 131, 159);
		panel.add(yuleButton);
		yuleButton.setIcon(new ImageIcon("D:\\work\\sohu\\resource\\濞变箰.jpg"));
		yuleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == yuleButton) {
					scrollPane.setVisible(false);
					yuleNewpanels.setVisible(true);
					DefaultListModel model = new DefaultListModel();
					yuleNewList.setModel(model);
					String title = null;
					String sql_yule = "SELECT newstitle FROM yule";
					manager = new ConnectionManager();

					try {
						pstmt = manager.getConnection().prepareStatement(
								sql_yule);

						ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						while (resultSet.next()) {
							title = resultSet.getString("newstitle");
							model.add(i, title);
							i++;
						}
					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					} finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException ex) {
							Logger.getLogger(SohuNews.class.getName()).log(
									Level.SEVERE, null, ex);
						}

					}

				}
			}
		});
		textField_3 = new JTextField();
		textField_3.setBounds(151, 179, 131, 159);
		panel.add(textField_3);
		textField_3.setColumns(10);
		yuleButton.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("released");
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				textField.setText(null);
				// /System.out.println("entered");
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField_3.getDocument().addDocumentListener(new DocumentListener() {
			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱

			public void removeUpdate(DocumentEvent e) {

			}

			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱
			public void insertUpdate(DocumentEvent e) {
				String url = textField_3.getText();
				if (url != null) {
					SohuNews news = new SohuNews();

					try {
						news.parser(url, YULE);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					title = news.newsTitle;

				}
			}

			// 鐩戝惉鏂囨湰灞炴�х殑鍙樺寲锛�
			public void changedUpdate(DocumentEvent e) {

			}
		});

		// 浣撹偛鏂伴椈
		final JButton tiyuButton = new JButton("");
		tiyuButton.setBounds(151, 348, 131, 159);
		panel.add(tiyuButton);
		tiyuButton.setIcon(new ImageIcon("D:\\work\\sohu\\resource\\浣撹偛.jpg"));
		tiyuButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == tiyuButton) {
					scrollPane.setVisible(false);
					tiyuNewpanels.setVisible(true);
					DefaultListModel model = new DefaultListModel();
					tiyuNewList.setModel(model);
					String title = null;
					String sql_tiyu = "SELECT newstitle FROM tiyu";
					manager = new ConnectionManager();

					try {
						pstmt = manager.getConnection().prepareStatement(
								sql_tiyu);

						ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						while (resultSet.next()) {
							title = resultSet.getString("newstitle");
							model.add(i, title);
							i++;
						}
					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					} finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException ex) {
							Logger.getLogger(SohuNews.class.getName()).log(
									Level.SEVERE, null, ex);
						}

					}

				}
			}
		});
		textField_5 = new JTextField();
		textField_5.setBounds(151, 348, 131, 159);
		panel.add(textField_5);
		textField_5.setColumns(10);
		tiyuButton.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("released");
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				textField.setText(null);
				// /System.out.println("entered");
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField_5.getDocument().addDocumentListener(new DocumentListener() {
			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱

			public void removeUpdate(DocumentEvent e) {

			}

			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱
			public void insertUpdate(DocumentEvent e) {
				String url = textField_5.getText();
				if (url != null) {
					SohuNews news = new SohuNews();

					try {
						news.parser(url, TIYU);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					title = news.newsTitle;

				}
			}

			// 鐩戝惉鏂囨湰灞炴�х殑鍙樺寲锛�
			public void changedUpdate(DocumentEvent e) {

			}
		});

		// 璐㈢粡鏂伴椈
		final JButton financeNewButton = new JButton("");
		financeNewButton.setBounds(151, 10, 131, 159);
		panel.add(financeNewButton);
		financeNewButton.setIcon(new ImageIcon(
				"D:\\work\\sohu\\resource\\璐㈢粡.jpg"));
		financeNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == financeNewButton) {
					scrollPane.setVisible(false);
					fiancenewpanels.setVisible(true);
					DefaultListModel model = new DefaultListModel();
					financeNewList.setModel(model);
					String title = null;
					String sql_finance = "SELECT newstitle FROM finance";
					manager = new ConnectionManager();

					try {
						pstmt = manager.getConnection().prepareStatement(
								sql_finance);

						ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						while (resultSet.next()) {
							title = resultSet.getString("newstitle");
							model.add(i, title);
							i++;
						}
					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					} finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException ex) {
							Logger.getLogger(SohuNews.class.getName()).log(
									Level.SEVERE, null, ex);
						}

					}

				}
			}
		});

		textField_1 = new JTextField();
		textField_1.setBounds(151, 10, 131, 159);
		panel.add(textField_1);
		textField_1.setColumns(10);
		financeNewButton.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("released");
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				textField.setText(null);
				// /System.out.println("entered");
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField_1.getDocument().addDocumentListener(new DocumentListener() {
			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱

			public void removeUpdate(DocumentEvent e) {

			}

			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱
			public void insertUpdate(DocumentEvent e) {
				String url = textField_1.getText();
				if (url != null) {
					SohuNews news = new SohuNews();

					try {
						news.parser(url, FINANCE);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					title = news.newsTitle;

				}
			}

			// 鐩戝惉鏂囨湰灞炴�х殑鍙樺寲锛�
			public void changedUpdate(DocumentEvent e) {

			}
		});

		// 鏃跺皻鏂伴椈
		final JButton fashionButton = new JButton("");
		fashionButton.setBounds(10, 522, 131, 159);
		panel.add(fashionButton);
		fashionButton
				.setIcon(new ImageIcon("D:\\work\\sohu\\resource\\鏃跺皻.jpg"));
		fashionButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == fashionButton) {
					scrollPane.setVisible(false);
					fashionNewpanels.setVisible(true);
					DefaultListModel model = new DefaultListModel();
					fashionNewList.setModel(model);
					String title = null;
					String sql_fashion = "SELECT newstitle FROM fashion";
					manager = new ConnectionManager();

					try {
						pstmt = manager.getConnection().prepareStatement(
								sql_fashion);

						ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						while (resultSet.next()) {
							title = resultSet.getString("newstitle");
							model.add(i, title);
							i++;
						}
					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					} finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException ex) {
							Logger.getLogger(SohuNews.class.getName()).log(
									Level.SEVERE, null, ex);
						}

					}

				}
			}
		});

		textField_6 = new JTextField();
		textField_6.setBounds(10, 522, 131, 159);
		panel.add(textField_6);
		textField_6.setColumns(10);
		fashionButton.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("released");
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				textField.setText(null);
				// /System.out.println("entered");
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField_6.getDocument().addDocumentListener(new DocumentListener() {
			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱

			public void removeUpdate(DocumentEvent e) {

			}

			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱
			public void insertUpdate(DocumentEvent e) {
				String url = textField_6.getText();
				if (url != null) {
					SohuNews news = new SohuNews();

					try {
						news.parser(url, FASHION);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					title = news.newsTitle;

				}
			}

			// 鐩戝惉鏂囨湰灞炴�х殑鍙樺寲锛�
			public void changedUpdate(DocumentEvent e) {

			}
		});

		// 鏇村鏂伴椈
		final JButton moreButton = new JButton("");
		moreButton.setBounds(151, 522, 131, 159);
		panel.add(moreButton);
		moreButton.setIcon(new ImageIcon("D:\\work\\sohu\\resource\\鏇村.jpg"));
		moreButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == moreButton) {
					scrollPane.setVisible(false);
					moreNewpanels.setVisible(true);
					DefaultListModel model = new DefaultListModel();
					moreNewList.setModel(model);
					String title = null;
					String sql_more = "SELECT newstitle FROM more";
					manager = new ConnectionManager();

					try {
						pstmt = manager.getConnection().prepareStatement(
								sql_more);

						ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						while (resultSet.next()) {
							title = resultSet.getString("newstitle");
							model.add(i, title);
							i++;
						}
					} catch (SQLException ex) {
						Logger.getLogger(SohuNews.class.getName()).log(
								Level.SEVERE, null, ex);
					} finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException ex) {
							Logger.getLogger(SohuNews.class.getName()).log(
									Level.SEVERE, null, ex);
						}

					}

				}
			}
		});

		textField_7 = new JTextField();
		textField_7.setBounds(151, 522, 131, 159);
		panel.add(textField_7);
		textField_7.setColumns(10);
		moreButton.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("released");
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				textField.setText(null);
				// /System.out.println("entered");
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField_7.getDocument().addDocumentListener(new DocumentListener() {
			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱

			public void removeUpdate(DocumentEvent e) {

			}

			// 鐩戝惉鏂囨湰鍐呭鐨勬彃鍏ヤ簨浠讹紱
			public void insertUpdate(DocumentEvent e) {
				String url = textField_7.getText();
				if (url != null) {
					SohuNews news = new SohuNews();

					try {
						news.parser(url, MORE);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					title = news.newsTitle;

				}
			}

			// 鐩戝惉鏂囨湰灞炴�х殑鍙樺寲锛�
			public void changedUpdate(DocumentEvent e) {

			}
		});
	}
}

class MyListView extends JLabel implements ListCellRenderer, MouseListener {
	// private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
	public MyListView() {
		// setOpaque(false);//璁剧疆鎺т欢閫忔槑
		// setIconTextGap(5);//璁剧疆鍥炬爣鍜屾枃鏈箣闂寸殑闂撮殭
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		// if (isSelected) {
		// //this.setIcon(XX);
		// //this.setBorder(new LineBorder(Color.yellow));
		// ((CustomerUI) list.getUI()).setCellHeight(index, 40, 10);
		// //TODO 娣诲姞鍥剧墖
		// return this;
		// } else {
		// //this.setIcon(XX);
		// //this.setBorder(new LineBorder(Color.yellow));
		// ((CustomerUI) list.getUI()).setCellHeight(index, 20);
		// this.setOpaque(true);
		// return this;
		// }
		return this;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// 榧犳爣鐐瑰嚮浠ュ悗鍙戠敓鐨勪簨浠�,杩涘叆涓荤晫闈�,鏄剧ず鍑烘鏂�
		// 杩涘叆姝ｆ枃椤甸潰setvisible
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

// class listIcon {
// Icon icon;
// String text;
//
// public listIcon(String icon, String text) {
// this.icon = new ImageIcon(icon);
// this.text = text;
//
// }
//
// public Icon getIcon() {
// return icon;
// }
//
// public String getText() {
// return text;
// }
// }
// class CustomerUI extends BasicListUI {
//
// public CustomerUI() {
// super();
// cellHeights = new int[8];
// }
//
// public void setCellHeight(int index, int value, int defaultHeight) {
// for (int i = 0; i < cellHeights.length; i++) {
// cellHeights[i] = defaultHeight;
// }
// cellHeights[index] = value;
// }
//
// void setCellHeight(int index, int i) {
// cellHeights[index] = i;
// }
// }