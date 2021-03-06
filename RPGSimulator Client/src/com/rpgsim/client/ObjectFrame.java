package com.rpgsim.client;

import com.rpgsim.common.AsyncTask;
import com.rpgsim.common.FileManager;
import com.rpgsim.common.PrefabID;
import com.rpgsim.common.game.Input;
import com.rpgsim.common.serverpackages.InstantiatePrefabRequest;
import com.rpgsim.common.serverpackages.BackgroundUpdateRequest;
import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.DefaultListModel;

public class ObjectFrame extends javax.swing.JFrame
{
    private final CardLayout cards;
    private final DefaultListModel<String> list = new DefaultListModel<>();
    private int pi = -1;
    
    public ObjectFrame(ClientManager client)
    {
        initComponents();
        cards = (CardLayout) pnlMain.getLayout();
        lstFiles.setModel(list);
        
        btnObject.addActionListener(l ->
        {
            list.clear();
            File parent = new File(FileManager.app_dir + "data files\\objects");
            for (File file : parent.listFiles())
            {
                if (file.isFile())
                    list.addElement(file.getName());
            }
            pi = 0;
            cards.show(pnlMain, "select");
        });
        
        btnBackground.addActionListener(l ->
        {
            list.clear();
            File parent = new File(FileManager.app_dir + "data files\\backgrounds");
            for (File file : parent.listFiles())
            {
                if (file.isFile())
                    list.addElement(file.getName());
            }
            pi = 1;
            cards.show(pnlMain, "select");
        });
        
        btnSelect.addActionListener(l -> 
        {
            String name = lstFiles.getSelectedValue();
            
            if (name == null)
                return;
            
            File parent = new File(FileManager.app_dir);
            
            switch (pi)
            {
                case 0:
                    AsyncTask.executeAsyncTask("Object Load", () ->
                    {
                        client.sendPackage(new InstantiatePrefabRequest(Input.mousePosition(), 
                                PrefabID.DRAGGABLE_OBJECT, -1, 
                                parent.toURI().relativize(new File(FileManager.app_dir + "data files\\objects\\" + name).toURI()).getPath()));
                    });
                    break;
                case 1:
                    AsyncTask.executeAsyncTask("Background Load", () ->
                    {
                        client.sendPackage(new BackgroundUpdateRequest(parent.toURI().relativize(new File(FileManager.app_dir + "data files\\backgrounds\\" + name).toURI()).getPath()));
                    });
                    break;
            }
            
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        
        btnBack.addActionListener(l ->
        {
            cards.show(pnlMain, "options");
        });
    }
    
    public void open()
    {
        cards.show(pnlMain, "options");
        setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlOptions = new javax.swing.JPanel();
        btnObject = new javax.swing.JButton();
        btnBackground = new javax.swing.JButton();
        pnlSelect = new javax.swing.JPanel();
        scrList = new javax.swing.JScrollPane();
        lstFiles = new javax.swing.JList<>();
        btnSelect = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setTitle("Object Creation");

        pnlMain.setLayout(new java.awt.CardLayout());

        btnObject.setText("Object");

        btnBackground.setText("Background");

        javax.swing.GroupLayout pnlOptionsLayout = new javax.swing.GroupLayout(pnlOptions);
        pnlOptions.setLayout(pnlOptionsLayout);
        pnlOptionsLayout.setHorizontalGroup(
            pnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnObject, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlOptionsLayout.setVerticalGroup(
            pnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(btnObject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlMain.add(pnlOptions, "options");

        scrList.setViewportView(lstFiles);

        btnSelect.setText("Select");

        btnBack.setText("Back");

        javax.swing.GroupLayout pnlSelectLayout = new javax.swing.GroupLayout(pnlSelect);
        pnlSelect.setLayout(pnlSelectLayout);
        pnlSelectLayout.setHorizontalGroup(
            pnlSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrList, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSelectLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlSelectLayout.setVerticalGroup(
            pnlSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrList, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelect)
                    .addComponent(btnBack))
                .addContainerGap())
        );

        pnlMain.add(pnlSelect, "select");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBackground;
    private javax.swing.JButton btnObject;
    private javax.swing.JButton btnSelect;
    private javax.swing.JList<String> lstFiles;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlOptions;
    private javax.swing.JPanel pnlSelect;
    private javax.swing.JScrollPane scrList;
    // End of variables declaration//GEN-END:variables

}
