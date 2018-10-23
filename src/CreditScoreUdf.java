
import com.aliyun.odps.udf.UDF;

import java.util.*;

public class CreditScoreUdf extends UDF {
    private String resource = "dlq_cnt`0;(-inf,0];8.651969636794846#1;(0,1];8.053347490440455#2;(1,+inf);-10.755179356272519\n" +
            "dlq_cnt_rate`0;(-inf,0];8.634582875770377#1;(0,0.2];-7.374796108630175#2;(0.2,+inf);-9.542791157320734\n" +
            "dlq_days_in3`0;(-inf,0];8.186955357750024#1;(0,+inf);5.32182832091459\n" +
            "dlq_gt15_cnt`0;(-inf,0];6.2539272898542#1;(0,+inf);4.053095224017153\n" +
            "dlq_gt3_cnt`0;(-inf,0];6.534175414084377#1;(0,+inf);1.3496001368115365\n" +
            "dlq_gt7_cnt`0;(-inf,0];6.478400045359021#1;(0,+inf);-1.6419035798286274\n" +
            "dql_days_now`0;(-inf,0];6.244845817876927#1;(0,+inf);5.55734115714897\n" +
            "first_dlq_days`0;(-inf,0];8.6661130415637#1;(0,+inf);-14.656824914330455\n" +
            "id_no_plat_debt`0;(-inf,0];6.286939179207213#1;(0,+inf);-1.6419363669792628\n" +
            "id_no_plat_loan`0;(-inf,0];16.34452942343296#1;(0,1];7.000520946565539#2;(1,2];3.267834134276752#3;(2,8];-1.7682744324274045#4;(8,+inf);-6.354665242539355\n" +
            "latefee_unpaid`0;(-inf,0];6.292965666431625#1;(0,+inf);1.9339024899368642\n" +
            "max_dlq_days`0;(-inf,0];9.10683181912805#1;(0,1];0.3917056487991042#2;(1,+inf);-2.823946168565221\n" +
            "rent_dlq_unpaid`0;(-inf,0];6.244845789075484#1;(0,+inf);5.55734242640113";

    private String intervals = "dlq_cnt`(-999999999,0,1,999999999)\n" +
            "dlq_cnt_rate`(-999999999,0,0.2,999999999)\n" +
            "dlq_days_in3`(-999999999,0,999999999)\n" +
            "dlq_gt15_cnt`(-999999999,0,999999999)\n" +
            "dlq_gt3_cnt`(-999999999,0,999999999)\n" +
            "dlq_gt7_cnt`(-999999999,0,999999999)\n" +
            "dql_days_now`(-999999999,0,999999999)\n" +
            "first_dlq_days`(-999999999,0,999999999)\n" +
            "id_no_plat_debt`(-999999999,0,999999999)\n" +
            "id_no_plat_loan`(-999999999,0,1,2,8,999999999)\n" +
            "latefee_unpaid`(-999999999,0,999999999)\n" +
            "max_dlq_days`(-999999999,0,1,999999999)\n" +
            "rent_dlq_unpaid`(-999999999,0,999999999)";
    public String evaluate(String s) {
        if(null== s)
            return null;
        //入参分割
        String[] inputs = s.split(",");
        String inputFeatureName = inputs[0].trim();
        double inputFeatureValue = Double.valueOf(inputs[1]);
        Map<String,String> featureIntervals = new HashMap<>();
        String[] lines = resource.split("\n");
        int len = lines.length;
        //解析区间分数文件
        Map<String, Map<Integer, Double>> intervalScores = new HashMap<>();
        for(int i=0; i<len; i++)
        {
            String line = lines[i];
            String[] tmp = line.split("`");
            featureIntervals.put(tmp[0], tmp[1]);
//            System.out.println(tmp[0]+"----------"+tmp[1]);
            String[] intervals = tmp[1].split("#");
            Map<Integer, Double> tmpMap = new HashMap<>();

            for(int j=0; j<intervals.length; j++)
            {
                String[] intervalScore = intervals[j].split(";");
                int rangeId = Integer.valueOf(intervalScore[0]);
                double rangeScore = Double.valueOf(intervalScore[2]);
                tmpMap.put(rangeId,rangeScore);
            }
            intervalScores.put(tmp[0], tmpMap);
        }
        //解析区间文件
        lines = intervals.split("\n");
        len = lines.length;
        //放置区间数组
        Map<String, List<Double>> intervalMap = new HashMap<>();
        for(int i=0; i<len; i++)
        {
            String[] line = lines[i].split("`");
            String featureName = line[0];
            String intervalStr = line[1];
            intervalStr = intervalStr.replaceAll("\\(","").replaceAll("\\)","");
            String[] points = intervalStr.split(",");
            List<Double> pointsList = new ArrayList<>();
            for(int j=0; j<points.length; j++)
            {
                pointsList.add(Double.valueOf(points[j]));
            }
            //对points排序
            Collections.sort(pointsList);
            intervalMap.put(featureName, pointsList);
        }
        //判断输入值在那个区间
        List<Double> inputList = intervalMap.get(inputFeatureName);
        int index = getIntervalIndex(inputFeatureValue, inputList);
        Map<Integer, Double> certainFeatureScore = intervalScores.get(inputFeatureName);
        double score = certainFeatureScore.get(index);
        String result = score+"*"+index;
        return result;
    }
    public int getIntervalIndex(double value, List<Double> intervals)
    {
        if(null==intervals||intervals.size()==0)
            return  0;
        int size = intervals.size();
        for(int i=0; i<size-1; i++)
        {
            if(value>intervals.get(i)&&value<=intervals.get(i+1))
                return i;
        }
        return 0;
    }
    public static void main(String[] args)
    {
        String test = "-2.1080955991265027e-17";

        CreditScoreUdf scoreUdf = new CreditScoreUdf();
        String inputs = "week_my_cnt\n" +
                "week_details_cnt\n" +
                "week_driver_service_cnt\n" +
                "week_order_cnt\n" +
                "week_push_cnt\n" +
                "week_search_cnt\n" +
                "week_share_cnt\n" +
                "week_story_cnt\n" +
                "week_tab_cnt\n" +
                "half_month_my_cnt\n" +
                "half_month_details_cnt\n" +
                "half_month_driver_service_cnt\n" +
                "half_month_order_cnt\n" +
                "half_month_push_cnt\n" +
                "half_month_search_cnt\n" +
                "half_month_share_cnt\n" +
                "half_month_story_cnt\n" +
                "half_month_tab_cnt\n" +
                "month_my_cnt\n" +
                "month_details_cnt\n" +
                "month_driver_service_cnt\n" +
                "month_order_cnt\n" +
                "month_push_cnt\n" +
                "month_search_cnt\n" +
                "month_share_cnt\n" +
                "month_story_cnt\n" +
                "month_tab_cnt";
        String[] paras = inputs.split("\n");
//        {
            String result = scoreUdf.evaluate("dlq_cnt"+","+5);
//        }
    }
}
