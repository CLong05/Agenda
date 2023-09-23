package Agenda;

import java.util.Calendar;
import java.util.List;

/**
 * ����ʵ�ֲ�ѯĳһʱ���ڵĻ���,�����ʽ��query [userName] [password] [start] [end]
 */
public class Query implements Command {
	public boolean check(String[] command, List<User> users) {
		return command.length == 5;
	}

	public int exec(String[] command, List<User> users, int count) {
		/* ��ȡ��ѯ�Ŀ�ʼ�����ʱ�� */
		String[] startTime = command[3].split("\\.|:");
		String[] endTime = command[4].split("\\.|:");
		Calendar dateBegin = Calendar.getInstance();
		Calendar dateEnd = Calendar.getInstance();
		dateBegin.set(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]), Integer.parseInt(startTime[2]),
				Integer.parseInt(startTime[3]), Integer.parseInt(startTime[4]));
		dateEnd.set(Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]), Integer.parseInt(endTime[2]),
				Integer.parseInt(endTime[3]), Integer.parseInt(endTime[4]));
		
		/* �������ʱ��ĺϷ��ԣ���ʼʱ�����ڽ���ʱ�� */
		if (dateBegin.after(dateEnd)) {
//			System.out.println("��ѯʱ�εĿ�ʼʱ��������ڽ���ʱ�䣬��ѯʧ��!");
			return 4;
		}
		
		/* ��ȡ�û���� */
		int index;
		for (index = 0; index < users.size(); index++) {
			if (users.get(index).getUserName().equals(command[1])) {
				break;
			}
		}
		if (index == users.size()) {
			return 1;
//			System.out.println("���û������ڣ�����������");
		} else {
			if (users.get(index).checkUser(command[1], command[2])) {
//				List<Agenda> res;
//				if (users.get(index).getAgendas().size() == 0) {					
////					System.out.println("���û����޻��鰲��");
//					
//				} else {
//					for (int j = 0; j < users.get(index).getAgendas().size(); j++) {
//						if (dateBegin.before(users.get(index).getAgendas().get(j).getStartTime())
//								&& dateEnd.after(users.get(index).getAgendas().get(j).getStartTime())
//								|| dateBegin.before(users.get(index).getAgendas().get(j).getEndTime())
//										&& dateEnd.after(users.get(index).getAgendas().get(j).getEndTime())) {
//							/* ������ָ��ʱ���� */
//							users.get(index).getAgendas().get(j).printAgenda();
//						}
//					}
//				}
				return 0;
			} else {
				return 3;
//				System.out.println("���������������ȷ������");
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
//			System.out.println("���û����޻��鰲��");
			
		} else {
			for (int j = 0; j < users.get(index).getAgendas().size(); j++) {
				if (dateBegin.before(users.get(index).getAgendas().get(j).getStartTime())
						&& dateEnd.after(users.get(index).getAgendas().get(j).getStartTime())
						|| dateBegin.before(users.get(index).getAgendas().get(j).getEndTime())
								&& dateEnd.after(users.get(index).getAgendas().get(j).getEndTime())) {
					/* ������ָ��ʱ���� */
					res.add(users.get(index).getAgendas().get(j));
				}
			}
		}
	}
}
