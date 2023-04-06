package rocks.zipcode.textedit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ZipEdit extends JFrame implements ActionListener{
    private JTextArea area;
    private JFrame frame;
    private String filename = "untitled";
    JScrollPane scrollPane;

    public ZipEdit() {  }

    public static void main(String[] args) {
        ZipEdit runner = new ZipEdit();
        runner.run();
    }


    public void run() {
        frame = new JFrame(frameTitle());

        // Set the look-and-feel (LNF) of the application
        // Try to default to whatever the host system prefers
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ZipEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set attributes of the app window
        area = new JTextArea();
        //Border blackline = BorderFactory.createLineBorder(Color.black);
        //area.setBorder(blackline);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        area.setText("");
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setLocationRelativeTo(null);
        frame.setSize(640, 480);

//        scrollPane = new JScrollPane(area);
//        this.add(scrollPane);

        // Build the menu
        JMenuBar menu_main = new JMenuBar();

        JMenu menu_file = new JMenu("File");
        JMenu edit_file = new JMenu("Edit");

        JMenuItem menuitem_new = new JMenuItem("New");
        JMenuItem menuitem_open = new JMenuItem("Open");
        JMenuItem menuitem_save = new JMenuItem("Save");
        JMenuItem menuitem_quit = new JMenuItem("Quit");

        JMenuItem edititem_cut = new JMenuItem("Cut");
        JMenuItem edititem_copy = new JMenuItem("Copy");
        JMenuItem edititem_paste = new JMenuItem("Paste");
        JMenuItem edititem_find = new JMenuItem("Find");


        menuitem_new.addActionListener(this);
        menuitem_open.addActionListener(this);
        menuitem_save.addActionListener(this);
        menuitem_quit.addActionListener(this);

        edititem_cut.addActionListener(this);
        edititem_copy.addActionListener(this);
        edititem_paste.addActionListener(this);
        edititem_find.addActionListener(this);


        menu_main.add(menu_file);
        menu_main.add(edit_file);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_quit);

        edit_file.add(edititem_cut);
        edit_file.add(edititem_copy);
        edit_file.add(edititem_paste);
        edit_file.add(edititem_find);

        frame.setJMenuBar(menu_main);

        frame.setVisible(true);

    }

    public String frameTitle() {
        return "Zip Edit ("+this.filename+")";
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String ingest = "";
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        String ae = e.getActionCommand();
        int returnValue;
        if (ae.equals("Open")) {
            returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                this.filename = jfc.getSelectedFile().getName();
                this.frame.setTitle(this.frameTitle());
                try{
                    FileReader read = new FileReader(f);
                    Scanner scan = new Scanner(read);
                    while(scan.hasNextLine()){
                        String line = scan.nextLine() + "\n";
                        ingest = ingest + line;
                    }
                    area.setText(ingest);
                }
                catch ( FileNotFoundException ex) { ex.printStackTrace(); }
            }
            // SAVE
        } else if (ae.equals("Save")) {
            returnValue = jfc.showSaveDialog(null);
            this.filename = jfc.getSelectedFile().getName();
            this.frame.setTitle(this.frameTitle());
            try {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                FileWriter out = new FileWriter(f);
                out.write(area.getText());
                out.close();
            } catch (FileNotFoundException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f,"File not found.");
            } catch (IOException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f,"Error.");
            }
        } else if (ae.equals("New")) {
            area.setText("");
        } else if (ae.equals("Quit")) {
            System.exit(0);
        }
        else if (ae.equals("Copy")) {
//            Action copy = new DefaultEditorKit.CopyAction();
//            copy.putValue(Action.NAME, "Copy");

            area.copy();
        }
        else if (ae.equals("Cut")) {
//            Action cut = new DefaultEditorKit.CutAction();
//            String selected = area.getSelectedText();
//            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
//            cut.putValue(Action.NAME, "Cut");
            area.cut();

        }
        else if (ae.equals("Paste")) {
//            area.replaceRange(selected, );
            area.paste();
        }


        else if (ae.equals("Find")) {

        }
    }
}


