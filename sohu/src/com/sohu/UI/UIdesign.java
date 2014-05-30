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
import java.util.ArrayList;
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

import javax.swing.SwingConstants;



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
    String newsType = null;
	
	private ArrayList<String> newsIds = new ArrayList<String>();
	
    private ConnectionManager manager = null;    //数据库连接管理器。
    private PreparedStatement pstmt = null;
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
		frame.setTitle("新闻发布平台");
		frame.setSize(331, 530);
		frame.getContentPane().setLayout(null);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 315, 492);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane);

		// 主界面
		final JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(313, 690));
		panel.setLayout(null);
		
		
		
		
		// 所有新闻的正文标签
		final JScrollPane newscontentpanel = new JScrollPane();
		newscontentpanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		newscontentpanel.setBounds(0, 0, 315, 492);
		frame.getContentPane().add(newscontentpanel);
		newscontentpanel.setVisible(false);
		
		final JLabel newContentLabel = new JLabel("New label");
		newContentLabel.setVerticalAlignment(SwingConstants.TOP);//置顶对齐
		newContentLabel.setBounds(0, 0, 315, 492);
		newContentLabel.setPreferredSize(new Dimension(300, 820));//300这个宽度比较适合
		newscontentpanel.setViewportView(newContentLabel);
		
		
		
		
		
		// List所在的滚动面板
		final JScrollPane newsContentlistpanel = new JScrollPane();
		newsContentlistpanel.setBounds(0, 0, 315, 492);
		newsContentlistpanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		newsContentlistpanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(newsContentlistpanel);
		newsContentlistpanel.setVisible(false);
		
		
		
		//所有新闻的列表
		final JList newsContentlist = new JList();
		newsContentlist.setPreferredSize(new Dimension(313, 820));
		newsContentlistpanel.setViewportView(newsContentlist);
		final DefaultListModel model = new DefaultListModel();
		newsContentlist.setModel(model);//给list定义一个模板,并且安装这个模板
		newsContentlist.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				newsContentlistpanel.setVisible(false);
				newscontentpanel.setVisible(true);//从列表到正文的转换
				String newstitle = null;
				String newsdate = null;
				String newsContent = null;
				String newsauthor = null;
				String sql = "SELECT newstitle,newsdate,newscontent,newsauthor FROM "+ newsType +" where newsid = '" + newsIds.get(newsContentlist.getSelectedIndex())+"'";
				manager = new ConnectionManager();

				try {
					pstmt = manager.getConnection().prepareStatement(sql);

					ResultSet resultSet = pstmt.executeQuery();
					if(resultSet.next()) {
						newstitle = resultSet.getString("newstitle");
						newsdate = resultSet.getString("newsdate");
						newsContent = resultSet.getString("newscontent");
						newsauthor = resultSet.getString("newsauthor");
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
				newContentLabel.setText("<html>" + "<h1>" + newstitle + "</h1>" + newsdate + "<br>" + newsContent.replaceAll("\n", "<br>") + "<br>" +  newsauthor + "</html>" );
			}
         });
		
		
		
		

		
        
		

		


		
		
		//头条新闻
		final JButton topNewButton = new JButton("");
		topNewButton.setBounds(10, 10, 131, 159);
		topNewButton.setIcon(new ImageIcon(".\\resource\\top.jpg"));
		panel.add(topNewButton);
		topNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == topNewButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
			        newsType = "top";
					String title = null;
			        String sql_top = "SELECT newstitle,newsid FROM top";
			      
			        manager = new ConnectionManager();

			        try {
			            pstmt = manager.getConnection().prepareStatement(sql_top);

			            ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						if(newsIds!=null)
						{
							newsIds.clear();
						}
			            while(resultSet.next())
			            {
			            	title = resultSet.getString("newstitle"); 
			            	model.add(i,title);//添加条目
			            	newsIds.add(resultSet.getString("newsid"));
			            	i++;
			            }



			        } catch (SQLException ex) {
			            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
			        } finally {
			            try {
			                pstmt.close();
			                manager.close();
			            } catch (SQLException ex) {
			                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
				 //System.out.println("released");
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
				 ///System.out.println("entered");
				 }
				
				 public void mouseClicked(MouseEvent e) {
				 // TODO Auto-generated method stub
				
				 }
				 });
				 textField.getDocument().addDocumentListener(new DocumentListener() {
				 	// 监听文本内容的插入事件；

				 	public void removeUpdate(DocumentEvent e) {
//				 		System.out.println("remove");
				 	}

				 	// 监听文本内容的插入事件；
				 	public void insertUpdate(DocumentEvent e) {
				 		String url = textField.getText();
				 		if (url != null) {
				 			SohuNews news = new SohuNews();

				 			try {
				 				news.parser(url,TOP);

				 			} catch (Exception e1) {
				 				// TODO Auto-generated catch block
				 				e1.printStackTrace();
				 			}
				 			title = news.newsTitle;
				 			
				 		}
				 	}
				 	// 监听文本属性的变化；
				 	public void changedUpdate(DocumentEvent e) {
//				 		System.out.println("change");
				 	}
				 });


				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
        //军事新闻
		final JButton militaryNewButton = new JButton("");
		militaryNewButton.setBounds(10, 179, 131, 159);
		panel.add(militaryNewButton);
		militaryNewButton.setIcon(new ImageIcon(".\\resource\\military.jpg"));
		militaryNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == militaryNewButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "military";
					String title = null;
			        String sql_military = "SELECT newstitle,newsid FROM military";
			        manager = new ConnectionManager();

			        try {
			            pstmt = manager.getConnection().prepareStatement(sql_military);

			            ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						if(newsIds!=null)
						{
							newsIds.clear();
						}
			            while(resultSet.next())
			            {
			            	title = resultSet.getString("newstitle"); 
			            	model.add(i,title);//添加条目
			            	newsIds.add(resultSet.getString("newsid"));
			            	i++;
			            }



			        } catch (SQLException ex) {
			            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
			        } finally {
			            try {
			                pstmt.close();
			                manager.close();
			            } catch (SQLException ex) {
			                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
			 //System.out.println("released");
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
			 ///System.out.println("entered");
			 }
			
			 public void mouseClicked(MouseEvent e) {
			 // TODO Auto-generated method stub
			
			 }
			 });
		textField_2.getDocument().addDocumentListener(new DocumentListener() {
		 	// 监听文本内容的插入事件；

		 	public void removeUpdate(DocumentEvent e) {
		 		
		 	}

		 	// 监听文本内容的插入事件；
		 	public void insertUpdate(DocumentEvent e) {
		 		String url = textField_2.getText();
		 		if (url != null) {
		 			SohuNews news = new SohuNews();

		 			try {
		 				news.parser(url,MILITARY);

		 			} catch (Exception e1) {
		 				// TODO Auto-generated catch block
		 				e1.printStackTrace();
		 			}
		 			title = news.newsTitle;
		 			
		 		}
		 	}
		 	// 监听文本属性的变化；
		 	public void changedUpdate(DocumentEvent e) {
		 		
		 	}
		 });

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
        //旅游新闻
		final JButton travelButton = new JButton("");
		travelButton.setBounds(10, 348, 131, 159);
		panel.add(travelButton);
		travelButton.setIcon(new ImageIcon(".\\resource\\travel.jpg"));
		travelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == travelButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "travel";
					String title = null;
			        String sql_travel = "SELECT newstitle,newsid FROM travel";
			        manager = new ConnectionManager();

			        try {
			            pstmt = manager.getConnection().prepareStatement(sql_travel);

			            ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						if(newsIds!=null)
						{
							newsIds.clear();
						}
			            while(resultSet.next())
			            {
			            	title = resultSet.getString("newstitle"); 
			            	model.add(i,title);//添加条目
			            	newsIds.add(resultSet.getString("newsid"));
			            	i++;
			            }



			        } catch (SQLException ex) {
			            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
			        } finally {
			            try {
			                pstmt.close();
			                manager.close();
			            } catch (SQLException ex) {
			                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
			 //System.out.println("released");
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
			 ///System.out.println("entered");
			 }
			
			 public void mouseClicked(MouseEvent e) {
			 // TODO Auto-generated method stub
			
			 }
			 });
		textField_4.getDocument().addDocumentListener(new DocumentListener() {
		 	// 监听文本内容的插入事件；

		 	public void removeUpdate(DocumentEvent e) {
		 		
		 	}

		 	// 监听文本内容的插入事件；
		 	public void insertUpdate(DocumentEvent e) {
		 		String url = textField_4.getText();
		 		if (url != null) {
		 			SohuNews news = new SohuNews();

		 			try {
		 				news.parser(url,TRAVEL);

		 			} catch (Exception e1) {
		 				// TODO Auto-generated catch block
		 				e1.printStackTrace();
		 			}
		 			title = news.newsTitle;
		 			
		 		}
		 	}
		 	// 监听文本属性的变化；
		 	public void changedUpdate(DocumentEvent e) {
		 		
		 	}
		 });

		
		
		
		
		
		
		
		
		
		
