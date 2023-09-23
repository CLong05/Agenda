package Agenda;

import java.util.List;

/**
 * ҵ���߼���ӿڣ����ݾ�����������ʵ����Ϊ��ͬ�������࣬�Ӷ�ʵ�ֲ�ͬ�Ĺ���
 */
public interface Command {
	/**
	 * ��������ʽ�Ƿ���ȷ
	 *
	 * @param command ���������
	 * @param users   �û��б�
	 * @return ���������Ƿ�Ϸ�
	 */
	boolean check(String[] command, List<User> users);

	/**
	 * ִ�кϷ�����
	 *
	 * @param command ���������
	 * @param users   �û��б�
	 * @param count	  Ŀǰ�������������������ID
	 */
	int exec(String[] command, List<User> users, int count);
}
