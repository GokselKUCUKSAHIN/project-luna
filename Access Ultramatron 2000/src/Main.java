import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{

    private static Connection connection;
    private static Statement statement;
    private static boolean isInit = false;

    public static void main(String[] args) throws SQLException, FileNotFoundException
    {
        //initDatabase();
        //showTable("TCVatandas");
        //statement.executeQuery("insert into [Calisir] ([eczaciNo], [isyeriNo], [iseGirisTarih], [maas]) values (1, 2,'1998-08-08 12:45:30', '123,32');");
        //executeSQL("Vatandas.txt", "Insan");
        //createInserHasta();
        //initDatabase();
        //executeSQL("dogum","Hasta");
        //AddBirthdate();
        //Vatandas();
    }
    /*
    * ArrayList<String> sqls = loadDataFromTxt("Sehir.sql");
        for (String sql : sqls)
        {
            System.out.println(sql);
            try
            {
                //statement.executeUpdate(sql);
            }
            catch (Exception e)
            {
                //
            }
        }*/

    private static void showTable(String tableName) throws SQLException
    {
        ResultSet rs = statement.executeQuery(String.format("Select * from [%s];", tableName));
        ResultSetMetaData msm = rs.getMetaData();
        while (rs.next())
        {
            for (int i = 1; i <= msm.getColumnCount(); i++)
            {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    private static void showSehir() throws SQLException
    {
        ResultSet rs = statement.executeQuery("Select * from [ATC];");
        ResultSetMetaData msm = rs.getMetaData();
        while (rs.next())
        {
            for (int i = 1; i <= msm.getColumnCount(); i++)
            {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    private static void initDatabase() throws SQLException
    {
        //String url = "jdbc:ucanaccess://C:/VTYS/VTYS.accdb";
        String url = "jdbc:ucanaccess://ACCESSDB.accdb";
        //
        Connection connection = DriverManager.getConnection(url);
        statement = connection.createStatement();
    }

    private static ArrayList<String> loadDataFromTxt(String FileName) throws FileNotFoundException
    {
        ArrayList<String> rows = new ArrayList<>();
        String path = "QueryFolder/" + FileName; //res/data.txt //default
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
        {
            //Scan everyline and append to arrayList
            rows.add(scanner.nextLine());
        }
        return rows;
    }

    private static void vatandas()
    {
        ArrayList<String> insert = new ArrayList<>();
        for (int i = 1; i <= 750; i++)
        {
            if (Utils.getRandom(1) < 0.96)
            {
                //TC
                ArrayList<String> usedTC = new ArrayList<>();
                String tc = createTC();
                while (usedTC.contains(tc))
                {
                    tc = createTC();
                }
                //
                String guvence = "";
                if (Utils.getRandom(1) <= 0.8)
                {
                    guvence = "SGK";
                } else
                {
                    guvence = "Yeşil Kart";
                }
                insert.add(String.format("Insert into [TCVatandas] ([TCno], [Guvence], [insanNo]) values ('%s', '%s', %d);", tc, guvence, i));
            } else
            {
                int ikamet = Utils.getRandomInt(100000, 999999);
                int taahut = Utils.getRandomInt(100000, 999999);
                insert.add(String.format("Insert into [Yabanci] ([ikametgahNo], [taahhutnameNo], [insanNo]) values ('%06d', '%06d', %d);", ikamet, taahut, i));
            }
        }
        for (String row : insert)
        {
            System.out.println(row);
        }
    }

    private static void createInserHasta() throws FileNotFoundException
    {
        ArrayList<String> isimCinsiyet = loadDataFromTxt("isim.txt");
        ArrayList<String> soyisimList = loadDataFromTxt("soyisim.txt");
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
        {
            nums.add(i + 1); //rip
        }
        ArrayList<String> usedTel = new ArrayList<>();
        //DONE
        for (int i = 0; i < 750; i++)
        {
            String tel = createTel();
            while (usedTel.contains(tel))
            {
                tel = createTel();
            }
            //
            int adIndex = Rand.getInt(isimCinsiyet.size());
            String[] div = isimCinsiyet.get(adIndex).split(";");
            String ad = div[0];
            String cinsiyet = div[1];
            isimCinsiyet.remove(adIndex);
            //
            int soyadIndex = Rand.getInt(soyisimList.size());
            String soyad = soyisimList.get(soyadIndex);
            soyisimList.remove(soyadIndex);
            //

            int adrindex = Rand.getInt(nums.size() - 100);
            int adr = nums.get(adrindex);
            nums.remove(adrindex);
            //
            String date = String.format("%04d-%02d-%02d 0:0:0", Rand.getInt(1940, 2005), Rand.getInt(1, 13), Rand.getInt(1, 28));
            //
            System.out.printf("Insert into [Insan] ([Ad], [Soyad], [TelNo], [Cinsiyet], [DogumTarih], [AdresNo]) values ('%s', '%s', '%s', '%s', '%s', %d);\n", ad, soyad, tel, cinsiyet, date, adr);
        }
    }

    private static void createInsertAdres() throws FileNotFoundException
    {
        ArrayList<String> queries = loadDataFromTxt("adress.txt");
        ArrayList<String> mahalle = new ArrayList<>();
        ArrayList<String> cadde = new ArrayList<>();
        ArrayList<String> zipcode = new ArrayList<>();
        for (String row : queries)
        {
            String[] arr = row.split(";");
            mahalle.add(arr[0].trim());
            cadde.add(arr[1].trim());
            zipcode.add(arr[2].trim());
        }
        int limit = mahalle.size();
        for (int i = 0; i < 1200; i++)
        {
            int ilceNo = Rand.getInt(1, 971);
            int randomAdr = Rand.getInt(limit);
            String no = "" + Rand.getInt(76) + "/" + Rand.getInt(35);
            System.out.printf("Insert into [Adres] ([ilce_no], [mahalle], [cadde], [no], [postakodu]) values (%s, '%s', '%s', '%s', %s);\n",
                    ilceNo, mahalle.get(randomAdr), cadde.get(randomAdr), no, zipcode.get(randomAdr));
        }
    }

    private static void executeSQL(String queryPath, String tableName) throws SQLException, FileNotFoundException
    {
        initDatabase();
        ArrayList<String> queries = loadDataFromTxt(queryPath);
        for (String query : queries)
        {
            //System.out.println(query);
            try
            {
                statement.executeUpdate(query);
            }
            catch (Exception ex)
            {
                System.out.println("ERR: " + query);
            }
        }
        showTable(tableName);
    }

    private static void addBirthdate()
    {
        for (int i = 1; i <= 751; i++)
        {
            String date = String.format("%04d-%02d-%02d 0:0:0", Rand.getInt(1940, 2005), Rand.getInt(1, 13), Rand.getInt(1, 28));
            System.out.printf("Update [Hasta] set [DogumTarih] = '%s' where [id] = %d;\n", date, i);
        }
    }

    private static void commaSeperatedWorker()
    {
        int index = 1;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                System.out.printf("%d;%d;%d;%4d-%02d-%02d 0:0:0;%d,%03d\n", index++, index + 103, j + 1, Utils.getRandomInt(2000, 2014),
                        Utils.getRandomInt(1, 13), Utils.getRandomInt(1, 28), Utils.getRandomInt(4000, 8500), Utils.getRandomInt(0, 1000));
            }
        }
    }

    private static void divIsim() throws FileNotFoundException
    {
        ArrayList<String> queries = loadDataFromTxt("ISIM.txt");
        for (String row : queries)
        {
            String[] liste = row.split(";");
            if (!liste[2].equals("U"))
            {
                System.out.println(liste[1] + ";" + liste[2]);
            }
        }
    }

    private static String createTel()
    {
        return String.format("05%02d%03d%04d", Rand.getInt(30, 40), Rand.getInt(100, 980), Rand.getInt(0, 9999));
    }

    private static String createTC()
    {
        int seed = Utils.getRandomInt(100000000, 999999999);
        String tc = Integer.toString(seed);
        int odd = 0;
        int even = 0;
        for (int i = 0; i < tc.length(); i++)
        {
            if (i % 2 == 0)
            {
                //odd
                odd += Character.getNumericValue(tc.charAt(i));
            } else
            {
                //even
                even += Character.getNumericValue(tc.charAt(i));
            }
        }
        odd = (odd * 7 - even) % 10;
        tc += Integer.toString(odd);
        int sum = 0;
        for (int i = 0; i < tc.length(); i++)
        {
            sum += Character.getNumericValue(tc.charAt(i));
        }
        tc += Integer.toString(sum % 10);
        return tc;
    }
}