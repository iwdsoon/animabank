package com.mindhub.homebanking.utilities;

public class Utility {

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
