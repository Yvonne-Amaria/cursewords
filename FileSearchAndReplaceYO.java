import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class FileSearchAndReplaceYO extends JFrame {
    private JTextField searchFieldYO;
    private JButton searchButtonYO;
    private JButton replaceButtonYO;
    private JTextArea textAreaYO;
    private String originalTextYO;
    private String wordToReplaceYO;

    public FileSearchAndReplaceYO() {
        // create the search field and button
        searchFieldYO = new JTextField();
        searchButtonYO = new JButton("Search");
        searchButtonYO.addActionListener(new searchButtonListenerYO());

        // create the replace button and text area
        replaceButtonYO = new JButton("Replace");
        replaceButtonYO.setEnabled(false);
        replaceButtonYO.addActionListener(new replaceButtonListenerYO());
        textAreaYO = new JTextArea();
        textAreaYO.setEditable(false);

        // add the search field and button to the GUI
        add(searchFieldYO, BorderLayout.NORTH);
        add(searchButtonYO, BorderLayout.WEST);
        add(replaceButtonYO, BorderLayout.EAST);
        add(new JScrollPane(textAreaYO), BorderLayout.CENTER);

        // read the content of the file and store it in a variable
        try {
            File inputFile = new File("curse-words-text-file.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            originalTextYO = sb.toString();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading the file: " + e.getMessage());
        }
    }

    private class searchButtonListenerYO implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String wordToSearch = searchFieldYO.getText();
            int index = originalTextYO.indexOf(wordToSearch);
            if (index != -1) {
                textAreaYO.setText(originalTextYO);
                textAreaYO.setCaretPosition(index);
                replaceButtonYO.setEnabled(true);
                wordToReplaceYO = wordToSearch;
            } else {
                textAreaYO.setText("Word not found.");
                replaceButtonYO.setEnabled(false);
            }
        }
    }

    private class replaceButtonListenerYO implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String updatedText = originalTextYO.replace(wordToReplaceYO, "[Moderated]");
            textAreaYO.setText(updatedText);

            // create a new file with the updated content
            try {
                File inputFile = new File("curse-words-text-file.txt");
                String newFileName = inputFile.getName().substring(0, inputFile.getName().lastIndexOf(".")) + "_2.txt";
                File outputFile = new File(newFileName);
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                writer.write(updatedText);
                writer.close();
                JOptionPane.showMessageDialog(FileSearchAndReplaceYO.this, "File updated and saved as " + newFileName);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(FileSearchAndReplaceYO.this, "Error creating new file: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        FileSearchAndReplaceYO frame = new FileSearchAndReplaceYO();
        frame.setTitle("Search and Replace Curse Word");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
