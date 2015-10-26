package com.agrotime.rest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.agrotime.bo.TemperaturaDiariaBO;

public class MyApplicationBinder extends AbstractBinder{

	@Override
	protected void configure() {
		bind(TemperaturaDiariaBO.class).to(TemperaturaDiariaBO.class);
	}
	
}
