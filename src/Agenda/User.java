package Agenda;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ݷ��ʲ�ṹ1��User ���ڴ洢��¼�û�����Ϣ
 */
public class User {
	/**
	 * �û�����Ϣ
	 */
	private final String userName;
	/**
	 * �û�������Ϣ
	 */
	private final String password;
	/**
	 * �û�����Ļ����б���Ϣ
	 */
	private final List<Agenda> agendas = new ArrayList<>();

	/**
	 * ע�����û�
	 *
	 * @param name     �û���
	 * @param password ����
	 */
	public User(String name, String password) {
		this.userName = name;
		this.password = password;
	}

	/**
	 * �û���¼ ����û��ṩ�������Ƿ���ȷ
	 *
	 * @param name     �û���
	 * @param password ����
	 * @return �����Ƿ���ȷ
	 */
	public boolean checkUser(String name, String password) {
		return name.equals(this.userName) && password.equals(this.password);
	}

	/**
	 * ��ȡ�û���
	 *
	 * @return �û���
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * ��ȡ�û��Ļ����б�
	 */
	public List<Agenda> getAgendas() {
		return agendas;
	}

	/**
	 * ����»��鰲��
	 *
	 * @param indexOfUser1 ��ǰ�û����û��б��е�����ֵ
	 * @param indexOfUser2 �������û����û��б��е�����ֵ
	 * @param user1        ��ǰ�û��û���
	 * @param user2        �������û��û���
	 * @param startTime    ��ʼʱ��
	 * @param endTime      ����ʱ��
	 * @param title        ��ǩ
	 * @param users        �û��б�
	 * @param ID		   ���������Ӧ��ID��Ϣ
	 * @return �Ƿ���ӳɹ�
	 */
	public int addAgenda(int indexOfUser1, int indexOfUser2, String user1, String user2, String[] startTime,
			String[] endTime, String title, List<User> users, int ID) {
		Agenda newAgenda = new Agenda(user1, user2, startTime, endTime, title, ID);
		/* �������ʱ��ĺϷ��ԣ���ʼʱ�����ڽ���ʱ�� */
		if (newAgenda.getStartTime().after(newAgenda.getEndTime())) {
//			System.out.println("���鿪ʼʱ��������ڻ������ʱ�䣬���ʧ��!");
			return 8;
		}
		/* �ж���Ҫ��ӵ��ճ̺��Ѵ��ڵ��ճ��Ƿ��г�ͻ */
		if (hasConflict(indexOfUser1, users, newAgenda))
			return 9;
		if (hasConflict(indexOfUser2, users, newAgenda))
			return 9;
		getAgendas().add(newAgenda);
		users.get(indexOfUser2).getAgendas().add(newAgenda);
		return 0;
	}

	/**
	 * ɾ���û��Ļ����б���ָ��ID�Ļ���
	 * 
	 * @param ID ɾ���Ļ���ID
	 * @return �Ƿ�ɾ���ɹ�
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
	 * �ж��»��鰲�������л��鰲���Ƿ��ͻ
	 *
	 * @param indexOfUser ����ѯ���û�
	 * @param users       �û��б�
	 * @param newAgenda   ����ӵ����ճ�
	 * @return �Ƿ��г�ͻ
	 */
	public boolean hasConflict(int indexOfUser, List<User> users, Agenda newAgenda) {
		for (int j = 0; j < users.get(indexOfUser).getAgendas().size(); j++) {
			if (newAgenda.getStartTime().after(users.get(indexOfUser).getAgendas().get(j).getEndTime())
					|| newAgenda.getEndTime().before(users.get(indexOfUser).getAgendas().get(j).getStartTime())) {
				/* ���������鲻��ͻ����������ʼʱ��������������Ľ���ʱ������ǽ���ʱ��������������Ŀ�ʼʱ�� */
				continue;
			} else {
				//System.out.println("����������ʱ���ͻ�����ʧ��!");
				return true;
			}
		}
		return false;
	}

}
