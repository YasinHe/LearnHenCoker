package hyx.hencoderlearn.ui.one;

/**
 * Created by HeYingXin on 2018/7/18.
 */
public class TestMemory {
    public static void main(String[] args) {
        TestMemory testMemory=new TestMemory();
        DataObject dataObject=new DataObject();
        dataObject.setNumber1(9527);
        dataObject.setNumber2(1314);
        System.out.println("main方法中，数据交换前：number1="+dataObject.getNumber1()+" , number2="+dataObject.getNumber2());
        testMemory.swapData(dataObject);
        System.out.println("main方法中，数据交换后：number1="+dataObject.getNumber1()+" , number2="+dataObject.getNumber2());


        compare("aabbccdd1!","1ddaaccbb!");


        String str = " abcaabcd";
        str = removeDaplicate(str);
        System.out.println(str);
    }


    private void swapData(DataObject dataObject) {
        dataObject = null;
        try {
            System.out.println("swapData方法中,数据交换前：number1="+dataObject.getNumber1()+" , number2="+dataObject.getNumber2());
            int temp=dataObject.getNumber1();
            dataObject.setNumber1(dataObject.getNumber2());
            dataObject.setNumber2(temp);
            System.out.println("swapData方法中,数据交换后：number1=" + dataObject.getNumber1() + " , number2=" + dataObject.getNumber2());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void compare(String s1, String s2) {
        byte[] b1 = s1.getBytes();
        byte[] b2 = s2.getBytes();
        int[] bCount = new int[256];
        for (int i = 0; i < 256; i++) {
            bCount[i] = 0;
        }
        for (int i = 0; i < b1.length; i++) {
            bCount[b1[i]]++;
        }
        for (int i = 0; i < b2.length; i++) {
            bCount[b2[i]]--;
        }

        for (int i = 0; i < 256; i++) {
            if (bCount[i] != 0) {
                System.out.println("not equal");
                return;
            }
        }
        System.out.println("equal");
    }

    public static String removeDaplicate(String str){
        char[] c = str.toCharArray();
        int len = c.length;
        int[] flags = new int[8];   //只需要8个32bit的int
        int i;
        for (i = 0; i < 8; i++) {
            flags[i] = 0;
        }
        for(i = 0; i < len; i++){
            int index = (int)c[i]/32;
            int shift = (int)c[i]%32;
            if((flags[index] & 1 << shift) != 0){
                c[i] = '\0';
            }
            flags[index] |= (1<<shift);
        }
        int l = 0;
        for(i = 0; i < len; i++){
            if(c[i] != '\0'){
                c[l++] = c[i];
            }
        }
        return new String(c,0,l);
    }
}

class DataObject {

    private int number1;
    private int number2;

    public int getNumber1() {
        return number1;
    }
    public void setNumber1(int number1) {
        this.number1 = number1;
    }
    public int getNumber2() {
        return number2;
    }
    public void setNumber2(int number2) {
        this.number2 = number2;
    }

}

