package Agenda;

import java.util.Calendar;
//import java.util.Random;

/**
 * 数据访问层结构2：Agenda 用于存储记录会议信息
 */
public class Agenda {
	/**
	 * 会议创建者的用户名信息
	 */
	private final String user1;
	/**
	 * 会议受邀者的用户名信息
	 */
	private final String user2;
	/**
	 * 会议开始时间
	 */
	private final Calendar startTime = Calendar.getInstance();
	/**
	 * 会议结束时间
	 */
	private final Calendar endTime = Calendar.getInstance();
	/**
	 * 会议标签信息
	 */
	private final String title;
	/**
	 * 会议ID信息
	 */
	private final int ID;

	/**
	 * 实例化一个日程的构造方法，使用传入的ID 用于会议受邀者添加会议时使用
	 *
	 * @param user1     当前用户用户名
	 * @param user2     被邀请用户用户名
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param title     标签
	 * @param ID        想要设置的ID
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
	 * 获取会议标签
	 *
	 * @return 会议标签
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 获取会议创建者用户名
	 *
	 * @return 创建者用户名
	 */
	public String getUser1() {
		return user1;
	}

	/**
	 * 获取被邀请者用户名
	 *
	 * @return 被邀请者用户名
	 */
	public String getUser2() {
		return user2;
	}

	/**
	 * 获取会议ID
	 *
	 * @return 会议ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * 获取会议开始时间
	 *
	 * @return 开始时间
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * 获取会议结束时间
	 *
	 * @return 结束时间
	 */
	public Calendar getEndTime() {
		return endTime;
	}

}
