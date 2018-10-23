
import com.aliyun.odps.udf.UDF;

import java.util.*;

public class ActionScoreUdf extends UDF {
    private String resource = "fine_amount`0;(-inf,0];6.183702038620795#1;(0,50];4.030591459250834#2;(50,200];3.792910421268436#3;(200,550];2.0033120176362615#4;(550,+inf);-2.769289513470661\n" +
            "fine_score`0;(-inf,0];7.833488066969204#1;(0,3];6.641049078979996#2;(3,9];4.610812816678453#3;(9,+inf);-9.486807606618722\n" +
            "handle_fine_amount`0;(-inf,0];4.974324992416239#1;(0,200];3.08685792608543#2;(200,+inf);1.0224294882029832\n" +
            "handle_fine_amount_rate`0;(-inf,0];-0.5832169251133655#1;(0,0.65];0.24866670782502798#2;(0.65,+inf);4.936515694542384\n" +
            "handle_fine_score`0;(-inf,0];4.054026925275423#1;(0,3];3.9733397046422603#2;(3,+inf);-2.2975189654227646\n" +
            "handle_fine_score_rate`0;(-inf,0];2.667420800234139#1;(0,+inf);3.869807227674506\n" +
            "handle_illegal`0;(-inf,0];3.468831131571816#1;(0,1];2.9255792674889483#2;(1,+inf);1.2993603881162155\n" +
            "handle_illegal_num_rate`0;(-inf,0];6.424859300166508#1;(0,0.625];9.784912316867086#2;(0.625,+inf);5.589499443664798\n" +
            "illegal_num`0;(-inf,0];10.056504833981048#1;(0,1];7.162388665607139#2;(1,2];3.9956383654298935#3;(2,4];3.541248145757662#4;(4,+inf);-7.774813462317611\n" +
            "insurance_num`0;(-inf,0];3.4923138144083445#1;(0,+inf);2.9051018382165372\n" +
            "mile_contract`0;(-inf,+inf);2.9819986446226103\n" +
            "mile_power_plus`0;(-inf,0];2.9707268891844727#1;(0,+inf);5.054633397469397\n" +
            "miles_month`0;(-inf,578073.2215];6.441407780008896#1;(578073.2215,966083.9161];4.482525685835006#2;(966083.9161,1396049.505];2.716681676988928#3;(1396049.505,2050191.781];1.5844757856863985#4;(2050191.781,+inf);-0.3151061631616693\n" +
            "only_amount`0;(-inf,0];3.273128353482095#1;(0,200];1.3740917037240186#2;(200,+inf);4.320558991659551\n" +
            "only_amount_num`0;(-inf,0];1.660381748694552#1;(0,2];-3.099839680221111#2;(2,+inf);-4.407774940449581\n" +
            "over_mile_rate`0;(-inf,0];3.3844502064434803#1;(0,+inf);-2.4452648086284414\n" +
            "sum_miles`0;(-inf,1845];-3.550132524583278#1;(1845,4341];3.0770513349086714#2;(4341,7675];5.156127961360199#3;(7675,12932];5.36526806601605#4;(12932,+inf);4.8625169834565165\n" +
            "untreat_fine_amount`0;(-inf,0];1.3337354250529323#1;(0,100];3.832143807394957#2;(100,300];5.166721707166567#3;(300,+inf);5.212500211214154\n" +
            "untreat_fine_score`0;(-inf,0];4.484286163947616#1;(0,6];1.9033286081276897#2;(6,+inf);-2.1115479390749163\n" +
            "untreat_illegal`0;(-inf,0];7.617589778942901#1;(0,1];2.8415310233889266#2;(1,2];-2.9318423974911463#3;(2,+inf);-6.643067730905409\n" +
            "untreat_only_amount`0;(-inf,0];3.021703455781414#1;(0,100];1.6741743411813643#2;(100,+inf);1.0665691032350453\n" +
            "untreat_only_amount_num`0;(-inf,0];3.090425018893251#1;(0,1];2.8771393631597846#2;(1,+inf);1.901455222627352";

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
