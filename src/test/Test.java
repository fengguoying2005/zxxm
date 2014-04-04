package test;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;


public class Test {

	public static void main(String[] args) {
		Random rdm = new Random();
		for(int i = 0 ; i < 10; i ++){
			int n = rdm.nextInt(10);
			System.out.println(n);
		}
		
	}
	public static void paixu(ClassA[] data, final boolean pxfs) {
		Comparator comparator = new Comparator(){
			public int compare(Object v1, Object v2) {
				ClassA vv1 = (ClassA)v1;
				ClassA vv2 = (ClassA)v2;
				if(pxfs) {
					return vv1.getValue()-vv2.getValue();
				} else {
					return vv2.getValue()-vv1.getValue();
				}
			}};
		Arrays.sort(data, comparator );
	}
}
class ClassA {
	int value;
	public ClassA(int v) {
		this.value = v;
	}
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}