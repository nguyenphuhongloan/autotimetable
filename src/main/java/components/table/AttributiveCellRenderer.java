package components.table;
import classes.model.ChiTietMonHoc;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.plaf.ColorUIResource;
/*Source github*/
public class AttributiveCellRenderer extends JTextPane
                                     implements TableCellRenderer {
    protected static Border border;
    public AttributiveCellRenderer() {
        Border lineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border paddingBorder = BorderFactory.createEmptyBorder(7,5,0,5);
        border = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);
        setBorder(border);
        setContentType("text/html");
        setOpaque(true);
        UIDefaults defaults = new UIDefaults();
        java.awt.Color bgColor = Color.decode("#f5f5dc");
        defaults.put("TextPane.selectionBackground", new ColorUIResource(bgColor));
        defaults.put("TextPane.background", new ColorUIResource(java.awt.Color.PINK));
        putClientProperty("Nimbus.Overrides", defaults);
        putClientProperty("Nimbus.Overrides.InheritDefaults", false);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
        Color foreground = null;
        Color background = null;
        Font font = null;
        TableModel model = table.getModel();
        if (table.getRowHeight(row) != getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }
        if (model instanceof AttributiveCellTableModel) {
            CellAttribute cellAtt = ((AttributiveCellTableModel)model).getCellAttribute();
            if (cellAtt instanceof ColoredCell) {
                foreground = ((ColoredCell) cellAtt).getForeground(row,column);
                background = ((ColoredCell) cellAtt).getBackground(row,column);
            }
            if (cellAtt instanceof CellFont) {
                font = ((CellFont) cellAtt).getFont(row,column);
            }
        }
        if (isSelected) {
            setForeground((foreground != null) ? foreground
                          : table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        }
        else {
            setForeground((foreground != null) ? foreground 
                              : table.getForeground());
            setBackground((background != null) ? background 
                              : table.getBackground());
        }
        setFont((font != null) ? font : table.getFont());
        if (hasFocus) {
            //setBorder( UIManager.getBorder("Table.focusCellHighlightBorder") );
            if (table.isCellEditable(row, column)) {
                setForeground((foreground != null) ? foreground
                          : UIManager.getColor("Table.focusCellForeground") );
                setBackground( UIManager.getColor("Table.focusCellBackground") );
            }
        }
        else {
            setBorder(border);
        }
        if(value != null)
        {
            if(value instanceof ChiTietMonHoc)
            {
                if(!isSelected)
                {
                    setBackground(java.awt.Color.decode("#f5f5dc"));
                }
                StringBuilder str = new StringBuilder();
                str.append("<html>")
                        .append("<body>")
                            .append("<div style='margin-bottom: 2px'>")
                                .append("<span style='color: Teal; font-weight:bold; font-size:11pt; font-family: Arial'>").append(((ChiTietMonHoc) value).getMaMonHoc()).append("</span>")
                            .append("</div>")
                            .append("<div>")
                                .append("<span style='color: gray; font-style: italic; font-size:11pt; font-weight:bold; font-family: Arial'>").append("Phòng học: ").append("</span>")
                                .append("<span style='color: Teal; font-weight:bold; font-size:11pt; font-family: Arial'>").append(((ChiTietMonHoc) value).getSoPhong()).append("</span>")
                            .append("</div>")
                        .append("</body>")
                   .append("</html>")
                ;
                setValue(str.toString());
            }
            else{
                setValue(value);
            }
        }
        else{
            setValue(value);
        }
        return this;
    }
    protected void setValue(Object value) {
        setText((value == null) ? "" : value.toString());
    }
}


