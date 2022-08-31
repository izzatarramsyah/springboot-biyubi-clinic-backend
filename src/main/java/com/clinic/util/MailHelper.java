package com.clinic.util;

public class MailHelper {
	

	public static String forgotPasswordMessage(String fullname, String username, String password) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("Hai ").append(username).append(", <br>");
		builder.append("Berikut detail informasi akun <b>Biyubi App</b> anda:<br> ");
		builder.append("Username : ").append(username).append("<br>");
		builder.append("Password : ").append(password).append("<br><br>");
		builder.append("Terima Kasih, <br>");
		builder.append("Biyubi App");
		
		return builder.toString();
	}
	
	public static String successRegistrationMessage(String fullname, String username, String password) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("Hai ").append(username).append(", <br>");
		builder.append("<h3><b>Selamat, akun Biyubi App anda terlah berhasil dibuat!<b></h3><br>");
		builder.append("Terima kasih telah menggunakan <b>Biyubi App</b>. Berikut detail informasi akun anda:<br>");
		builder.append("Username : ").append(username).append("<br>");
		builder.append("Password : ").append(password).append("<br><br>");
		builder.append("Terima Kasih,<br>");
		builder.append("Biyubi App");
		
		return builder.toString();
	}
	
}
