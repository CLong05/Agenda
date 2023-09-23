package Agenda;

import java.util.Calendar;
import java.util.List;

/**
 * 用于实现查询某一时段内的会议,命令格式：query [userName] [password] [start] [end]
 */
public class Query implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 5;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* 获取查询的开始与结束时间 */
		String[] startTime = command[3].split("\\.|:");
		String[] endTime = command[4].split("\\.|:");
		Calendar dateBegin = Calendar.getInstance();
		Calendar dateEnd = Calendar.getInstance();
		dateBegin.set(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]), Integer.parseInt(startTime[2]),
				Integer.parseInt(startTime[3]), Integer.parseInt(startTime[4]));
		dateEnd.set(Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]), Integer.parseInt(endTime[2]),
				Integer.parseInt(endTime[3]), Integer.parseInt(endTime[4]));
		
		/* 检查输入时间的合法性：开始时间早于结束时间 */
		if (dateBegin.after(dateEnd)) {
//			System.out.println("查询时段的开始时间必须早于结束时间，查询失败!");
			return 4;
		}
		
		/* 获取用户编号 */
		int index;
		for (index = 0; index < users.size(); index++) {
			if (users.get(index).getUserName().equals(command[1])) {
				break;
			}
		}
		if (index == users.size()) {
			return 1;
//			System.out.println("该用户不存在，请重新输入");
		} else {
			if (users.get(index).checkUser(command[1], command[2])) {
//				List<Agenda> res;
//				if (users.get(index).getAgendas().size() == 0) {					
////					System.out.println("该用户暂无会议安排");
//					
//				} else {
//					for (int j = 0; j < users.get(index).getAgendas().size(); j++) {
//						if (dateBegin.before(users.get(index).getAgendas().get(j).getStartTime())
//								&& dateEnd.after(users.get(index).getAgendas().get(j).getStartTime())
//								|| dateBegin.before(users.get(index).getAgendas().get(j).getEndTime())
//										&& dateEnd.after(users.get(index).getAgendas().get(j).getEndTime())) {
//							/* 会议在指定时段内 */
//							users.get(index).getAgendas().get(j).printAgenda();
//						}
//					}
//				}
				return 0;
			} else {
				return 3;
//				System.out.println("密码错误，请输入正确的密码");
			}
		}
	}
	public void getResult(String[] command, List<User> users,List<Agenda> res){
		String[] startTime = command[3].split("\\.|:");
		String[] endTime = command[4].split("\\.|:");
		Calendar dateBegin = Calendar.getInstance();
		Calendar dateEnd = Calendar.getInstance();
		dateBegin.set(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]), Integer.parseInt(startTime[2]),
				Integer.parseInt(startTime[3]), Integer.parseInt(startTime[4]));
		dateEnd.set(Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]), Integer.parseInt(endTime[2]),
				Integer.parseInt(endTime[3]), Integer.parseInt(endTime[4]));
		int index;
		for (index = 0; index < users.size(); index++) {
			if (users.get(index).getUserName().equals(command[1])) {
				break;
			}
		}
		if (users.get(index).getAgendas().size() == 0) {
			return;
//			System.out.println("该用户暂无会议安排");
			
		} else {
			for (int j = 0; j < users.get(index).getAgendas().size(); j++) {
				if (dateBegin.before(users.get(index).getAgendas().get(j).getStartTime())
						&& dateEnd.after(users.get(index).getAgendas().get(j).getStartTime())
						|| dateBegin.before(users.get(index).getAgendas().get(j).getEndTime())
								&& dateEnd.after(users.get(index).getAgendas().get(j).getEndTime())) {
					/* 会议在指定时段内 */
					res.add(users.get(index).getAgendas().get(j));
				}
			}
		}
	}
}
