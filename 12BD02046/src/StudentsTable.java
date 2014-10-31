
import java.util.List;

import javax.swing.table.AbstractTableModel;
import model.Student;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class StudentsTable extends AbstractTableModel{
        public static final int OBJECT_COL = -1;
        private static final int LAST_NAME_COL = 2;
	private static final int FIRST_NAME_COL = 1;
	private static final int ID_COL = 0;
	

	private String[] columnNames = { "id","Last Name", "First Name" };
	private List<Student> students;

	public StudentsTable(List<Student> theStudents) {
		students = theStudents;
	}

	

	
	public Object getValueAt(int row, int col) {

		Student tempStudent = students.get(row);

		switch (col) {
                case ID_COL:
                        return tempStudent.getId();
		case LAST_NAME_COL:
			return tempStudent.getLastName();
		case FIRST_NAME_COL:
			return tempStudent.getFirstName();
		case OBJECT_COL:
			return tempStudent;
		
		default:
			return tempStudent.getLastName();
		}
	}

	
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

   
    

    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return students.size(); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
