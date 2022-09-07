package a_basic;

public class FunctionTest {

	public static void main(String[] args) {

		MyInterface myInterface = new MyInterface() {			
			@Override
			public void method() {
				System.out.println("재정의로 만든 익명 구현 객체");
			}
		};
		
		myInterface.method();
		
		MyInterface ramda = () -> System.out.println("람다식으로 만든 익명 구현 객체");
		ramda.method();
		
		myInterface = () -> { 
			System.out.println("실행문이 여러개");
			System.out.println("일때 중괄호를");
			System.out.println("생략할 수 없음");
		};
		myInterface.method();
		
		
		Thread t = new Thread(() -> System.out.println("ㅋㅋ"));
		
		t.start();
		
	}
}