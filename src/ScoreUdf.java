
import com.aliyun.odps.udf.UDF;

import java.util.*;

public class ScoreUdf extends UDF {
    private String resource = "half_month_details_cnt`0;(-inf,0];-0.1423274305695129#1;(0,4];0.309#2;(4,+inf);0.7521560596111081\n" +
            "half_month_driver_service_cnt`0;(-inf,0];-0.20847681748679828#1;(0,2];-0.175#2;(2,4];-0.007#3;(4,+inf);1.173296677432992\n" +
            "half_month_my_cnt`0;(-inf,0];-0.01888463006044634#1;(0,2];0.289#2;(2,5];0.297#3;(5,10];0.344#4;(10,+inf);0.7665945524643153\n" +
            "half_month_order_cnt`0;(-inf,0];-0.026061158842800324#1;(0,1];0.024925298123434678#2;(1,+inf);0.17506630294973827\n" +
            "half_month_push_cnt`0;(-inf,+inf);0\n" +
            "half_month_search_cnt`0;(-inf,0];-0.011406414752052762#1;(0,+inf);0.14321305326751566\n" +
            "half_month_share_cnt`0;(-inf,0];-0.0012566479180315703#1;(0,+inf);0.1677019519219776\n" +
            "half_month_story_cnt`0;(-inf,+inf);0\n" +
            "half_month_tab_cnt`0;(-inf,0];-0.8602039445505933#1;(0,2];-0.26416278948817706#2;(2,6];-0.11039615604949911#3;(6,14];-0.023902567402816214#4;(14,+inf);1.392841309633974\n" +
            "month_details_cnt`0;(-inf,0];-0.3115949162027252#1;(0,2];-0.263#2;(2,11];-0.148#3;(11,+inf);1.6537901939632031\n" +
            "month_driver_service_cnt`0;(-inf,0];-0.5455108933555248#1;(0,2];-0.4729689036954765#2;(2,4];-0.37403966270913314#3;(4,9];-0.283#4;(9,+inf);2.070926784960484\n" +
            "month_my_cnt`0;(-inf,2];-0.2223240894252162#1;(2,6];-0.08030022620096945#2;(6,13];0.301#3;(13,25];0.441#4;(25,+inf);1.0738315954051103\n" +
            "month_order_cnt`0;(-inf,0];-0.142#1;(0,1];-0.07817342695993376#2;(1,2];-0.06756413975484166#3;(2,+inf);0.23701440896930415\n" +
            "month_push_cnt`0;(-inf,+inf);0\n" +
            "month_search_cnt`0;(-inf,0];0.0051227707541396505#1;(0,+inf);0.031\n" +
            "month_share_cnt`0;(-inf,0];-0.001972453491076628#1;(0,+inf);0.22908796115670393\n" +
            "month_story_cnt`0;(-inf,0];-0.00007125554759669726#1;(0,+inf);2.236961033476915\n" +
            "month_tab_cnt`0;(-inf,3];-0.8904331500704543#1;(3,8];-0.33130899034957106#2;(8,16];-0.0442834819486111#3;(16,33];0.006166095794711221#4;(33,+inf);1.3841788817910428\n" +
            "week_details_cnt`0;(-inf,0];-0.06941192336301856#1;(0,+inf);0.2873875245322391\n" +
            "week_driver_service_cnt`0;(-inf,0];-0.07644763966542274#1;(0,2];-0.02177622226297317#2;(2,+inf);0.37762895111850114\n" +
            "week_my_cnt`0;(-inf,0];-0.238#1;(0,1];-0.1700893243913456#2;(1,4];-0.06591404896251196#3;(4,+inf);0.26364778830172086\n" +
            "week_order_cnt`0;(-inf,0];-0.0009860547621011332#1;(0,+inf);0.008393341156330628\n" +
            "week_push_cnt`0;(-inf,+inf);0\n" +
            "week_search_cnt`0;(-inf,0];-0.008857648479493989#1;(0,+inf);0.23518047805799963\n" +
            "week_share_cnt`0;(-inf,0];-0.0028430953169773397#1;(0,+inf);0.4456873364862643\n" +
            "week_story_cnt`0;(-inf,+inf);0\n" +
            "week_tab_cnt`0;(-inf,0];0.028123664813363425#1;(0,2];0.129#2;(2,6];0.346#3;(6,+inf);0.39783204319599813";

    private String intervals = "half_month_details_cnt`(-99999999999, 0, 4, 99999999999)\n" +
            "half_month_driver_service_cnt`(-99999999999, 0, 2, 4, 99999999999)\n" +
            "half_month_my_cnt`(-99999999999, 0, 2, 5,10, 99999999999)\n" +
            "half_month_order_cnt`(-99999999999, 0,1,99999999999)\n" +
            "half_month_push_cnt`(-99999999999, 99999999999)\n" +
            "half_month_search_cnt`(-99999999999,0, 99999999999)\n" +
            "half_month_share_cnt`(-99999999999, 0, 99999999999)\n" +
            "half_month_story_cnt`(-99999999999, 99999999999)\n" +
            "half_month_tab_cnt`(-99999999999, 0,2,6,14,99999999999)\n" +
            "month_details_cnt`(-99999999999,0,2,11, 99999999999)\n" +
            "month_driver_service_cnt`(-99999999999, 0,2,4,9,99999999999)\n" +
            "month_my_cnt`(-99999999999,2,6,13,25, 99999999999)\n" +
            "month_order_cnt`(-99999999999,0,1,2, 99999999999)\n" +
            "month_push_cnt`(-99999999999, 99999999999)\n" +
            "month_search_cnt`(-99999999999, 0, 99999999999)\n" +
            "month_share_cnt`(-99999999999,0, 99999999999)\n" +
            "month_story_cnt`(-99999999999, 0, 99999999999)\n" +
            "month_tab_cnt`(-99999999999, 3, 8, 16, 33,99999999999)\n" +
            "week_details_cnt`(-99999999999,0, 99999999999)\n" +
            "week_driver_service_cnt`(-99999999999,0,2, 99999999999)\n" +
            "week_my_cnt`(-99999999999, 0,1,4,99999999999)\n" +
            "week_order_cnt`(-99999999999,0, 99999999999)\n" +
            "week_push_cnt`(-99999999999, 99999999999)\n" +
            "week_search_cnt`(-99999999999, 0, 99999999999)\n" +
            "week_share_cnt`(-99999999999,0, 99999999999)\n" +
            "week_story_cnt`(-99999999999, 99999999999)\n" +
            "week_tab_cnt`(-99999999999, 0,2,6, 99999999999)";
    public String evaluate(String s) {
        if (s == null) { return null; }
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
        double a = Double.parseDouble(test);
        ScoreUdf scoreUdf = new ScoreUdf();
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
        for(int i=0; i<paras.length; i++)
        {
            String result = scoreUdf.evaluate(paras[i]+","+5);
        }
        System.out.println(a);
    }
}