//娱乐新闻
		final JButton yuleButton = new JButton("");
		yuleButton.setBounds(151, 179, 131, 159);
		panel.add(yuleButton);
		yuleButton.setIcon(new ImageIcon(".\\resource\\yule.jpg"));
		yuleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == yuleButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "yule";
					String title = null;
			        String sql_yule = "SELECT newstitle,newsid FROM yule";
			        manager = new ConnectionManager();

			        try {
			            pstmt = manager.getConnection().prepareStatement(sql_yule);

			            ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						if(newsIds!=null)
						{
							newsIds.clear();
						}
			            while(resultSet.next())
			            {
			            	title = resultSet.getString("newstitle"); 
			            	model.add(i,title);//添加条目
			            	newsIds.add(resultSet.getString("newsid"));
			            	i++;
			            }



			        } catch (SQLException ex) {
			            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
			        } finally {
			            try {
			                pstmt.close();
			                manager.close();
			            } catch (SQLException ex) {
			                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
			 //System.out.println("released");
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
			 ///System.out.println("entered");
			 }
			
			 public void mouseClicked(MouseEvent e) {
			 // TODO Auto-generated method stub
			
			 }
			 });
		textField_3.getDocument().addDocumentListener(new DocumentListener() {
		 	// 监听文本内容的插入事件；

		 	public void removeUpdate(DocumentEvent e) {
		 		
		 	}

		 	// 监听文本内容的插入事件；
		 	public void insertUpdate(DocumentEvent e) {
		 		String url = textField_3.getText();
		 		if (url != null) {
		 			SohuNews news = new SohuNews();

		 			try {
		 				news.parser(url,YULE);

		 			} catch (Exception e1) {
		 				// TODO Auto-generated catch block
		 				e1.printStackTrace();
		 			}
		 			title = news.newsTitle;
		 			
		 		}
		 	}
		 	// 监听文本属性的变化；
		 	public void changedUpdate(DocumentEvent e) {
		 		
		 	}
		 });


		
		
		
		
		
		
		
		
		
		
		
		
		
		//体育新闻
		final JButton tiyuButton = new JButton("");
		tiyuButton.setBounds(151, 348, 131, 159);
		panel.add(tiyuButton);
		tiyuButton.setIcon(new ImageIcon(".\\resource\\tiyu.jpg"));
		tiyuButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == tiyuButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "tiyu";
					String title = null;
			        String sql_tiyu = "SELECT newstitle,newsid FROM tiyu";
			        manager = new ConnectionManager();

			        try {
			            pstmt = manager.getConnection().prepareStatement(sql_tiyu);

			            ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						if(newsIds!=null)
						{
							newsIds.clear();
						}
			            while(resultSet.next())
			            {
			            	title = resultSet.getString("newstitle"); 
			            	model.add(i,title);//添加条目
			            	newsIds.add(resultSet.getString("newsid"));
			            	i++;
			            }



			        } catch (SQLException ex) {
			            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
			        } finally {
			            try {
			                pstmt.close();
			                manager.close();
			            } catch (SQLException ex) {
			                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
			 //System.out.println("released");
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
			 ///System.out.println("entered");
			 }
			
			 public void mouseClicked(MouseEvent e) {
			 // TODO Auto-generated method stub
			
			 }
			 });
		textField_5.getDocument().addDocumentListener(new DocumentListener() {
		 	// 监听文本内容的插入事件；

		 	public void removeUpdate(DocumentEvent e) {
		 		
		 	}

		 	// 监听文本内容的插入事件；
		 	public void insertUpdate(DocumentEvent e) {
		 		String url = textField_5.getText();
		 		if (url != null) {
		 			SohuNews news = new SohuNews();

		 			try {
		 				news.parser(url,TIYU);

		 			} catch (Exception e1) {
		 				// TODO Auto-generated catch block
		 				e1.printStackTrace();
		 			}
		 			title = news.newsTitle;
		 			
		 		}
		 	}
		 	// 监听文本属性的变化；
		 	public void changedUpdate(DocumentEvent e) {
		 		
		 	}
		 });


		
		
		
		
		
		
		
		
		
		
		
        //财经新闻
		final JButton financeNewButton = new JButton("");
		financeNewButton.setBounds(151, 10, 131, 159);
		panel.add(financeNewButton);
		financeNewButton.setIcon(new ImageIcon(".\\resource\\finance.jpg"));
		financeNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == financeNewButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "finance";
					String title = null;
			        String sql_finance = "SELECT newstitle,newsid FROM finance";
			        manager = new ConnectionManager();

			        try {
			            pstmt = manager.getConnection().prepareStatement(sql_finance);

			            ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						if(newsIds!=null)
						{
							newsIds.clear();
						}
			            while(resultSet.next())
			            {
			            	title = resultSet.getString("newstitle"); 
			            	model.add(i,title);//添加条目
			            	newsIds.add(resultSet.getString("newsid"));
			            	i++;
			            }



			        } catch (SQLException ex) {
			            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
			        } finally {
			            try {
			                pstmt.close();
			                manager.close();
			            } catch (SQLException ex) {
			                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
			 //System.out.println("released");
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
			 ///System.out.println("entered");
			 }
			
			 public void mouseClicked(MouseEvent e) {
			 // TODO Auto-generated method stub
			
			 }
			 });
		textField_1.getDocument().addDocumentListener(new DocumentListener() {
		 	// 监听文本内容的插入事件；

		 	public void removeUpdate(DocumentEvent e) {
		 		
		 	}

		 	// 监听文本内容的插入事件；
		 	public void insertUpdate(DocumentEvent e) {
		 		String url = textField_1.getText();
		 		if (url != null) {
		 			SohuNews news = new SohuNews();

		 			try {
		 				news.parser(url,FINANCE);

		 			} catch (Exception e1) {
		 				// TODO Auto-generated catch block
		 				e1.printStackTrace();
		 			}
		 			title = news.newsTitle;
		 			
		 		}
		 	}
		 	// 监听文本属性的变化；
		 	public void changedUpdate(DocumentEvent e) {
		 		
		 	}
		 });
		

		
		
		
		
		
		
		
		
		
		
		
		
		//时尚新闻
		final JButton fashionButton = new JButton("");
		fashionButton.setBounds(10, 522, 131, 159);
		panel.add(fashionButton);
		fashionButton.setIcon(new ImageIcon(".\\resource\\fashion.jpg"));
		fashionButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == fashionButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "fashion";
					String title = null;
			        String sql_fashion = "SELECT newstitle,newsid FROM fashion";
			        manager = new ConnectionManager();

			        try {
			            pstmt = manager.getConnection().prepareStatement(sql_fashion);

			            ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						if(newsIds!=null)
						{
							newsIds.clear();
						}
			            while(resultSet.next())
			            {
			            	title = resultSet.getString("newstitle"); 
			            	model.add(i,title);//添加条目
			            	newsIds.add(resultSet.getString("newsid"));
			            	i++;
			            }



			        } catch (SQLException ex) {
			            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
			        } finally {
			            try {
			                pstmt.close();
			                manager.close();
			            } catch (SQLException ex) {
			                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
			 //System.out.println("released");
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
			 ///System.out.println("entered");
			 }
			
			 public void mouseClicked(MouseEvent e) {
			 // TODO Auto-generated method stub
			
			 }
			 });
		textField_6.getDocument().addDocumentListener(new DocumentListener() {
		 	// 监听文本内容的插入事件；

		 	public void removeUpdate(DocumentEvent e) {
		 		
		 	}

		 	// 监听文本内容的插入事件；
		 	public void insertUpdate(DocumentEvent e) {
		 		String url = textField_6.getText();
		 		if (url != null) {
		 			SohuNews news = new SohuNews();

		 			try {
		 				news.parser(url,FASHION);

		 			} catch (Exception e1) {
		 				// TODO Auto-generated catch block
		 				e1.printStackTrace();
		 			}
		 			title = news.newsTitle;
		 			
		 		}
		 	}
		 	// 监听文本属性的变化；
		 	public void changedUpdate(DocumentEvent e) {
		 		
		 	}
		 });

		
		
		
		
		
		
		
		
		
		
		
		
		
		//更多新闻
		final JButton moreButton = new JButton("");
		moreButton.setBounds(151, 522, 131, 159);
		panel.add(moreButton);
		moreButton.setIcon(new ImageIcon(".\\resource\\more.jpg"));
		moreButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == moreButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "more";
					String title = null;
			        String sql_more = "SELECT newstitle,newsid FROM more";
			        manager = new ConnectionManager();

			        try {
			            pstmt = manager.getConnection().prepareStatement(sql_more);

			            ResultSet resultSet = pstmt.executeQuery();
						int i = 0;
						if(newsIds!=null)
						{
							newsIds.clear();
						}
			            while(resultSet.next())
			            {
			            	title = resultSet.getString("newstitle"); 
			            	model.add(i,title);//添加条目
			            	newsIds.add(resultSet.getString("newsid"));
			            	i++;
			            }



			        } catch (SQLException ex) {
			            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
			        } finally {
			            try {
			                pstmt.close();
			                manager.close();
			            } catch (SQLException ex) {
			                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
			 //System.out.println("released");
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
			 ///System.out.println("entered");
			 }
			
			 public void mouseClicked(MouseEvent e) {
			 // TODO Auto-generated method stub
			
			 }
			 });
		textField_7.getDocument().addDocumentListener(new DocumentListener() {
		 	// 监听文本内容的插入事件；

		 	public void removeUpdate(DocumentEvent e) {
		 		
		 	}

		 	// 监听文本内容的插入事件；
		 	public void insertUpdate(DocumentEvent e) {
		 		String url = textField_7.getText();
		 		if (url != null) {
		 			SohuNews news = new SohuNews();

		 			try {
		 				news.parser(url,MORE);

		 			} catch (Exception e1) {
		 				// TODO Auto-generated catch block
		 				e1.printStackTrace();
		 			}
		 			title = news.newsTitle;
		 			
		 		}
		 	}
		 	// 监听文本属性的变化；
		 	public void changedUpdate(DocumentEvent e) {
		 		
		 	}
		 });
	}
	
}

class MyListView extends JLabel implements ListCellRenderer ,MouseListener{
//	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
	public MyListView() {
		//setOpaque(false);//设置控件透明
		//setIconTextGap(5);//设置图标和文本之间的间隙
	}
	

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

//		 if (isSelected) {
//             //this.setIcon(XX);
//             //this.setBorder(new LineBorder(Color.yellow));
//             ((CustomerUI) list.getUI()).setCellHeight(index, 40, 10);
//             //TODO 添加图片
//             return this;
//         } else {
//             //this.setIcon(XX);
//             //this.setBorder(new LineBorder(Color.yellow));
//             ((CustomerUI) list.getUI()).setCellHeight(index, 20);
//             this.setOpaque(true);
//             return this;
//         }
		return this;
	}


	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//鼠标点击以后发生的事件,进入主界面,显示出正文
        //进入正文页面setvisible
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

