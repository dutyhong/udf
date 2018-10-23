
import com.aliyun.odps.udf.UDF;

import java.util.*;

public class ScoreUdf extends UDF {
    private String resource =
            "half_month_details_cnt`0;(-inf,0];0.1071744469756492#1;(0,4];2.6780617191613243#2;(4,+inf);5.202402576991547\n" +
            "half_month_driver_service_cnt`0;(-inf,0];-0.26963096162523953#1;(0,2];-0.07893764639401094#2;(2,4];0.8780373399970971#3;(4,+inf);7.601337321091787\n" +
            "half_month_my_cnt`0;(-inf,0];0.81033916326877#1;(0,2];2.5641361255433353#2;(2,5];2.609706362990531#3;(5,10];2.8774315079928057#4;(10,+inf);5.284648270454084\n" +
            "half_month_order_cnt`0;(-inf,0];0.7694596481864568#1;(0,1];1.0598927670042755#2;(1,+inf);1.9151379220661755\n" +
            "half_month_push_cnt`0;(-inf,+inf);0.9179112977633934\n" +
            "half_month_search_cnt`0;(-inf,0];0.8529371691793635#1;(0,+inf);1.733692903130714\n" +
            "half_month_share_cnt`0;(-inf,0];0.9107530797618655#1;(0,+inf);1.8731885189437314\n" +
            "half_month_story_cnt`0;(-inf,+inf);0.9179112977633939\n" +
            "half_month_tab_cnt`0;(-inf,0];-3.9820509530097117#1;(0,2];-0.5868338324478295#2;(2,6];0.28906391720922636#3;(6,14];0.7817555887454017#4;(14,+inf);8.851924948548785\n" +
            "month_details_cnt`0;(-inf,0];-0.8570204920737583#1;(0,2];-0.580210258313163#2;(2,11];0.07486190499027429#3;(11,+inf);10.338362776106754\n" +
            "month_driver_service_cnt`0;(-inf,0];-2.1894713197669913#1;(0,2];-1.77625185805444#2;(2,4];-1.212723232777127#3;(4,9];-0.6941358519311518#4;(9,+inf);12.714489463564233\n" +
            "month_my_cnt`0;(-inf,2];-0.34850889540393876#1;(2,6];0.4604987508831812#2;(6,13];2.632491481714129#3;(13,25];3.4299706370400522#4;(25,+inf);7.034756395377366\n" +
            "month_order_cnt`0;(-inf,0];0.10903958307567102#1;(0,1];0.4726135941852451#2;(1,2];0.5330470613204379#3;(2,+inf);2.268011659655634\n" +
            "month_push_cnt`0;(-inf,+inf);0.9179112977633934\n" +
            "month_search_cnt`0;(-inf,0];0.9470920327201049#1;(0,+inf);1.0944959678712765\n" +
            "month_share_cnt`0;(-inf,0];0.9066756510206544#1;(0,+inf);2.2228603960390094\n" +
            "month_story_cnt`0;(-inf,0];0.9175054062354668#1;(0,+inf);13.660266979721788\n" +
            "month_tab_cnt`0;(-inf,3];-4.154244962182229#1;(3,8];-0.9693173720641814#2;(8,16];0.6656601993400424#3;(16,33];0.9530351039492864#4;(33,+inf);8.802581336839838\n" +
            "week_details_cnt`0;(-inf,0];0.5225215690984806#1;(0,+inf);2.55495101430038\n" +
            "week_driver_service_cnt`0;(-inf,0];0.4824441612845228#1;(0,2];0.7938678453600689#2;(2,+inf);3.068991418939085\n" +
            "week_my_cnt`0;(-inf,0];-0.43780326629067656#1;(0,1];-0.05096506470494444#2;(1,4];0.5424464399724247#3;(4,+inf);2.419722837180567\n" +
            "week_order_cnt`0;(-inf,0];0.9122944540577824#1;(0,+inf);0.9657221164470566\n" +
            "week_push_cnt`0;(-inf,+inf);0.9179112977633945\n" +
            "week_search_cnt`0;(-inf,0];0.8674556547091017#1;(0,+inf);2.257565076269396\n" +
            "week_share_cnt`0;(-inf,0];0.9017162316784348#1;(0,+inf);3.4566710166242984\n" +
            "week_story_cnt`0;(-inf,+inf);0.9179112977633932\n" +
            "week_tab_cnt`0;(-inf,0];1.0781115581921823#1;(0,2];1.6527313765994232#2;(2,6];2.888824067354604#3;(6,+inf);3.184073881831471";

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
