package dx.timesheet;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Me
 */
public class TaskPanel extends javax.swing.JPanel {
    private Image img;
    MouseListener playPauseListener;
    int count = 0;

    
    public TaskPanel(){
        
    }
    /**
     * Creates new form TaskPanel
     */
    public TaskPanel(Image img) {

        initComponents();
           this.img = img;
    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);
       /* panelforScrollPane.setLayout(new BorderLayout());

        panelSubTaskContainer = new JPanel(new GridBagLayout());
        panelSubTaskContainer.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JPanel scrollpanel = new JPanel();
        scrollpanel.setBackground(Color.white);

        panelSubTaskContainer.add(scrollpanel, gbc);
        JScrollPane sPane = new JScrollPane(panelSubTaskContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sPane.getVerticalScrollBar().setUnitIncrement(6);
        sPane.getVerticalScrollBar().setPreferredSize(new Dimension(7, 0));
        panelforScrollPane.add(sPane);
        
        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JPanel panel = new PanelPlayPause();
                //         panel.add(new JLabel("Hello"));
                //        panel.add(new JButton("Hello"));
                //        panel.add(new JButton("Exit"));
                panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                panelSubTaskContainer.add(panel, gbc, count);
                panel.setName("panel" + count);
                count += 1;

                panel.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String pName = panel.getName().toString();
                        System.out.println(panel.getName().toString());
                        System.out.println("Name of Label at this Panel is : " + panel.getComponent(0).getName());
                        Component c = panel.getComponent(0);
                        JLabel lbl = (JLabel) panel.getComponent(0);
                        System.out.println("Text On Label at this Panel is : " + lbl.getText().toString());
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        panel.setBackground(new Color(240, 240, 240));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        panel.setBackground(Color.white);
                    }
                });
                try {
                    PanelPlayPause.lblPlay.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            String s = panel.getName().toString();
                            System.out.println("Container of this element is : " + s);
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            panel.setBackground(new Color(240, 240, 240));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            panel.setBackground(Color.white);
                        }
                    });
                } catch (Exception ex) {
                }
                panelforScrollPane.validate();
                panelforScrollPane.repaint();
            }
        });

        panelforScrollPane.add(add, BorderLayout.SOUTH);*/



