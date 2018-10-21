import java.io.*;

public class StrProcess {
    public static void main(String[] args)
    {
        String test = "dlq_cnt\n" +
                "dlq_cnt_rate\n" +
                "dlq_days_in3\n" +
                "dlq_gt15_cnt\n" +
                "dlq_gt3_cnt\n" +
                "dlq_gt7_cnt\n" +
                "dql_days_now\n" +
                "first_dlq_days\n" +
                "id_no_plat_debt\n" +
                "id_no_plat_loan\n" +
                "latefee_unpaid\n" +
                "max_dlq_days\n" +
                "rent_dlq_unpaid";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("txt"));
            String[] lines = test.split("\n");
            String str = "";
            for(int i=0; i<lines.length; i++) {
                String featureName = lines[i].trim();
//                str = "cast(split_part(credit_score_udf(concat('"+featureName+
//                        "',',',coalesce("+featureName+",0))),'*',1) as double) as "+featureName+",";
                str = str+ "a."+featureName+"+";
            }
            bw.write(str + "\n");

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
