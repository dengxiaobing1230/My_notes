package bag;
//�����һ��ע��

interface BTtools{
	static String  name = "�������";
	static String gender = "��";
	static int age = 70;
	
	public void up();
	public void down();
	public void right();
	public void left();
	
}
class BT implements BTtools{

	public void up() {
		// TODO Auto-generated method stub
		print(BTtools.name+"����������");
	}

	private void print(String str) {
		// TODO Auto-generated method stub
		System.out.print(str);
	}

	
	public void down() {
		// TODO Auto-generated method stub
		print(BTtools.name+"����������");
	}

	
	public void right() {
		// TODO Auto-generated method stub
		print(BTtools.name+"����������");
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		print(BTtools.name+"����������");
	}
	
}

public class exp4 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(BT.age+"��");
		
		BT bt = new BT();
		bt.up();
	}

}