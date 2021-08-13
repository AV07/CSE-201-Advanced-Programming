import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.util.Pair;

class Student{
	// roll number
	final int RN;
	final float CGPA;
	// branch of student
	final String course; 
	boolean isPlaced;
	// company in which he is placed in
	String company; 
	// score of each student in the technical round
	int scoresTechnical;
	// is the student removed or not
	boolean removed; 
	
	// Constructor
	public Student(int RN, float CGPA, String course) {
		this.RN = RN;
		this.CGPA = CGPA;
		this.course = course;
		this.isPlaced = false;
		this.scoresTechnical = 0;
		this.removed = false;
	}
}

class Company{
	// students that filled the application form for the company
	ArrayList<Student> arr;
	String name;
	// Eligible courses
	ArrayList<String> courses;
	// Scores of students in pair <Roll No., Score in Technical Round>
	ArrayList<Pair<Integer, Integer>> scoresTechnical = new ArrayList<>();
	// Roll number of students that filled the application form
	ArrayList<Integer> rollNumbers = new ArrayList<>();
	// required number of students
	int reqS;
	// is the Application Open or Closed
	boolean ApplicationOpen;
	// is the company removed or not
	boolean removed;
	
	// Constructor
	public Company(String name, int reqS, ArrayList<Student> arr, ArrayList<String> courses, ArrayList<Pair<Integer, Integer>> scores, ArrayList<Integer> RollNos) {
		this.arr = arr; 
		this.name = name;
		this.courses = courses;
		this.scoresTechnical = scores;
		this.reqS = reqS;
		this.ApplicationOpen = true;
		this.rollNumbers = RollNos;
		this.removed = false;
	}
	
	public Company() {
		this.name = "";
		this.reqS = -1;
		this.ApplicationOpen = true;
		this.removed = false;
	}
}

public class Lab1{
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		final int n = s.nextInt();
		float CGPA;
		String course;
		
		// Students
		final ArrayList<Student> students = new ArrayList<Student>();
		// Companies
		final ArrayList<Company> comp = new ArrayList<>();
		
		for(int i = 0; i < n; i++) {
			CGPA = s.nextFloat();
			course = s.next();
			Student z = new Student(i + 1, CGPA, course);
			students.add(z);
		}
		
