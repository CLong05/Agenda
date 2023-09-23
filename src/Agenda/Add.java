package Agenda;

import java.util.List;


/**
 * ����ʵ����ӻ���,�����ʽ��add [userName] [password] [other] [start] [end] [title]
 */
public class Add implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 7;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* ��֤���鷢�����������ߵĺϷ��ԣ����Ϸ��򷵻ض�Ӧ������ֵ */
		int indexOfUser1 = -1, indexOfUser2 = -1;
		for (int i = 0; (indexOfUser1 == -1 || indexOfUser2 == -1) && i < users.size(); i++) {
			if (indexOfUser1 == -1 && users.get(i).getUserName().equals(command[1])) {
				indexOfUser1 = i;
			}
			if (indexOfUser2 == -1 && users.get(i).getUserName().equals(command[3])) {
				indexOfUser2 = i;
			}
		}

		/* ����û��Ƿ���� */
		if (indexOfUser1 == -1) {
			return 1;
			//System.out.println("���û������ڣ���������ȷ���û���");
		} else if (indexOfUser2 == -1) {
			return 2;
			//System.out.println("�����û������ڣ���������ȷ���û���");
		} else {
			if (users.get(indexOfUser1).checkUser(command[1], command[2])) { // ��������Ƿ���ȷ
				return users.get(indexOfUser1).addAgenda(
						indexOfUser1, indexOfUser2, users.get(indexOfUser1).getUserName(),
						command[3], command[4].split("\\.|:"), command[5].split("\\.|:"), 
						command[6], users, count); // ���»�����ӵ����鷢���ߺ������ߵĻ����б���
					
			} else {
				return 3;
//				System.out.println("���������������ȷ������");
			}
		}

	}
}
