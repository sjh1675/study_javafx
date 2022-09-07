package c_return;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@FunctionalInterface
interface MyInterface {
	int method(int a, int b, String s);
}

public class RamdaExam {

	public static void main(String[] args) {

		MyInterface i = (z, x, c) -> {
			System.out.println(c);
			return z * x;
		};

		int q = 10;
		int g = 20;
		System.out.println(i.method(q, g, q + "*" + g + " 두 수를 곱합니다"));

		Set<Integer> set = new TreeSet<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});
		
		set = new TreeSet<> ((o1, o2) -> {return o1-o2;}); // 오름차순
		
		set.add(10);
		set.add(5);
		set.add(7);
		System.out.println(set);
	}
}