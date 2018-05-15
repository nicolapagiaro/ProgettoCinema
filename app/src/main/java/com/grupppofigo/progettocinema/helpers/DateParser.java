package com.grupppofigo.progettocinema.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateParser {

    /**
     * Converte la data scritta in gg/mm/yyyy in dd MMMM
     * @param rawDate data basic
     * @return stringa convertita
     * @throws ParseException eccezione di conversione sbagliata
     */
    public static String getFormattedDate(String rawDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM", Locale.ITALY);
        SimpleDateFormat parse = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

        return format.format(parse.parse(rawDate));
    }

    /**
     * Converte la data scritta in gg/mm/yyyy in dd MMMM, YYYY
     * @param rawDate data basic
     * @param showYear se mostrare anche l'anno
     * @return stringa convertita
     * @throws ParseException eccezione di conversione sbagliata
     */
    public static String getFormattedDate(String rawDate, boolean showYear) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM, YYYY", Locale.ITALY);
        SimpleDateFormat parse = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

        return format.format(parse.parse(rawDate));
    }
}
