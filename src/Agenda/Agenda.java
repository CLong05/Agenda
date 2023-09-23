package Agenda;

import java.util.Calendar;
//import java.util.Random;

/**
 * ���ݷ��ʲ�ṹ2��Agenda ���ڴ洢��¼������Ϣ
 */
public class Agenda {
	/**
	 * ���鴴���ߵ��û�����Ϣ
	 */
	private final String user1;
	/**
	 * ���������ߵ��û�����Ϣ
	 */
	private final String user2;
	/**
	 * ���鿪ʼʱ��
	 */
	private final Calendar startTime = Calendar.getInstance();
	/**
	 * �������ʱ��
	 */
	private final Calendar endTime = Calendar.getInstance();
	/**
	 * �����ǩ��Ϣ
	 */
	private final String title;
	/**
	 * ����ID��Ϣ
	 */
	private final int ID;

	/**
	 * ʵ����һ���ճ̵Ĺ��췽����ʹ�ô����ID ���ڻ�����������ӻ���ʱʹ��
	 *
	 * @param user1     ��ǰ�û��û���
	 * @param user2     �������û��û���
	 * @param startTime ��ʼʱ��
	 * @param endTime   ����ʱ��
	 * @param title     ��ǩ
	 * @param ID        ��Ҫ���õ�ID
	 */
	public Agenda(String user1, String user2, String[] startTime, String[] endTime, String title, int ID) {
		this.user1 = user1;
		this.user2 = user2;
		this.startTime.set(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]),
				Integer.parseInt(startTime[2]), Integer.parseInt(startTime[3]), Integer.parseInt(startTime[4]));
		this.endTime.set(Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]), Integer.parseInt(endTime[2]),
				Integer.parseInt(endTime[3]), Integer.parseInt(endTime[4]));
		this.title = title;
		this.ID = ID;
	}
	
	/**
	 * ��ȡ�����ǩ
	 *
	 * @return �����ǩ
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * ��ȡ���鴴�����û���
	 *
	 * @return �������û���
	 */
	public String getUser1() {
		return user1;
	}

	/**
	 * ��ȡ���������û���
	 *
	 * @return ���������û���
	 */
	public String getUser2() {
		return user2;
	}

	/**
	 * ��ȡ����ID
	 *
	 * @return ����ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * ��ȡ���鿪ʼʱ��
	 *
	 * @return ��ʼʱ��
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * ��ȡ�������ʱ��
	 *
	 * @return ����ʱ��
	 */
	public Calendar getEndTime() {
		return endTime;
	}

}
