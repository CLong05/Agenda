package Agenda;

import java.util.List;


/**
 * 用于实现添加会议,命令格式：add [userName] [password] [other] [start] [end] [title]
 */
public class Add implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 7;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* 验证会议发起者与受邀者的合法性，若合法则返回对应的索引值 */
		int indexOfUser1 = -1, indexOfUser2 = -1;
		for (int i = 0; (indexOfUser1 == -1 || indexOfUser2 == -1) && i < users.size(); i++) {
			if (indexOfUser1 == -1 && users.get(i).getUserName().equals(command[1])) {
				indexOfUser1 = i;
			}
			if (indexOfUser2 == -1 && users.get(i).getUserName().equals(command[3])) {
				indexOfUser2 = i;
			}
		}

		/* 检查用户是否存在 */
		if (indexOfUser1 == -1) {
			return 1;
			//System.out.println("该用户不存在，请输入正确的用户名");
		} else if (indexOfUser2 == -1) {
			return 2;
			//System.out.println("受邀用户不存在，请输入正确的用户名");
		} else {
			if (users.get(indexOfUser1).checkUser(command[1], command[2])) { // 检查密码是否正确
				return users.get(indexOfUser1).addAgenda(
						indexOfUser1, indexOfUser2, users.get(indexOfUser1).getUserName(),
						command[3], command[4].split("\\.|:"), command[5].split("\\.|:"), 
						command[6], users, count); // 将新会议添加到会议发起者和受邀者的会议列表中
					
			} else {
				return 3;
//				System.out.println("密码错误，请输入正确的密码");
			}
		}

	}
}
