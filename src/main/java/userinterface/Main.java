package userinterface;
import classes.ImageIconConverter;
import classes.model.ChiTietMonHoc;
import classes.model.HocPhan;
import components.table.AttributiveCellTableModel;
import components.table.MultiSpanCellTable;
import components.table.AttributiveCellRenderer;
import components.event.KeyListenerMoveRow;
import components.table.DefaultCellAttribute;
import components.table.TableHeaderRenderer;
import connection.ClientExecute;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Main extends javax.swing.JFrame {
    private final MultiSpanCellTable tblLichhoc;
    private final ClientExecute client;
    public static final Color df_BG_CELL = Color.decode("#F0F0DC");
    public static final Color df_FG_CELL = Color.WHITE;
    public static final String[] dsThu = new String[]{"Thứ hai","Thứ ba","Thứ tư",
                                                      "Thứ năm","Thứ sáu","Thứ bảy",
                                                      "Chủ nhật"};
    private ExecutorService ex = Executors.newFixedThreadPool(1);
    public Main() {
        initComponents();
        AttributiveCellTableModel table = new AttributiveCellTableModel(dsThu,14){
                                                @Override
                                                public boolean isCellEditable(int row,int column)
                                                {
                                                    return false;
                                                }
                                            };
        tblLichhoc = new MultiSpanCellTable(table);
        jScrollPane3.getViewport().add(tblLichhoc);
        addOn();
        client = new ClientExecute();
    }
    private void addOn()
    {
        //setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        javax.swing.ImageIcon icon = ImageIconConverter.getScaledImage(new javax.swing.ImageIcon("src/main/resources/images/logoSGU.png"),400,400);
        FrameDragListener frameDrag = new FrameDragListener(this);
        setIconImage(icon.getImage());
        getRootPane().setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        addMouseListener(frameDrag);
        addMouseMotionListener(frameDrag);
        btnOption2.setVisible(false);
        /*--------------------Thiet ke lai Table-------------------*/
        
            tblTiethoc.getTableHeader().setPreferredSize(new java.awt.Dimension(
                                                            tblLichhoc.getTableHeader().getWidth(),
                                                            25
                                                        ));
            tblLichhoc.getTableHeader().setPreferredSize(new java.awt.Dimension(
                                                            tblTiethoc.getTableHeader().getWidth(),
                                                            25
                                                        ));
            tblMonhoc.getTableHeader().setPreferredSize(new java.awt.Dimension(
                                                            tblMonhoc.getTableHeader().getWidth(),
                                                            25
                                                        ));
            java.awt.Color background;
            java.awt.Color foreground;
            java.awt.Font textFont; 
            textFont = new java.awt.Font("Tahoma",java.awt.Font.BOLD,11);
            background = Color.WHITE;
            foreground = Color.WHITE;
            setHeaderCellStyle(tblTiethoc, background, foreground);
            background = Color.decode("#6699CC");
            setHeaderCellStyle(tblLichhoc, background, foreground, textFont);
            setHeaderCellStyle(tblMonhoc,background,foreground, textFont);
            setCenteredAlignmentTableBodyCell(tblTiethoc);
            tblLichhoc.setRowHeight(25);
            setReorderingColumn(tblMonhoc, false);
            setMyCellStyle(tblLichhoc);
            tblLichhoc.setBackground(java.awt.Color.WHITE);
            tblLichhoc.setSelectionBackground(java.awt.Color.decode("#E6E6FA"));
            tblMonhoc.setSelectionBackground(java.awt.Color.decode("#E6E6FA"));
            tblMonhoc.setSelectionForeground(java.awt.Color.BLACK);
            tblMonhoc.setBackground(java.awt.Color.WHITE);
            tblMonhoc.remove(0);
            ((DefaultTableModel)tblMonhoc.getModel()).setNumRows(0);
        /*------------------------------------------------------------*/





        /*-------------------Them su kien-----------------------------*/
        
            tblMonhoc.addKeyListener(new KeyListenerMoveRow(tblMonhoc));
            tblLichhoc.addMouseMotionListener(new MouseAdapter(){
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e)
                {
                    int rowIndex = tblLichhoc.rowAtPoint(e.getPoint());
                    int columnIndex = tblLichhoc.columnAtPoint(e.getPoint());
                    Object object = tblLichhoc.getValueAt(rowIndex, columnIndex);
                    tblLichhoc.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
                    if(object != null)
                    {
                        tblLichhoc.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
                    }
                   tblLichhoc.changeSelection(rowIndex, columnIndex, false, false);
                }
            });
            tblLichhoc.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override
                public void mouseExited(java.awt.event.MouseEvent e)
                {
                    tblLichhoc.clearSelection();
                }
            });
            tblMonhoc.addMouseMotionListener(new MouseAdapter(){
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e)
                {
                    int rowIndex = tblMonhoc.rowAtPoint(e.getPoint());
                    int columnIndex = tblMonhoc.columnAtPoint(e.getPoint());
                   // tblMonhoc.changeSelection(rowIndex, columnIndex, false, false);
                }
            });
           
            /*tblMonhoc.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override
                public void mouseExited(java.awt.event.MouseEvent e)
                {
                    tblMonhoc.clearSelection();
                }
            });*/
    }

    private void setTable(ArrayList<ChiTietMonHoc> list){
        clearTable();
        AttributiveCellTableModel model = (AttributiveCellTableModel) tblLichhoc.getModel();
      //  model.setRowCount(0);
       // model.setColumnCount(0);
        DefaultCellAttribute cellAttr = (DefaultCellAttribute) model.getCellAttribute();
        list.stream().map(ct -> {
            model.setValueAt(ct, ct.getTietBatDau()-1, Integer.parseInt(ct.getThu())-2);
            return ct;
        }).forEachOrdered(ct -> {
            int[] rowspan = new int[ct.getSotiet()];
            int rowStart = ct.getTietBatDau()-1;
            for (int i = 0; i < ct.getSotiet(); i++) {
                rowspan[i] = rowStart;
                rowStart++;
            }
            cellAttr.combine(rowspan, new int[]{Integer.parseInt(ct.getThu())-2});
        });
    }

    private void clearTable(){
        AttributiveCellTableModel model = (AttributiveCellTableModel) tblLichhoc.getModel();
        DefaultCellAttribute cellAttr = (DefaultCellAttribute) model.getCellAttribute();
        for (int i = 0; i<7; i++)
            for (int j = 0; j<14; j++) {
                model.setValueAt(null, j, i);
                if(cellAttr.getSpan(j,i).length>1){
                    cellAttr.split(j,i);
                }
                cellAttr.combine(new int[]{j}, new int[]{i});
            }
    }

    private void clearList(){
        DefaultTableModel model = (DefaultTableModel) tblMonhoc.getModel();
        model.setRowCount(0);
        //model.setColumnCount(0);
    }

    private static ArrayList<ChiTietMonHoc> getListCTMHFromJsonArray(JSONArray getData ){
        ArrayList<ChiTietMonHoc> ct = new ArrayList<>();
        if (getData != null) {

            //Iterating JSON array
            for (int i=0;i<getData.length();i++){

                //Adding each element of JSON array into ArrayList
                // System.out.println(getData.get(i));
                Gson gson = new Gson();
                ChiTietMonHoc ctmh = gson.fromJson(getData.get(i).toString(),ChiTietMonHoc.class);
                System.out.println(ctmh);
                ct.add(ctmh);
            }
        }
        return ct;
    }

    private void setReorderingColumn(javax.swing.JTable table, boolean allowReorder)
    {
        table.getTableHeader().setReorderingAllowed(allowReorder);
    }
    private void setHeaderCellStyle(javax.swing.JTable table, java.awt.Color background, java.awt.Color foreground)
    {
        for(int i=0;i<table.getColumnCount();i++)
        {
            table.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer(background,foreground));
            table.getColumnModel().getColumn(i).setResizable(false);
        }
    }
    private void setHeaderCellStyle(javax.swing.JTable table, java.awt.Color background, java.awt.Color foreground, java.awt.Font TextFont)
    {
        for(int i=0;i<table.getColumnCount();i++)
        {
            table.getColumnModel().getColumn(i).setResizable(false);
            table.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer(background,foreground, TextFont));
        }
    }
    private void setMyCellStyle(javax.swing.JTable table)
    {
        for(int i=0;i<table.getColumnCount();i++)
        {
            table.getColumnModel().getColumn(i).setResizable(false);
            table.getColumnModel().getColumn(i).setCellRenderer(new AttributiveCellRenderer());
        }
    }
    private void setCenteredAlignmentTableBodyCell(javax.swing.JTable table){
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        for(int i=0;i<table.getColumnCount();i++)
        {
            table.getColumnModel().getColumn(i).setResizable(false);
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblLogout = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        inpSubjectID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnMaMonHoc = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMonhoc = new javax.swing.JTable();
        btnOption2 = new javax.swing.JButton();
        btnOption3 = new javax.swing.JButton();
        btnOption4 = new javax.swing.JButton();
        btnOption1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTiethoc = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Xếp thời khoá biểu SGU");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1090, 690));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(246, 246, 246));
        jPanel1.setPreferredSize(new java.awt.Dimension(1084, 40));

        lblLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Close_24px.png"))); // NOI18N
        lblLogout.setToolTipText("Close");
        lblLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblLogout.setPreferredSize(new java.awt.Dimension(30, 30));
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });

        lblMinimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_subtract_24px_1.png"))); // NOI18N
        lblMinimize.setToolTipText("Minimize");
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblMinimize.setDoubleBuffered(true);
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unicorn.png"))); // NOI18N
        jLabel1.setText("Chương trình sắp xếp thời khoá biểu (DEMO)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 757, Short.MAX_VALUE)
                .addComponent(lblMinimize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Chọn tiêu chí TKB:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 128, 29));

        inpSubjectID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpSubjectIDActionPerformed(evt);
            }
        });
        jPanel3.add(inpSubjectID, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 302, 29));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Chọn môn theo mã:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 29));

        btnMaMonHoc.setText("Chọn");
        btnMaMonHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaMonHocActionPerformed(evt);
            }
        });
        jPanel3.add(btnMaMonHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, 29));

        tblMonhoc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblMonhoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã MH", "Tên môn học"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMonhoc.setRowHeight(24);
        tblMonhoc.setShowGrid(true);
        tblMonhoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMonhocMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMonhoc);
        if (tblMonhoc.getColumnModel().getColumnCount() > 0) {
            tblMonhoc.getColumnModel().getColumn(0).setMinWidth(4);
            tblMonhoc.getColumnModel().getColumn(0).setPreferredWidth(6);
            tblMonhoc.getColumnModel().getColumn(1).setPreferredWidth(3);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 27, 295, 171));

        btnOption2.setText("Số buổi học ít nhất");
        btnOption2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOption2ActionPerformed(evt);
            }
        });
        jPanel3.add(btnOption2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 132, 29));

        btnOption3.setText("Phòng học thấp nhất");
        btnOption3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOption3ActionPerformed(evt);
            }
        });
        jPanel3.add(btnOption3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 150, 29));

        btnOption4.setText("Xếp ngẫu nhiên");
        btnOption4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOption4ActionPerformed(evt);
            }
        });
        jPanel3.add(btnOption4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, 132, 30));

        btnOption1.setText("Số tiết học ít nhất");
        btnOption1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOption1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnOption1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 150, 30));

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(600, 224));
        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 938, 380));

        jScrollPane2.setToolTipText("");
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(50, 224));

        tblTiethoc.setBackground(new java.awt.Color(102, 153, 204));
        tblTiethoc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tblTiethoc.setForeground(new java.awt.Color(255, 255, 255));
        tblTiethoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Tiết 1"},
                {"Tiết 2"},
                {"Tiết 3"},
                {"Tiết 4"},
                {"Tiết 5"},
                {"Tiết 6"},
                {"Tiết 7"},
                {"Tiết 8"},
                {"Tiết 9"},
                {"Tiết 10"},
                {"Tiết 11"},
                {"Tiết 12"},
                {"Tiết 13"},
                {"Tiết 14"}
            },
            new String [] {
                ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTiethoc.setMinimumSize(new java.awt.Dimension(15, 350));
        tblTiethoc.setPreferredSize(new java.awt.Dimension(75, 350));
        tblTiethoc.setRowHeight(25);
        tblTiethoc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTiethoc.setShowGrid(true);
        jScrollPane2.setViewportView(tblTiethoc);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 70, 380));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        jButton1.setText("Xóa môn đang chọn");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 200, 160, -1));
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 530));

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
        int confirmExit = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Chương trình", javax.swing.JOptionPane.YES_NO_OPTION);
        if(confirmExit == javax.swing.JOptionPane.YES_OPTION)
        {
            client.close();
            ex.shutdown();
            dispose();
            
        }
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked
        this.setState(javax.swing.JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMouseClicked

    private void btnOption1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOption1ActionPerformed
        if(!client.isConnected())
        {
            javax.swing.JOptionPane.showMessageDialog( null,
                    "Không thể kết nối đến server",
                    "Chương trình",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        else{
            if(tblMonhoc.getRowCount()==0){
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn môn để sắp xếp",
                        "Chương trình",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String input = "";
            TableModel model  = tblMonhoc.getModel();
            int[] data = new int[tblMonhoc.getRowCount()];
            for (int i = 0; i < tblMonhoc.getRowCount(); i++) {
                data[i] = Integer.parseInt((String) model.getValueAt(0, 1));
                input+= Integer.parseInt((String) model.getValueAt(i, 1))+"/";
            }
            System.out.println(Arrays.toString(data));
            client.sendMessage("OPTION1;"+input);
            LoadingDialog ld = new LoadingDialog(this, "Đang xếp thời khoá biểu. Chờ tí nhé!!!", "src/main/resources/images/loading.gif");
            SwingWorker<String,String> worker = new SwingWorker<>(){
                @Override
                protected String doInBackground() throws InterruptedException {
                    Thread.sleep(1000);
                    return client.getMessage();
                }
                @Override
                protected void done() {
                    try {
                        //System.out.println("What i get:" + get());
                        String json = get();
                        JSONArray getData = new JSONArray(json);
                        ArrayList<ChiTietMonHoc> ct = Main.getListCTMHFromJsonArray(getData);
                        setTable(ct);
                        //clearList();
                        ld.setVisible(false);
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            ex.submit(worker);
            //Loading dialog will be displayed on the screen after the button has been clicked
            ld.setVisible(true);
        }
    }//GEN-LAST:event_btnOption1ActionPerformed

    private void btnOption4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOption4ActionPerformed
        if(!client.isConnected())
        {
            javax.swing.JOptionPane.showMessageDialog( null,
                "Không thể kết nối đến server",
                "Chương trình",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        else{
            if(tblMonhoc.getRowCount()==0){
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn môn để sắp xếp",
                        "Chương trình",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String input = "";
            TableModel model  = tblMonhoc.getModel();
            int[] data = new int[tblMonhoc.getRowCount()];
            for (int i = 0; i < tblMonhoc.getRowCount(); i++) {
                data[i] = Integer.parseInt((String) model.getValueAt(0, 1));
                input+= Integer.parseInt((String) model.getValueAt(i, 1))+"/";
            }
            System.out.println(Arrays.toString(data));
            client.sendMessage("OPTION4;"+input);
            LoadingDialog ld = new LoadingDialog(this, "Đang xếp thời khoá biểu. Chờ tí nhé!!!", "src/main/resources/images/loading.gif");
            SwingWorker<String,String> worker = new SwingWorker<>(){
                @Override
                protected String doInBackground() throws InterruptedException {
                    Thread.sleep(1000);
                    return client.getMessage();
                }
                @Override
                protected void done() {
                    try {
                        //System.out.println("What i get:" + get());
                        String json = get();
                        JSONArray getData = new JSONArray(json);
                        ArrayList<ChiTietMonHoc> ct = Main.getListCTMHFromJsonArray(getData);
                        setTable(ct);
                        //clearList();
                        ld.setVisible(false);
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            ex.submit(worker);
            //Loading dialog will be displayed on the screen after the button has been clicked
            ld.setVisible(true);
        }
    }//GEN-LAST:event_btnOption4ActionPerformed

    private void btnOption2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOption2ActionPerformed
        if(!client.isConnected())
        {
            javax.swing.JOptionPane.showMessageDialog( null,
                    "Không thể kết nối đến server",
                    "Chương trình",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        else{
            if(tblMonhoc.getRowCount()==0){
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn môn để sắp xếp",
                        "Chương trình",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String input = "";
            TableModel model  = tblMonhoc.getModel();
            int[] data = new int[tblMonhoc.getRowCount()];
            for (int i = 0; i < tblMonhoc.getRowCount(); i++) {
                data[i] = Integer.parseInt((String) model.getValueAt(0, 1));
                input+= Integer.parseInt((String) model.getValueAt(i, 1))+"/";
            }
            System.out.println(Arrays.toString(data));
            client.sendMessage("OPTION2;"+input);
            LoadingDialog ld = new LoadingDialog(this, "Đang xếp thời khoá biểu. Chờ tí nhé!!!", "src/main/resources/images/loading.gif");
            SwingWorker<String,String> worker = new SwingWorker<>(){
                @Override
                protected String doInBackground() throws InterruptedException {
                    Thread.sleep(1000);
                    return client.getMessage();
                }
                @Override
                protected void done() {
                    try {
                        //System.out.println("What i get:" + get());
                        String json = get();
                        JSONArray getData = new JSONArray(json);
                        ArrayList<ChiTietMonHoc> ct = Main.getListCTMHFromJsonArray(getData);
                        setTable(ct);
                        //clearList();
                        ld.setVisible(false);
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            ex.submit(worker);
            //Loading dialog will be displayed on the screen after the button has been clicked
            ld.setVisible(true);
        }
    }//GEN-LAST:event_btnOption2ActionPerformed

    private void inpSubjectIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpSubjectIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpSubjectIDActionPerformed

    private void btnMaMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaMonHocActionPerformed
        if(inpSubjectID.toString() == null || inpSubjectID.toString().equals(""))
        {
            return;
        }
        if(client.isConnected())
        {
            client.sendMessage("REGISTERCOURSE;"+inpSubjectID.getText());
            String json = client.getMessage();
            JSONObject getData = new JSONObject(json);
            if(getData.getBoolean("status"))
            {
                inpSubjectID.setText("");
                HocPhan hocphan = new Gson().fromJson(getData.getJSONObject("hocphan").toString(),HocPhan.class);
                System.out.println(hocphan.getMaMonHoc());
                if (checkSubject(hocphan.getMaMonHoc())){
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblMonhoc.getModel();
                model.addRow(new Object[]{tblMonhoc.getRowCount()+1,
                                          hocphan.getMaMonHoc(),
                                          hocphan.getTenMonHoc()});

                javax.swing.JOptionPane.showMessageDialog(null,"Chọn môn thành công","Chương trình",javax.swing.JOptionPane.PLAIN_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this, "Môn học đã được chọn!");
            }
            else {
                javax.swing.JOptionPane.showMessageDialog(null, 
                                                          getData.get("message"),
                                                          "Chương trình",
                                                          javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
            
        }else{
            javax.swing.JOptionPane.showMessageDialog(null,
                                                      "Không thể kết nối đến server",
                                                      "Chương trình",
                                                      javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnMaMonHocActionPerformed

    private void btnOption3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOption3ActionPerformed
        if(!client.isConnected())
        {
            javax.swing.JOptionPane.showMessageDialog( null,
                    "Không thể kết nối đến server",
                    "Chương trình",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        else{
            if(tblMonhoc.getRowCount()==0){
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn môn để sắp xếp",
                        "Chương trình",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String input = "";
            TableModel model  = tblMonhoc.getModel();
            int[] data = new int[tblMonhoc.getRowCount()];
            for (int i = 0; i < tblMonhoc.getRowCount(); i++) {
                data[i] = Integer.parseInt((String) model.getValueAt(0, 1));
                input+= Integer.parseInt((String) model.getValueAt(i, 1))+"/";
            }
            System.out.println(Arrays.toString(data));
            client.sendMessage("OPTION3;"+input);
            LoadingDialog ld = new LoadingDialog(this, "Đang xếp thời khoá biểu. Chờ tí nhé!!!", "src/main/resources/images/loading.gif");
            SwingWorker<String,String> worker = new SwingWorker<>(){
                @Override
                protected String doInBackground() throws InterruptedException {
                    Thread.sleep(1000);
                    return client.getMessage();
                }
                @Override
                protected void done() {
                    try {
                        //System.out.println("What i get:" + get());
                        String json = get();
                        JSONArray getData = new JSONArray(json);
                        ArrayList<ChiTietMonHoc> ct = Main.getListCTMHFromJsonArray(getData);
                        setTable(ct);
                        //clearList();
                        ld.setVisible(false);
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            ex.submit(worker);
            //Loading dialog will be displayed on the screen after the button has been clicked
            ld.setVisible(true);
        }
    }//GEN-LAST:event_btnOption3ActionPerformed

    private void tblMonhocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMonhocMouseClicked
       
    }//GEN-LAST:event_tblMonhocMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                                                   
        System.out.print(tblMonhoc.getSelectedRow());
        int index = tblMonhoc.getSelectedRow();
        if(index==-1){
            javax.swing.JOptionPane.showMessageDialog(null,"Vui lòng chọn hàng cần xoá","Chương trình",javax.swing.JOptionPane.PLAIN_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tblMonhoc.getModel();
        System.out.println(model.getValueAt(index,2));

        for(int i= index; i<tblMonhoc.getRowCount() - 1; i++){
            String mamh = (String) model.getValueAt(i+1, 1);
            String tenmh = (String) model.getValueAt(i+1, 2);
            model.setValueAt(mamh, i, 1);
            model.setValueAt(tenmh, i, 2);

        }
        model.setRowCount(model.getRowCount()-1);

    }                                        
    public boolean checkSubject(String mmh) {
        boolean kt=true;
        int size = tblMonhoc.getRowCount();
        for (int i = 0; i< size; i++){
                    if (tblMonhoc.getValueAt(i,1).toString().equals(mmh)){
                           kt=false;
                           break;
                    }
                }
        return kt;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String... args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMaMonHoc;
    private javax.swing.JButton btnOption1;
    private javax.swing.JButton btnOption2;
    private javax.swing.JButton btnOption3;
    private javax.swing.JButton btnOption4;
    private javax.swing.JTextField inpSubjectID;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JTable tblMonhoc;
    private javax.swing.JTable tblTiethoc;
    // End of variables declaration//GEN-END:variables
}
class LoadingDialog extends JDialog {
    public LoadingDialog(javax.swing.JFrame container, String title, String urlGIF)
    {
        super(container,title,true);
        setLayout(new java.awt.BorderLayout());
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        javax.swing.ImageIcon getImgIcon = new javax.swing.ImageIcon(urlGIF);
        javax.swing.ImageIcon imgicon = ImageIconConverter.getScaledImage(getImgIcon,300,300);
        label.setIcon(imgicon);
        add(label,java.awt.BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
class FrameDragListener extends MouseAdapter {
    
    /*Source: https://stackoverflow.com/questions/16046824/making-a-java-swing-frame-movable-and-setundecorated*/
    private final javax.swing.JFrame container;
    private java.awt.Point mouseDownCompCoords = null;
    
    public FrameDragListener(javax.swing.JFrame container) {
        this.container = container;
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        mouseDownCompCoords = null;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        mouseDownCompCoords = e.getPoint();
    }
        
    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        java.awt.Point currCoords = e.getLocationOnScreen();
        container.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }
}
