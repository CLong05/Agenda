package Agenda;

import java.util.List;

/**
 * 用于实现用户注册命令,命令格式：register [userName] [password]
 */
public class Register implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 3;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* 检查是否已经存在该用户名 */
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserName().equals(command[1])) {
//				System.out.println("注册失败，该用户名已存在！");
				return 5;
			}
		}
		User newUser = new User(command[1], command[2]);
		users.add(newUser);
		return 0;
//		System.out.println("注册成功！欢迎新用户" + command[1] + "！");

	}
}
