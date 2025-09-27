/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duynh.tools;

/**
 *
 * @author huuduy
 */
public interface Acceptable {
    // Student ID: must start with SE, HE, DE, QE, CE + 6 digits
    public final String STU_ID_VALID = "^(SE|HE|DE|QE|CE)\\d{6}$";

    // Name: 2–20 characters, not empty
    public final String NAME_VALID = "^.{2,20}$";

    // Phone number: exactly 10 digits
    public final String PHONE_VALID = "^\\d{10}$";

    // Viettel numbers: (03x, 086, 096, 097, 098) + 7 digits
    public final String VIETTEL_VALID = "^(03[2-9]|086|09[6-8])\\d{7}$";

    // VNPT numbers: (081–085, 088, 091, 094) + 7 digits
    public final String VNPT_VALID = "^(081|082|083|084|085|088|091|094)\\d{7}$";

    // Email: standard format
    public final String EMAIL_VALID = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // --- Static method check ---
    public static boolean isValid(String data, String pattern) {
        return data != null && data.matches(pattern);
    }
}

