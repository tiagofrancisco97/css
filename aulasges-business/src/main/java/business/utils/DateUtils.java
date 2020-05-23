package business.utils;

import java.util.Calendar;

import business.Dia;

/**
 * An utility class with date utilities.
 *
 */
public class DateUtils {
	
	private DateUtils() {
	}

	
	public static Calendar getAtual() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020, 03, 20, 14, 00, 00);
		return calendar;
	}
	
	
	public static Dia converte(int i) {
		if(i == Calendar.MONDAY) {
			return Dia.SEGUNDA;
		}
		if(i == Calendar.TUESDAY) {
			 return Dia.TERCA;
		}
		if(i == Calendar.WEDNESDAY) {
			return Dia.QUARTA;
		}
		if(i == Calendar.THURSDAY) {
			return Dia.QUINTA;
		}
		if(i == Calendar.FRIDAY) {
			return Dia.SEXTA;
		}
		if(i == Calendar.SATURDAY) {
			return Dia.SABADO;
		}
		if(i == Calendar.SUNDAY) {
			return Dia.DOMINGO;
		}
		return null;
		
	}
	
}
