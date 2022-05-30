package components.table;
/*
 * Source java2s
*/
public interface CellFont {
    public java.awt.Font getFont(int row, int column);
    public void setFont(java.awt.Font font, int row, int column);
    public void setFont(java.awt.Font font, int[] rows, int[] columns);
}

