package Agenda;

import java.util.List;

/**
 * ����ʵ���û�ע������,�����ʽ��register [userName] [password]
 */
public class Register implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 3;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* ����Ƿ��Ѿ����ڸ��û��� */
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserName().equals(command[1])) {
//				System.out.println("ע��ʧ�ܣ����û����Ѵ��ڣ�");
				return 5;
			}
		}
		User newUser = new User(command[1], command[2]);
		users.add(newUser);
		return 0;
//		System.out.println("ע��ɹ�����ӭ���û�" + command[1] + "��");

	}
}
