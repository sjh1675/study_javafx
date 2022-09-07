package b_args;

public class ArgumentTest {

	public static void main(String[] args) {
		
		MyInterface i = new MyInterface() {
			@Override
			public void method(int x, int y) {
				
			}
		};
		
		i.method(10, 20);
		
		i = (int a, int b) -> System.out.println(a+b);
		
		i.method(10, 20);
		
		SecondInterface si = s -> System.out.println(s);
		
		si.method("매개변수건 실행문이건 딱 한개면 괄호 중괄호 생략 할 수 있음");
	}
}