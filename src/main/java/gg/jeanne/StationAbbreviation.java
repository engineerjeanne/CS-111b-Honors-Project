package gg.jeanne;

import java.util.HashMap;

public class StationAbbreviation {

    public static String getStationFromAbbrev(String abbrev) {
        HashMap<String, String> map = new HashMap<>();
        map.put("12th",	"12TH/OAKLAND");
        map.put("16th",	"16TH/MISSION");
        map.put("19th",	"19TH/OAKLAND");
        map.put("24th",	"24TH/MISSION");
        map.put("ashb",	"ASHBY");
        map.put("antc",	"ANTIOCH");
        map.put("balb",	"BALBOA PARK");
        map.put("bayf",	"BAY FAIR");
        map.put("bery",	"BERRYESSA");
        map.put("cast",	"CSTRO VALLEY");
        map.put("civc",	"CIVIC CENTER");
        map.put("cols",	"COLISEUM");
        map.put("colm",	"COLMA");
        map.put("conc",	"CONCORD");
        map.put("daly",	"DALY CITY");
        map.put("dbrk",	"DTWN BERKLEY");
        map.put("dubl",	"DUBLIN");
        map.put("deln",	"EC DEL NORTE");
        map.put("plza",	"EC PLAZA");
        map.put("embr",	"EMBARCADERO");
        map.put("frmt",	"FREMONT");
        map.put("ftvl",	"FRUITVALE");
        map.put("glen",	"GLEN PARK");
        map.put("hayw",	"HAYWARD");
        map.put("lafy",	"LAFAYETTE");
        map.put("lake",	"LK MERRITT");
        map.put("mcar",	"MACARTHUR");
        map.put("mlbr", "MILLBRAE");
        map.put("mlpt",	"MILPITAS");
        map.put("mont",	"MONTGOMERY");
        map.put("nbrk",	"N. BERKELEY");
        map.put("ncon",	"N. CONCORD");
        map.put("oakl",	"OAK AIRPORT");
        map.put("orin",	"ORINDA");
        map.put("pitt",	"PITTSBURG/BP");
        map.put("pctr",	"PTS CENTER");
        map.put("phil",	"PLEASANT HILL");
        map.put("powl",	"POWELL");
        map.put("rich",	"RICHMOND");
        map.put("rock",	"ROCKRIDGE");
        map.put("sbrn",	"SAN BRUNO");
        map.put("sfia",	"SFO AIRPORT");
        map.put("sanl",	"SAN LEANDRO");
        map.put("shay",	"S. HAYWARD");
        map.put("ssan",	"SOUTH CITY");
        map.put("ucty",	"UNION CITY");
        map.put("warm",	"WARM SPRINGS");
        map.put("wcrk",	"WALNUT CRK");
        map.put("wdub",	"W. DUBLIN");
        map.put("woak",	"W. OAKLAND");


        return map.get(abbrev);
    }

    public static String getColorAbbrev(String color) {
        HashMap<String, String> map = new HashMap<>();
        map.put("RED", "RD");
        map.put("ORANGE", "OR");
        map.put("YELLOW", "YL");
        map.put("GREEN", "GN");
        map.put("BLUE", "BL");
        map.put("PURPLE", "PR");
        map.put("null", "");

        return map.get(color);
    }

}
