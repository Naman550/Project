/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Me
 */
public class CheckedList {
   public CheckedList() {
            JFrame f=new JFrame();
            String[] labels={"a","b","c","d","e"};
            JCheckBox[] ch=new JCheckBox[labels.length];

            final DefaultListModel model = new DefaultListModel();
            JList list=new JList(model);
            list.setCellRenderer(new CheckListRenderer());
            for (int i = 0; i < labels.length; i++) {
                //ch[i]=new JCheckBox("CheckBox"+i);
                model.addElement(new CheckListItem("CheckBox"+i));
            }
            JScrollPane pane=new JScrollPane(list);
            list.addMouseListener(new MouseAdapter()
            {
                 public void mouseClicked(MouseEvent event)
                 {
                    JList list = (JList) event.getSource();
                    // Get index of item clicked
                    int index = list.locationToIndex(event.getPoint());
                    CheckListItem item = (CheckListItem)
                       list.getModel().getElementAt(index);
                    // Toggle selected state
                    item.setSelected(!item.isSelected());
                    System.out.println("Selected valueList : "+list.getSelectedValuesList());
                    System.out.println("Selected value : "+list.getSelectedValue());
                    
                    for(int i=0; i<list.getModel().getSize();i++){                //get selected index
                       CheckListItem item1 = (CheckListItem)
                       list.getModel().getElementAt(i);
                        System.out.println("Selected value : "+item1.isSelected);
                        if(item1.isSelected){
                            System.out.println(i);
                        }
                    }
                    
                    
                    // Repaint cell
                    list.repaint(list.getCellBounds(index, index));
                 }
            }); 
            f.add(pane);
            f.pack();
     //       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
         //  f.setVisible(true);
    }
    static class CheckListItem{
        private String  label;
        private boolean isSelected = false;

        public CheckListItem(String label){
            this.label = label;
        }
        public boolean isSelected(){
            return isSelected;
        } 
        public void setSelected(boolean isSelected){
            this.isSelected = isSelected;
        }
        public String toString(){
            return label;
        }
    }

    static class CheckListRenderer extends JCheckBox implements ListCellRenderer
    {
        public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected, boolean hasFocus)
        {
          //  setEnabled(list.isEnabled());
            setSelected(((CheckListItem)value).isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }
}
