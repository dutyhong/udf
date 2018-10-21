
import com.aliyun.odps.udf.UDF;

import java.util.*;

public class CreditScoreUdf extends UDF {
    private String resource = "dlq_cnt`0;(-999999999,0];5.094239557794846#1;(0,1];4.495617411440454#2;(1,999999999);-14.31290943527252\n" +
            "dlq_cnt_rate`0;(-999999999,0];5.076852796770377#1;(0,0.2];-10.932526187630176#2;(0.2,999999999);-13.100521236320734\n" +
            "dlq_days_in3`0;(-999999999,0];4.629225278750025#1;(0,999999999);1.76409824191459\n" +
            "dlq_gt15_cnt`0;(-999999999,0];2.6961972108542#1;(0,999999999);0.4953651450171533\n" +
            "dlq_gt3_cnt`0;(-999999999,0];2.9764453350843767#1;(0,999999999);-2.2081299421884637\n" +
            "dlq_gt7_cnt`0;(-999999999,0];2.920669966359021#1;(0,999999999);-5.199633658828628\n" +
            "dql_days_now`0;(-999999999,0];2.687115738876927#1;(0,999999999);1.99961107814897\n" +
            "first_dlq_days`0;(-999999999,0];5.1083829625637#1;(0,999999999);-18.214554993330456\n" +
            "id_no_plat_debt`0;(-999999999,0];2.729209100207213#1;(0,999999999);-5.199666445979263\n" +
            "id_no_plat_loan`0;(-999999999,0];12.78679934443296#1;(0,1];3.4427908675655385#2;(1,2];-0.28989594472324787#3;(2,8];-5.326004511427405#4;(8,999999999);-9.912395321539355\n" +
            "latefee_unpaid`0;(-999999999,0];2.735235587431625#1;(0,999999999);-1.623827589063136\n" +
            "max_dlq_days`0;(-999999999,0];5.54910174012805#1;(0,1];-3.166024430200896#2;(1,999999999);-6.381676247565221\n" +
            "rent_dlq_unpaid`0;(-999999999,0];2.6871157100754837#1;(0,999999999);1.9996123474011296";

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
