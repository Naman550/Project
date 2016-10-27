/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Me
 */
public class PanelPlayPause extends javax.swing.JPanel {

    /**
     * Creates new form PanelPlayPause
     */
    public PanelPlayPause() {
        initComponents();
        final Dimension size = lblTask.getPreferredSize();
        lblTask.setMinimumSize(size);
        lblTask.setPreferredSize(size);
    }

    public void setMaxDays(int days){
        taskProgressbar.setMaximum(days);
    }
    
    public void setTaskProgressValue(int value){
        taskProgressbar.setValue(value);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTask = new javax.swing.JLabel();
        lblAssigned = new javax.swing.JLabel();
        lblPlay = new javax.swing.JLabel();
        lblStop = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblStatus = new javax.swing.JLabel();
        lblTimesheetId = new javax.swing.JLabel();
        lblDone = new javax.swing.JLabel();
        taskProgressbar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        lblDeadlineDate = new javax.swing.JLabel();
        lblExtentDeadline = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        lblTask.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTask.setText("DSX:MISCWORK:Working On RemindMe");
        lblTask.setName("labelTask"); // NOI18N

        lblAssigned.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblAssigned.setText("Assigned By: Kulbir Singh");
        lblAssigned.setName("AssignedBy"); // NOI18N

        lblPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/play.png"))); // NOI18N
        lblPlay.setToolTipText("Start Task");
        lblPlay.setName("lblPlay"); // NOI18N

        lblStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/stop.png"))); // NOI18N
        lblStop.setToolTipText("Stop Task");
        lblStop.setName("lblStop"); // NOI18N

        jSeparator1.setName("seperator"); // NOI18N

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        lblStatus.setText("jLabel1");
        lblStatus.setName("lblStatus"); // NOI18N

        lblTimesheetId.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        lblTimesheetId.setText("jLabel2");
        lblTimesheetId.setName("lblTimeid"); // NOI18N

        lblDone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/done.png"))); // NOI18N
        lblDone.setToolTipText("Finish Task");
        lblDone.setName("lblDone"); // NOI18N

        taskProgressbar.setValue(50);
        taskProgressbar.setName("taskProgressbar"); // NOI18N
        taskProgressbar.setString("0%");
        taskProgressbar.setStringPainted(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("Deadline:");
        jLabel1.setName("lblDeadline"); // NOI18N

        lblDeadlineDate.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        lblDeadlineDate.setText("25 Feb 2016");
        lblDeadlineDate.setName("lbldeadLineDate"); // NOI18N

        lblExtentDeadline.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblExtentDeadline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/extend.png"))); // NOI18N
        lblExtentDeadline.setToolTipText("Extend Deadline");
        lblExtentDeadline.setName("lblPlus"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblStatus)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblTimesheetId))
                                            .addComponent(lblAssigned, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(51, 51, 51)
                                        .addComponent(lblPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStop)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblDone))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(taskProgressbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(3, 3, 3)
                                                .addComponent(lblDeadlineDate)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblExtentDeadline)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTask)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblTask)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAssigned)
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStatus)
                            .addComponent(lblTimesheetId))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblDeadlineDate))
                        .addGap(2, 2, 2)
                        .addComponent(taskProgressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStop)
                            .addComponent(lblPlay)
                            .addComponent(lblDone)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblExtentDeadline)))
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        lblPlay.getAccessibleContext().setAccessibleDescription("Play");
        lblStop.getAccessibleContext().setAccessibleDescription("Finish");
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLabel1;
    public javax.swing.JSeparator jSeparator1;
    public static javax.swing.JLabel lblAssigned;
    public static javax.swing.JLabel lblDeadlineDate;
    public static javax.swing.JLabel lblDone;
    public static javax.swing.JLabel lblExtentDeadline;
    public static javax.swing.JLabel lblPlay;
    public static javax.swing.JLabel lblStatus;
    public static javax.swing.JLabel lblStop;
    public static javax.swing.JLabel lblTask;
    public static javax.swing.JLabel lblTimesheetId;
    public static javax.swing.JProgressBar taskProgressbar;
    // End of variables declaration//GEN-END:variables
}
