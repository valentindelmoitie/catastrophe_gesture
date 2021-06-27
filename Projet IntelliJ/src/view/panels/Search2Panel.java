package view.panels;

import controller.ApplicationController;
import model.Disaster;
import model.Region;
import view.tableModel.DisastersSearch2Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Search2Panel extends JPanel {
    private JLabel titleLbl;
    private JPanel centerPanel, choicePanel, resultPanel;
    private JComboBox choiceCB;
    private JButton choiceBtn;
    private ArrayList<Disaster> disasters;
    private ApplicationController controler;
    private JTable resultTable;
    private JScrollPane resultScrollPane;
    private DisastersSearch2Model resultModel;

    public Search2Panel() {
        this.setLayout(new BorderLayout());

        controler = new ApplicationController();

        try {
            disasters = controler.getAllDisaster();

            titlePanelCreation();
            centerPanelCreation();

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Exception levée", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void titlePanelCreation() {
        titleLbl = new JLabel("<html><h3>Rechercher les régions et les pays touchés par une catastrophe</h3><html>");
        titleLbl.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(titleLbl, BorderLayout.NORTH);
    }

    private void centerPanelCreation() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        choicePanelCreation();

        resultPanelCreation();

        this.add(centerPanel);
    }

    private void choicePanelCreation() {
        choicePanel = new JPanel();
        choicePanel.setLayout(new FlowLayout());

        ArrayList<String> choices =  new ArrayList<>();
        for (Disaster disaster : disasters) {
            choices.add( disaster.getId() + " " + (disaster.getDescription() == null ? "" : (disaster.getName()) + " ") + disaster.getType() + " " + disaster.getDateString() );
        }
        choiceCB = new JComboBox(choices.toArray());
        choiceCB.setPreferredSize(new Dimension(400, 50));

        choiceBtn = new JButton("Rechercher");
        choiceBtn.addActionListener(new SearchButtonListener());
        choiceBtn.setPreferredSize(new Dimension(100, 25));

        choicePanel.add(choiceCB);
        choicePanel.add(choiceBtn);

        centerPanel.add(choicePanel, BorderLayout.NORTH);
    }

    private void resultPanelCreation() {
        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());

        resultModel = new DisastersSearch2Model(new ArrayList<>());
        resultTable = new JTable(resultModel);
        resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setPreferredSize(new Dimension(1900, 700));

        centerPanel.add(resultScrollPane, BorderLayout.CENTER);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedId = Integer.parseInt(choiceCB.getSelectedItem().toString().split(" ")[0]); // [0] pour choisir le première elem (version raccourcie de la search 3)

            try {
                ArrayList<Region> regions = controler.getRegionsImpactedBy(selectedId);

                resultModel  = new DisastersSearch2Model(regions);
                resultTable.setModel(resultModel);

                repaint();
                validate();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Exception levée", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
