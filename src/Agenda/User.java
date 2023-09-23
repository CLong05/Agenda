package Agenda;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据访问层结构1：User 用于存储记录用户的信息
 */
public class User {
	/**
	 * 用户名信息
	 */
	private final String userName;
	/**
	 * 用户密码信息
	 */
	private final String password;
	/**
	 * 用户参与的会议列表信息
	 */
	private final List<Agenda> agendas = new ArrayList<>();

	/**
	 * 注册新用户
	 *
	 * @param name     用户名
	 * @param password 密码
	 */
	public User(String name, String password) {
		this.userName = name;
		this.password = password;
	}

	/**
	 * 用户登录 检查用户提供的密码是否正确
	 *
	 * @param name     用户名
	 * @param password 密码
	 * @return 密码是否正确
	 */
	public boolean checkUser(String name, String password) {
		return name.equals(this.userName) && password.equals(this.password);
	}

	/**
	 * 获取用户名
	 *
	 * @return 用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 获取用户的会议列表
	 */
	public List<Agenda> getAgendas() {
		return agendas;
	}

	/**
	 * 添加新会议安排
	 *
	 * @param indexOfUser1 当前用户在用户列表中的索引值
	 * @param indexOfUser2 被邀请用户在用户列表中的索引值
	 * @param user1        当前用户用户名
	 * @param user2        被邀请用户用户名
	 * @param startTime    开始时间
	 * @param endTime      结束时间
	 * @param title        标签
	 * @param users        用户列表
	 * @param ID		   新增会议对应的ID信息
	 * @return 是否添加成功
	 */
	public int addAgenda(int indexOfUser1, int indexOfUser2, String user1, String user2, String[] startTime,
			String[] endTime, String title, List<User> users, int ID) {
		Agenda newAgenda = new Agenda(user1, user2, startTime, endTime, title, ID);
		/* 检查输入时间的合法性：开始时间早于结束时间 */
		if (newAgenda.getStartTime().after(newAgenda.getEndTime())) {
//			System.out.println("会议开始时间必须早于会议结束时间，添加失败!");
			return 8;
		}
		/* 判断想要添加的日程和已存在的日程是否有冲突 */
		if (hasConflict(indexOfUser1, users, newAgenda))
			return 9;
		if (hasConflict(indexOfUser2, users, newAgenda))
			return 9;
		getAgendas().add(newAgenda);
		users.get(indexOfUser2).getAgendas().add(newAgenda);
		return 0;
	}

	/**
	 * 删除用户的会议列表中指定ID的会议
	 * 
	 * @param ID 删除的会议ID
	 * @return 是否删除成功
	 */
	public boolean deletaAgenda(int ID) {
		for (int i = 0; i < agendas.size(); i++) {
			if (getAgendas().get(i).getID() == ID) {
				getAgendas().remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断新会议安排与现有会议安排是否冲突
	 *
	 * @param indexOfUser 被查询的用户
	 * @param users       用户列表
	 * @param newAgenda   想添加的新日程
	 * @return 是否有冲突
	 */
	public boolean hasConflict(int indexOfUser, List<User> users, Agenda newAgenda) {
		for (int j = 0; j < users.get(indexOfUser).getAgendas().size(); j++) {
			if (newAgenda.getStartTime().after(users.get(indexOfUser).getAgendas().get(j).getEndTime())
					|| newAgenda.getEndTime().before(users.get(indexOfUser).getAgendas().get(j).getStartTime())) {
				/* 与其他会议不冲突的条件：开始时间晚于其他会议的结束时间或者是解释时间早于其他会议的开始时间 */
				continue;
			} else {
				//System.out.println("与其它会议时间冲突，添加失败!");
				return true;
			}
		}
		return false;
	}

}
