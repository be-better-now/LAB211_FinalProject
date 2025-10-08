/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duynh.dao;

import duynh.models.Mountain;
import duynh.models.Student;
import duynh.tools.Acceptable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author huuduy
 */
public class Students {

    private List<Student> studentList = new ArrayList<>();

    //1. Function 1: New registration
    public void addStudent(Scanner sc, List<Mountain> mountains) {
        String id, name, phone, email, mountainCode;
        double fee = 6000000; //Default

        // ===== ID =====
        while (true) {
            System.out.print("Enter Student ID (SE/HE/DE/QE/CE + 6 digits): ");
            id = sc.nextLine().trim();
            if (!Acceptable.isValid(id, Acceptable.STU_ID_VALID)) {
                System.out.println("Invalid ID format!");
                continue;
            }
            if (findById(id) != null) {
                System.out.println("ID already exists!");
                continue;
            }
            break;
        }

        // ===== Name =====
        while (true) {
            System.out.print("Enter Name (2-20 chars): ");
            name = sc.nextLine().trim();
            if (!Acceptable.isValid(name, Acceptable.NAME_VALID)) {
                System.out.println("Invalid name!");
                continue;
            }
            break;
        }

        // ===== Phone =====
        while (true) {
            System.out.print("Enter Phone (10 digits): ");
            phone = sc.nextLine().trim();
            if (!Acceptable.isValid(phone, Acceptable.PHONE_VALID)) {
                System.out.println("Invalid phone!");
                continue;
            }
            break;
        }

        // ===== Email =====
        while (true) {
            System.out.print("Enter Email: ");
            email = sc.nextLine().trim();
            if (!Acceptable.isValid(email, Acceptable.EMAIL_VALID)) {
                System.out.println("Invalid email!");
                continue;
            }
            break;
        }

        // ===== Mountain code =====
        while (true) {
            System.out.print("Enter Mountain Code: ");
            mountainCode = sc.nextLine().trim();
            boolean exists = false;
            for (Mountain m : mountains) {
                if (m.getMountainCode().equalsIgnoreCase(mountainCode)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                System.out.println("Mountain code not found!");
                continue;
            }
            break;
        }

        // ===== Tuition fee discount =====
        if (Acceptable.isValid(phone, Acceptable.VIETTEL_VALID)
                || Acceptable.isValid(phone, Acceptable.VNPT_VALID)) {
            fee *= 0.65; // Nếu là Viettel hoặc VNPT thì discount 35%
        }

        // ===== Add student =====
        Student st = new Student(id, name, phone, email, mountainCode, fee);
        studentList.add(st);
        System.out.println("Student registered successfully!");
    }

    //Function 2: Update registration information
    public void updateStudent(Scanner sc, List<Mountain> mountains) {
        System.out.print("Enter Student ID to update: ");
        String id = sc.nextLine().trim();

        Student st = findById(id);
        if (st == null) {
            System.out.println("This student has not registered yet!");
            return;
        }

        System.out.println("Updating information for: " + st.getId());

        // ===== Name =====
        System.out.print("Enter new Name (Enter to keep '" + st.getName() + "'): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) {
            if (Acceptable.isValid(name, Acceptable.NAME_VALID)) {
                st.setName(name);
            } else {
                System.out.println("Invalid name! Keeping old value.");
            }
        }

        // ===== Phone =====
        System.out.print("Enter new Phone (Enter to keep '" + st.getPhone() + "'): ");
        String phone = sc.nextLine().trim();
        if (!phone.isEmpty()) {
            if (Acceptable.isValid(phone, Acceptable.PHONE_VALID)) {
                st.setPhone(phone);
            } else {
                System.out.println("Invalid phone! Keeping old value.");
            }
        }

        // ===== Email =====
        System.out.print("Enter new Email (Enter to keep '" + st.getEmail() + "'): ");
        String email = sc.nextLine().trim();
        if (!email.isEmpty()) {
            if (Acceptable.isValid(email, Acceptable.EMAIL_VALID)) {
                st.setEmail(email);
            } else {
                System.out.println("Invalid email! Keeping old value.");
            }
        }

        // ===== Mountain Code =====
        System.out.print("Enter new Mountain Code (Enter to keep '" + st.getMountainCode() + "'): ");
        String mountainCode = sc.nextLine().trim();
        if (!mountainCode.isEmpty()) {
            boolean exists = false;
            for (Mountain m : mountains) {
                if (m.getMountainCode().equalsIgnoreCase(mountainCode)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                st.setMountainCode(mountainCode);
            } else {
                System.out.println("Mountain code not found! Keeping old value.");
            }
        }

        // ===== Tuition Fee recalculation =====
        double fee = 6000000; // Default
        if (Acceptable.isValid(st.getPhone(), Acceptable.VIETTEL_VALID)
                || Acceptable.isValid(st.getPhone(), Acceptable.VNPT_VALID)) {
            fee *= 0.65; // Nếu là Viettel hoặc VNPT thì discount 35%
        }
        st.setTutionFee(fee);

        System.out.println("Update successfully!");
    }

    //Function 3: Display registered list
    public void displayStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }

        System.out.println("Registered Students:");
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-12s | %-8s | %10s\n",
                "StudentID", "Name", "Phone", "PeakCode", "Fee");
        System.out.println("-------------------------------------------------------------");

