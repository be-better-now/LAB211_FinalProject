package duynh.controller;

import java.util.Scanner;
import duynh.dao.Students;
import duynh.models.Mountain;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author huuduy
 */
public class Main {

    public static List<Mountain> loadMountains(String fileName) {
        // Danh sách lưu các mountain đã đăng ký
        List<Mountain> list = new ArrayList<>();
        File f = new File(fileName);

        if (!f.exists()) {
            System.out.println("File not found: " + fileName);
            return list;
        }

        try ( BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), "UTF-8"))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // bỏ dòng trắng
                }
                if (isFirstLine) { // bỏ dòng header: "Code,Mountain,Province,Description"
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",", -1); // -1 để giữ cả cột trống
                if (parts.length >= 4) {
                    Mountain mtn = new Mountain(
                            parts[0].trim(), // Code
                            parts[1].trim(), // Mountain
                            parts[2].trim(), // Province
                            parts[3].trim() // Description
                    );
                    list.add(mtn);
                } else {
                    System.out.println("Skipped line (not enough fields): " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading mountain list: " + e.getMessage());
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println("HIKING MOUNTAIN");
        int choice;
        Scanner sc = new Scanner(System.in);

        // 1. Load mountain list từ file CSV
        List<Mountain> mountainList = loadMountains("MountainList.csv");
        if (mountainList.isEmpty()) {
            System.out.println("Mountain list is empty! Please check MountainList.csv");
        }

        // 2. Tạo Students DAO (lưu ds sinh viên)
        Students dao = new Students();
        do {
            System.out.println("1. New Registration");
            System.out.println("2. Update Registration Information");
            System.out.println("3. Display Registered List");
            System.out.println("4. Delete Registration Information");
            System.out.println("5. Search Participants by Name");
            System.out.println("6. Filter Data by Campus");
            System.out.println("7. Statistics of Registration Numbers of Location");
            System.out.println("8. Save Data to File");
            System.out.println("9. Exit Program!!!");
            System.out.println("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    dao.addStudent(sc, mountainList);
                    break;

                case 2:
                    dao.updateStudent(sc, mountainList);
                    break;

                case 3:
                    dao.displayStudents();
                    break;

                case 4:
                    dao.deleteRegistration(sc);
                    break;

                case 5:
                    dao.searchByName(sc);
                    break;

                case 6:
                    dao.filterByCampus(sc);
                    break;

                case 7:
                    dao.statisticsByMountain(mountainList);
                    break;

                case 8:
                    dao.saveToFile("registrations.dat");
                    break;

                case 9:
                    // Trước khi thoát thì hỏi người dùng có muốn lưu lại dữ liệu không
                    System.out.print("Do you want to save the registrations before exiting? (Y/N): ");
                    String confirm = sc.nextLine().trim().toUpperCase();

                    if (confirm.equals("Y")) {
                        dao.saveToFile("registrations.dat");
                        System.out.println("Data saved successfully!");
                    } else {
                        System.out.println("Exit without saving data!");
                    }

                    System.out.println("Exiting program... Byeeee!");
                    sc.close(); // đóng scanner để tránh memory leak
                    System.exit(0);
                    break;

            }
        } while (choice != 9);
    }

}
