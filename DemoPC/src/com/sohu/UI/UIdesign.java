package com.sohu.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.net.Socket;
import java.net.UnknownHostException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import com.sohu.SohuNews;
import com.sohu.db.ConnectionManager;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;



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
	
	private JTextArea titleEdit;
	private JButton saveTitleButton;
	private JTextArea contentEdit;
	private JButton saveContentButton;
	
	String newstitle = null;
	String newsContent = null;
	String saveID;
	
	private ImageIcon background;
	private JPanel imagePanel;

	private int xx, yy;

	
	String title = null;
    String newsType = null;
    int state = 1;
	
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
		frame.setSize(290, 570);
		frame.setUndecorated(true);//设为无边框
		frame.setLocation(250, 80);
		frame.getContentPane().setLayout(null);//框架使用绝对定位
		//移动软件窗体
		frame.addMouseListener(new MouseAdapter() {
			// 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				xx = e.getX();
				yy = e.getY();
			}
		});
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			// 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				Point p = frame.getLocation();
				// 设置窗口的位置
				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
				frame.setLocation(p.x + e.getX() - xx, p.y + e.getY()- yy);
			}
		});
		
		
		
		background = new ImageIcon(".\\resource\\backphoto.jpg");// 背景图片
		  JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
		  // 把标签的大小位置设置为图片刚好填充整个面板
		  label.setBounds(0, 0, background.getIconWidth(),
		    background.getIconHeight());
		  // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		  imagePanel = (JPanel) frame.getContentPane();
		  imagePanel.setOpaque(false);
		  frame.getLayeredPane().setLayout(null);
		  // 把背景图片添加到分层窗格的最底层作为背景
		  frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		frame.getContentPane().setLayout(null);
		
        //ip地址输入框
        final JTextField ipEdit = new JTextField();
        ipEdit.setFont(new Font("Adobe Caslon Pro Bold", Font.PLAIN, 17));
        ipEdit.setBounds(65,145,160,29);
        frame.getContentPane().add(ipEdit);
         
        //主界面的滚动面板
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24,72,243,431);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane);
		scrollPane.setVisible(false);
		
		//ip地址确认按钮
        final JButton ipButton = new JButton("确定");
        ipButton.setIcon(new ImageIcon(".\\resource\\startbutton.jpg"));
        ipButton.setBounds(120,181,58,50);
        ipButton.setContentAreaFilled(false);
        ipButton.setBorderPainted(false);
        frame.getContentPane().add(ipButton);
        ipButton.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        		ipEdit.setVisible(false);
        		ipButton.setVisible(false);
        		scrollPane.setVisible(true);
        		String ip = ipEdit.getText();
        		SohuNews.SERVERIP=ip;//设ip地址
        	}
        });
        
		// 主界面
		final JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(243, 580));
		
		
		
		
		// 所有新闻的正文标签
		final JScrollPane newscontentpanel = new JScrollPane();
		newscontentpanel.setBounds(24,72,243,431);
		frame.getContentPane().add(newscontentpanel);
		newscontentpanel.setVisible(false);
		
		final JLabel newContentLabel = new JLabel("");
		newContentLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
		newContentLabel.setVerticalAlignment(SwingConstants.TOP);//置顶对齐
		newContentLabel.setOpaque(true);//必须设为透明背景色才可以显示出来
		newContentLabel.setBackground(Color.WHITE);
		newscontentpanel.setViewportView(newContentLabel);
		
		
		//编辑标题框
		titleEdit = new JTextArea();	
		titleEdit.setLineWrap(true); 
		titleEdit.setWrapStyleWord(true);

		//保存标题的按钮
		saveTitleButton = new JButton("保存");
		saveTitleButton.setIcon(new ImageIcon(".\\resource\\save.jpg"));
		saveTitleButton.setBounds(186,160, 67, 23);
		saveTitleButton.setContentAreaFilled(false);
		saveTitleButton.setBorderPainted(false);
		frame.getContentPane().add(saveTitleButton);
		saveTitleButton.setVisible(false);
		saveTitleButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String sql = "UPDATE " + newsType + " SET newstitle  = '" + titleEdit.getText() + "' WHERE newsid = '"+ saveID + "'";
				manager = new ConnectionManager();
				
					try {
						pstmt = manager.getConnection().prepareStatement(sql);
						pstmt.execute();//要用execute()而不能用executeQuery()!!!!!!!
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
					finally {
					try {
						pstmt.close();
						manager.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					}
					JOptionPane.showMessageDialog(null, "修改成功", "简易新闻发布平台", JOptionPane.PLAIN_MESSAGE);//显示修改成功以后的对话框
					//更新数据
					Socket socket = null;
					try {
						socket = new Socket(SohuNews.SERVERIP, SohuNews.SERVERPORT);

						PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
						writer.println(sql);
						writer.flush();

						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						writer.close();
						reader.close();
						socket.close();
					} catch (UnknownHostException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
			        
				
				
			}});
		
		// 编辑正文框
		contentEdit = new JTextArea();
		contentEdit.setLineWrap(true); 
		contentEdit.setWrapStyleWord(true);

		// 保存正文的按钮
		saveContentButton = new JButton("保存");
		saveContentButton.setBounds(186,470,67,23);
		saveContentButton.setContentAreaFilled(false);
		saveContentButton.setBorderPainted(false);
		saveContentButton.setIcon(new ImageIcon(".\\resource\\save.jpg"));
		frame.getContentPane().add(saveContentButton);
		saveContentButton.setVisible(false);
		saveContentButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String sql = "UPDATE " + newsType + " SET newscontent  = '" + contentEdit.getText() + "' WHERE newsid = '"+ saveID + "'";
				manager = new ConnectionManager();
				
					try {
						pstmt = manager.getConnection().prepareStatement(sql);
						pstmt.execute();//要用execute()而不能用executeQuery()!!!!!!!
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
					finally {
					try {
						pstmt.close();
						manager.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					}
					JOptionPane.showMessageDialog(null, "修改成功", "简易新闻发布平台", JOptionPane.PLAIN_MESSAGE);
					Socket socket = null;
					try {
						socket = new Socket(SohuNews.SERVERIP, SohuNews.SERVERPORT);

						PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
						writer.println(sql);
						writer.flush();

						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						writer.close();
						reader.close();
						socket.close();
					} catch (UnknownHostException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
				
			}});
		//编辑标题的滚动面板
		final JScrollPane editTitlePanel = new JScrollPane(titleEdit);
		editTitlePanel.setBounds(24,72,243,80);
		frame.getContentPane().add(editTitlePanel);
		editTitlePanel.setVisible(false);
		
		//编辑内容的滚动面板
		final JScrollPane editContentPanel = new JScrollPane(contentEdit);
		editContentPanel.setBounds(24,192,243,270);
		frame.getContentPane().add(editContentPanel);
		editContentPanel.setVisible(false);
		
		//编辑按钮
		final JButton editButton = new JButton("编辑");
		editButton.setIcon(new ImageIcon(".\\resource\\edit.jpg"));
		editButton.setBounds(0,0,54,23);
		newscontentpanel.add(editButton);
		editButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				state =4;
				
				newscontentpanel.setVisible(false);
				editButton.setVisible(false);
				
				
				editTitlePanel.setVisible(true);
				titleEdit.setText(newstitle);
				saveTitleButton.setVisible(true);
				

				editContentPanel.setVisible(true);
				saveContentButton.setVisible(true);
				contentEdit.setText(newsContent);
			}});
		

		    //删除按钮
			final JButton removeButton = new JButton("删除");
			removeButton.setIcon(new ImageIcon(".\\resource\\delete.jpg"));
			removeButton.setBounds(62,0,54,23);
			newscontentpanel.add(removeButton);
			removeButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					removeButton.setVisible(false);
					String sql = "DELETE FROM " + newsType + " WHERE newsid = '" + saveID + "'";
					manager = new ConnectionManager();
					
						try {
							pstmt = manager.getConnection().prepareStatement(sql);
							pstmt.execute();//要用execute()而不能用executeQuery()!!!!!!!
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
						finally {
						try {
							pstmt.close();
							manager.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						}
					
						JOptionPane.showMessageDialog(null, "删除成功", "简易新闻发布平台", JOptionPane.PLAIN_MESSAGE);
				
			}});


		
		
		// List所在的滚动面板
		final JScrollPane newsContentlistpanel = new JScrollPane();
		newsContentlistpanel.setBounds(24,72,243,431);
		newsContentlistpanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(newsContentlistpanel);
		newsContentlistpanel.setVisible(false);
		
		
		
		//所有新闻的列表
		final JList newsContentlist = new JList();
		newsContentlist.setPreferredSize(new Dimension(243, 820));//List的长度应该也有根据实际来变化
		newsContentlistpanel.setViewportView(newsContentlist);
		final DefaultListModel model = new DefaultListModel();
		newsContentlist.setModel(model);//给list定义一个模板,并且安装这个模板
		newsContentlist.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				newsContentlistpanel.setVisible(false);
				newscontentpanel.setVisible(true);//从列表到正文的转换
				state = 3;//在第三页
				editButton.setVisible(true);
				removeButton.setVisible(true);
				String newsdate = null;	
				String newsauthor = null;
				saveID = newsIds.get(newsContentlist.getSelectedIndex());
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
				View labelView = BasicHTML.createHTMLView(newContentLabel, newContentLabel.getText());			
				int labelWidth = 235;			
				int labelHeight = 500;				
				labelView.setSize(labelWidth, labelHeight);				
				// 根据限定宽度计算文本内容换行后的真实高度.			
				int trueHight = (int) labelView.getMinimumSpan(View.Y_AXIS);	
				newContentLabel.setPreferredSize(new Dimension(labelWidth,trueHight));
			}
		});
		
		
		JButton exitButton = new JButton("");
		exitButton.setBounds(51, 518, 63, 11);
		frame.getContentPane().add(exitButton);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(1);
			}});
		
		
		
		//主页键
		JButton homeButton = new JButton("");
		homeButton.setBounds(113, 518, 64, 11);
		homeButton.setContentAreaFilled(false);
		homeButton.setBorderPainted(false);

		frame.getContentPane().add(homeButton);
		homeButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(state == 2)//在List页
				{
					newsContentlistpanel.setVisible(false);
					scrollPane.setVisible(true);
					state = 1;
				}
				else if(state == 3)//在正文页
				{
					newscontentpanel.setVisible(false);
					scrollPane.setVisible(true);
					state = 1;
				}
				else if(state == 4){				
					editTitlePanel.setVisible(false);
					editContentPanel.setVisible(false);
					saveContentButton.setVisible(false);
					saveTitleButton.setVisible(false);
					scrollPane.setVisible(true);
					state = 1;
				}
			}});
		
		
		
		
        //返回键 
		JButton returnButton = new JButton("");
		returnButton.setBounds(175,518,63,11);
		returnButton.setContentAreaFilled(false);
		returnButton.setBorderPainted(false);
		frame.getContentPane().add(returnButton);
		returnButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(state == 2)
				{
					newsContentlistpanel.setVisible(false);
					scrollPane.setVisible(true);
					state = 1;
				}
				else if(state == 3)
				{
					newscontentpanel.setVisible(false);
					newsContentlistpanel.setVisible(true);
					state = 2;
				}
				else if(state == 4){				
					editTitlePanel.setVisible(false);
					editContentPanel.setVisible(false);
					saveContentButton.setVisible(false);
					saveTitleButton.setVisible(false);
					newscontentpanel.setVisible(true);
					state = 3;
				}
			
			}});
		panel.setLayout(null);
		

		


		
		
		//头条新闻
		final JButton topNewButton = new JButton("");
		topNewButton.setBounds(5, 5, 106, 136);
		topNewButton.setIcon(new ImageIcon(".\\resource\\top.jpg"));
		panel.add(topNewButton);
		topNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == topNewButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
			        newsType = "top";
			        state = 2;
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
							model.clear();
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
		textField.setBounds(5, 5, 106, 136);
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
		militaryNewButton.setBounds(5, 148, 106, 136);
		panel.add(militaryNewButton);
		militaryNewButton.setIcon(new ImageIcon(".\\resource\\military.jpg"));
		militaryNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == militaryNewButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "military";
			        state = 2;
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
							model.clear();
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
		textField_2.setBounds(5, 148, 106, 136);
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
		travelButton.setBounds(5, 291, 106, 136);
		panel.add(travelButton);
		travelButton.setIcon(new ImageIcon(".\\resource\\travel.jpg"));
		travelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == travelButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "travel";
			        state = 2;
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
							model.clear();
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
		textField_4.setBounds(5, 291, 106, 136);
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
		yuleButton.setBounds(115, 148, 106, 136);
		panel.add(yuleButton);
		yuleButton.setIcon(new ImageIcon(".\\resource\\yule.jpg"));
		yuleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == yuleButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "yule";
			        state = 2;
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
							model.clear();
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
		textField_3.setBounds(115, 148, 105, 136);
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
		tiyuButton.setBounds(115, 291, 106, 136);
		panel.add(tiyuButton);
		tiyuButton.setIcon(new ImageIcon(".\\resource\\tiyu.jpg"));
		tiyuButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == tiyuButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "tiyu";
			        state = 2;
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
							model.clear();
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
		textField_5.setBounds(115, 291, 106, 136);
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
		financeNewButton.setBounds(115, 5, 106, 136);
		panel.add(financeNewButton);
		financeNewButton.setIcon(new ImageIcon(".\\resource\\finance.jpg"));
		financeNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == financeNewButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "finance";
			        state = 2;
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
							model.clear();
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
		textField_1.setBounds(115, 5, 105, 136);
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
		fashionButton.setBounds(5, 434, 106, 136);
		panel.add(fashionButton);
		fashionButton.setIcon(new ImageIcon(".\\resource\\fashion.jpg"));
		fashionButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == fashionButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "fashion";
			        state = 2;
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
							model.clear();
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
		textField_6.setBounds(5, 434, 106, 136);
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
		moreButton.setBounds(115, 434, 106, 136);
		panel.add(moreButton);
		moreButton.setIcon(new ImageIcon(".\\resource\\more.jpg"));
		moreButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == moreButton) {
					scrollPane.setVisible(false);
					newsContentlistpanel.setVisible(true);
					newsType = "more";
			        state = 2;
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
							model.clear();
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
		textField_7.setBounds(115, 434, 106, 136);
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