        for (Student s : studentList) {
            System.out.printf("%-10s | %-20s | %-12s | %-8s | %,10.0f\n",
                    s.getId(), s.getName(), s.getPhone(),
                    s.getMountainCode(), s.getTutionFee());
        }
        System.out.println("--------------------------------------------------------------------------");
    }

    // Function 4: Delete registration information
    public void deleteRegistration(Scanner sc) {
        System.out.print("Enter Student Code: ");
        String studentCode = sc.nextLine().trim();

        Student found = null;
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(studentCode)) {
                found = s;
                break;
            }
        }

        if (found != null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Student ID : " + found.getId());
            System.out.println("Name       : " + found.getName());
            System.out.println("Phone      : " + found.getPhone());
            System.out.println("Email      : " + found.getEmail());
            System.out.println("Mountain   : " + found.getMountainCode());
            System.out.println("Fee        : " + found.getTutionFee());
            System.out.println("--------------------------------------------------");

            System.out.print("Are you sure you want to delete this registration? (Y/N): ");
            String confirm = sc.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y")) {
                studentList.remove(found);
                System.out.println("The registration has been successfully deleted.");
            } else {
                System.out.println("Deletion canceled. Returning to main menu...");
            }
        } else {
            System.out.println("This student has not registered yet.");
        }
    }

    // Function 5: Search participants by Name
    public void searchByName(Scanner sc) {
        System.out.print("Enter student name (or part of it): ");
        String keyword = sc.nextLine().trim().toLowerCase();

        List<Student> results = new ArrayList<>();

       for (Student s : studentList) {
           if(s.getName().toLowerCase().contains(keyword)) {
               results.add(s);
           }
       }
       
       
        if (results.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            System.out.println("Matching Students:");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-12s | %-8s | %10s%n",
                    "StudentID", "Name", "Phone", "PeakCode", "Fee");
            System.out.println("-----------------------------------------------------------");
            for (Student s : results) {
                System.out.printf("%-10s | %-20s | %-12s | %-8s | %,10.0f%n",
                        s.getId(), s.getName(), s.getPhone(),
                        s.getMountainCode(), s.getTutionFee());
            }
            System.out.println("-----------------------------------------------------------");
        }
    }

    // Function 6: Filter data by Campus
    public void filterByCampus(Scanner sc) {
        System.out.print("Enter Campus Code (CE, DE, HE, SE, QE): ");
        String campusCode = sc.nextLine().trim().toUpperCase();

        List<Student> results = new ArrayList<>();

        // Lọc sinh viên có mã bắt đầu bằng campusCode
        for (Student s : studentList) {
            if (s.getId().toUpperCase().startsWith(campusCode)) {
                results.add(s);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No students have registered under this campus.");
        } else {
            System.out.println("Registered Students Under " + campusCode + " Campus:");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-12s | %-8s | %10s%n",
                    "StudentID", "Name", "Phone", "PeakCode", "Fee");
            System.out.println("-----------------------------------------------------------");
            for (Student s : results) {
                System.out.printf("%-10s | %-20s | %-12s | %-8s | %,10.0f%n",
                        s.getId(), s.getName(), s.getPhone(),
                        s.getMountainCode(), s.getTutionFee());
            }
            System.out.println("-----------------------------------------------------------");
        }
    }

    // Function 7: Statistics of registration number by Location
    public void statisticsByMountain(List<Mountain> mountains) {
        if (studentList.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }

        
        java.util.Map<String, double[]> stats = new java.util.HashMap<>();
        

        for (Student s : studentList) {
            String code = s.getMountainCode();
            double fee = s.getTutionFee();

            stats.putIfAbsent(code, new double[2]);
            stats.get(code)[0]++;        // count
            stats.get(code)[1] += fee;   // total
        }

        System.out.println("Statistics of Registration by Mountain Peak:");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-15s | %-12s%n",
                "Peak Code", "Peak Name", "Participants", "Total Cost");
        System.out.println("----------------------------------------------------------------------------------");

        for (Mountain m : mountains) {
            if (stats.containsKey(m.getMountainCode())) {
                double[] data = stats.get(m.getMountainCode());
                int count = (int) data[0];
                double total = data[1];
                System.out.printf("%-10s | %-20s | %-15d | %,12.0f%n",
                        m.getMountainCode(), m.getMountain(), count, total);
            }
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    // Function 8: Save data to file (Object Serialization)
    @SuppressWarnings("unchecked")
    public void saveToFile(String fileName) {
        try {
            java.io.File f = new java.io.File(fileName);

            if (studentList.isEmpty() && f.exists() && f.length() > 0) {
                try ( java.io.ObjectInputStream ois
                        = new java.io.ObjectInputStream(new java.io.FileInputStream(f))) {
                    studentList = (List<Student>) ois.readObject();
                    System.out.println("Data loaded successfully from '" + fileName + "'.");
                    return;
                } catch (Exception e) {
                    System.out.println("Error loading existing data: " + e.getMessage());
                }
            }

            if (!studentList.isEmpty()) {
                try ( java.io.ObjectOutputStream oos
                        = new java.io.ObjectOutputStream(new java.io.FileOutputStream(fileName))) {
                    oos.writeObject(studentList);
                    oos.flush();
                    System.out.println("Data saved successfully to '" + fileName + "'.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error saving/loading data: " + e.getMessage());
        }
    }

    public Student findById(String id) {
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> getAll() {
        return studentList;
    }
}
