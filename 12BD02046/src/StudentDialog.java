
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import dao.StudentDAO;
import javax.swing.JDialog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.Student;
public class StudentDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;

	private StudentDAO dao;

	private StudentsAppTable studentsTabApp;

	private Student prevStudent = null;
	private boolean updateMode = false;

	public StudentDialog(StudentsAppTable studentsTabApp1,
			StudentDAO dao1, Student prevStudent1, boolean theUpdateMode) {
		this();
		studentsTabApp = studentsTabApp1;
                dao = dao1;
		prevStudent = prevStudent1;
        
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Update");
			
			populateGui(prevStudent);
		}
	}

	private void populateGui(Student theStudent) {

		firstNameTextField.setText(theStudent.getFirstName());
		lastNameTextField.setText(theStudent.getLastName());
				
	}

	public StudentDialog(StudentsAppTable studentsTabApp1,
			StudentDAO dao1) {
		this(studentsTabApp1, dao1, null, false);
	}

	/**
	 * Create the dialog.
	 */
	public StudentDialog() {
		setTitle("Update");
		setBounds(100, 100, 450, 234);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel
			.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));
		{
			JLabel lblFirstName = new JLabel("First Name");
			contentPanel.add(lblFirstName, "2, 2, right, default");
		}
		{
			firstNameTextField = new JTextField();
			contentPanel.add(firstNameTextField, "4, 2, fill, default");
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			contentPanel.add(lblLastName, "2, 4, right, default");
		}
		{
			lastNameTextField = new JTextField();
			contentPanel.add(lastNameTextField, "4, 4, fill, default");
			lastNameTextField.setColumns(10);
		}
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveStudent();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	

	protected void saveStudent() {

		
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		

		Student tempStudent = null;

		if (updateMode) {
			tempStudent = prevStudent;
			
			tempStudent.setFirstName(firstName);
			tempStudent.setLastName(lastName);
			
		} else {
			tempStudent = new Student(firstName, lastName);
		}

		try {
	
			if (updateMode) {
				dao.updateStudent(tempStudent);
			} else {
				dao.addStudent(tempStudent);
			}

			setVisible(false);
			dispose();

			studentsTabApp.refreshStudentsView();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(studentsTabApp,
					"Error saving student: " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
    
}
