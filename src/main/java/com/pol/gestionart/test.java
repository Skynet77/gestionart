package com.pol.gestionart;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {

	public static void main(String[] args) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("user"));
	}

}