        //        PanelPlayPause.lblPlay.addMouseListener(playPauseListener);


    }
    @Override
    public void paintComponent(Graphics g) {
     super.paintComponent(g);
    g.drawImage(img, 0, 0, null);
  }
    public static Document parse(InputStream is) {
        Document ret = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;
        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
            ret = builder.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("unable to load XML: " + ex);
        }
        return ret;
    }

    public void readPlayPauseTasksFromXml() {
        InputStream in = null;
        try {
            URL xmlUrl = new URL("http://pms.trimonks.com/sahil/projpier/index.php/login/xml/lakhan");
            in = xmlUrl.openStream();
            Document doc = parse(in);
            doc.getDocumentElement().normalize();
            System.out.println("Root element of this doc is "
                    + doc.getDocumentElement().getNodeName());
            NodeList listOfCodes = doc.getElementsByTagName("tasks");
            int totalTasks = listOfCodes.getLength();
            System.out.println("Total no of Tasks : " + totalTasks);
            for (int temp = 0; temp < listOfCodes.getLength(); temp++) {
                
                Node nNode = listOfCodes.item(temp);
                String stf = nNode.getNodeValue();
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //       System.out.println("Atribute client : " + eElement.getAttributes().getNamedItem("client_code"));
                    ///     String code = eElement.getAttribute("id");
                    NodeList nodelist = eElement.getElementsByTagName("task_id");
                    Element task_id = (Element) nodelist.item(0);
                    System.out.println("Code Name : " + task_id.getNodeName());
                    System.out.println("Attributes : " + task_id.getChildNodes().item(0).getNodeValue());

                    NodeList nodelist1 = eElement.getElementsByTagName("client_code");
                    Element cl_code = (Element) nodelist1.item(0);
                    System.out.println("Code Name : " + cl_code.getNodeName());
                    System.out.println("Attributes : " + cl_code.getChildNodes().item(0).getNodeValue());

                    NodeList nodelist2 = eElement.getElementsByTagName("project_code");
                    Element pr_code = (Element) nodelist2.item(0);
                    System.out.println("Code Name : " + pr_code.getNodeName());
                    System.out.println("Attributes : " + pr_code.getChildNodes().item(0).getNodeValue());

                    NodeList nodelist3 = eElement.getElementsByTagName("task");
                    Element task1 = (Element) nodelist3.item(0);
                    System.out.println("Code Name : " + task1.getNodeName());
                    System.out.println("Attributes : " + task1.getChildNodes().item(0).getNodeValue());

                    NodeList nodelist4 = eElement.getElementsByTagName("priority");
                    Element priority = (Element) nodelist4.item(0);
                    System.out.println("Code Name : " + priority.getNodeName());
                    System.out.println("Attributes : " + priority.getChildNodes().item(0).getNodeValue());

                    NodeList nodelist5 = eElement.getElementsByTagName("assigned_by");
                    Element assigned_by = (Element) nodelist5.item(0);
                    System.out.println("Code Name : " + assigned_by.getNodeName());
                    System.out.println("Attributes : " + assigned_by.getChildNodes().item(0).getNodeValue());

                    NodeList nodelist6 = eElement.getElementsByTagName("status");
                    Element status1 = (Element) nodelist6.item(0);
                    System.out.println("Code Name : " + status1.getNodeName());
                    System.out.println("Attributes : " + status1.getChildNodes().item(0).getNodeValue());
                }
            }
        } catch (IOException | DOMException ex) {
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class ImagePanel extends JPanel {

        private Image img;

        public ImagePanel(String img) {
            this(new ImageIcon(img).getImage());
        }

        public ImagePanel(Image img) {
            this.img = img;
            Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        panelLogo = new ImagePanel(new ImageIcon(this.getClass().getResource("/dx/timesheet/top_img.png")).getImage());
        lblClose3 = new javax.swing.JLabel();
        lblMinimize3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        seperatorMain = new javax.swing.JSeparator();
        lblLogo = new javax.swing.JLabel();
        lblPic = new javax.swing.JLabel();
        lblSignOut2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblDx = new javax.swing.JLabel();
        panelforScrollPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblRefreshMain = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        setName("panelTask"); // NOI18N

        lblClose3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/close.png"))); // NOI18N

        lblMinimize3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/minimize.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("Timesheet");

        javax.swing.GroupLayout panelLogoLayout = new javax.swing.GroupLayout(panelLogo);
        panelLogo.setLayout(panelLogoLayout);
        panelLogoLayout.setHorizontalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMinimize3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblClose3)
                .addGap(14, 14, 14))
        );
        panelLogoLayout.setVerticalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblMinimize3)
                        .addComponent(lblClose3)))
                .addGap(10, 10, 10))
        );

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/logo.png"))); // NOI18N

        lblPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/user.png"))); // NOI18N
        lblPic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblSignOut2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblSignOut2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblSignOut2.setText("Sign Out");

        lblDx.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblDx.setText("www.designersx.com");

        panelforScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        panelforScrollPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        panelforScrollPane.setPreferredSize(new java.awt.Dimension(296, 227));

        javax.swing.GroupLayout panelforScrollPaneLayout = new javax.swing.GroupLayout(panelforScrollPane);
        panelforScrollPane.setLayout(panelforScrollPaneLayout);
        panelforScrollPaneLayout.setHorizontalGroup(
            panelforScrollPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelforScrollPaneLayout.setVerticalGroup(
            panelforScrollPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/status_online.png"))); // NOI18N

        lblUserName.setText("User");

        jLabel2.setText("| ");

        lblRefreshMain.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblRefreshMain.setText("Refresh");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(seperatorMain, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(panelLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(panelforScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addGap(3, 3, 3))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDx, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(0, 0, 0)
                        .addComponent(lblUserName))
                    .addComponent(lblLogo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(lblSignOut2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(1, 1, 1)
                        .addComponent(lblRefreshMain))
                    .addComponent(lblPic))
                .addGap(4, 4, 4))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lblUserName)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSignOut2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(lblRefreshMain))
                        .addGap(18, 18, 18)
                        .addComponent(lblPic)))
                .addGap(4, 4, 4)
                .addComponent(seperatorMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelforScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDx)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox jComboBox1;
    public javax.swing.JInternalFrame jInternalFrame1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JSeparator jSeparator2;
    public static javax.swing.JLabel lblClose3;
    public static javax.swing.JLabel lblDx;
    public javax.swing.JLabel lblLogo;
    public static javax.swing.JLabel lblMinimize3;
    public static javax.swing.JLabel lblPic;
    public static javax.swing.JLabel lblRefreshMain;
    public static javax.swing.JLabel lblSignOut2;
    public static javax.swing.JLabel lblUserName;
    public static javax.swing.JPanel panelLogo;
    public static javax.swing.JPanel panelforScrollPane;
    public javax.swing.JSeparator seperatorMain;
    // End of variables declaration//GEN-END:variables
}
