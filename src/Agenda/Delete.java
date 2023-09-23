package Agenda;

import java.util.List;

/**
 * ����ʵ��ɾ��ĳһ�ض�ID�Ļ����,�����ʽ��delete [userName] [password] [meetingId]
 */
public class Delete implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 4;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* ��ȡ�û������� */
		int index;
		int ID = Integer.parseInt(command[3]);
		for (index = 0; index < users.size(); index++) {
			if (users.get(index).getUserName().equals(command[1])) {
				break;
			}
		}
		if (index == users.size()) {
			return 1;
//			System.out.println("���û�������");
		} else {
			if (users.get(index).checkUser(command[1], command[2])) { // ������ȷ
				if(delete2Agenda(users, index, command[1], ID))return 0;
				else return 7;
			} else {
				return 3;
//				System.out.println("���������������ȷ������");
			}
		}
	}

	static boolean delete2Agenda(List<User> users, int index, String username, int ID) {
		int index2 = -1;
		for (int i = 0; i < users.get(index).getAgendas().size(); i++) {
			if (users.get(index).getAgendas().get(i).getID() == ID) {
				String user1 = users.get(index).getAgendas().get(i).getUser1();
				String user2 = users.get(index).getAgendas().get(i).getUser2();
				String userother;
				if (user1.equals(username))
					userother = user2;
				else
					userother = user1;
				/* Ѱ����һ���û�������ֵ */
				for (int j = 0; j < users.size(); j++) {
					if (users.get(j).getUserName().equals(userother)) {
						index2 = j;
						break;
					}
				}
				break;
			}
		}
		if (users.get(index).deletaAgenda(ID) && users.get(index2).deletaAgenda(ID)) {
			return true;
//			System.out.println("�ɹ�ɾ������(�û���IDΪ" + ID + ")");
		} else {
			return false;
//			System.out.println("ɾ��ʧ��");
		}
		//System.out.println(" ");
	}

}
