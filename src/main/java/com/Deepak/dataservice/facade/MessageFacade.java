package com.Deepak.dataservice.facade;

@FunctionalInterface
public interface MessageFacade {
	
	public Object process(Object object) throws Exception;

}
