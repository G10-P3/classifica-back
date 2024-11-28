package br.com.g10.BEM.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class DateUtils {

    private DateUtils() {
        // Construtor privado para evitar instâncias de uma classe utilitária
    }

    /**
     * Calcula a idade com base na data de nascimento.
     *
     * @param birthDate A data de nascimento
     * @return A idade calculada em anos, ou 0 se a data de nascimento for nula
     */
    public static int calculateAge(Date birthDate) {
        if (birthDate == null) return 0;
        LocalDate birthLocalDate = new java.sql.Date(birthDate.getTime()).toLocalDate();
        return Period.between(birthLocalDate, LocalDate.now()).getYears();
    }
}
