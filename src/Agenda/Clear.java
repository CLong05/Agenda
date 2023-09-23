package Agenda;

import java.util.List;



/**
 * 用于实现清除某一用户创建的所有会议,指令格式：delete [userName] [password] [meetingId]
 */
public class Clear implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 3;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* 获取自己在用户列表中的索引 */
		int index;
		for (index = 0; index < users.size(); index++) {
			if (users.get(index).getUserName().equals(command[1])) {
				break;
			}
		}
		if (index == users.size()) {
			return 1;
			//System.out.println("该用户不存在");
		} else {
			if (users.get(index).checkUser(command[1], command[2])) { // 密码正确
				// int len = users.get(index).getAgendas().size();
				for (int j = 0; j < users.get(index).getAgendas().size(); j++) {
					/* 寻找该用户创建的会议 */
					if (users.get(index).getAgendas().get(j).getUser1().equals(command[1])) {
						int ID = users.get(index).getAgendas().get(j).getID();
						Delete.delete2Agenda(users, index, command[1], ID);
						j -= 1; // 删除了一个会议，导致列表长度减1，需要将j-1,从而访问该位置上替补上来的新会议
					}
				}
				return 0;
//				System.out.println("会议清除成功");
			} else {
				return 3;
//				System.out.println("密码错误");
			}
		}
	}
}