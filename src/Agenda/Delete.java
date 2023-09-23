package Agenda;

import java.util.List;

/**
 * 用于实现删除某一特定ID的会议程,命令格式：delete [userName] [password] [meetingId]
 */
public class Delete implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 4;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* 获取用户的索引 */
		int index;
		int ID = Integer.parseInt(command[3]);
		for (index = 0; index < users.size(); index++) {
			if (users.get(index).getUserName().equals(command[1])) {
				break;
			}
		}
		if (index == users.size()) {
			return 1;
//			System.out.println("该用户不存在");
		} else {
			if (users.get(index).checkUser(command[1], command[2])) { // 密码正确
				if(delete2Agenda(users, index, command[1], ID))return 0;
				else return 7;
			} else {
				return 3;
//				System.out.println("密码错误，请输入正确的密码");
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
				/* 寻找另一个用户的索引值 */
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
//			System.out.println("成功删除会议(该会议ID为" + ID + ")");
		} else {
			return false;
//			System.out.println("删除失败");
		}
		//System.out.println(" ");
	}

}
