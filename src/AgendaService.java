import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import Agenda.*;


/**
 * ��ʾ�㣺������ʾ���ݺͽ����û��������Ϣ��Ϊ�û��ṩ����ʽ�����Ľ���
 */
public class AgendaService {
	/**
	 * ����ϵͳ��main����.
	 * 
	 * ������ʾ�ʺ���������ʽ���ٶ�ȡ�������ҵ���߼�����д���.
	 *
	 * @param args �����л�õĲ�������ʵ�ʲ�û���õ��������
	 * @throws IOException ��д�쳣
	 */
	public static void main(String[] args) throws IOException {
		/* ��ʾ�ʺ�����ϵͳ�����������ʽ */
		System.out.println("---------------------------------------------------------------");
		System.out.println("��ӭ������̹���ϵͳ��");
		System.out.println("---------------------------------------------------------------");
		System.out.print("ϵͳ�Ľ��������ʽ���£������������������:\n" + "> ע�����û���register [userName] [password]\n"
				+ "> ����»��飺add [userName] [password] [other] [start] [end] [title]\n"
				+ "> ��ѯ���飺query [userName] [password] [start] [end]\n"
				+ "   ����ʾ��ʱ���밴\"year.month.day.hour:minute\"�ĸ�ʽ��������,���磺2022.1.1.8:30��\n"
				+ "> ɾ�����飺delete [userName] [password] [meetingId]\n" + "> ����û����鰲�ţ�clear [userName] [password]\n"
				+ "> ������batch [fileName]\n" + "> �˳�ϵͳ��quit\n");
		System.out.println(" ");
		System.out.print("$ ");

		Scanner input = new Scanner(System.in);
		String[] commands = input.nextLine().split(" "); // ��ȡ�û�����
		List<User> users = new ArrayList<>(); // �½��û��б�,��Ϊuser manager
		int count = 0; // ������������������ID
		while (!commands[0].equalsIgnoreCase("quit")) { // �ж��Ƿ�Ϊquit����
			if (!commands[0].equalsIgnoreCase("batch")) { // �ж��Ƿ�Ϊbatch����
				/* ����batch����ʱ */
				Command c = instantiateCommand(commands);

				/* �����쳣���� */
				if (c == null) {
					System.out.println(" ");
					System.out.print("$ ");
					input = new Scanner(System.in);
					commands = input.nextLine().split(" ");
					continue;
				} else {
					/* ��������ʽ�Ƿ���ȷ */
					if (!c.check(commands, users)) {
						System.out.println("�����ʽ��������������!");
					} else {
						if (c instanceof Add) { // ��Ϊ���ӻ��飬��count+1
							count += 1;
						}
						int res=c.exec(commands, users, count);
//						System.out.println(res);
						if(res==0&&commands[0].toLowerCase().equals("query")) {
							Query q = new Query();
							List<Agenda> resOfQuery = new ArrayList<>();;
							q.getResult(commands, users,resOfQuery);
							if(resOfQuery.size()==0) System.out.println("���û����޻��鰲��!");
							else {
								for(int i=0;i<resOfQuery.size();i++) {
									printAgenda(resOfQuery.get(i));
								}
							}
						}
						outputResult(res,commands);
					}
					System.out.println(" ");
					System.out.print("$ ");

					input = new Scanner(System.in);
					commands = input.nextLine().split(" ");

				}
			} else {
				/* ִ��batch���� */
				int res = excuteBatchCommand(commands, users, count);
				if (res == -1) {
					System.out.println("batch����ִ��ʧ��!");
					System.out.println(" ");
					System.out.print("$ ");
				}
				count = res;
				commands = input.nextLine().split(" ");
			}
		}
		input.close();
	}

	/**
	 * ���������������Ӧ��ʵ��������
	 *
	 * @param commands �û����������
	 * @return ��Command�ӿ�ʵ�����õ��Ķ���
	 */
	static Command instantiateCommand(String[] commands) {
		switch (commands[0].toLowerCase()) {
		case "register":
			return new Register();
		case "add":
			return new Add();
		case "query":
			return new Query();
		case "delete":
			return new Delete();
		case "clear":
			return new Clear();
		default:
			System.out.println("�����ڸ��������������!");
		}
		return null;
	}

