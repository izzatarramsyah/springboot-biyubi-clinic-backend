package com.clinic.util;

import com.clinic.entity.User;

public class MailHelper {
	

	public static String checkUpMessage(String fullname) {
		StringBuilder builder = new StringBuilder();
		builder.append("Hi <b>").append(fullname).append(",</b>");
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Terima kasih telah mempercayakan perkembangan anak anak pada Biyubi Klinik!");
		builder.append("<br>");
		builder.append("Silahkan unduh file yang terlampir pada email anda untuk melihat detail pencatatan rekam medis anak anda");		
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Salam hangat,");
		builder.append("<br>");
		builder.append("Biyubi App");
		return builder.toString();
	}
	
	public static String vaccineMessage(String fullname) {
		StringBuilder builder = new StringBuilder();
		builder.append("Hi <b>").append(fullname).append(",</b>");
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Terima kasih telah mempercayakan imunisasi anak anda pada Biyubi Klinik!");
		builder.append("<br>");
		builder.append("Silahkan unduh file yang terlampir pada email anda untuk melihat detail pencatatan imunisasi anak anda");		
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Salam hangat,");
		builder.append("<br>");
		builder.append("Biyubi App");
		return builder.toString();
	}
	
	public static String registrationUserMessage(User user) {
		StringBuilder builder = new StringBuilder();
		builder.append("Hi <b>").append(user.getFullname()).append(",</b>");
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Pendaftaran akun anda berhasil!");
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Berikut adalah detail akun anda : ");
		builder.append("<br>");
		builder.append("Username : ").append(user.getUsername());
		builder.append("<br>");
		String decryptedPassword = Security.decrypt(user.getPassword());
		builder.append("Password : ").append(decryptedPassword);
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Salam hangat,");
		builder.append("<br>");
		builder.append("Biyubi App");
		return builder.toString();
	}
	
	public static String registrationChildMessage(String fullname, String childname) {
		StringBuilder builder = new StringBuilder();
		builder.append("Hi <b>").append(fullname).append(",</b>");
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Pendaftaran data anak anda berhasil! Berikut adalah detail pencatatan pertama <b>").append(childname).append("</b>");
		builder.append("<br>");
		builder.append("Silahkan unduh file yang terlampir pada email anda untuk melihat detail pencatatan rekam medis anak anda");		
		builder.append("<br>");
		builder.append("<br>");
		builder.append("Salam hangat,");
		builder.append("<br>");
		builder.append("Biyubi App");
		return builder.toString();
	}

}
