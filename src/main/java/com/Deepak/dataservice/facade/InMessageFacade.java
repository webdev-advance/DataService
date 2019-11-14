package com.Deepak.dataservice.facade;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class InMessageFacade implements MessageFacade{
	
	@Value("${inbound.data.queue}")
	private String inMessageQueue;
	
	
	@Autowired
	private AmqpTemplate template;
	
	
	public Object process(Object obj) {
		try {
			if(obj!=null) {
				template.convertAndSend(inMessageQueue,obj);
			}
		}catch(Exception e) {
			log.error("Error Occured"+e.getMessage());
		}
		
		return null;
	}

}
