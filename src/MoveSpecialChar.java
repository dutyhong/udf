import com.aliyun.odps.udf.UDF;

public class MoveSpecialChar  extends UDF {
    public String evaluate(String s)
    {
        if(null==s)
            return null;
        String str = s.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]","");
        return str;
    }
    public static void main(String[] args)
    {
        MoveSpecialChar moveSpecialChar = new MoveSpecialChar();
        String result = moveSpecialChar.evaluate("hello\uD83D\uDE0A\uD83C\uDF39\uD83D\uDC66\uD83D\uDC74你好 ");
        System.out.println(result);
    }
}
