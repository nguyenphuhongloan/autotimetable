package components.event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyListenerMoveRow implements KeyListener{
    private final javax.swing.JTable table;
    public KeyListenerMoveRow(javax.swing.JTable table)
    {
        this.table = table;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode)
        {
            case KeyEvent.VK_UP:
                moveUpwards();
                break;
            case KeyEvent.VK_DOWN:
                moveDownwards();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    public javax.swing.JTable getTable()
    {
        return table;
    }
    public void moveUpwards()
    {
        moveRowBy(-1);
    }
    
    public void moveDownwards()
    {
        moveRowBy(1);
    }
    
    private void moveRowBy(int by)
    {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        int destination = rows[0] + by;
        int rowCount = model.getRowCount();

        if (destination < 0 || destination >= rowCount)
        {
            return;
        }
        model.moveRow(rows[0], rows[rows.length - 1], destination);
        try{
            int row1_priority = (int) table.getValueAt(table.getSelectedRow(), 1);
            int row2_priority = (int) table.getValueAt(destination, 1);
            model.setValueAt(row1_priority, destination, 1);
            model.setValueAt(row2_priority, table.getSelectedRow(), 1);
        }catch(NumberFormatException ex)
        {
            
        }
    }
}
