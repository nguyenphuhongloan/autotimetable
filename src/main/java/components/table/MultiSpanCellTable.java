package components.table;
import classes.model.ChiTietMonHoc;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
/*Source java2s*/
public class MultiSpanCellTable extends JTable {
    public MultiSpanCellTable(TableModel model) {
        super(model);
        setUI(new MultiSpanCellTableUI());
        getTableHeader().setReorderingAllowed(false);
        setCellSelectionEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }
    @Override
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        Rectangle sRect = super.getCellRect(row,column,includeSpacing);
        if ((row < 0) || (column < 0) || (getRowCount() <= row) || (getColumnCount() <= column)) {
            return sRect;
        }
        CellSpan cellAtt = (CellSpan)((AttributiveCellTableModel)getModel()).getCellAttribute();
        if (!cellAtt.isVisible(row,column)) {
            int temp_row    = row;
            int temp_column = column;
            row    += cellAtt.getSpan(temp_row,temp_column)[CellSpan.ROW];
            column += cellAtt.getSpan(temp_row,temp_column)[CellSpan.COLUMN];      
        }
        int[] n = cellAtt.getSpan(row,column);
        int index = 0;
        int columnMargin = getColumnModel().getColumnMargin();
        Rectangle cellFrame = new Rectangle();
        int aCellHeight = rowHeight + rowMargin;
        cellFrame.y = row * aCellHeight;
        cellFrame.height = n[CellSpan.ROW] * aCellHeight;
        Enumeration eeration = getColumnModel().getColumns();
        while (eeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn)eeration.nextElement();
            cellFrame.width = aColumn.getWidth() + columnMargin;
            if (index == column) break;
            cellFrame.x += cellFrame.width;
            index++;
        }
        for (int i=0;i<n[CellSpan.COLUMN]-1;i++) {
            TableColumn aColumn = (TableColumn)eeration.nextElement();
            cellFrame.width += aColumn.getWidth() + columnMargin;
        }
        if (!includeSpacing) {
            Dimension spacing = getIntercellSpacing();
            cellFrame.setBounds(cellFrame.x      + spacing.width/2,
                                cellFrame.y      + spacing.height/2,
                                cellFrame.width  - spacing.width,
                                cellFrame.height - spacing.height);
        }
        return cellFrame;
    }
    private int[] rowColumnAtPoint(Point point) {
        int[] retValue = {-1,-1};
        int row = point.y / (rowHeight + rowMargin);
        if ((row < 0)||(getRowCount() <= row)) return retValue;
        int column = getColumnModel().getColumnIndexAtX(point.x);
        CellSpan cellAtt = (CellSpan)((AttributiveCellTableModel)getModel()).getCellAttribute();
        if (cellAtt.isVisible(row,column)) {
            retValue[CellSpan.COLUMN] = column;
            retValue[CellSpan.ROW   ] = row;
            return retValue;
        }
        retValue[CellSpan.COLUMN] = column + cellAtt.getSpan(row,column)[CellSpan.COLUMN];
        retValue[CellSpan.ROW   ] = row    + cellAtt.getSpan(row,column)[CellSpan.ROW];
        return retValue;
    }
    @Override
    public int rowAtPoint(Point point) {
        return rowColumnAtPoint(point)[CellSpan.ROW];
    }
    @Override
    public int columnAtPoint(Point point) {
        return rowColumnAtPoint(point)[CellSpan.COLUMN];
    }
    @Override
    public void columnSelectionChanged(ListSelectionEvent e) {
        repaint();
    }
    @Override
    public JToolTip createToolTip()
    {
        JToolTip tip = new JToolTip(){
            @Override
            public void paintComponent(java.awt.Graphics g){
                java.awt.Graphics2D g2D = (java.awt.Graphics2D) g;
                try{
                    java.awt.image.BufferedImage image = ImageIO.read(new java.io.File("src/main/resources/images/tooltip.png"));
                    g2D.drawImage(image,super.getX(),super.getY(),super.getWidth(),super.getHeight(),null);
                    super.paintComponent(g);
                }catch(java.io.IOException ex){
                    //Catch an image not found error
                }
            }
        };
        tip.setPreferredSize(new java.awt.Dimension(350,200));
        tip.setBackground(new java.awt.Color(0.5f, 0.5f, 0.5f, 0.0f));
        tip.setOpaque(false);
        return tip;
    }
    @Override
    public String getToolTipText(MouseEvent e)
    {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        try {
            if(rowIndex >= 0){
                Object obj = getValueAt(rowIndex, colIndex);
                if(obj instanceof ChiTietMonHoc)
                {
                    tip = String.format(
                            "<html>"
                            + "<body style = 'font-size:13pt'>"
                                + "<table style='padding-left:8px;'>"
                                   + "<tr><td><img src='%s' width=24 height=24 /></td>" + "<td><span style='color:white;font-weight:bold;font-size:14pt;padding-bottom:10px'>"+((ChiTietMonHoc) obj).getMaMonHoc()+"</span></td></tr>"
                                + "</table>"
                                + "<div style='padding-left:8px;margin-top:5px'>"
                                    + "<div>"
                                        + "<p><b>Mã môn học: </b>"+((ChiTietMonHoc) obj).getMaMonHoc()+"</p>"
                                    + "</div>"
                                    + "<div>"
                                        + "<p><b>Nhóm môn học: </b>"+((ChiTietMonHoc) obj).getNhomMonHoc()+"</p>"
                                    + "</div>"
                                    + "<div>"
                                        + "<p><b>Thứ: </b>"+((ChiTietMonHoc) obj).getThu()+"</p>"
                                    + "</div>"
                                    + "<div>"
                                        + "<p><b>Phòng học: </b>"+((ChiTietMonHoc) obj).getSoPhong()+"</p>"
                                    + "</div>"
                                    + "<div>"
                                        + "<p><b> Tiết bắt đầu: </b>"+((ChiTietMonHoc) obj).getTietBatDau()+"</p>"
                                    + "</div>"
                                    + "<div>"
                                        + "<p><b> Số tiết: </b>"+((ChiTietMonHoc) obj).getSotiet()+"</p>"
                                    + "</div>"
                                + "</div>"
                             + "</body>"
                          + "</html>"
                          ,getClass().getResource("/images/lecturer.png"));
                }else{
                    tip = (String) obj;
                }
            }
        } catch (RuntimeException e1) {
            //catch null pointer exception if mouse is over an empty line
        }
        return tip;
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int firstIndex = e.getFirstIndex();
        int  lastIndex = e.getLastIndex();
        if (firstIndex == -1 && lastIndex == -1) { // Selection cleared.
            repaint();
        }
        Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
        int numCoumns = getColumnCount();
        int index = firstIndex;
        for (int i=0;i<numCoumns;i++) {
            dirtyRegion.add(getCellRect(index, i, false));
        }
        index = lastIndex;
        for (int i=0;i<numCoumns;i++) {
            dirtyRegion.add(getCellRect(index, i, false));
        }
        repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
    } 
}