	/**
	 * ִ��batch������ڰ�ȫ�ԵĿ��ǣ�batch����ִ�й����в���֧��batch�����quit�����ִ��
	 *
	 * @param commands �û����������
	 * @param users    �û��б�
	 * @param count    Ŀǰ�������������������ID
	 * @return ִ�н��������ɹ�����countֵ�����򷵻�-1
	 * @throws IOException ��д�쳣
	 */
	static int excuteBatchCommand(String[] commands, List<User> users, int count) throws IOException {
		// FileReader m;
		BufferedReader reader = null;

		/* ��ȡ�ļ� */
		try {
			// m = new FileReader(commands[1]);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(commands[1]), "UTF-8"));
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.out.println("����ϵͳ�Ҳ���ָ�����ļ�!");
			return -1;
		}

		/* ��ȡ���� */
		try {
			System.out.print("$ ");
			String nextLine;
			nextLine = reader.readLine(); // һ�ζ�һ��
			while (!(nextLine == null)) {
				System.out.println(nextLine);
				commands = nextLine.split(" ");
				Command c = instantiateCommand(commands); // ��������ѡ����
				if (c == null) {
					System.out.println(" ");
					System.out.print("$ ");
					nextLine = reader.readLine();
					continue;
				}
				// ����������
				if (!c.check(commands, users)) {
					System.out.println("�����ʽ��������������!");
				} else {
					if (c instanceof Add) {
						count += 1;
					}
					// ִ������
					int res=c.exec(commands, users, count);
					if(res==0&&commands[0].toLowerCase().equals("query")) {
						Query q = new Query();
						List<Agenda> resOfQuery = new ArrayList<>();;
						q.getResult(commands, users,resOfQuery);
						if(resOfQuery.size()==0) System.out.println("���û����޻��鰲��!");
						else {
							for(int i=0;i<resOfQuery.size();i++) {
								printAgenda(resOfQuery.get(i));
							}
						}
					}
					outputResult(res,commands);
				}
				System.out.println(" ");
				System.out.print("$ ");
				nextLine = reader.readLine();
			}
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("���󣺶�д�쳣!");
			reader.close();
			return -1;
		}
		reader.close();
		return count;
	}
	
	static void outputResult(int res,String[] commands) {
		switch (res) {
		case 0:
			System.out.println("ִ�гɹ�!");
			break;
		case 1:
			System.out.println("���û������ڣ���������ȷ���û���!");
			break;			
		case 2:
			System.out.println("�����û������ڣ���������ȷ���û���!");
			break;			
		case 3:
			System.out.println("���������������ȷ������!");
			break;			
		case 4:
			System.out.println("��ѯʱ�εĿ�ʼʱ��������ڽ���ʱ�䣬��ѯʧ��!");
			break;			
		case 5:
			System.out.println("���û����Ѵ��ڣ�ע��ʧ��!");
			break;
		case 6:
			System.out.println("ִ��ʧ��!");
			break;
		case 7:
			System.out.println("�Ƿ�ɾ�������û��Ļ��飬ɾ��ʧ��!");
			break;
		case 8:
			System.out.println("���鿪ʼʱ��������ڻ������ʱ�䣬���ʧ��!");
			break;
		case 9:
			System.out.println("����������ʱ���ͻ�����ʧ��!");
			break;
		default:
			System.out.println("ִ��ʧ��!");
		}
	}
	
	/**
	 * ��ʾʱ��
	 * 
	 * @param date ��ӡ������ʱ��
	 */
	static void printTime(Calendar date) {
		SimpleDateFormat simpleformat = new SimpleDateFormat("HH:mm");
		System.out.print(date.get(Calendar.YEAR) + "." + date.get(Calendar.MONTH) + "." + date.get(Calendar.DATE) + " "
				+ simpleformat.format(date.getTime()));

	}
	
	/**
	 * ��ӡĳһ�ճ���Ϣ
	 */
	static public void printAgenda(Agenda resAgenda) {
		System.out.print("> ������Ϣ��");
		System.out.print(" ��ʼʱ�䣺");
		printTime(resAgenda.getStartTime());
		System.out.print("������ʱ�䣺");
		printTime(resAgenda.getEndTime());
		System.out.println("�����鷢���ߣ�" + resAgenda.getUser1() + "�������ߣ�" 
		+ resAgenda.getUser2() + "�������ǩ��" + resAgenda.getTitle() + "������ID��" 
				+ resAgenda.getID());
//    	Calendar cal = Calendar.getInstance();
//        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
//        System.out.println("Today's date = "+simpleformat.format(this.startTime.getTime()));
	}
}
