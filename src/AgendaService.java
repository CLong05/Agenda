import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import Agenda.*;


/**
 * 表示层：用于显示数据和接收用户输入的信息，为用户提供交互式操作的界面
 */
public class AgendaService {
	/**
	 * 运行系统的main函数.
	 * 
	 * 首先显示问候语和命令格式，再读取命令并交给业务逻辑层进行处理.
	 *
	 * @param args 命令行获得的参数，但实际并没有用到这个参数
	 * @throws IOException 读写异常
	 */
	public static void main(String[] args) throws IOException {
		/* 显示问候语与系统交互的命令格式 */
		System.out.println("---------------------------------------------------------------");
		System.out.println("欢迎进入议程管理系统！");
		System.out.println("---------------------------------------------------------------");
		System.out.print("系统的交互命令格式如下，请根据需求输入命令:\n" + "> 注册新用户：register [userName] [password]\n"
				+ "> 添加新会议：add [userName] [password] [other] [start] [end] [title]\n"
				+ "> 查询会议：query [userName] [password] [start] [end]\n"
				+ "   （提示：时间请按\"year.month.day.hour:minute\"的格式进行输入,例如：2022.1.1.8:30）\n"
				+ "> 删除会议：delete [userName] [password] [meetingId]\n" + "> 清除用户会议安排：clear [userName] [password]\n"
				+ "> 批处理：batch [fileName]\n" + "> 退出系统：quit\n");
		System.out.println(" ");
		System.out.print("$ ");

		Scanner input = new Scanner(System.in);
		String[] commands = input.nextLine().split(" "); // 获取用户输入
		List<User> users = new ArrayList<>(); // 新建用户列表,作为user manager
		int count = 0; // 会议总数，用于生成ID
		while (!commands[0].equalsIgnoreCase("quit")) { // 判断是否为quit命令
			if (!commands[0].equalsIgnoreCase("batch")) { // 判断是否为batch命令
				/* 不是batch命令时 */
				Command c = instantiateCommand(commands);

				/* 处理异常命令 */
				if (c == null) {
					System.out.println(" ");
					System.out.print("$ ");
					input = new Scanner(System.in);
					commands = input.nextLine().split(" ");
					continue;
				} else {
					/* 检查命令格式是否正确 */
					if (!c.check(commands, users)) {
						System.out.println("命令格式错误，请重新输入!");
					} else {
						if (c instanceof Add) { // 若为增加会议，则count+1
							count += 1;
						}
						int res=c.exec(commands, users, count);
//						System.out.println(res);
						if(res==0&&commands[0].toLowerCase().equals("query")) {
							Query q = new Query();
							List<Agenda> resOfQuery = new ArrayList<>();;
							q.getResult(commands, users,resOfQuery);
							if(resOfQuery.size()==0) System.out.println("该用户暂无会议安排!");
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
				/* 执行batch命令 */
				int res = excuteBatchCommand(commands, users, count);
				if (res == -1) {
					System.out.println("batch命令执行失败!");
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
	 * 产生并返回命令对应的实例化对象
	 *
	 * @param commands 用户输入的命令
	 * @return 从Command接口实例化得到的对象
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
			System.out.println("不存在该命令，请重新输入!");
		}
		return null;
	}

	/**
	 * 执行batch命令，出于安全性的考虑，batch命令执行过程中不再支持batch命令和quit命令的执行
	 *
	 * @param commands 用户输入的命令
	 * @param users    用户列表
	 * @param count    目前会议的总数，用于生成ID
	 * @return 执行结果，如果成功返回count值，否则返回-1
	 * @throws IOException 读写异常
	 */
	static int excuteBatchCommand(String[] commands, List<User> users, int count) throws IOException {
		// FileReader m;
		BufferedReader reader = null;

		/* 读取文件 */
		try {
			// m = new FileReader(commands[1]);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(commands[1]), "UTF-8"));
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.out.println("错误：系统找不到指定的文件!");
			return -1;
		}

		/* 读取命令 */
		try {
			System.out.print("$ ");
			String nextLine;
			nextLine = reader.readLine(); // 一次读一行
			while (!(nextLine == null)) {
				System.out.println(nextLine);
				commands = nextLine.split(" ");
				Command c = instantiateCommand(commands); // 根据命令选择功能
				if (c == null) {
					System.out.println(" ");
					System.out.print("$ ");
					nextLine = reader.readLine();
					continue;
				}
				// 检查参数个数
				if (!c.check(commands, users)) {
					System.out.println("命令格式错误，请重新输入!");
				} else {
					if (c instanceof Add) {
						count += 1;
					}
					// 执行命令
					int res=c.exec(commands, users, count);
					if(res==0&&commands[0].toLowerCase().equals("query")) {
						Query q = new Query();
						List<Agenda> resOfQuery = new ArrayList<>();;
						q.getResult(commands, users,resOfQuery);
						if(resOfQuery.size()==0) System.out.println("该用户暂无会议安排!");
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
			System.out.println("错误：读写异常!");
			reader.close();
			return -1;
		}
		reader.close();
		return count;
	}
	
	static void outputResult(int res,String[] commands) {
		switch (res) {
		case 0:
			System.out.println("执行成功!");
			break;
		case 1:
			System.out.println("该用户不存在，请输入正确的用户名!");
			break;			
		case 2:
			System.out.println("受邀用户不存在，请输入正确的用户名!");
			break;			
		case 3:
			System.out.println("密码错误，请输入正确的密码!");
			break;			
		case 4:
			System.out.println("查询时段的开始时间必须早于结束时间，查询失败!");
			break;			
		case 5:
			System.out.println("该用户名已存在，注册失败!");
			break;
		case 6:
			System.out.println("执行失败!");
			break;
		case 7:
			System.out.println("非法删除其他用户的会议，删除失败!");
			break;
		case 8:
			System.out.println("会议开始时间必须早于会议结束时间，添加失败!");
			break;
		case 9:
			System.out.println("与其它会议时间冲突，添加失败!");
			break;
		default:
			System.out.println("执行失败!");
		}
	}
	
	/**
	 * 显示时间
	 * 
	 * @param date 打印的日期时间
	 */
	static void printTime(Calendar date) {
		SimpleDateFormat simpleformat = new SimpleDateFormat("HH:mm");
		System.out.print(date.get(Calendar.YEAR) + "." + date.get(Calendar.MONTH) + "." + date.get(Calendar.DATE) + " "
				+ simpleformat.format(date.getTime()));

	}
	
	/**
	 * 打印某一日程信息
	 */
	static public void printAgenda(Agenda resAgenda) {
		System.out.print("> 会议信息：");
		System.out.print(" 开始时间：");
		printTime(resAgenda.getStartTime());
		System.out.print("；结束时间：");
		printTime(resAgenda.getEndTime());
		System.out.println("；会议发起者：" + resAgenda.getUser1() + "；受邀者：" 
		+ resAgenda.getUser2() + "；会议标签：" + resAgenda.getTitle() + "；会议ID：" 
				+ resAgenda.getID());
//    	Calendar cal = Calendar.getInstance();
//        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
//        System.out.println("Today's date = "+simpleformat.format(this.startTime.getTime()));
	}
}