		int q = s.nextInt();
		s.nextLine();
		for(int i = 0; i < q; i++) {
			String InputValue = s.nextLine();
			String[] Value = InputValue.split(" ");
			int a;
			int b = 0;
			if(Value.length == 2) {
				a = Integer.parseInt(Value[0]);
				if(a == 6 || a == 7) {
					InputValue = Value[1];
				}else {
					b = Integer.parseInt(Value[1]);
				}
			}else {
				a = Integer.parseInt(InputValue);
			}
			
			
			if(a == 1) {
				// Add Company
				String name = s.nextLine();
				System.out.println("Number of Eligible Courses");
				int eligC = s.nextInt();
				
				ArrayList<String> courses = new ArrayList<>();
				String c;
				for(int j = 0; j < eligC; j++) {
					c = s.next();
					s.nextLine();
					courses.add(c);
				}
				
				System.out.println("Number of Required Students");
				int reqS = s.nextInt();
				
				// print
				System.out.println(name);
				System.out.println("Course Criteria");
				for(int j = 0; j< eligC; j++) {
					System.out.println(courses.get(j));
				}
				System.out.println("Number of Required Students = " + reqS);
				System.out.println("Application Status = OPEN");
				
				System.out.println("Enter Scores for the technical round.");
				ArrayList<Pair<Integer, Integer>> scoresTechnical = new ArrayList<>();
				ArrayList<Integer> rollNumbers = new ArrayList<>();
				Pair<Integer,Integer> p;
				ArrayList<Student> arr = new ArrayList<>();
				for(int j = 0; j < students.size(); j++) {
					if(courses.contains(students.get(j).course)) {
						System.out.println("Enter score for Roll no. " + students.get(j).RN);
						int m = s.nextInt();
						p = new Pair<>(students.get(j).RN, m);
						scoresTechnical.add(p);
						rollNumbers.add(students.get(j).RN);
						students.get(j).scoresTechnical = m;
						arr.add(students.get(j));
					}
				}
				Company com = new Company(name, reqS, arr, courses, scoresTechnical, rollNumbers);
				comp.add(com);
				s.nextLine();
			}else if(a == 2) {
				// Remove the accounts of placed students
				System.out.println("Accounts removed for");
				for(int j = 0; j < students.size(); j++) {
					if(students.get(j).isPlaced && students.get(j).removed == false) {
						students.get(j).removed = true;
						System.out.println(students.get(j).RN);
					}
				}
			}else if(a == 3) {
				// Remove the accounts of companies whose applications are closed
				System.out.println("Accounts removed for");
				int j = 0;
				while (j < comp.size()) {
					if(comp.get(j).ApplicationOpen == false && comp.get(j).removed == false) {
						System.out.println(comp.get(j).name);
						comp.get(j).removed = true;
					}
					j += 1;
				}
			}else if(a == 4) {
				// Number of unplaced students
				int count = 0;
				for(int j = 0; j < students.size(); j++) {
					if(students.get(j).isPlaced == false) {
						count += 1;
					}
				}
				System.out.println(count + " students left.");
			}else if(a == 5) {
				// Name of companies whose applications are open
				for(int j = 0; j < comp.size(); j++) {
					if(comp.get(j).ApplicationOpen) {
						System.out.println(comp.get(j).name);
					}
				}
			}else if(a == 6) {
				// Select Students for the company
				String name = InputValue;
				int index = 0;
				Company currCompany = new Company();
				boolean found = false;
				for(int j = 0; j < comp.size(); j++) {
					if(comp.get(j).name.matches(name)) {
						index = j;
						currCompany = comp.get(index);
						found = true;
						break;
					}else {
						continue;
					}
				}
				
				if(!found) {
					System.out.println("No such company found or none of the students is selected.");
				}else {
					Collections.sort(currCompany.arr, new Comparator<Student>() {
						@Override
						public int compare(Student o1, Student o2) {
							if(o1.scoresTechnical > o2.scoresTechnical) {
								return -1;
							}else if(o1.scoresTechnical < o2.scoresTechnical) {
								return 1;
							}else {
								if(o1.CGPA > o2.CGPA) {
									return -1;
								}else{
									return 1;
								}
							}
						}
					});
					System.out.println("Roll Number of Selected Students");
					if(currCompany.reqS >= currCompany.arr.size()) {
						int selected = 0;
						for(int j = 0; j < currCompany.arr.size(); j++) {
							int rn = currCompany.arr.get(j).RN;
							for(int k = 0; k<students.size(); k++) {
								if(students.get(j).RN == rn) {
									if(!students.get(j).isPlaced) {
										students.get(j).isPlaced = true;
										System.out.println(rn);
										selected += 1;
										break;
									}else {
										break;
									}
								}else {
									continue;
								}
							}
						}
						if(currCompany.reqS == selected) {
							currCompany.ApplicationOpen = false;
						}
					}else {
						int selected = 0;
						int curr = 0;
						while(selected < currCompany.reqS && curr < currCompany.scoresTechnical.size()) {
							int rn = currCompany.arr.get(curr).RN;
							for(int j = 0; j<students.size(); j++) {
								if(students.get(j).RN == rn) {
									if(!students.get(j).isPlaced) {
										students.get(j).isPlaced = true;
										System.out.println(rn);
										selected += 1;
										curr += 1;
										break;
									}else {
										curr += 1;
										break;
									}
								}else {
									continue;
								}
							}
						}
						if(selected == currCompany.reqS) {
							comp.get(index).ApplicationOpen = false;
						}
					}
				}
			}else if(a == 7) {
				// Display details of the company
				String Cname = InputValue;
				for(int j = 0; j < comp.size(); j++) {
					Company currCompany = comp.get(j);
					if(currCompany.removed == false && currCompany.name.matches(Cname)) {
						System.out.println(Cname);
						System.out.println("Course Criteria");
						for(int k = 0; k < currCompany.courses.size(); k++) {
							System.out.println(currCompany.courses.get(k));
						}
						System.out.println("Number of Required Students = " + currCompany.reqS);
						if(currCompany.ApplicationOpen) {
							System.out.println("Application Status = OPEN");
						}else {
							System.out.println("Application Status = CLOSED");
						}
						break;
					}else {
						continue;
					}
				}
			}else if(a == 8) {
				// Display details of the student
				int rn = b;
				if(rn > students.size() - 1) {
					System.out.println("Doesn't exist.");
				}else {
					Student currStudent = students.get(rn - 1);
					System.out.println(rn);
					System.out.println(currStudent.CGPA);
					System.out.println(currStudent.course);
					if(currStudent.isPlaced) {
						System.out.println("Placement Status : Placed");
						System.out.println("Company : " + currStudent.company);
					}else {
						System.out.println("Placement Status : Not Placed");
					}
				}
			}else if(a == 9) {
				// Display names of the companies for which the student has applied and their scores in technical rounds
				 int rn = b;
				 boolean found = false;
				 for(int j = 0; j < comp.size(); j++) {
					 if(comp.get(j).removed == false && comp.get(j).rollNumbers.contains(rn)) {
						 int index = comp.get(j).rollNumbers.indexOf(rn);
						 System.out.println(comp.get(j).name + " " + comp.get(j).scoresTechnical.get(index).getValue());
						 found = true;
					 }
				 }
				 if(!found) {
					 System.out.println("No student with the given roll number has an account.");
				 }
			}
		}
		s.close();
	}
}