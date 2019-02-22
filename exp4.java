package bag;


interface BTtools{
	static String  name = "里面的人";
	static String gender = "男";
	static int age = 70;
	
	public void up();
	public void down();
	public void right();
	public void left();
	
}
class BT implements BTtools{

	public void up() {
		// TODO Auto-generated method stub
		print(BTtools.name+"正在向上走");
	}

	private void print(String str) {
		// TODO Auto-generated method stub
		System.out.print(str);
	}

	
	public void down() {
		// TODO Auto-generated method stub
		print(BTtools.name+"正在向下走");
	}

	
	public void right() {
		// TODO Auto-generated method stub
		print(BTtools.name+"正在向右走");
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		print(BTtools.name+"正在向左走");
	}
	
}

public class exp4 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(BT.age+"岁");
		
		BT bt = new BT();
		bt.up();
	}

}