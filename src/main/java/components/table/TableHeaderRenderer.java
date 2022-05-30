package components.table;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
public class TableHeaderRenderer extends DefaultTableCellRenderer {
    private java.awt.Font textFont;
    public TableHeaderRenderer(java.awt.Color Background, java.awt.Color Foreground)
    {
        super();
        setBackground(Background);
        setForeground(Foreground);
        setHorizontalAlignment(SwingConstants.CENTER);
    }
    public TableHeaderRenderer(java.awt.Color Background, java.awt.Color Foreground, java.awt.Font TextFont)
    {
        this(Background,Foreground);
        this.textFont = TextFont;
    }
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable tblData,
                                                            Object value, boolean isSelected, boolean hasFocus,
                                                            int row, int column) {
            java.awt.Component cellComponent = super.getTableCellRendererComponent(
                    tblData, value, isSelected, hasFocus, row, column);
            cellComponent.setFont(textFont);
            return cellComponent;
        }
}
