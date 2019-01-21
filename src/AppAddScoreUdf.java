import com.aliyun.odps.udf.UDF;

import java.util.*;

public class AppAddScoreUdf extends UDF {
    private String resource = "insurance_cnt`0;(-inf,0];0.24311886846416086#1;(0,+inf);1.066310747435507\n" +
            "mileage_cnt`0;(-inf,0];0.26398155730714273#1;(0,+inf);11.910435911540374\n" +
            "month_article_praise_article`0;(-inf,0];0.2883438106639517#1;(0,+inf);20.857616398586135\n" +
            "month_article_praise_review`0;(-inf,0];0.24509952483884617#1;(0,+inf);20.775615821827127\n" +
            "month_article_review_send`0;(-inf,0];0.2680802040345832#1;(0,+inf);20.822610715362888\n" +
            "month_car_detail`0;(-inf,0];-0.044012729543032356#1;(0,+inf);2.1366724235657673\n" +
            "month_car_detail_erji`0;(-inf,0];-0.03098818427747376#1;(0,1];0.2349651568760764#2;(1,+inf);1.9174230719442151\n" +
            "month_carcare_page`0;(-inf,0];-0.08591670877188899#1;(0,1];0.5191089451622194#2;(1,+inf);2.611204767753861\n" +
            "month_detail_order`0;(-inf,0];-0.39338769234275367#1;(0,+inf);19.48614621792543\n" +
            "month_document_page`0;(-inf,0];0.15753888975136687#1;(0,1];0.12389620152698166#2;(1,+inf);1.5079257786316784\n" +
            "month_insurance_page`0;(-inf,0];0.148471960759965#1;(0,1];0.15112891404664777#2;(1,+inf);2.2231046968119523\n" +
            "month_member`0;(-inf,0];0.13188076909260407#1;(0,1];0.21193149222528868#2;(1,2];0.4117169471733474#3;(2,+inf);1.1180521512576556\n" +
            "month_mileage_page`0;(-inf,0];0.10606372338567427#1;(0,1];0.15361575686858553#2;(1,+inf);1.9919366123733235\n" +
            "month_my_boon`0;(-inf,0];0.07836206667622955#1;(0,1];0.0976170844329478#2;(1,+inf);1.9662851918793465\n" +
            "month_newcarservice_carlife`0;(-inf,1];-0.20075896883062488#1;(1,4];-0.4119830985364263#2;(4,8];-0.12175155493150949#3;(8,17];0.5031090730155433#4;(17,+inf);2.0474971063529837\n" +
            "month_newcarservice_carlife_article`0;(-inf,0];-0.9035992428960725#1;(0,+inf);20.259409526124223\n" +
            "month_newcarservice_carlife_banner`0;(-inf,0];0.26836352290565635#1;(0,+inf);1.0015728621288746\n" +
            "month_tab_car`0;(-inf,0];-0.09991203276970977#1;(0,2];0.19208043013367213#2;(2,+inf);2.539460694525266\n" +
            "month_violation_page`0;(-inf,0];0.10309230655432554#1;(0,1];-0.019296284358550842#2;(1,4];-0.13420334692245592#3;(4,+inf);1.5737243957775608\n" +
            "peccancy_cnt`0;(-inf,0];-0.49076662813386396#1;(0,+inf);23.56124834561741\n" +
            "purchase_mileage`0;(-inf,0];0.2639815573078836#1;(0,+inf);11.910435911403685\n" +
            "task_cnt`0;(-inf,0];0.16512589722999657#1;(0,3];0.20353207003636734#2;(3,+inf);1.0149477100645938";
    private String intervals = "insurance_cnt`(-999999999,0,999999999)\n" +
            "mileage_cnt`(-999999999,0,999999999)\n" +
            "month_article_praise_article`(-999999999,0,999999999)\n" +
            "month_article_praise_review`(-999999999,0,999999999)\n" +
            "month_article_review_send`(-999999999,0,999999999)\n" +
            "month_car_detail`(-999999999,0,999999999)\n" +
            "month_car_detail_erji`(-999999999,0,1,999999999)\n" +
            "month_carcare_page`(-999999999,0,1,999999999)\n" +
            "month_detail_order`(-999999999,0,999999999)\n" +
            "month_document_page`(-999999999,0,1,999999999)\n" +
            "month_insurance_page`(-999999999,0,1,999999999)\n" +
            "month_member`(-999999999,0,1,2,999999999)\n" +
            "month_mileage_page`(-999999999,0,1,999999999)\n" +
            "month_my_boon`(-999999999,0,1,999999999)\n" +
            "month_newcarservice_carlife`(-999999999,1,4,8,17,999999999)\n" +
            "month_newcarservice_carlife_article`(-999999999,0,999999999)\n" +
            "month_newcarservice_carlife_banner`(-999999999,0,999999999)\n" +
            "month_tab_car`(-999999999,0,2,999999999)\n" +
            "month_violation_page`(-999999999,0,1,4,999999999)\n" +
            "peccancy_cnt`(-999999999,0,999999999)\n" +
            "purchase_mileage`(-999999999,0,999999999)\n" +
            "task_cnt`(-999999999,0,3,999999999)\n";
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
        AppAddScoreUdf scoreUdf = new AppAddScoreUdf();
        String result = scoreUdf.evaluate("month_article_praise_article"+ ",3");
        System.out.println(result);
    }
}
