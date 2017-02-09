package cu.uci.abcd.opac;

import cu.uci.abcd.opac.impl.OpacEmailConfigServiceImpl;

public class EmailConfigService {

	private String smtpHost;
	private String starttlsRequired;
	private String starttlsEnable;
	private String sslEnable;
	private String sslTrust;
	private String socketFactoryPort;
	private String port;
	private String mailSender;
	private String userEmail;
	private String passwordEmail;
	private String auth;
	private String transportProtocol;
	private String email;   

	public EmailConfigService(OpacEmailConfigServiceImpl opacEmailConfigServiceImpl) {
		smtpHost = opacEmailConfigServiceImpl.getSmtpHost();
		starttlsRequired = opacEmailConfigServiceImpl.getStarttlsRequired();
		starttlsEnable = opacEmailConfigServiceImpl.getStarttlsEnable();
		sslEnable = opacEmailConfigServiceImpl.getSslEnable();
		sslTrust = opacEmailConfigServiceImpl.getSslTrust();
		socketFactoryPort = opacEmailConfigServiceImpl.getSocketFactoryPort();
		port = opacEmailConfigServiceImpl.getPort();
		mailSender = opacEmailConfigServiceImpl.getMailSender();
		userEmail = opacEmailConfigServiceImpl.getUserEmail();
		passwordEmail = opacEmailConfigServiceImpl.getPasswordEmail();
		auth = opacEmailConfigServiceImpl.getAuth();
		transportProtocol = opacEmailConfigServiceImpl.getTransportProtocol();
		email = opacEmailConfigServiceImpl.getEmail();
	}

	public String getSmtpHost() {

		return smtpHost;
	}

	public String getStarttlsRequired() {
		return starttlsRequired;
	}

	public String getStarttlsEnable() {
		return starttlsEnable;
	}

	public String getSslEnable() {
		return sslEnable;
	}

	public String getSslTrust() {
		return sslTrust;
	}

	public String getSocketFactoryPort() {
		return socketFactoryPort;
	}

	public String getPort() {
		return port;
	}

	public String getMailSender() {
		return mailSender;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getPasswordEmail() {
		return passwordEmail;
	}

	public String getAuth() {
		return auth;
	}

	public String getTransportProtocol() {
		return transportProtocol;
	}

	public String getEmail() {
		return email;
	}

}
