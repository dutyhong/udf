
import com.aliyun.odps.udf.UDF;

import java.util.*;

public class ActionScoreUdf extends UDF {
    private String resource = "fine_amount`0;(-inf,0];5.338749808620795#1;(0,50];3.1856392292508344#2;(50,200];2.947958191268436#3;(200,550];1.1583597876362617#4;(550,+inf);-3.6142417434706613\n" +
            "fine_score`0;(-inf,0];6.988535836969205#1;(0,3];5.796096848979996#2;(3,9];3.7658605866784534#3;(9,+inf);-10.331759836618723\n" +
            "handle_fine_amount`0;(-inf,0];4.1293727624162395#1;(0,200];2.2419056960854298#2;(200,+inf);0.17747725820298318\n" +
            "handle_fine_amount_rate`0;(-inf,0];-1.4281691551133655#1;(0,0.65];-0.596285522174972#2;(0.65,+inf);4.091563464542384\n" +
            "handle_fine_score`0;(-inf,0];3.2090746952754228#1;(0,3];3.12838747464226#2;(3,+inf);-3.1424711954227647\n" +
            "handle_fine_score_rate`0;(-inf,0];1.822468570234139#1;(0,+inf);3.024854997674506\n" +
            "handle_illegal`0;(-inf,0];2.623878901571816#1;(0,1];2.080627037488948#2;(1,+inf);0.45440815811621554\n" +
            "handle_illegal_num_rate`0;(-inf,0];5.5799070701665086#1;(0,0.625];8.939960086867085#2;(0.625,+inf);4.744547213664799\n" +
            "illegal_num`0;(-inf,0];9.211552603981048#1;(0,1];6.3174364356071395#2;(1,2];3.1506861354298934#3;(2,4];2.696295915757662#4;(4,+inf);-8.61976569231761\n" +
            "insurance_num`0;(-inf,0];2.6473615844083445#1;(0,+inf);2.060149608216537\n" +
            "mile_contract`0;(-inf,+inf);2.1370464146226102\n" +
            "mile_power_plus`0;(-inf,0];2.1257746591844726#1;(0,+inf);4.209681167469397\n" +
            "miles_month`0;(-inf,578073.2215];5.596455550008896#1;(578073.2215,966083.9161];3.6375734558350064#2;(966083.9161,1396049.505];1.8717294469889278#3;(1396049.505,2050191.781];0.7395235556863986#4;(2050191.781,+inf);-1.1600583931616693\n" +
            "only_amount`0;(-inf,0];2.428176123482095#1;(0,200];0.5291394737240185#2;(200,+inf);3.475606761659551\n" +
            "only_amount_num`0;(-inf,0];0.815429518694552#1;(0,2];-3.944791910221111#2;(2,+inf);-5.252727170449581\n" +
            "over_mile_rate`0;(-inf,0];2.53949797644348#1;(0,+inf);-3.2902170386284415\n" +
            "sum_miles`0;(-inf,1845];-4.395084754583278#1;(1845,4341];2.2320991049086714#2;(4341,7675];4.3111757313601995#3;(7675,12932];4.52031583601605#4;(12932,+inf);4.017564753456517\n" +
            "untreat_fine_amount`0;(-inf,0];0.4887831950529323#1;(0,100];2.987191577394957#2;(100,300];4.321769477166567#3;(300,+inf);4.367547981214154\n" +
            "untreat_fine_score`0;(-inf,0];3.6393339339476163#1;(0,6];1.0583763781276896#2;(6,+inf);-2.9565001690749164\n" +
            "untreat_illegal`0;(-inf,0];6.772637548942901#1;(0,1];1.9965787933889267#2;(1,2];-3.7767946274911464#3;(2,+inf);-7.488019960905409\n" +
            "untreat_only_amount`0;(-inf,0];2.176751225781414#1;(0,100];0.8292221111813642#2;(100,+inf);0.2216168732350452\n" +
            "untreat_only_amount_num`0;(-inf,0];2.245472788893251#1;(0,1];2.0321871331597845#2;(1,+inf);1.056502992627352";

    private String intervals = "fine_amount`(-999999999,0,50,200,550,999999999)\n" +
            "fine_score`(-999999999,0,3,9,999999999)\n" +
            "handle_fine_amount`(-999999999,0,200,999999999)\n" +
            "handle_fine_amount_rate`(-999999999,0.65,999999999)\n" +
            "handle_fine_score`(-999999999,0,3,999999999)\n" +
            "handle_fine_score_rate`(-999999999,0,999999999)\n" +
            "handle_illegal`(-999999999,0,1,999999999)\n" +
            "handle_illegal_num_rate`(-999999999,0,0.625,999999999)\n" +
            "illegal_num`(-999999999,0,1,2,4,999999999)\n" +
            "insurance_num`(-999999999,0,999999999)\n" +
            "mile_contract`(-999999999,999999999)\n" +
            "mile_power_plus`(-999999999,0,999999999)\n" +
            "miles_month`(-999999999,578073.2215,966083.9161,1396049.505,2050191.781,999999999)\n" +
            "only_amount`(-999999999,0,200,999999999)\n" +
            "only_amount_num`(-999999999,0,2,999999999)\n" +
            "over_mile_rate`(-999999999,0,999999999)\n" +
            "sum_miles`(-999999999,1845,4341,7675,12932,999999999)\n" +
            "untreat_fine_amount`(-999999999,0,100,300,999999999)\n" +
            "untreat_fine_score`(-999999999,0,6,999999999)\n" +
            "untreat_illegal`(-999999999,0,1,2,999999999)\n" +
            "untreat_only_amount`(-999999999,0,100,999999999)\n" +
            "untreat_only_amount_num`(-999999999,0,1,999999999)";
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
        ActionScoreUdf scoreUdf = new ActionScoreUdf();
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
//        for(int i=0; i<paras.length; i++)
//        {
            String result = scoreUdf.evaluate("fine_amount,"+"60");
//        }
        System.out.println(result);
    }
}
