package cu.uci.abcd.opac.impl;

public class OpacEmailConfigServiceImpl {

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

	public String getSmtpHost() {

		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getStarttlsRequired() {
		return starttlsRequired;
	}

	public void setStarttlsRequired(String starttlsRequired) {
		this.starttlsRequired = starttlsRequired;
	}

	public String getStarttlsEnable() {
		return starttlsEnable;
	}

	public void setStarttlsEnable(String starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}

	public String getSslEnable() {
		return sslEnable;
	}

	public void setSslEnable(String sslEnable) {
		this.sslEnable = sslEnable;
	}

	public String getSslTrust() {
		return sslTrust;
	}

	public void setSslTrust(String sslTrust) {
		this.sslTrust = sslTrust;
	}

	public String getSocketFactoryPort() {
		return socketFactoryPort;
	}

	public void setSocketFactoryPort(String socketFactoryPort) {
		this.socketFactoryPort = socketFactoryPort;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getMailSender() {
		return mailSender;
	}

	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPasswordEmail() {
		return passwordEmail;
	}

	public void setPasswordEmail(String passwordEmail) {
		this.passwordEmail = passwordEmail;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}   

	public String getTransportProtocol() {
		return transportProtocol;
	}

	public void setTransportProtocol(String transportProtocol) {
		this.transportProtocol = transportProtocol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
