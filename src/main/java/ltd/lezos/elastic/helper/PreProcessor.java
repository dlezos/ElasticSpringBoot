package ltd.lezos.elastic.helper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class PreProcessor {
    static Long index = 0L;
    static Random random = new Random(System.currentTimeMillis());

    static String[] countries = {"AFG", "ALA", "ALB", "DZA", "ASM", "AND", "AGO", "AIA", "ATA", "ATG", "ARG", "ARM", "ABW", "AUS", "AUT", "AZE", "BHS", "BHR", "BGD", "BRB", "BLR", "BEL", "BLZ", "BEN", "BMU", "BTN", "BOL", "BES", "BIH", "BWA", "BVT", "BRA", "IOT", "BRN", "BGR", "BFA", "BDI", "CPV", "KHM", "CMR", "CAN", "CYM", "CAF", "TCD", "CHL", "CHN", "CXR", "CCK", "COL", "COM", "COD", "COG", "COK", "CRI", "CIV", "HRV", "CUB", "CUW", "CYP", "CZE", "DNK", "DJI", "DMA", "DOM", "ECU", "EGY", "SLV", "GNQ", "ERI", "EST", "SWZ", "ETH", "FLK", "FRO", "FJI", "FIN", "FRA", "GUF", "PYF", "ATF", "GAB", "GMB", "GEO", "DEU", "GHA", "GIB", "GRC", "GRL", "GRD", "GLP", "GUM", "GTM", "GGY", "GIN", "GNB", "GUY", "HTI", "HMD", "VAT", "HND", "HKG", "HUN", "ISL", "IND", "IDN", "IRN", "IRQ", "IRL", "IMN", "ISR", "ITA", "JAM", "JPN", "JEY", "JOR", "KAZ", "KEN", "KIR", "PRK", "KOR", "KWT", "KGZ", "LAO", "LVA", "LBN", "LSO", "LBR", "LBY", "LIE", "LTU", "LUX", "MAC", "MKD", "MDG", "MWI", "MYS", "MDV", "MLI", "MLT", "MHL", "MTQ", "MRT", "MUS", "MYT", "MEX", "FSM", "MDA", "MCO", "MNG", "MNE", "MSR", "MAR", "MOZ", "MMR", "NAM", "NRU", "NPL", "NLD", "NCL", "NZL", "NIC", "NER", "NGA", "NIU", "NFK", "MNP", "NOR", "OMN", "PAK", "PLW", "PSE", "PAN", "PNG", "PRY", "PER", "PHL", "PCN", "POL", "PRT", "PRI", "QAT", "REU", "ROU", "RUS", "RWA", "BLM", "SHN", "KNA", "LCA", "MAF", "SPM", "VCT", "WSM", "SMR", "STP", "SAU", "SEN", "SRB", "SYC", "SLE", "SGP", "SXM", "SVK", "SVN", "SLB", "SOM", "ZAF", "SGS", "SSD", "ESP", "LKA", "SDN", "SUR", "SJM", "SWE", "CHE", "SYR", "TWN", "TJK", "TZA", "THA", "TLS", "TGO", "TKL", "TON", "TTO", "TUN", "TUR", "TKM", "TCA", "TUV", "UGA", "UKR", "ARE", "GBR", "UMI", "USA", "URY", "UZB", "VUT", "VEN", "VNM", "VGB", "VIR", "WLF", "ESH", "YEM", "ZMB", "ZWE"};
    public static void main(String[] args) {
        try {
            //First handle the names
            HashMap<String, Name> nameHashMap = new HashMap<>();
            readNames(nameHashMap, "./names.csv");
            //Now write the new file adding the index value for each entry
            writeWithIndex(nameHashMap.values(), "./names_comb.csv");
            //Now handle the surnames
            nameHashMap = new HashMap<>();
            readNames(nameHashMap, "./surnames.txt");
            //Now write the new file adding the index value for each entry
            writeWithIndex(nameHashMap.values(), "./surnames_comb.csv");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void readNames(HashMap<String, Name> nameHashMap, String filePath) {
        try {
            //First handle the names
            Path path = Paths.get(filePath);
            //Read the input (which contains multiple entries for each name
            Stream<String> lines = Files.lines(path);
            lines.forEach(s -> {
                //System.out.println(s);
                if (!"Name,Sex,Count,Year".equals(s) && !"name,rank,count,prop100k,cum_prop100k,pctwhite,pctblack,pctapi,pctaian,pct2prace,pcthispanic".equals(s)) {
                    Name name = new Name(s.split(","));
                    if (!nameHashMap.containsKey(name.getName())) {
                        nameHashMap.put(name.getName(), name);
                    } else {
                        nameHashMap.get(name.getName()).addNumber(name.getNumber());
                    }
                }
            });
            lines.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void writeWithIndex(Collection<Name> names, String path) {
        index = 0L;
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            names.forEach(n -> {
                try {
                    index += n.getNumber();
                    n.setIndex(index);
                    writer.write(n.toString());
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Name[] readNames(String filePath) {
        LinkedList<Name> names = new LinkedList<>();
        try {
            //First handle the names
            Path path = Paths.get(filePath);
            //Read the input (which contains multiple entries for each name
            Stream<String> lines = Files.lines(path);
            lines.forEach(s -> {
                Name name = new Name(s.split(","));
                names.add(name);
            });
            lines.close();
            Collections.sort(names);
            return names.toArray(new Name[names.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String randomName(Name[] names) {
        long index = (long)(random.nextDouble()*names[names.length-1].index);
        for(Name name: names){
            if(name.index > index) {
                return name.getName();
            }
        }
        return names[names.length-1].getName();
    }

    public static Date randomDate(int past, int future) {
        return new Date((long) (System.currentTimeMillis()+(future*1000*60*60*24*365)-(random.nextGaussian()*1000*60*60*24*365*(past+future))));
    }

    public static String randomCountry() {
        return countries[(int)(random.nextDouble()*countries.length)];
    }

    public static String randomNumber() {
        String number = "000000"+random.nextLong()%1000000;
        return number.substring(number.length()-6);
    }

    public static Long randomNumber(Long min, Long max) {
        return min+(random.nextLong()%(max-min));
    }

    public static boolean isPercent(double percent) {
        return random.nextDouble() > percent/100;
    }

    public static class Name implements Comparable<Name> {
        String name;
        String gender;
        Long number;

        Long index;

        public Name(String[] fields){
            name = fields[0];
            gender = "M".equals(fields[1]) || "F".equals(fields[1]) ? fields[1] : "G";
            number = Long.parseLong(fields[2]);
            if (fields.length == 4) {
                index = Long.parseLong(fields[3]);
            }
        }

        public void addNumber(Long number) {
            this.number += number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Long getNumber() {
            return number;
        }

        public void setNumber(Long number) {
            this.number = number;
        }

        public Long getIndex() {
            return index;
        }

        public void setIndex(Long index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return name + ',' + gender + ',' + number + ',' + index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Name name1 = (Name) o;
            return name.equals(name1.name) && index.equals(name1.index);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, index);
        }

        @Override
        public int compareTo(Name o) {
            if (this == o || o == null || getClass() != o.getClass()) {
                return 1;
            }
            int result = 0;
            if (this.index != null && o.index != null) {
                result = index.compareTo(o.index);
            }
            if (result == 0) {
                if (this.name != null && o.name != null) {
                    result = name.compareTo(o.name);
                } else {
                    result = 1;
                }
            }
            return result;
        }
    }
}
