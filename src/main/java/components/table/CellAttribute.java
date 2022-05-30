package components.table;
import java.awt.Dimension;
/**
 *
 * Source java2s
 */
public interface CellAttribute {
    public void addColumn();
    public void addRow();
    public void insertRow(int row);
    public Dimension getSize();
    public void setSize(Dimension size);
}
