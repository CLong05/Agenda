package Agenda;

import java.util.List;

/**
 * 业务逻辑层接口：根据具体命令内容实例化为不同的命令类，从而实现不同的功能
 */
public interface Command {
	/**
	 * 检查命令格式是否正确
	 *
	 * @param command 输入的命令
	 * @param users   用户列表
	 * @return 返回命令是否合法
	 */
	boolean check(String[] command, List<User> users);

	/**
	 * 执行合法命令
	 *
	 * @param command 输入的命令
	 * @param users   用户列表
	 * @param count	  目前会议的总数，用于生成ID
	 */
	int exec(String[] command, List<User> users, int count);
}
