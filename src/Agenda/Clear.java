package Agenda;

import java.util.List;



/**
 * ����ʵ�����ĳһ�û����������л���,ָ���ʽ��delete [userName] [password] [meetingId]
 */
public class Clear implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 3;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* ��ȡ�Լ����û��б��е����� */
		int index;
		for (index = 0; index < users.size(); index++) {
			if (users.get(index).getUserName().equals(command[1])) {
				break;
			}
		}
		if (index == users.size()) {
			return 1;
			//System.out.println("���û�������");
		} else {
			if (users.get(index).checkUser(command[1], command[2])) { // ������ȷ
				// int len = users.get(index).getAgendas().size();
				for (int j = 0; j < users.get(index).getAgendas().size(); j++) {
					/* Ѱ�Ҹ��û������Ļ��� */
					if (users.get(index).getAgendas().get(j).getUser1().equals(command[1])) {
						int ID = users.get(index).getAgendas().get(j).getID();
						Delete.delete2Agenda(users, index, command[1], ID);
						j -= 1; // ɾ����һ�����飬�����б��ȼ�1����Ҫ��j-1,�Ӷ����ʸ�λ�����油�������»���
					}
				}
				return 0;
//				System.out.println("��������ɹ�");
			} else {
				return 3;
//				System.out.println("�������");
			}
		}
	}
}