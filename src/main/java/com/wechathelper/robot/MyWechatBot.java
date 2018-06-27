package com.wechathelper.robot;

import com.wechathelper.model.AutoReplyMessage;
import com.wechathelper.model.TextMessageTask;
import com.wechathelper.repository.AutoReplyTextRepository;
import com.wechathelper.repository.TextMessageTaskRepository;
import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.AccountType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class MyWechatBot extends WeChatBot implements Job {
	
	private boolean auto_reply = false;

	@Autowired
	private TextMessageTaskRepository textMessageTaskRepository;

	@Autowired
	private AutoReplyTextRepository autoReplyTextRepository;


	private AutoReplyMessage autoReplyMessage;

	private TextMessageTask textMessageTask;



	public boolean isAuto_reply() {
		return auto_reply;
	}


	public void setAuto_reply(boolean auto_reply) {
		this.auto_reply = auto_reply;
	}


	public MyWechatBot(Config config) {
		super(config);
		// TODO Auto-generated constructor stub
		autoSendMessage();
	}
	
	
	@Bind(accountType = AccountType.TYPE_FRIEND)
	public void friendMessage(WeChatMessage message) {
	    if(StringUtils.isNotEmpty(message.getName())  && auto_reply){
			autoReplyMessage = autoReplyTextRepository.findByWechatId(this.session().getUserName());
	        this.sendMsg(message.getFromUserName(), "自动回复: " + autoReplyMessage.getMessage());
	    }
	}


	public void autoSendMessage(){
		try {

			List<TextMessageTask> listMessageTask = textMessageTaskRepository.findAllByWechatId(this.session().getUserName());
			if (listMessageTask.size() <= 0) return;

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();

			for (TextMessageTask textMessageTask : listMessageTask){
				JobDetail job = newJob(MyWechatBot.class)
						.withIdentity("AutoSendMessage", "MyWechatBot")
						.build();

				String triggerTask = getDayTrigger(textMessageTask.getTaskTimeMinute(),textMessageTask.getTaskTimeHour());
				Trigger trigger = newTrigger()
						.withIdentity("AutoSendMessageTrigger", "MyWechatBot")
						.withSchedule(cronSchedule(triggerTask))//"0 0/3 17-23 * * ?"
						.build();

				scheduler.scheduleJob(job, trigger);
			}

			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	/**
	 *  拼接成quartz的trigger任务
	 *  例如 0 0/30 20-23 ? * MON-WED,SAT
	 *  每周一，周二，周三，周六的晚上 20:00 到 23:00，每半小时执行一次的 CronTrigger
	 * */
	private String getDayTrigger(int minute, int hour){

		return String.format("0 %d %d * * ?",minute,hour);
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

	}
}
