package com.oasis.firebird.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.oasis.firebird.communication.CommunicationInterface;
import com.oasis.firebird.storage.DaoFactory;

@Deprecated
public abstract class AbstractController {

	private ExecutorService executorService;
	private CommunicationInterface communication;
	private DaoFactory daoFactory;

	public AbstractController(CommunicationInterface communication, DaoFactory daoFactory) {

		executorService = Executors.newSingleThreadExecutor();
		this.communication = communication;
		this.daoFactory = daoFactory;

	}

	protected ExecutorService getExecutorService() {
		return executorService;
	}

	protected CommunicationInterface getCommunication() {
		return communication;
	}

	protected DaoFactory getDaoFactory() {
		return daoFactory;
	}

}
